package ua.alex.ioc.core;

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
    private static final Logger log = LoggerFactory.getLogger(SaxParserHandler.class);

    public List<BeanDefinition> getBeanDefinitionList() {
        return beanDefinitionList;
    }

    private List<BeanDefinition> beanDefinitionList = new ArrayList<>();
    private Deque<BeanDefinition> beanStack = new ArrayDeque<>();
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
            BeanDefinition beanDefinition = new BeanDefinition();
            beanStack.push(beanDefinition);
            beanDefinitionList.add(beanDefinition);
            int length = attributes.getLength();
            // process each attribute
            for (int i = 0; i < length; i++) {
                String name = attributes.getQName(i);
                String value = attributes.getValue(i);
                if ("id".equals(name)) {
                    beanDefinition.setBeanId(value);
                } else if ("class".equals(name)) {
                    beanDefinition.setBeanClass(value);
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
                    beanStack.peek().setValueDependecies(propertyName, propertyValue);

                } else {
                    beanStack.peek().setRefDependecy(propertyName, propertyRef);
                }
            }

        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        log.debug("End Element {}", qName);
        if ("bean".equals(qName)) {
            beanStack.pop();
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        //log.debug("in characters section:{}", new String(ch, start, length));
    }
}
