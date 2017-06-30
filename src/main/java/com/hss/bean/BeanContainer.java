package com.hss.bean;

public interface BeanContainer {
    Object getByValue(String value);

    Object getByType(Class<?> type);

    Object getByClassName(String className);

    Object getByParent(Class<?> parentType);

    void setByClassName(String className, String value, ClassLoader classLoader, boolean isSingleton);

    void setByType(Class<?> type, String value, boolean isSingleton);
}
