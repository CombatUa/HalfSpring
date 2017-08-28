package ua.alex.ioc.core;

import ua.alex.ioc.entity.BeanDefenition;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.util.List;

public class XmlBeanDefenitionReader implements BeanDefenitionReader {
    public List<BeanDefenition> readBean(String... configurations) throws Exception {
        SaxParserHandler handler = new SaxParserHandler();
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setValidating(false);
        SAXParser parser = factory.newSAXParser();
        File configFile = new File(configurations[0]);
        parser.parse(configFile, handler);
        return handler.getBeanDefenitionList();
    }
}
