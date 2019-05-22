package com.pintec.springcloud.nacos.discovery.consumer.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "nacos-provider")
public interface ProviderClient {

    @GetMapping(value = "echo")
    String echo(@RequestParam(value = "code") String code);
}
