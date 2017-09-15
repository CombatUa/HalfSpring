package ua.alex.ioc.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.alex.ioc.exception.BeanInstantiationException;
import ua.alex.ioc.reader.BeanDefinitionReader;
import ua.alex.ioc.reader.XmlBeanDefinitionReader;
import ua.alex.ioc.entity.Bean;
import ua.alex.ioc.entity.BeanDefinition;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenericApplicationContext implements ApplicationContext {
    private static final String SETTER_PREFIX = "set";
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final Map<String, BeanDefinition> beanIdToBeandefinitionMap = new HashMap<>();
    private final List<Bean> beansInstances = new ArrayList<>();
    private BeanDefinitionReader reader;
    private String path;

    public GenericApplicationContext(String... paths) {
        log.debug("Context Started!");
        reader = new XmlBeanDefinitionReader();
        List<BeanDefinition> beanDefinitions = null;
        try {
            beanDefinitions = reader.readBean(paths);
        } catch (Exception e) {
            throw new BeanInstantiationException(e);
        }
        for (BeanDefinition beanDefinition : beanDefinitions) {
            this.beanIdToBeandefinitionMap.put(beanDefinition.getBeanId(), beanDefinition);
        }
        try {
            injectValueDependecies();
            injectRefDependecies();
        } catch (Exception e) {
            throw new BeanInstantiationException(e);
        }
        log.debug("Beans instances:{}", beansInstances);
    }

    private void injectRefDependecies() throws Exception {
        for (Map.Entry<String, BeanDefinition> stringBeanDefinitionEntry : beanIdToBeandefinitionMap.entrySet()) {
            Class aClass = Class.forName(stringBeanDefinitionEntry.getValue().getBeanClass());
            Object beanInstance = getBean(stringBeanDefinitionEntry.getKey());
            for (Map.Entry<String, String> stringStringEntry : stringBeanDefinitionEntry.getValue().getAllRefDependencies()) {
                String refBeanId = stringStringEntry.getValue();
                Object refBeanInstance = getBean(refBeanId);
                String fieldName = stringStringEntry.getKey();
                Method method = getSetterMethod(aClass, fieldName);
                method.invoke(beanInstance, refBeanInstance);
            }
        }
    }

    private void injectValueDependecies() throws Exception {
        for (Map.Entry<String, BeanDefinition> stringBeanDefinitionEntry : beanIdToBeandefinitionMap.entrySet()) {

            Class aClass = Class.forName(stringBeanDefinitionEntry.getValue().getBeanClass());
            Object beanInstance = aClass.newInstance();
            for (Map.Entry<String, String> stringStringEntry : stringBeanDefinitionEntry.getValue().getAllValueDependencies()) {
                String value = stringStringEntry.getValue();
                String fieldName = stringStringEntry.getKey();
                Object castedValue = null;
                Method method = getSetterMethod(aClass, fieldName);
                for (Parameter parameter : method.getParameters()) {
                    Class fieldType = parameter.getType();
                    castedValue = getParsedValue(value, fieldType);
                    method.invoke(beanInstance, castedValue);
                }
            }
            Bean bean = new Bean();
            bean.setBeadId(stringBeanDefinitionEntry.getKey());
            bean.setValue(beanInstance);
            beansInstances.add(bean);
        }

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
                foundedBean = clazz.cast(beansInstance.getValue());
                break;
            }
        }

        return foundedBean;
    }

    @Override
    public <T> T getBean(String beanId, Class<T> clazz) {
        Object beanInstance = getBean(beanId);
        return clazz.cast(beanInstance);
    }

    @Override
    public List<String> getBeanNames() {
        return null;
    }

//    private void construct() throws Exception {
//        for (Map.Entry<String, BeanDefinition> stringBeanDefinitionEntry : beanIdToBeandefinitionMap.entrySet()) {
//            Object bean = getBean(stringBeanDefinitionEntry.getKey());
//            if (bean == null) {
////                createBean(stringBeanDefinitionEntry.getValue());
//                createBeanWithSetter(stringBeanDefinitionEntry.getValue());
//            }
//        }
//    }


