package ua.alex.service;

public class PaymentService {
    private EmailService emailService;

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

    @Override
    public String toString() {
        return "PaymentService{" +
                "emailService=" + emailService +
                ", paymentType='" + paymentType + '\'' +
                '}';
    }
}
