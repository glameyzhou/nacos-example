package com.pintec.springcloud.nacos.discovery.provider;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
public class NacosDiscoveryProvider {

    public static void main(String[] args) {
        SpringApplication.run(NacosDiscoveryProvider.class, args);
    }

    @Slf4j
    @RestController
    public static class EchoController {
        @GetMapping(value = "echo")
        public String echo(@RequestParam(value = "code") String code) {
            log.info("nacos provider --> echo {}", code);
            return "nacos provider --> echo " + code;
        }
    }
}


