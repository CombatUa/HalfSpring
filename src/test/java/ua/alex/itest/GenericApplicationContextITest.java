package ua.alex.itest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ua.alex.domain.PaymentService;
import ua.alex.ioc.context.ApplicationContext;
import ua.alex.ioc.context.GenericApplicationContext;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("Integration Test")
 class GenericApplicationContextITest {
    @Test
      void start() {
        try {
            ApplicationContext context = new GenericApplicationContext("src/main/resources/xml-config.xml");
            PaymentService paymentService = context.getBean(PaymentService.class);
            assertAll(PaymentService.class.getSimpleName(),()->assertNotNull(paymentService));
            paymentService.createPayment(1234888);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
