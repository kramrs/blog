package com.kramrs.annotation;

import java.lang.annotation.*;

/**
 * @Author: kramrs
 * @Description: 访问日志注解
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface VisitLogger {

    /**
     * @return 访问页面
     */
    String value() default "";
}
