package ru.billingProject.controller;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.billingProject.model.*;

import java.io.*;

public class MainController {

    public static class DocumentRow {
        private final Document document;
        private final BooleanProperty selected = new SimpleBooleanProperty(false);

        public DocumentRow(Document document) {
            this.document = document;
        }

        public Document getDocument() {
            return document;
        }

        public BooleanProperty selectedProperty() {
            return selected;
        }

        public boolean isSelected() {
            return selected.get();
        }
    }

    private final ObservableList<DocumentRow> documentRows = FXCollections.observableArrayList();

    public ObservableList<DocumentRow> getDocumentRows() {
        return documentRows;
    }

    public void addDocument(Document doc) {
        documentRows.add(new DocumentRow(doc));
    }

    public void saveDocument(Document doc, File file) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(doc.serialize());
        }
    }

    public Document loadDocument(File file) throws Exception {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String type = reader.readLine();
            switch (type) {
                case "Invoice": return Invoice.deserialize(reader);
                case "Payment": return Payment.deserialize(reader);
                case "PaymentRequest": return PaymentRequest.deserialize(reader);
            }
        }
        return null;
    }
}
