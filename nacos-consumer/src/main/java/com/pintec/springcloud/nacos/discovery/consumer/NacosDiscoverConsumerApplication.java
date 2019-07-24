package com.pintec.springcloud.nacos.discovery.consumer;


import com.alibaba.ttl.threadpool.TtlExecutors;
import com.google.common.collect.Lists;
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

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
//@Import(NacosDiscoverConsumerApplication.ProviderFeignController.class)
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
        String feignEcho(@RequestParam(value = "code") String code);
    }

    //    @FeignClient(value = "nacos-provider")
    public interface ProviderFeignClient2 {
        @GetMapping(value = "echo")
        String feignEcho(@RequestParam(value = "code") String code);
    }


    /*@Import(FeignClientsConfiguration.class)
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
    }*/


    @Slf4j
    @RestController
    public static class EchoConsumerController {

        @Autowired
        private RestTemplate restTemplate;

        @Autowired
        private ProviderFeignClient providerFeignClient;
//        @Autowired
//        private ProviderFeignClient2 providerFeignClient2;


        @GetMapping(value = "echo-feign")
        public String echoByFeign(String code) {
            String result = providerFeignClient.feignEcho(code);
            log.info("echo feign code = {}, response = {}", code, result);
            return result;
        }

        @GetMapping(value = "echo-rest")
        public String echoByRestTemplate(String code) {
            String result = restTemplate.getForObject("http://nacos-provider/echo?code=" + code, String.class);
            log.info("echo rest code = {}, response = {}", code, result);
            return result;
        }


        @GetMapping("concurrent")
        public String concurrent() {

            providerFeignClient.feignEcho("demo init");
//            ExecutorService executorService = Executors.newFixedThreadPool(10);
            ExecutorService executorService = TtlExecutors.getTtlExecutorService(Executors.newFixedThreadPool(10));
           /* List<CompletableFuture<String>> completableFutures = Lists.newArrayListWithCapacity(100);
            for (int i = 0; i < 100; i++) {
                final String code = "code_" + i + "_code";
                CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
                    Thread thread = Thread.currentThread();
                    String response = providerFeignClient.feignEcho(code);
                    log.info("threadId={}, request={}, response={}", thread.getId() + thread.getName(), code, response);
                    return response;
                }, executorService);
                completableFutures.add(completableFuture);
            }
            CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[completableFutures.size()])).join();*/


            for (int i = 0; i < 30; i++) {
                final String code = "code_" + i + "_code";
                executorService.submit(() -> {
                    Thread thread = Thread.currentThread();
                    String response = providerFeignClient.feignEcho(code);
                    log.info("threadId={}, request={}, response={}", thread.getId() + thread.getName(), code, response);
                    return response;
                });
            }
            return "concurrent ok";
        }
    }
}
