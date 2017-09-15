package ua.alex.ioc.reader.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import ua.alex.ioc.entity.BeanDefinition;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class SaxParserHandler extends DefaultHandler {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final List<BeanDefinition> beanDefinitionList = new ArrayList<>();
    private final Deque<BeanDefinition> beanStack = new ArrayDeque<>();
    BeanDefinition currentBeanDefinition;

    public List<BeanDefinition> getBeanDefinitionList() {
        return beanDefinitionList;
    }
//    private Deque<String> elementStack = new ArrayDeque<>();

    @Override
    public String toString() {
        return "SaxParserHandler{" +
                "beanDefinitionList=" + beanDefinitionList +
                ", beanStack=" + beanStack +
                '}';
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        log.debug("Start Element {}", qName);
        String propertyName = null;
        String propertyValue = null;
        String propertyRef = null;
        if ("bean".equals(qName)) {
            currentBeanDefinition = new BeanDefinition();
            beanDefinitionList.add(currentBeanDefinition);
            int length = attributes.getLength();
            // process each attribute
            for (int i = 0; i < length; i++) {
                String name = attributes.getQName(i);
                String value = attributes.getValue(i);
                if ("id".equals(name)) {
                    currentBeanDefinition.setBeanId(value);
                } else if ("class".equals(name)) {
                    currentBeanDefinition.setBeanClass(value);
                }

            }


        } else if ("property".equals(qName)) {
            int length = attributes.getLength();

            // process each attribute
            for (int i = 0; i < length; i++) {
                String name = attributes.getQName(i);
                String value = attributes.getValue(i);
                if ("name".equals(name)) {
                    propertyName = value;
                } else if ("value".equals(name)) {
                    propertyValue = value;
                } else if ("ref".equals(name)) {
                    propertyRef = value;
                }
            }
            if (propertyName != null) {

                if (propertyValue != null) {
                    currentBeanDefinition.setValueDependencies(propertyName, propertyValue);

                } else {
                    currentBeanDefinition.setRefDependency(propertyName, propertyRef);
                }
            }

        }
    }
}
