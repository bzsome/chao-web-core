package com.bzchao.core.web;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解，用于判断使用进行分页参数的处理
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface PageAble {
}
