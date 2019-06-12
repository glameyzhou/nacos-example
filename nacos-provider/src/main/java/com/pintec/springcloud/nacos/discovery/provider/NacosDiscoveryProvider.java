package com.pintec.springcloud.nacos.discovery.provider;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;

@SpringBootApplication
@EnableDiscoveryClient
public class NacosDiscoveryProvider {

    public static void main(String[] args) {
        SpringApplication.run(NacosDiscoveryProvider.class, args);
    }

    @Slf4j
    @RestController
    public static class EchoController {

        @Resource
        private EnvProperties envProperties;

        @GetMapping(value = "echo")
        public String echo(@RequestParam(value = "code") String code) {
            log.info("nacos provider --> echo {}", code);
            return "nacos provider --> echo " + code;
        }

        @GetMapping(value = "getProperties")
        public String getProperties() {
            return envProperties.toString();
        }


        @GetMapping(value = "echoPropertie")
        public String outputEnv(String key) {
            ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) (ContextLoader.getCurrentWebApplicationContext());
            String propertyValue = configurableApplicationContext.getEnvironment().getProperty(key);
            return propertyValue;
        }
    }

    @Data
    @Component
    public static class EnvProperties {

        @Value(value = "${provider.name}")
        private String name;

        @Value(value = "${provider.address}")
        private String address;
    }
}


