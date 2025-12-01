package ru.billingProject.model;

import java.io.BufferedReader;
import java.time.LocalDate;

public class Payment extends Document{

    private double sum;
    private String employee;

    public Payment(String number, LocalDate date, String user, double sum, String employee){
        super(number, date, user);
        this.sum = sum;
        this.employee = employee;
    }


    @Override
    public String getDisplayName() {
        return "Платежка от " + date + " номер " + number;
    }

    @Override
    public String getDetails() {
        return "Номер: " + number + "\nДата: " + date + "\nПользователь: " + user +
                "\nСумма: " + sum + "\nСотрудник: " + employee;
    }
    @Override
    public String serialize() {
        return "Payment\n" +
                number + "\n" +
                date + "\n" +
                user + "\n" +
                sum + "\n" +
                employee + "\n";
    }
    public static Payment deserialize(BufferedReader reader) throws Exception {
        String number = reader.readLine();
        LocalDate date = LocalDate.parse(reader.readLine());
        String user = reader.readLine();
        double sum = Double.parseDouble(reader.readLine());
        String employee = reader.readLine();
        return new Payment(number, date, user, sum, employee);
    }
}
