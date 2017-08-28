package ua.alex.ioc.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BeanDefinition {

    private Map<String, String> refDependecies = new HashMap<>();
    private Map<String, String> valueDependecies = new HashMap<>();
    private String beanId;
    private String beanClass;

    public Set<Map.Entry<String, String>> getAllRefDependecies() {
        return refDependecies.entrySet();
    }

    public Set<Map.Entry<String, String>> getAllValueDependecies() {
        return valueDependecies.entrySet();
    }

    public String getRefDependecies(String refName) {
        return refDependecies.get(refName);
    }

    public void setRefDependecy(String name, String value) {
        refDependecies.put(name, value);
    }

    public String getValueDependecies(String name) {
        return valueDependecies.get(name);
    }

    public void setValueDependecies(String name, String value) {
        valueDependecies.put(name, value);
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
                "refDependecies=" + refDependecies +
                ", valueDependecies=" + valueDependecies +
                ", beanId='" + beanId + '\'' +
                ", beanClass='" + beanClass + '\'' +
                '}';
    }
}
