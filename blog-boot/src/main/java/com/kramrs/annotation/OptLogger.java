package com.kramrs.annotation;

import java.lang.annotation.*;

/**
 * @Author: kramrs
 * @Description: 操作日志注解
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OptLogger {

    /**
     * @return 操作描述
     */
    String value() default "";

}
