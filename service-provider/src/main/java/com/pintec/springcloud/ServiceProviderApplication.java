package com.pintec.springcloud;


import com.pintec.springcloud.properties.ServerProviderProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@SpringBootApplication
//@EnableDiscoveryClient
public class ServiceProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceProviderApplication.class, args);
    }


    @RestController
    public static class Controller {

        @Resource
        private ServerProviderProperties serverProviderProperties;

        @GetMapping(value = "echo")
        public String echo(@RequestParam(value = "code") String code) {
            log.info("server provider echo -> {}", code);
            return "server provider echo : " + code;
        }


        @GetMapping(value = "getConfig")
        public String getNacosConfig() {
            return serverProviderProperties.toString();
        }
    }
}
