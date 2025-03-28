package com.wjh.picturebackend.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author 
 * @Date 2025/2/5 00:43:03
 * @Description
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface  AuthCheck {
    /**
     * 必须有某个角色
     * @return 角色 权限
     */
    String mustRole() default "";
}
