package com.pintec.springcloud.nacos.config.properties;

import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@RefreshScope
@ToString
@Data
public class ThreadPoolProperties implements java.io.Serializable{
    @Value("${threadPool.coreSize}")
    private int coreSize;
    @Value("${threadPool.maxSize}")
    private int maxSize;
    @Value("${threadPool.queueSize}")
    private int queueSize;
    @Value("${threadPool.keepLive}")
    private int keepLive;
}
