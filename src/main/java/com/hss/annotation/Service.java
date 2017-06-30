package com.hss.annotation;

import com.hss.common.InstanceModel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Service {
    Class<?> type() default Class.class;

    String value() default "";

    InstanceModel model() default InstanceModel.SINGLETON;
}
