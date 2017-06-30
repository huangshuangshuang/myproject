package com.hss.bean;

import com.hss.common.CollectionUtils;
import com.hss.common.StringUtils;
import com.hss.exception.BeanCreateException;
import com.hss.exception.BeanNotFoundException;
import com.hss.exception.InvalidParameterException;

import java.util.HashSet;
import java.util.Set;

public class SimpleBeanContainer implements BeanContainer {

    private static final Set<BeanDefinition> singletonDefinitions = new HashSet<>();

    private static final Set<BeanDefinition> normalDefinitions = new HashSet<>();

    @Override
    public Object getByValue(String value) {
        if (StringUtils.isEmpty(value)) {
            throw new InvalidParameterException("parameter is not valid.");
        }
        if (!CollectionUtils.isEmpty(singletonDefinitions)) {
            for (BeanDefinition beanDefinition : singletonDefinitions) {
                if (beanDefinition.getValue().equals(value)) {
                    return beanDefinition.getBean();
                }
            }
        }
        if (!CollectionUtils.isEmpty(normalDefinitions)) {
            for (BeanDefinition beanDefinition : normalDefinitions) {
                if (beanDefinition.getValue().equals(value)) {
                    try {
                        return beanDefinition.getType().newInstance();
                    } catch (InstantiationException | IllegalAccessException e) {
                        throw new BeanCreateException(e.getMessage());
                    }
                }
            }
        }

        throw new BeanNotFoundException("not found bean base on [" + value + "]");
    }

    @Override
    public Object getByType(Class<?> type) {
        if (type == null) {
            throw new InvalidParameterException("parameter is not valid.");
        }
        if (!CollectionUtils.isEmpty(singletonDefinitions)) {
            for (BeanDefinition beanDefinition : singletonDefinitions) {
                if (beanDefinition.getType().equals(type)) {
                    return beanDefinition.getBean();
                }
            }
        }
        if (!CollectionUtils.isEmpty(normalDefinitions)) {
            for (BeanDefinition beanDefinition : normalDefinitions) {
                if (beanDefinition.getType().equals(type)) {
                    try {
                        return beanDefinition.getType().newInstance();
                    } catch (InstantiationException | IllegalAccessException e) {
                        throw new BeanCreateException(e.getMessage());
                    }
                }
            }
        }

        throw new BeanNotFoundException("not found bean base on [" + type + "]");
    }

    @Override
    public Object getByClassName(String className) {
        if (StringUtils.isEmpty(className)) {
            throw new InvalidParameterException("parameter is not valid.");
        }
        try {
            Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new BeanNotFoundException("not found bean base on [" + className + "]");
        }
        if (!CollectionUtils.isEmpty(singletonDefinitions)) {
            for (BeanDefinition beanDefinition : singletonDefinitions) {
                if (beanDefinition.getClassName().equals(className)) {
                    return beanDefinition.getBean();
                }
            }
        }
        if (!CollectionUtils.isEmpty(normalDefinitions)) {
            for (BeanDefinition beanDefinition : normalDefinitions) {
                if (beanDefinition.getClassName().equals(className)) {
                    try {
                        return beanDefinition.getType().newInstance();
                    } catch (InstantiationException | IllegalAccessException e) {
                        throw new BeanCreateException(e.getMessage());
                    }
                }
            }
        }

        throw new BeanNotFoundException("not found bean base on [" + className + "]");
    }

    @Override
    public Object getByParent(Class<?> parentType) {
        if (parentType == null) {
            throw new InvalidParameterException("parameter is not valid.");
        }
        if (!CollectionUtils.isEmpty(singletonDefinitions)) {
            for (BeanDefinition beanDefinition : singletonDefinitions) {
                if (beanDefinition.getParent().equals(parentType)) {
                    return beanDefinition.getBean();
                }
            }
        }
        if (!CollectionUtils.isEmpty(normalDefinitions)) {
            for (BeanDefinition beanDefinition : normalDefinitions) {
                if (beanDefinition.getParent().equals(parentType)) {
                    try {
                        return beanDefinition.getType().newInstance();
                    } catch (InstantiationException | IllegalAccessException e) {
                        throw new BeanCreateException(e.getMessage());
                    }
                }
            }
        }

        throw new BeanNotFoundException("not found bean base on [" + parentType + "]");
    }

    @Override
    public void setByClassName(String className, String value, ClassLoader classLoader, boolean isSingleton) {
        if (StringUtils.isEmpty(className) || StringUtils.isEmpty(value)) {
            throw new InvalidParameterException("parameter is not valid.");
        }
        if (classLoader == null) {
            classLoader = this.getClass().getClassLoader();
        }
        try {
            Class<?> cls = classLoader.loadClass(className);
            Class<?> parent = cls.getSuperclass();
            BeanDefinition definition = new BeanDefinition();
            definition.setClassName(className);
            definition.setParent(parent);
            definition.setValue(value);
            definition.setType(cls);
            if (isSingleton) {
                definition.setBean(cls.newInstance());
                singletonDefinitions.add(definition);
            }else {
                normalDefinitions.add(definition);
            }
        } catch (Exception e) {
            throw new BeanCreateException(e.getMessage());
        }
    }

    @Override
    public void setByType(Class<?> type, String value, boolean isSingleton) {
        if (type == null || StringUtils.isEmpty(value)) {
            throw new InvalidParameterException("parameter is not valid.");
        }
        try {
            Class<?> parent = type.getSuperclass();
            BeanDefinition definition = new BeanDefinition();
            definition.setClassName(type.getName());
            definition.setParent(parent);
            definition.setValue(value);
            definition.setType(type);
            if (isSingleton) {
                definition.setBean(type.newInstance());
                singletonDefinitions.add(definition);
            }else {
                normalDefinitions.add(definition);
            }
        } catch (Exception e) {
            throw new BeanCreateException(e.getMessage());
        }
    }
}
