package com.hss.bean;

public class SimpleBeanFactory implements BeanFactory {

    @Override
    public <T> void setBean(Class<T> cls) {

    }

    @Override
    public void setBean(String className) {

    }

    @Override
    public <T> T getBean(Class<T> tClass) {
        return null;
    }

    @Override
    public <T> T getBean(String className) {
        return null;
    }
}
