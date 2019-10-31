package com.ri.generalFramework.annotation;

import com.ri.generalFramework.handle.CustomizeCheckHandle;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 自定义检查注解
 * 注解处理类 {@link CustomizeCheckHandle}
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD})
public @interface CustomizeCheck {
    int serial() default -1;

    String pattern() default "";

    String msg() default "is wrong";

    boolean canNull() default false;
}
