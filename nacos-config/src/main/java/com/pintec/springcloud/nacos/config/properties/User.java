package com.pintec.springcloud.nacos.config.properties;

import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @author yang.zhou
 * @date 2019.08.05.11.
 */
@Slf4j
@RefreshScope
@Component
@ToString
@Data
public class User implements java.io.Serializable {
    @Value("${user.code:我是本地@Value中defaultValue的user.code}")
    private String code;

    @Value("${user.age}")
    private int age;

    @Value("${user.content}")
    private String content;
}
