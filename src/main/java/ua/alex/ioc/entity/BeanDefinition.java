package ua.alex.ioc.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BeanDefinition {

    private final Map<String, String> refDependencies = new HashMap<>();
    private final Map<String, String> valueDependencies = new HashMap<>();
    private String beanId;
    private String beanClass;

    public Set<Map.Entry<String, String>> getAllRefDependencies() {
        return refDependencies.entrySet();
    }

    public Set<Map.Entry<String, String>> getAllValueDependencies() {
        return valueDependencies.entrySet();
    }

    public String getRefDependencies(String refName) {
        return refDependencies.get(refName);
    }

    public void setRefDependency(String name, String value) {
        refDependencies.put(name, value);
    }

    public String getValueDependencies(String name) {
        return valueDependencies.get(name);
    }

    public void setValueDependencies(String name, String value) {
        valueDependencies.put(name, value);
    }


    public String getBeanId() {
        return beanId;
    }

    public void setBeanId(String beanId) {
        this.beanId = beanId;
    }

    public String getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(String beanClass) {
        this.beanClass = beanClass;
    }
    @Override
    public String toString() {
        return "BeanDefinition{" +
                "refDependencies=" + refDependencies +
                ", valueDependencies=" + valueDependencies +
                ", beanId='" + beanId + '\'' +
                ", beanClass='" + beanClass + '\'' +
                '}';
    }
}
