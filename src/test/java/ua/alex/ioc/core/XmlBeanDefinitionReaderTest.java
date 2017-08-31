package ua.alex.ioc.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import ua.alex.ioc.entity.BeanDefinition;

import java.util.List;
import java.util.Optional;

class XmlBeanDefinitionReaderTest {
    @Test
    void readBean() throws Exception {
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader();
        List<BeanDefinition> beanDefinitions = xmlBeanDefinitionReader.readBean("src/test/resources/xml-config.xml");

        Optional<BeanDefinition> beanDefinition = beanDefinitions.stream().filter((s) -> "mySampleTest".equals(s.getBeanId())).findAny();
        assertAll(BeanDefinition.class.getSimpleName(),
                () -> assertTrue(beanDefinition.isPresent()),
                () -> assertEquals("mySampleTest", beanDefinition.get().getBeanId()),
                () -> assertEquals("6.2", beanDefinition.get().getValueDependencies("aDouble")));
    }

}