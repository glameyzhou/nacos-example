package com.pintec.springcloud.nacos.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 内嵌的数组或者map，通过@Value方式无法获取
 * <p>
 * https://blog.csdn.net/thc1987/article/details/78789426
 */
@RefreshScope
@Component
@Data
@ConfigurationProperties(prefix = "autobox")
public class AutoboxObject {
    private String code;
    private List<String> emailList = new ArrayList<>();
    private Map<String, String> mappings = new HashMap<>();
    private List<Contact> contactList = new ArrayList<>();
}
