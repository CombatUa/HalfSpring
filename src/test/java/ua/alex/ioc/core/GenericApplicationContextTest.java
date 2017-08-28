package ua.alex.ioc.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GenericApplicationContextTest {
    @Test
    void getBean() {
        try {
            GenericApplicationContext context = new GenericApplicationContext("src/main/resources/xml-config.xml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}