package com.pintec.springcloud.nacos.discovery.consumer;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class NacosDiscoverConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(NacosDiscoverConsumerApplication.class, args);
    }

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


    @FeignClient(value = "nacos-provider")
    public interface ProviderFeignClient {
        @GetMapping(value = "echo")
        String echo(@RequestParam(value = "code") String code);
    }


    @Slf4j
    @RestController
    public static class EchoConsumerController {

        @Autowired
        private RestTemplate restTemplate;

        @Autowired
        private ProviderFeignClient providerFeignClient;

        @GetMapping(value = "echo-feign")
        public String echoByFeign(String code) {
            return providerFeignClient.echo(code);
        }

        @GetMapping(value = "echo-rest")
        public String echoByRestTemplate(String code) {
            return restTemplate.getForObject("http://nacos-provider/echo?code=" + code, String.class);
        }
    }
}
