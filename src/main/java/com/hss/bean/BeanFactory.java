package com.hss.bean;

public interface BeanFactory {
    <T> void setBean(Class<T> cls);

    void setBean(String className);

    <T> T getBean(Class<T> tClass);

    <T> T getBean(String className);
}
