package com.pintec.springcloud.nacos.discovery.provider.interceptor;

import java.lang.annotation.*;

/**
 * @author yang.zhou
 * @date 2019.08.20.11.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MetricMonitor {
}
