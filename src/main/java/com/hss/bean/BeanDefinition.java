package com.hss.bean;

public class BeanDefinition {
    private Class<?> type;
    private String className;
    private String value;
    private Class<?> parent;
    private Object bean;

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Class<?> getParent() {
        return parent;
    }

    public void setParent(Class<?> parent) {
        this.parent = parent;
    }

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }
}
