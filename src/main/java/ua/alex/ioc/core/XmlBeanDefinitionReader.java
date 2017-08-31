package ua.alex.ioc.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.alex.ioc.entity.BeanDefinition;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class XmlBeanDefinitionReader implements BeanDefinitionReader {
    private static final Logger log = LoggerFactory.getLogger(XmlBeanDefinitionReader.class);

    public List<BeanDefinition> readBean(String... configurations) throws Exception {
        log.debug("Read Bean Started!");
        List<BeanDefinition> beanDefinitions = new ArrayList<>();
        SaxParserHandler handler = new SaxParserHandler();
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setValidating(false);
        SAXParser parser = factory.newSAXParser();
        for (String configuration : configurations) {
            File configFile = new File(configuration);
            parser.parse(configFile, handler);
            beanDefinitions.addAll(handler.getBeanDefinitionList());
        }
        return beanDefinitions;
    }
}
