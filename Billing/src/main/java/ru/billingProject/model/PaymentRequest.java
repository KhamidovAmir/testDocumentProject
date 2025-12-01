package ru.billingProject.model;

import java.io.BufferedReader;
import java.time.LocalDate;

public class PaymentRequest extends Document{

    private String contractor;
    private double sum;
    private String currency;
    private double currencyRate;
    private double commission;


    public PaymentRequest(String number, LocalDate date, String user,
                          String contractor, double sum, String currency,
                          double currencyRate, double commission) {
        super(number, date, user);
        this.contractor = contractor;
        this.sum = sum;
        this.currency = currency;
        this.currencyRate = currencyRate;
        this.commission = commission;
    }

    @Override
    public String getDisplayName() {
        return "Заявка на оплату от " + date + " номер " + number;
    }

    @Override
    public String getDetails() {
        return "Номер: " + number + "\nДата: " + date + "\nПользователь: " + user +
                "\nСумма: " + sum + "\nВалюта: " + currency + "\nКурс: " + currencyRate +
                "\nКонтрагент: " + contractor + "\nКомиссия: " + commission;
    }

    @Override
    public String serialize() {
        return "PaymentRequest\n" +
                number + "\n" +
                date + "\n" +
                user + "\n" +
                contractor + "\n" +
                sum + "\n" +
                currency + "\n" +
                currencyRate + "\n" +
                commission + "\n";
    }
    public static PaymentRequest deserialize(BufferedReader reader) throws Exception {
        String number = reader.readLine();
        LocalDate date = LocalDate.parse(reader.readLine());
        String user = reader.readLine();
        String contractor = reader.readLine();
        double sum = Double.parseDouble(reader.readLine());
        String currency = reader.readLine();
        double currencyRate = Double.parseDouble(reader.readLine());
        double commission = Double.parseDouble(reader.readLine());
        return new PaymentRequest(number, date, user, contractor, sum, currency, currencyRate, commission);
    }
}
