package com.hss.annotation;

import com.hss.common.InstanceModel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Repository {
    InstanceModel model() default InstanceModel.SINGLETON;
}
