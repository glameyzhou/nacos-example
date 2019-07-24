package com.pintec.springcloud.interceptor;

import java.lang.annotation.*;

/**
 * @date 2019.07.24.13. yang.zhou
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
}
