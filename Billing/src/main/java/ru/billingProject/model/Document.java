package ru.billingProject.model;

import java.time.LocalDate;

public abstract class Document {
    protected String number;
    protected LocalDate date;
    protected String user;

    public Document(String number, LocalDate date, String user){
        this.number = number;
        this.date = date;
        this.user = user;
    }
    public abstract String getDisplayName();
    public abstract String getDetails();

    public abstract String serialize();
}
