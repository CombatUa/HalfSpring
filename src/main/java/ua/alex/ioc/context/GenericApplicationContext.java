package ua.alex.ioc.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.alex.ioc.entity.Bean;
import ua.alex.ioc.entity.BeanDefinition;
import ua.alex.ioc.exception.BeanInstantiationException;
import ua.alex.ioc.reader.BeanDefinitionReader;
import ua.alex.ioc.reader.XmlBeanDefinitionReader;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class GenericApplicationContext implements ApplicationContext {
    private static final String SETTER_PREFIX = "set";
    private final Logger log = LoggerFactory.getLogger(getClass());

    private List<BeanDefinition> beanDefinitionList;
    private List<Bean> beansInstances = new ArrayList<>();
    private BeanDefinitionReader reader;

    public GenericApplicationContext(String... paths) {
        log.debug("Context Started!");
        reader = new XmlBeanDefinitionReader();
        beanDefinitionList = new ArrayList<>();
        try {
            beanDefinitionList.addAll(reader.readBean(paths));
            injectValueDependencies();
            injectRefDependencies();
        } catch (Exception e) {
            throw new BeanInstantiationException(e);
        }
        log.debug("Beans instances:{}", beansInstances);
    }


    private void injectValueDependencies() throws Exception {
        for (BeanDefinition beanDefinition : beanDefinitionList) {

            Class clazz = Class.forName(beanDefinition.getBeanClass());
            Object beanInstance = clazz.newInstance();

            populateBeanInstance(beanInstance, beanDefinition.getAllValueDependencies(), clazz);

            Bean bean = new Bean();
            bean.setBeadId(beanDefinition.getBeanId());
            bean.setValue(beanInstance);
            beansInstances.add(bean);
        }

    }

    private void injectRefDependencies() throws Exception {
        for (BeanDefinition beanDefinition : beanDefinitionList) {
            Class clazz = Class.forName(beanDefinition.getBeanClass());
            Object beanInstance = getBean(beanDefinition.getBeanId());

            populateBeanInstance(beanInstance, beanDefinition.getAllRefDependencies(), clazz);
        }
    }


    private void populateBeanInstance(Object beanInstance, Set<Map.Entry<String, String>> beanEntrySet, Class clazz) throws Exception {
        for (Map.Entry<String, String> stringStringEntry : beanEntrySet) {
            String value = stringStringEntry.getValue();
            String fieldName = stringStringEntry.getKey();
            Class parameterType = clazz.getDeclaredField(fieldName).getType();

            Object castedValue;

            if (ValueParser.isValidEnum(parameterType.getSimpleName())) {
                castedValue = getParsedValue(value, parameterType);
            } else {
                castedValue = getBean(parameterType);
            }
            Method method = getSetterMethod(clazz, fieldName);
            method.invoke(beanInstance, castedValue);
        }
    }

    @Override
    public Object getBean(String beanId) {
        return beansInstances.stream().filter((x)->beanId.equals(x.getBeadId())).findFirst().orElseGet(Bean::new).getValue();

    }

    @Override
    public <T> T getBean(Class<T> clazz) {

        return clazz.cast(beansInstances.stream().filter((x)->clazz.isInstance(x.getValue())).findFirst().orElseGet(Bean::new).getValue());

    }

    @Override
    public <T> T getBean(String beanId, Class<T> clazz) {
        Object beanInstance = getBean(beanId);
        return clazz.cast(beanInstance);
    }

    @Override
    public List<String> getBeanNames() {
        return beanDefinitionList.stream().map(BeanDefinition::getBeanId).collect(Collectors.toList());
    }


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
