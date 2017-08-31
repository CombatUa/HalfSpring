package ua.alex.ioc.entityJAXB;

import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import java.io.File;

class BeanDefinitionTest {
    @Test
    void justJAXBTest() throws Exception {
        JAXBContext jaxbContext = JAXBContext.newInstance(BeanDefinitions.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        BeanDefinitions beanDefinitions = (BeanDefinitions)unmarshaller.unmarshal(new File("src/main/resources/xml-config.xml"));
        System.out.println(beanDefinitions);
    }
}