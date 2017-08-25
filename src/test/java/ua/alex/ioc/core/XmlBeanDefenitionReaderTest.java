package ua.alex.ioc.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class XmlBeanDefenitionReaderTest {
    @Test
    void readBean() throws Exception {
        XmlBeanDefenitionReader xmlBeanDefenitionReader = new XmlBeanDefenitionReader();
        xmlBeanDefenitionReader.readBean("src/main/resources/xml-config.xml");
    }

}