package com.pintec.springcloud.nacos.discovery.consumer.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @author yang.zhou
 * @date 2019.08.15.13.
 */
@FeignClient("nacos-provider")
public interface NacosProviderFeign {

    @GetMapping(value = "echo")
    String echo(@RequestParam(value = "code") String code);

    @GetMapping(value = "getProperties")
    String getProperties();

    @GetMapping("echoEnv")
    Map<String, Map<String, String>> echoEnv();
}
