package com.mtx.common.annotation;

import java.lang.annotation.*;

/**
 * 初始化继承BaseService的service
 */
@Target({ElementType.TYPE})//应用于类、接口（包括注解类型）、枚举
@Retention(RetentionPolicy.RUNTIME)//注解的生命周期
@Documented   //使用了@Inherited注解的注解，所标记的类的子类也会拥有这个注解
public @interface BaseService {
}
