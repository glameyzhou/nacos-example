package com.pintec.springcloud;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.Map;

@Slf4j
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(EurekaServerApplication.class, args);
        ConfigurableEnvironment environment = context.getEnvironment();

        log.info("System properties ---->");
        printInfo(environment.getSystemProperties());

        log.info("System Environment ---->");
        printInfo(environment.getSystemEnvironment());

    }


    private static void printInfo(Map<String, Object> map) {
        map.forEach((key, value) -> log.info("{} -> {}", key, value));
    }
}
