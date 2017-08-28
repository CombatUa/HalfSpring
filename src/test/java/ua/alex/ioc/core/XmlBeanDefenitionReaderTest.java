package ua.alex.ioc.core;

import org.junit.jupiter.api.Test;
import ua.alex.ioc.entity.BeanDefenition;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class XmlBeanDefenitionReaderTest {
    @Test
    void readBean() throws Exception {
        XmlBeanDefenitionReader xmlBeanDefenitionReader = new XmlBeanDefenitionReader();
        List<BeanDefenition> beanDefenitions = xmlBeanDefenitionReader.readBean("src/main/resources/xml-config.xml");
        for (BeanDefenition beanDefenition : beanDefenitions) {
            System.out.println(beanDefenition);
        }


    }

}