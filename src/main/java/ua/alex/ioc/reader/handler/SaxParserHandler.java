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
    private BeanDefinition currentBeanDefinition;

    public List<BeanDefinition> getBeanDefinitionList() {
        return beanDefinitionList;
    }
    @Override
    public String toString() {
        return "SaxParserHandler{" +
                "beanDefinitionList=" + beanDefinitionList +
                '}';
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        log.debug("Start Element {}", qName);

        if ("bean".equals(qName)) {
            processBeanElement(attributes);
        } else if ("property".equals(qName)) {
            processPropertyElement(attributes);
        }
    }

    private void processPropertyElement(Attributes attributes) {
        String propertyName = null;
        String propertyValue = null;
        String propertyRef = null;
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

    private void processBeanElement(Attributes attributes) {
        currentBeanDefinition = new BeanDefinition();
        beanDefinitionList.add(currentBeanDefinition);
        int length = attributes.getLength();
        for (int i = 0; i < length; i++) {
            String name = attributes.getQName(i);
            String value = attributes.getValue(i);
            if ("id".equals(name)) {
                currentBeanDefinition.setBeanId(value);
            } else if ("class".equals(name)) {
                currentBeanDefinition.setBeanClass(value);
            }

        }
    }
}
