package com.pintec.springcloud.nacos.discovery.consumer.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author yang.zhou
 * @date 2019.08.15.13.
 */
@FeignClient("SERVICE-PROVIDER")
public interface ServiceProviderFeign {

    @GetMapping("echo/hasArgs")
    String hasArgs(@RequestParam("message") String message);


    @GetMapping("echo/noArgs")
    String noArgs();
}
