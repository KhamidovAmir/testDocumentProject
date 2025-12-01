package ru.billingProject;

import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ru.billingProject.controller.MainController;
import ru.billingProject.model.*;
import ru.billingProject.view.DocumentDialog;

import java.io.File;

public class App extends Application {

    private final MainController controller = new MainController();

    @Override
    public void start(Stage stage) {

        TableView<MainController.DocumentRow> tableView = new TableView<>();

        TableColumn<MainController.DocumentRow, Boolean> selectCol = new TableColumn<>("Выбрать");
        selectCol.setCellValueFactory(cellData -> cellData.getValue().selectedProperty());
        selectCol.setCellFactory(tc -> new CheckBoxTableCell<>());
        selectCol.setEditable(true);
        tableView.setEditable(true);
        
        TableColumn<MainController.DocumentRow, String> nameCol = new TableColumn<>("Документ");
        nameCol.setCellValueFactory(param -> new javafx.beans.property.SimpleObjectProperty<>(param.getValue().getDocument().getDisplayName()));

        tableView.getColumns().addAll(selectCol, nameCol);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.setItems(controller.getDocumentRows());

        // Кнопки создания
        Button createInvoiceBtn = new Button("Создать накладную");
        createInvoiceBtn.setOnAction(e -> addDocument("Накладная"));

        Button createPaymentBtn = new Button("Создать платёжку");
        createPaymentBtn.setOnAction(e -> addDocument("Платёжка"));

        Button createRequestBtn = new Button("Создать заявку");
        createRequestBtn.setOnAction(e -> addDocument("Заявка"));

        // Просмотр выделенного документа
        Button viewBtn = new Button("Просмотр");
        viewBtn.setOnAction(e -> {
            MainController.DocumentRow selectedRow = tableView.getSelectionModel().getSelectedItem();
            if (selectedRow != null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Просмотр документа");
                alert.setHeaderText(selectedRow.getDocument().getDisplayName());
                alert.setContentText(selectedRow.getDocument().getDetails());
                alert.showAndWait();
            }
        });

        // Удаление выбранных чекбоксами
        Button deleteSelectedBtn = new Button("Удалить выбранные");
        deleteSelectedBtn.setOnAction(e -> controller.getDocumentRows().removeIf(MainController.DocumentRow::isSelected));

        // Сохранение выделенного документа
        Button saveBtn = new Button("Сохранить");
        saveBtn.setOnAction(e -> {
            MainController.DocumentRow selectedRow = tableView.getSelectionModel().getSelectedItem();
            if (selectedRow != null) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Сохранить документ");
                File file = fileChooser.showSaveDialog(stage);
                if (file != null) {
                    try {
                        controller.saveDocument(selectedRow.getDocument(), file);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        // Загрузка документа из файла
        Button loadBtn = new Button("Загрузить");
        loadBtn.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Загрузить документ");
            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                try {
                    Document doc = controller.loadDocument(file);
                    if (doc != null) controller.addDocument(doc);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        HBox buttons = new HBox(10, createInvoiceBtn, createPaymentBtn, createRequestBtn, viewBtn, saveBtn, loadBtn, deleteSelectedBtn);
        VBox root = new VBox(10, tableView, buttons);
        VBox.setVgrow(tableView, Priority.ALWAYS);
        root.setPadding(new javafx.geometry.Insets(10));

        Scene scene = new Scene(root, 800, 500);
        stage.setScene(scene);
        stage.setTitle("Документы");
        stage.show();
    }

    private void addDocument(String type) {
        Document doc = DocumentDialog.showDialog(type);
        if (doc != null) {
            controller.addDocument(doc);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
