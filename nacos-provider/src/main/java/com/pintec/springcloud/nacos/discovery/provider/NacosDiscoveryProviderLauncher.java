package com.pintec.springcloud.nacos.discovery.provider;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class NacosDiscoveryProviderLauncher {

    public static void main(String[] args) {
        SpringApplication.run(NacosDiscoveryProviderLauncher.class, args);
    }
}


