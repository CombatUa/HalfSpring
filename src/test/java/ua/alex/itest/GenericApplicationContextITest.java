package ua.alex.itest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.alex.domain.PaymentService;
import ua.alex.ioc.context.ApplicationContext;
import ua.alex.ioc.context.GenericApplicationContext;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("Integration Test")
 class GenericApplicationContextITest {
    Logger log = LoggerFactory.getLogger(GenericApplicationContextITest.class);

    @Test
      void start() {
        try {
            ApplicationContext context = new GenericApplicationContext("src/main/resources/xml-config.xml");
            PaymentService paymentService = context.getBean(PaymentService.class);
            log.info("get bean names{}",context.getBeanNames());
            assertAll(PaymentService.class.getSimpleName(),()->assertNotNull(paymentService.getEmailService()));
            paymentService.createPayment(1234888);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
