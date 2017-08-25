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

    List<BeanDefenition> beanDefenitionList = new ArrayList<>();

    private Deque<Object> objectStack = new ArrayDeque<>();
    private Deque<String> elementStack = new ArrayDeque<>();

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        this.elementStack.push(qName);

        if ("bean".equals(qName)) {
            BeanDefenition beanDefenition = new BeanDefenition();
            objectStack.push(beanDefenition);
            beanDefenitionList.add(beanDefenition);
        }

        System.out.println(qName);
        // get the number of attributes in the list
        int length = attributes.getLength();
   // process each attribute
        for (int i = 0; i < length; i++) {
// get qualified (prefixed) name by index
            String name = attributes.getQName(i);
            System.out.println("Name:" + name);
// get attribute's value by index.
            String value = attributes.getValue(i);
            System.out.println("Value:" + value);
// get namespace URI by index (if parser is namespace-aware)
            String nsUri = attributes.getURI(i);
            System.out.println("NS Uri:" + nsUri);
// get local name by index
            String lName = attributes.getLocalName(i);
            System.out.println("Local Name:" + lName);


        }
    }

        @Override
        public void endElement (String uri, String localName, String qName) throws SAXException {
           elementStack.pop();
            if ("bean".equals(qName)) {
                Object object = (BeanDefenition)objectStack.pop();

            }
        }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
    }
}