//    private Bean createBean(BeanDefinition beanDefinition) throws Exception {
//        String beanId = beanDefinition.getBeanId();
//        Object beanInstance = getBean(beanId);
//        //check in instances
//        if (beanInstance == null) {
//            Class aClass = Class.forName(beanDefinition.getBeanClass());
//            beanInstance = aClass.newInstance();
//            for (Map.Entry<String, String> stringStringEntry : beanDefinition.getAllValueDependencies()) {
//                String value = stringStringEntry.getValue();
//                Field field = aClass.getDeclaredField(stringStringEntry.getKey());
//                Class fieldType = field.getType();
//                if (fieldType == String.class) {
//                    field.setAccessible(true);
//                    field.set(beanInstance, value);
//                    log.debug(" set string");
//                } else if ("int".equals(fieldType.getSimpleName())) {
//                    field.setAccessible(true);
//                    field.set(beanInstance, Integer.parseInt(value));
//                    log.debug(" set int");
//                } else {
//                    log.debug("unprocessed type:{}", fieldType.getSimpleName());
//                }
//                field.setAccessible(false);
//
//            }
//
//            for (Map.Entry<String, String> stringStringEntry : beanDefinition.getAllRefDependencies()) {
//                String refBeanId = stringStringEntry.getValue();
//                Object refBeanInstance = getBean(refBeanId);
//                if (refBeanInstance == null) {
//                    refBeanInstance = createBean(beanIdToBeandefinitionMap.get(refBeanId)).getValue();
//                }
//                Field field = aClass.getDeclaredField(stringStringEntry.getKey());
//                field.setAccessible(true);
//                field.set(beanInstance, refBeanInstance);
//                field.setAccessible(false);
//            }
//
//        }
//        Bean bean = new Bean();
//        bean.setBeadId(beanId);
//        bean.setValue(beanInstance);
//        beansInstances.add(bean);
//        return bean;
//    }

//    private Bean createBeanWithSetter(BeanDefinition beanDefinition) throws Exception {
//        String beanId = beanDefinition.getBeanId();
//        Object beanInstance = getBean(beanId);
//        //check in instances
//        if (beanInstance == null) {
//            Class aClass = Class.forName(beanDefinition.getBeanClass());
//            beanInstance = aClass.newInstance();
//            for (Map.Entry<String, String> stringStringEntry : beanDefinition.getAllValueDependencies()) {
//                String value = stringStringEntry.getValue();
//                String fieldName = stringStringEntry.getKey();
//                Object castedValue = null;
//                Method method = getSetterMethod(aClass, fieldName);
//                for (Parameter parameter : method.getParameters()) {
//                    Class fieldType = parameter.getType();
//                    castedValue = getParsedValue(value, fieldType);
//                    method.invoke(beanInstance, castedValue);
//                }
//            }
//
//            for (Map.Entry<String, String> stringStringEntry : beanDefinition.getAllRefDependencies()) {
//                String refBeanId = stringStringEntry.getValue();
//                Object refBeanInstance = getBean(refBeanId);
//                String fieldName = stringStringEntry.getKey();
//                if (refBeanInstance == null) {
//                    refBeanInstance = createBeanWithSetter(beanIdToBeandefinitionMap.get(refBeanId)).getValue();
//                }
//                Method method = getSetterMethod(aClass, fieldName);
//                method.invoke(beanInstance, refBeanInstance);
//
//            }
//
//        }
//        Bean bean = new Bean();
//        bean.setBeadId(beanId);
//        bean.setValue(beanInstance);
//        beansInstances.add(bean);
//        return bean;
//    }

    private Method getSetterMethod(Class<?> clazz, String fieldName) throws Exception {
        return clazz.getMethod(SETTER_PREFIX + capitalize(fieldName), clazz.getDeclaredField(fieldName).getType());
    }

    private Object getParsedValue(String value, Class fieldType) {
        return ValueParser.valueOf(fieldType.getSimpleName().toUpperCase()).getValue(value);
    }

    private String capitalize(String word) {
        return Character.toUpperCase(word.charAt(0)) + word.substring(1);
    }

}
