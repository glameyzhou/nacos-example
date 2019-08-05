package com.pintec.springcloud.nacos.config.properties;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class PropertiesEcho {

    @Resource
    private User user;

    @Resource
    private ThreadPoolProperties threadPoolProperties;

    @Resource
    private AutoboxObject autoboxObject;


    @Resource
    private RedisProperties redisProperties;


    @Resource
    private UnAnnotationProperties unAnnotationProperties;

    @Resource
    private RemoveProperties removeProperties;


    public User getUser() {
        return user;
    }

    public ThreadPoolProperties getThreadPoolProperties() {
        return threadPoolProperties;
    }

    public AutoboxObject getAutoboxObject() {
        return autoboxObject;
    }

    public RedisProperties getRedisProperties() {
        return redisProperties;
    }

    public UnAnnotationProperties getUnAnnotationProperties() {
        return unAnnotationProperties;
    }

    public RemoveProperties getRemoveProperties() {
        return removeProperties;
    }
}
