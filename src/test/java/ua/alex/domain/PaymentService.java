package ua.alex.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PaymentService {
    private EmailService emailService;
    private final Logger log = LoggerFactory.getLogger(getClass());

    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    private String paymentType;

    public void createPayment(int amount) {
        emailService.sendEmail("Create payment with amount " + amount);
    }

    public EmailService getEmailService() {
        return emailService;
    }

    @Override
    public String toString() {
        return "PaymentService{" +
                "emailService=" + emailService +
                ", paymentType='" + paymentType + '\'' +
                '}';
    }
}
