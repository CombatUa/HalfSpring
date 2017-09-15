package ua.alex.ioc.context;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import ua.alex.ioc.service.*;

class GenericApplicationContextTest {
    @Test
    void getBean() {
        try {
            ApplicationContext context = new GenericApplicationContext("src/test/resources/xml-config.xml");

            SampleService sampleServiceTest = context.getBean("mySampleTest",SampleService.class);
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

            SampleServiceTest sampleServiceTestClass = context.getBean(SampleServiceTest.class);
            assertAll(SampleServiceTest.class.getSimpleName(),
                    () -> assertEquals('G', sampleServiceTestClass.getAChar()),
                    () -> assertEquals(2, sampleServiceTestClass.getAByte()),
                    () -> assertEquals(3, sampleServiceTestClass.getAShort()),
                    () -> assertEquals(4, sampleServiceTestClass.getAnInt()),
                    () -> assertEquals(5, sampleServiceTestClass.getALong()),
                    () -> assertEquals(5.2f, sampleServiceTestClass.getAFloat()),
                    () -> assertEquals(6.2, sampleServiceTestClass.getADouble()),
                    () -> assertEquals("POP3", sampleServiceTestClass.getString())
            );

            SampleServiceTest sampleServiceTestObject = (SampleServiceTest)context.getBean("mySampleTest");
            assertAll(SampleServiceTest.class.getSimpleName(),
                    () -> assertEquals('G', sampleServiceTestObject.getAChar()),
                    () -> assertEquals(2, sampleServiceTestObject.getAByte()),
                    () -> assertEquals(3, sampleServiceTestObject.getAShort()),
                    () -> assertEquals(4, sampleServiceTestObject.getAnInt()),
                    () -> assertEquals(5, sampleServiceTestObject.getALong()),
                    () -> assertEquals(5.2f, sampleServiceTestObject.getAFloat()),
                    () -> assertEquals(6.2, sampleServiceTestObject.getADouble()),
                    () -> assertEquals("POP3", sampleServiceTestObject.getString())
            );

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}