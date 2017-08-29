package ua.alex.ioc.core;

import org.junit.jupiter.api.Test;
import ua.alex.ioc.entity.BeanDefinition;

import java.util.List;

class XmlBeanDefinitionReaderTest {
    @Test
    void readBean() throws Exception {
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader();
        List<BeanDefinition> beanDefinitions = xmlBeanDefinitionReader.readBean("src/main/resources/xml-config.xml");
        for (BeanDefinition beanDefinition : beanDefinitions) {
            System.out.println(beanDefinition);
        }
    }

}