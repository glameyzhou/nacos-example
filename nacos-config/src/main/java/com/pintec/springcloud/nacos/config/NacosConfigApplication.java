package com.pintec.springcloud.nacos.config;


import com.google.common.collect.ImmutableMap;
import com.pintec.springcloud.nacos.config.properties.AutoboxObject;
import com.pintec.springcloud.nacos.config.properties.PropertiesEcho;
import com.pintec.springcloud.nacos.config.properties.ThreadPoolProperties;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

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

        @Resource
        private AutoboxObject autoboxObject;

        @Value(value = "${spring.cloud.nacos.config.server-addr:}")
        private String serverAddr;

        @Value(value = "${spring.cloud.nacos.config.endpoint:}")
        private String endpoint;

        @Value(value = "${spring.cloud.nacos.config.group:DEFAULT_GROUP}")
        private String group;

        @Value(value = "${spring.cloud.nacos.config.prefix:${spring.application.name}}")
        private String prefix;

        @Value(value = "${spring.application.name}")
        private String applicationName;

        @Value(value = "${spring.profiles.active:dev}")
        private String activeProfile;

        @Value(value = "${spring.cloud.nacos.config.file-extension:properties")
        private String fileExtension;

        @GetMapping(value = "echoConfig")
        public String echoConfig() {
            log.info("nacos config -> {} \r\n ext config -> {}\r\n nested config -> {}, {} \r\n autoBox -> {}",
                    user, threadPoolProperties, propertiesEcho.echoThreadPool(), propertiesEcho.echoRedis(), autoboxObject);
            return String.format("nacos config -> %s <br/> ext config -> %s<br/> nested config -> %s, %s<br/>> autobox config -> %s<br/>",
                    user, threadPoolProperties, propertiesEcho.echoThreadPool(), propertiesEcho.echoRedis(), autoboxObject);
        }

        @GetMapping(value = "echoEnvProperties")
        public ImmutableMap<String, Map<String, Object>> echoProperties(HttpServletRequest request) {
            ConfigurableEnvironment environment = ((ConfigurableWebApplicationContext) RequestContextUtils.findWebApplicationContext(request)).getEnvironment();
            ImmutableMap<String, Map<String, Object>> map = ImmutableMap.of("env", environment.getSystemEnvironment(), "pros", environment.getSystemProperties());
            return map;
        }


        @GetMapping(value = "echoProperties")
        public Map<String, Object> getProperties(String key, HttpServletRequest request) {
            key = StringUtils.trim(key.trim());
            ConfigurableEnvironment environment = ((ConfigurableWebApplicationContext) RequestContextUtils.findWebApplicationContext(request)).getEnvironment();
            MutablePropertySources propertySources = environment.getPropertySources();
            for (PropertySource<?> propertySource : propertySources) {
                if (propertySource.containsProperty(key)) {
                    return ImmutableMap.of("properties", propertySource.getName(), key, propertySource.getProperty(key));
                }
            }
            return ImmutableMap.of("properties", null, key, null);
        }
    }

    @Slf4j
    @RefreshScope
    @Component
    @ToString
    public static class User {
        @Value("${user.code:我是本地@Value中defaultValue的user.code}")
        private String code;

        @Value("${user.age}")
        private int age;

        @Value("${user.content}")
        private String content;
    }
}
