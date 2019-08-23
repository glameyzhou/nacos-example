package com.pintec.springcloud.nacos.discovery.consumer.controller;

import com.pintec.springcloud.nacos.discovery.consumer.feign.NacosProviderFeign;
import com.pintec.springcloud.nacos.discovery.consumer.feign.ServiceProviderFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author yang.zhou
 * @date 2019.08.15.13.
 */
@Slf4j
@RestController
public class EchoController {

    @Resource
    private NacosProviderFeign nacosProviderFeign;

    @Resource
    private ServiceProviderFeign serviceProviderFeign;

    @GetMapping("/consumer/nacos-provider/echo")
    public String echo(String message) {
        return nacosProviderFeign.echo(message);
    }

    @GetMapping("/consumer/nacos-provider/echoEnv")
    public Map<String, Map<String, String>> echoEnv() {
        return nacosProviderFeign.echoEnv();
    }

    @GetMapping("consumer/service-provider/echo/hasArgs")
    public String echoMessage(String message) {
        return serviceProviderFeign.hasArgs(message);
    }

    @GetMapping("consumer/service-provider/echo/noArgs")
    public String noArgs() {
        return serviceProviderFeign.noArgs();
    }
}
