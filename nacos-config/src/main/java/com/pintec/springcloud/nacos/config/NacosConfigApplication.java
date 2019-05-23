package com.pintec.springcloud.nacos.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
public class NacosConfigApplication {
    public static void main(String[] args) {
        SpringApplication.run(NacosConfigApplication.class, args);
    }


    @RestController
    @RefreshScope
    @Slf4j
    public static class ConfigClientController {

        @Value("${user.code}")
        private String code;

        @Value("${user.age}")
        private int age;

        @GetMapping(value = "echoConfig")
        public String echoConfig() {
            log.info("nacos config --> code={}, age={}", code, age);
            return String.format("code=%s, age=%d", code, age);
        }
    }
}
