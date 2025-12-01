package ru.billingProject.view;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import ru.billingProject.model.Document;
import ru.billingProject.model.Invoice;
import ru.billingProject.model.Payment;
import ru.billingProject.model.PaymentRequest;

import java.time.LocalDate;

public class DocumentDialog {

    public static Document showDialog(String type) {
        Dialog<Document> dialog = new Dialog<>();
        dialog.setTitle("Создать " + type);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField numberField = new TextField();
        DatePicker datePicker = new DatePicker(LocalDate.now());
        TextField userField = new TextField();

        grid.addRow(0, new Label("Номер:"), numberField);
        grid.addRow(1, new Label("Дата:"), datePicker);
        grid.addRow(2, new Label("Пользователь:"), userField);

        TextField extra1 = new TextField();
        TextField extra2 = new TextField();
        TextField extra3 = new TextField();
        TextField extra4 = new TextField();
        TextField extra5 = new TextField();

        switch (type) {
            case "Накладная":
                grid.addRow(3, new Label("Сумма:"), extra1);
                grid.addRow(4, new Label("Валюта:"), extra2);
                grid.addRow(5, new Label("Курс валюты:"), extra3);
                grid.addRow(6, new Label("Товар:"), extra4);
                grid.addRow(7, new Label("Количество:"), extra5);
                break;
            case "Платёжка":
                grid.addRow(3, new Label("Сумма:"), extra1);
                grid.addRow(4, new Label("Сотрудник:"), extra2);
                break;
            case "Заявка":
                grid.addRow(3, new Label("Контрагент:"), extra1);
                grid.addRow(4, new Label("Сумма:"), extra2);
                grid.addRow(5, new Label("Валюта:"), extra3);
                grid.addRow(6, new Label("Курс валюты:"), extra4);
                grid.addRow(7, new Label("Комиссия:"), extra5);
                break;
        }

        dialog.getDialogPane().setContent(grid);

        // Возврат результата
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                try {
                    String number = numberField.getText();
                    LocalDate date = datePicker.getValue();
                    String user = userField.getText();

                    switch (type) {
                        case "Накладная":
                            return new Invoice(
                                    number,
                                    date,
                                    user,
                                    Double.parseDouble(extra1.getText()),
                                    extra2.getText(),
                                    Double.parseDouble(extra3.getText()),
                                    extra4.getText(),
                                    Double.parseDouble(extra5.getText())
                            );
                        case "Платёжка":
                            return new Payment(
                                    number,
                                    date,
                                    user,
                                    Double.parseDouble(extra1.getText()),
                                    extra2.getText()
                            );
                        case "Заявка":
                            return new PaymentRequest(
                                    number,
                                    date,
                                    user,
                                    extra1.getText(),
                                    Double.parseDouble(extra2.getText()),
                                    extra3.getText(),
                                    Double.parseDouble(extra4.getText()),
                                    Double.parseDouble(extra5.getText())
                            );
                    }
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Ошибка ввода данных");
                    alert.showAndWait();
                    return null;
                }
            }
            return null;
        });

        return dialog.showAndWait().orElse(null);
    }
}
