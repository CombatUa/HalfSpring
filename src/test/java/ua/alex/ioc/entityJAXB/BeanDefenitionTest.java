package ua.alex.ioc.entityJAXB;

import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import java.io.File;

class BeanDefenitionTest {
    @Test
    void justJAXBTest() throws Exception {
        JAXBContext jaxbContext = JAXBContext.newInstance(BeanDefenitions.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        BeanDefenitions beanDefenitions = (BeanDefenitions)unmarshaller.unmarshal(new File("src/main/resources/xml-config.xml"));
        System.out.println(beanDefenitions);
    }
}