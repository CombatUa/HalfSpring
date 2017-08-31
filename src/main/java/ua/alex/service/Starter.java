package ua.alex.service;

import ua.alex.ioc.core.ApplicationContext;
import ua.alex.ioc.core.GenericApplicationContext;

public class Starter {
    public static void main(String[] args) {
        try {
            ApplicationContext context = new GenericApplicationContext("src/main/resources/xml-config.xml");
            PaymentService paymentService =  context.getBean(PaymentService.class);
           paymentService.createPayment(1234888);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
