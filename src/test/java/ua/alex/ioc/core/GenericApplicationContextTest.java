package ua.alex.ioc.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import ua.alex.service.*;

class GenericApplicationContextTest {
    @Test
    void getBean() {
        try {
            GenericApplicationContext context = new GenericApplicationContext("src/test/resources/xml-config.xml");
            SampleServiceTest sampleServiceTest = context.getBean(SampleServiceTest.class);
            assertAll(SampleServiceTest.class.getSimpleName(),
                    () -> assertEquals('G', sampleServiceTest.getAChar()),
                    () -> assertEquals(2, sampleServiceTest.getAByte()),
                    () -> assertEquals(3, sampleServiceTest.getAShort()),
                    () -> assertEquals(4, sampleServiceTest.getAnInt()),
                    () -> assertEquals(5, sampleServiceTest.getALong()),
                    () -> assertEquals(5.2f, sampleServiceTest.getAFloat()),
                    () -> assertEquals(6.2, sampleServiceTest.getADouble()),
                    () -> assertEquals("POP3", sampleServiceTest.getString())
            );

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}