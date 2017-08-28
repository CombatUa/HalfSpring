package ua.alex.ioc.core;

import ua.alex.ioc.entity.Bean;
import ua.alex.ioc.entity.BeanDefenition;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenericApplicationContext implements ApplecationContext {
    public static final String SETTER_PREFIX = "set";
    Map<String, BeanDefenition> beansDefenitions = new HashMap<>();
    List<Bean> beansInstances = new ArrayList<>();
    BeanDefenitionReader reader;
    private String path;

    public GenericApplicationContext(String... paths) throws Exception {
        XmlBeanDefenitionReader xmlBeanDefenitionReader = new XmlBeanDefenitionReader();
        List<BeanDefenition> beanDefenitions = xmlBeanDefenitionReader.readBean(paths);
        for (BeanDefenition beanDefenition : beanDefenitions) {
            this.beansDefenitions.put(beanDefenition.getBeanId(), beanDefenition);
        }
        constract();
//        System.out.println(beansInstances);
    }

    @Override
    public Object getBean(String beanId) {
        Object foundedBean = null;
        for (Bean beansInstance : beansInstances) {
            if (beanId.equals(beansInstance.getBeadId())) {
                foundedBean = beansInstance.getValue();
                break;
            }
        }
        return foundedBean;
    }

    @Override
    public <T> T getBean(Class<T> clazz) {
        T foundedBean = null;
        for (Bean beansInstance : beansInstances) {
            if (clazz.isInstance(beansInstance.getValue())) {
                foundedBean = (T) beansInstance.getValue();
                break;
            }
        }
        return foundedBean;
    }

    @Override
    public <T> T getBean(String beanId, Class<T> clazz) {
        return null;
    }

    @Override
    public List<String> getBeanNames() {
        return null;
    }

    private void constract() throws Exception {
        //read beandefenitions
        for (Map.Entry<String, BeanDefenition> stringBeanDefenitionEntry : beansDefenitions.entrySet()) {
            Object bean = getBean(stringBeanDefenitionEntry.getKey());

            if (bean == null) {
//                createBean(stringBeanDefenitionEntry.getValue());
                createBeanWithSetter(stringBeanDefenitionEntry.getValue());
            }
        }

        //read value array
        //start reading ref array
        // if there is no instance - constract it - run constract
    }


    private Bean createBean(BeanDefenition beanDefenition) throws Exception {
        String beanId = beanDefenition.getBeanId();
        Object beanInstance = getBean(beanId);
        //check in instances
        if (beanInstance == null) {
            Class aClass = Class.forName(beanDefenition.getBeanClass());
            beanInstance = aClass.newInstance();
            for (Map.Entry<String, String> stringStringEntry : beanDefenition.getAllValueDependecies()) {
                String value = stringStringEntry.getValue();
                Field field = aClass.getDeclaredField(stringStringEntry.getKey());
                Class fieldType = field.getType();
                if (fieldType == String.class) {
                    field.setAccessible(true);
                    field.set(beanInstance, value);
                    System.out.println(" set string");
                } else if ("int".equals(fieldType.getSimpleName())) {
                    field.setAccessible(true);
                    field.set(beanInstance, Integer.parseInt(value));
                    System.out.println(" set int");
                } else {
                    System.out.println(fieldType.getSimpleName());
                }
                field.setAccessible(false);

            }

            for (Map.Entry<String, String> stringStringEntry : beanDefenition.getAllRefDependecies()) {
                String refBeanId = stringStringEntry.getValue();
                Object refBeanInstance = getBean(refBeanId);
                if (refBeanInstance == null) {
                    refBeanInstance = createBean(beansDefenitions.get(refBeanId)).getValue();
                }
                Field field = aClass.getDeclaredField(stringStringEntry.getKey());
                field.setAccessible(true);
                field.set(beanInstance, refBeanInstance);
                field.setAccessible(false);
            }

        }
        Bean bean = new Bean();
        bean.setBeadId(beanId);
        bean.setValue(beanInstance);
        beansInstances.add(bean);
        return bean;
    }

    private Bean createBeanWithSetter(BeanDefenition beanDefenition) throws Exception {
        String beanId = beanDefenition.getBeanId();
        Object beanInstance = getBean(beanId);
        //check in instances
        if (beanInstance == null) {
            Class aClass = Class.forName(beanDefenition.getBeanClass());
            beanInstance = aClass.newInstance();
            for (Map.Entry<String, String> stringStringEntry : beanDefenition.getAllValueDependecies()) {
                String value = stringStringEntry.getValue();
                String fieldName = stringStringEntry.getKey();
                Object castedValue = value;
                Field field = aClass.getDeclaredField(stringStringEntry.getKey());

                Method method = aClass.getMethod(SETTER_PREFIX + capitalize(fieldName),field.getType());


                for (Parameter parameter : method.getParameters()) {
                    Class fieldType = parameter.getType();
                    if (fieldType == String.class) {

                    } else if ("int".equals(fieldType.getSimpleName())) {
                        castedValue = Integer.valueOf(value);
                    } else {
                        System.out.println(fieldType.getSimpleName());
                    }
                    method.invoke(beanInstance, castedValue);
                }


            }

            for (Map.Entry<String, String> stringStringEntry : beanDefenition.getAllRefDependecies()) {
                String refBeanId = stringStringEntry.getValue();
                Object refBeanInstance = getBean(refBeanId);
                String fieldName = stringStringEntry.getKey();
                Field field = aClass.getDeclaredField(fieldName);


                if (refBeanInstance == null) {
                    refBeanInstance = createBeanWithSetter(beansDefenitions.get(refBeanId)).getValue();
                }

                Method method = aClass.getMethod(SETTER_PREFIX + capitalize(fieldName),field.getType());


                method.invoke(beanInstance, refBeanInstance);

            }

        }
        Bean bean = new Bean();
        bean.setBeadId(beanId);
        bean.setValue(beanInstance);
        beansInstances.add(bean);
        return bean;
    }

    private String capitalize(String word) {
        return Character.toUpperCase(word.charAt(0)) + word.substring(1);
    }

}
