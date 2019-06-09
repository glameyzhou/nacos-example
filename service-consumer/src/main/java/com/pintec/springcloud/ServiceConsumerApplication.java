package com.pintec.springcloud;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@Slf4j
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class ServiceConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceConsumerApplication.class, args);
    }


    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @RestController
    public static class Controller {

        @Resource
        private FeignService feignService;

        @Resource
        private RestTemplate restTemplate;

        @GetMapping(value = "echo/feign")
        public String echo_feign(@RequestParam(value = "code") String code) {
            return feignService.echo(code);
        }

        @GetMapping(value = "echo/rest")
        public String echo_rest(@RequestParam(value = "code") String code) {
            return restTemplate.getForObject("http://server-provider/echo?code=" + code, String.class);
        }


    }


    @FeignClient("server-provider")
    public interface FeignService {
        @GetMapping(value = "echo")
        String echo(String code);
    }
}
