package com.pintec.springcloud.nacos.discovery.provider.interceptor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @author yang.zhou
 * @date 2019.08.20.11.
 */

@Component
@Aspect
public class MetricMonitorInterceptor {

    public Object doAround(ProceedingJoinPoint pjp) {
        
        return null;
    }
}
