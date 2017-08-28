package ua.alex.ioc.core;

import ua.alex.ioc.entity.BeanDefinition;

import java.util.List;

public interface BeanDefinitionReader {
    List<BeanDefinition> readBean(String ... configurations) throws Exception;
}
