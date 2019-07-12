package com.pintec.springcloud.nacos.discovery.consumer;


import feign.Feign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@Import(NacosDiscoverConsumerApplication.ProviderFeignController.class)
public class NacosDiscoverConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(NacosDiscoverConsumerApplication.class, args);
    }

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


    //    @FeignClient(value = "nacos-provider")
    public interface ProviderFeignClient {
        @GetMapping(value = "echo")
        String feignEcho(@RequestParam(value = "code") String code);
    }

    @FeignClient(value = "nacos-provider")
    public interface ProviderFeignClient2 {
        @GetMapping(value = "echo")
        String feignEcho(@RequestParam(value = "code") String code);
    }


    @Import(FeignClientsConfiguration.class)
    public class ProviderFeignController {
        private ProviderFeignClient providerFeignClient;
        private ProviderFeignClient2 providerFeignClient2;

        @Autowired
        public ProviderFeignController(feign.codec.Decoder decoder, feign.codec.Encoder encoder, feign.Client client) {
            providerFeignClient = Feign.builder()
                    .client(client)
                    .decoder(decoder)
                    .encoder(encoder)
                    .contract(new org.springframework.cloud.openfeign.support.SpringMvcContract())
                    .target(ProviderFeignClient.class, "http://nacos-provider");
            providerFeignClient2 = Feign.builder()
                    .client(client)
                    .decoder(decoder)
                    .encoder(encoder)
                    .contract(new org.springframework.cloud.openfeign.support.SpringMvcContract())
                    .target(ProviderFeignClient2.class, "http://nacos-provider");
        }

        @Bean
        public ProviderFeignClient getProviderFeignClient() {
            return this.providerFeignClient;
        }

        @Bean
        public ProviderFeignClient2 getProviderFeignClient2() {
            return this.providerFeignClient2;
        }
    }


    @Slf4j
    @RestController
    public static class EchoConsumerController {

        @Autowired
        private RestTemplate restTemplate;

        @Autowired
        private ProviderFeignClient providerFeignClient;
        @Autowired
        private ProviderFeignClient2 providerFeignClient2;


        @GetMapping(value = "echo-feign")
        public String echoByFeign(String code) {
            return providerFeignClient.feignEcho(code) + " -> " + providerFeignClient2.feignEcho(code);
        }

        @GetMapping(value = "echo-rest")
        public String echoByRestTemplate(String code) {
            return restTemplate.getForObject("http://nacos-provider/echo?code=" + code, String.class);
        }
    }
}
