package com.pintec.springcloud.nacos.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @date 2019.06.28.17. yang.zhou
 */
@Data
@Component
@ConfigurationProperties(prefix = "remove")
public class RemoveProperties implements java.io.Serializable{


    private String appName;

    private EnvProperties envProperties;

}
