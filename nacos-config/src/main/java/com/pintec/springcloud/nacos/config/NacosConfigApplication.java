package com.pintec.springcloud.nacos.config;


import com.pintec.springcloud.nacos.config.properties.PropertiesEcho;
import com.pintec.springcloud.nacos.config.properties.ThreadPoolProperties;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@SpringBootApplication
@EnableDiscoveryClient
public class NacosConfigApplication {
    public static void main(String[] args) {
        SpringApplication.run(NacosConfigApplication.class, args);
    }


    @RestController
    @Slf4j
    public static class ConfigClientController {
        @Resource
        private User user;

        @Resource
        private ThreadPoolProperties threadPoolProperties;

        @Resource
        private PropertiesEcho propertiesEcho;

        @Value(value = "${spring.cloud.nacos.config.server-addr:}")
        private String serverAddr;

        @Value(value = "${spring.cloud.nacos.config.endpoint:}")
        private String endpoint;

        @Value(value = "${spring.cloud.nacos.config.group:DEFAULT_GROUP}")
        private String group;

        @Value(value = "${spring.cloud.nacos.config.prefix}")
        private String prefix;

        @Value(value = "${spring.application.name}")
        private String applicationName;

        @Value(value = "${spring.profiles.active:dev}")
        private String activeProfile;

        @Value(value = "${spring.cloud.nacos.config.file-extension:properties")
        private String fileExtension;

        @GetMapping(value = "echoConfig")
        public String echoConfig() {
            log.info("nacos config -> {} \r\n ext config -> {}\r\n nested config -> {}, {}",
                    user, threadPoolProperties, propertiesEcho.echoThreadPool(), propertiesEcho.echoRedis());
            return String.format("nacos config -> %s <br/> ext config -> %s<br/> nested config -> %s, %s<br/>",
                    user, threadPoolProperties, propertiesEcho.echoThreadPool(), propertiesEcho.echoRedis());
        }
    }

    @Slf4j
    @RefreshScope
    @Component
    @ToString
    public static class User {
        @Value("${user.code}")
        private String code;

        @Value("${user.age}")
        private int age;
    }
}
