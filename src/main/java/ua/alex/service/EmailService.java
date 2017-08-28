package ua.alex.service;

public class EmailService {
    private String protocol;

    private int port;

    public void sendEmail(String content) {
        System.out.println("Send email with content:" + content);
    }

    @Override
    public String toString() {
        return "EmailService{" +
                "protocol='" + protocol + '\'' +
                ", port=" + port +
                '}';
    }

    public void setProtocol(String protocol) {
        System.out.println("Setter protocol");
        this.protocol = protocol;
    }

    public void setPort(int port) {
        System.out.println("Setter port");
        this.port = port;
    }
}
