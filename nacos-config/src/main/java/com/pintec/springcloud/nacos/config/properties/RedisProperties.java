package com.pintec.springcloud.nacos.config.properties;

import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@RefreshScope
//@ConfigurationProperties(prefix = "redis")
@ToString
@Data
public class RedisProperties implements java.io.Serializable{
    @Value("${redis.serverAddr}")
    private String serverAddr;

    @Value("${redis.db}")
    private String redisDb;
}
