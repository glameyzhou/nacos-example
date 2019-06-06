package com.pintec.springcloud.nacos.config.properties;

import lombok.Data;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@RefreshScope
@Component
@Data
public class Contact {
    private String name;
    private int age;
    private String address;
}
