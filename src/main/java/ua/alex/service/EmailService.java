package ua.alex.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmailService {
    private static final Logger log = LoggerFactory.getLogger(EmailService.class);
    private String protocol;

    private int port;

    public void sendEmail(String content) {
        log.info("Send email with content:{}",content);
    }

    @Override
    public String toString() {
        return "EmailService{" +
                "protocol='" + protocol + '\'' +
                ", port=" + port +
                '}';
    }

    public void setProtocol(String protocol) {
       log.debug("Set protocol:{}",protocol);
        this.protocol = protocol;
    }

    public void setPort(int port) {
        log.debug("Set port:{}",port);
        this.port = port;
    }
}
