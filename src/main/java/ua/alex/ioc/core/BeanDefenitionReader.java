package ua.alex.ioc.core;

import ua.alex.ioc.entity.BeanDefenition;

import java.util.List;

public interface BeanDefenitionReader {
    List<BeanDefenition> readBean(String ... configurations) throws Exception;
}
