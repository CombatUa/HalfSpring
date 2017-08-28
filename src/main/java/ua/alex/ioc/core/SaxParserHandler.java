package ua.alex.ioc.core;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import ua.alex.ioc.entity.BeanDefenition;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class SaxParserHandler extends DefaultHandler {
    public List<BeanDefenition> getBeanDefenitionList() {
        return beanDefenitionList;
    }

    private List<BeanDefenition> beanDefenitionList = new ArrayList<>();
    private Deque<BeanDefenition> beanStack = new ArrayDeque<>();
//    private Deque<String> elementStack = new ArrayDeque<>();

    @Override
    public String toString() {
        return "SaxParserHandler{" +
                "beanDefenitionList=" + beanDefenitionList +
                ", beanStack=" + beanStack +
                '}';
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

//        this.elementStack.push(qName);
        System.out.println("--------");
        String propertyName = null;
        String propertyValue = null;
        String propertyRef = null;
        if ("bean".equals(qName)) {
            BeanDefenition beanDefenition = new BeanDefenition();
            beanStack.push(beanDefenition);
            beanDefenitionList.add(beanDefenition);
            int length = attributes.getLength();
            // process each attribute
            for (int i = 0; i < length; i++) {
                String name = attributes.getQName(i);
                String value = attributes.getValue(i);
                if ("id".equals(name)) {
                    beanDefenition.setBeanId(value);
                } else if ("class".equals(name)) {
                    beanDefenition.setBeanClass(value);
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
        System.out.println("++++++End+++++++");

        if ("bean".equals(qName)) {
            beanStack.pop();
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        System.out.println("+++++++++++++++++++");
        System.out.println(new String(ch, start, length).toString());
    }
}
