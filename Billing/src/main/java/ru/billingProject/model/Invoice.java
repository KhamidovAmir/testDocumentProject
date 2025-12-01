package ru.billingProject.model;

import java.io.BufferedReader;
import java.time.LocalDate;

public class Invoice extends Document{

    private double sum;
    private String currency;
    private double currencyRate;
    private String product;
    private double quantity;

    public Invoice(String number, LocalDate date, String user, double sum, String currency, double currencyRate, String product, double quantity){
        super(number, date, user);
        this.sum = sum;
        this.currency = currency;
        this.currencyRate = currencyRate;
        this.product = product;
        this.quantity = quantity;
    }

    @Override
    public String getDisplayName() {
        return "Накладная от " + date + " номер " + number;
    }

    @Override
    public String getDetails() {
        return "Номер: " + number + "\nДата: " + date + "\nПользователь: " + user +
                "\nСумма: " + sum + "\nВалюта: " + currency + "\nКурс: " + currencyRate +
                "\nТовар: " + product + "\nКоличество: " + quantity;
    }

    @Override
    public String serialize() {
        return "Invoice\n" +
                number + "\n" +
                date + "\n" +
                user + "\n" +
                sum + "\n" +
                currency + "\n" +
                currencyRate + "\n" +
                product + "\n" +
                quantity + "\n";
    }
    public static Invoice deserialize(BufferedReader reader) throws Exception {
        String number = reader.readLine();
        LocalDate date = LocalDate.parse(reader.readLine());
        String user = reader.readLine();
        double sum = Double.parseDouble(reader.readLine());
        String currency = reader.readLine();
        double currencyRate = Double.parseDouble(reader.readLine());
        String product = reader.readLine();
        double quantity = Double.parseDouble(reader.readLine());
        return new Invoice(number, date, user, sum, currency, currencyRate, product, quantity);
    }


}
