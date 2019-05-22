package com.pintec.springcloud.nacos.discovery.consumer.controller;

import com.pintec.springcloud.nacos.discovery.consumer.feign.ProviderClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RestController
public class EchoConsumerController {


    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ProviderClient providerClient;

    @GetMapping(value = "echo-feign")
    public String echoByFeign(String code) {
        return providerClient.echo(code);
    }


    @GetMapping(value = "echo-rest")
    public String echoByRestTemplate(String code) {
        return restTemplate.getForObject("http://nacos-provider/echo?code=" + code, String.class);
    }
}
