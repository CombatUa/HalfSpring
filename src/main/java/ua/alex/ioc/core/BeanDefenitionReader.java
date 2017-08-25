package ua.alex.ioc.core;

import java.util.List;

public interface BeanDefenitionReader {
    List<ua.alex.ioc.entity.BeanDefenition> readBean(String ... configurations) throws Exception;
}
