package ua.alex.itest;

import org.junit.jupiter.api.Test;
import ua.alex.domain.PaymentService;
import ua.alex.ioc.context.ApplicationContext;
import ua.alex.ioc.context.GenericApplicationContext;

public class GenericApplicationContextITest {
    @Test
    public  void start() {
        try {
            ApplicationContext context = new GenericApplicationContext("src/main/resources/xml-config.xml");
            PaymentService paymentService = context.getBean(PaymentService.class);
            paymentService.createPayment(1234888);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
