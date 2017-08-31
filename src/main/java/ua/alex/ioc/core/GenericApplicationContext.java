package ua.alex.ioc.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger log = LoggerFactory.getLogger(GenericApplicationContext.class);
    private static final String SETTER_PREFIX = "set";
    private final Map<String, BeanDefinition> beansDefinitions = new HashMap<>();
    private final List<Bean> beansInstances = new ArrayList<>();
    final BeanDefinitionReader reader;
    private String path;

    public GenericApplicationContext(String... paths) throws Exception {
        log.debug("Context Started!");
        reader = new XmlBeanDefinitionReader();
        List<BeanDefinition> beanDefinitions = reader.readBean(paths);
        for (BeanDefinition beanDefinition : beanDefinitions) {
            this.beansDefinitions.put(beanDefinition.getBeanId(), beanDefinition);
        }
        construct();
        log.debug("Beans instances:{}", beansInstances);
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
            if (clazz == beansInstance.getValue().getClass()) {
                foundedBean = (T) beansInstance.getValue();
                break;
            }
        }

        return foundedBean;
    }

    @Override
    public <T> T getBean(String beanId, Class<T> clazz) {
        Object beanInstance = getBean(beanId);
        return (T) beanInstance;
    }

    @Override
    public List<String> getBeanNames() {
        return null;
    }

    private void construct() throws Exception {
        for (Map.Entry<String, BeanDefinition> stringBeanDefinitionEntry : beansDefinitions.entrySet()) {
            Object bean = getBean(stringBeanDefinitionEntry.getKey());
            if (bean == null) {
//                createBean(stringBeanDefinitionEntry.getValue());
                createBeanWithSetter(stringBeanDefinitionEntry.getValue());
            }
        }
    }


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
//                    refBeanInstance = createBean(beansDefinitions.get(refBeanId)).getValue();
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

    private Bean createBeanWithSetter(BeanDefinition beanDefinition) throws Exception {
        String beanId = beanDefinition.getBeanId();
        Object beanInstance = getBean(beanId);
        //check in instances
        if (beanInstance == null) {
            Class aClass = Class.forName(beanDefinition.getBeanClass());
            beanInstance = aClass.newInstance();
            for (Map.Entry<String, String> stringStringEntry : beanDefinition.getAllValueDependencies()) {
                String value = stringStringEntry.getValue();
                String fieldName = stringStringEntry.getKey();
                Object castedValue = null;
                Field field = aClass.getDeclaredField(stringStringEntry.getKey());

                Method method = aClass.getMethod(SETTER_PREFIX + capitalize(fieldName), field.getType());


                for (Parameter parameter : method.getParameters()) {
                    Class fieldType = parameter.getType();
                    if (fieldType == String.class) {
                        castedValue = value;
                    } else if (fieldType == char.class) {
                        castedValue = value.charAt(0);
                    } else if (fieldType == byte.class) {
                        castedValue = Byte.parseByte(value);
                    } else if (fieldType == short.class) {
                        castedValue = Short.parseShort(value);
                    } else if (fieldType == int.class) {
                        castedValue = Integer.parseInt(value);
                    } else if (fieldType == long.class) {
                        castedValue = Long.parseLong(value);
                    } else if (fieldType == float.class) {
                        castedValue = Float.parseFloat(value);
                    } else if (fieldType == double.class) {
                        castedValue = Double.parseDouble(value);
                    } else {
                        log.debug("unprocessed type:{}", field.getType());
                    }
                    method.invoke(beanInstance, castedValue);
                }


            }

            for (Map.Entry<String, String> stringStringEntry : beanDefinition.getAllRefDependencies()) {
                String refBeanId = stringStringEntry.getValue();
                Object refBeanInstance = getBean(refBeanId);
                String fieldName = stringStringEntry.getKey();
                Field field = aClass.getDeclaredField(fieldName);


                if (refBeanInstance == null) {
                    refBeanInstance = createBeanWithSetter(beansDefinitions.get(refBeanId)).getValue();
                }

                Method method = aClass.getMethod(SETTER_PREFIX + capitalize(fieldName), field.getType());


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
