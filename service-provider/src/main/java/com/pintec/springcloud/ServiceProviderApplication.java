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
import java.util.concurrent.TimeUnit;

@Slf4j
@SpringBootApplication
@EnableDiscoveryClient
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


        @GetMapping(value = "gateway/demo")
        public String forGateway(@RequestParam(value = "code") String code) {
            return new StringBuffer().append("gateway-code -> ").append(code).toString();
        }


        @GetMapping(value = "exception")
        public String exception() {
            boolean isException = System.currentTimeMillis() % 2 == 0;
            log.info("exception test isException -> {}", isException);
            if (isException) {
                throw new RuntimeException("random exception");
            }
            return "success";
        }

        @GetMapping(value = "timeout")
        public String timeout() {
            Long timeout = null;
            ServerProviderProperties.CircuitBreaker circuitBreaker = serverProviderProperties.getCircuitBreaker();
            if (circuitBreaker != null) {
                timeout = circuitBreaker.getTimeout();
            }
            timeout = timeout != null ? timeout.longValue() : 10;
            log.info("timeout test, timeout is {} s", timeout);
            try {
                TimeUnit.SECONDS.sleep(timeout);
            } catch (InterruptedException e) {
                log.error("time sleep error", e);
            }
            return "success";
        }
    }
}
