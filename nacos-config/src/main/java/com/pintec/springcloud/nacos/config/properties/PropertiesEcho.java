package com.pintec.springcloud.nacos.config.properties;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class PropertiesEcho {


    @Resource
    private ThreadPoolProperties threadPoolProperties;

    @Resource
    private RedisProperties redisProperties;


    public String echoThreadPool() {
        return threadPoolProperties.toString();
    }

    public String echoRedis() {
        return redisProperties.toString();
    }
}
