package com.pintec.springcloud;


import com.google.common.collect.Maps;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

@Slf4j
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableCircuitBreaker  //开启熔断器
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
        @Value("${spring.application.name}")
        private String applicationName;

        @Resource
        private FeignService feignService;

        @Resource
        private RestTemplate restTemplate;

        @GetMapping(value = "echo/feign")
        public String echo_feign(@RequestParam(value = "code") String code) {
            return applicationName + " -> " + feignService.echo(code);
        }

        //可以设置到全局配置中，或者针对某一个groupkey进行设置
        @HystrixCommand(fallbackMethod = "fallback_echo_rest",
                commandKey = "demo_command_key",
//                commandProperties = {
//                        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "500")
//                },
                threadPoolKey = "demo_thread_pool_key",
                threadPoolProperties = {
                        @HystrixProperty(name = "coreSize", value = "30"),
                        @HystrixProperty(name = "maxQueueSize", value = "101"),
                        @HystrixProperty(name = "keepAliveTimeMinutes", value = "2"),
                        @HystrixProperty(name = "queueSizeRejectionThreshold", value = "15"),
                        @HystrixProperty(name = "metrics.rollingStats.numBuckets", value = "12"),
                        @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "1440")
                })
        @GetMapping(value = "echo/rest")
        public String echo_rest(@RequestParam(value = "code") String code) {
            return restTemplate.getForObject("http://service-provider/echo?code=" + code, String.class);
        }

        /**
         * 熔断：如果服务提供者出现问题，直接返回mock的固定内容
         *
         * @param code
         * @return
         */
        public String fallback_echo_rest(String code) {
            return "hystrix error response -> " + code;
        }


        /**
         * {@link com.netflix.hystrix.contrib.javanica.aop.aspectj.HystrixCommandAspect}
         *
         * @return
         * @HystrixCommand 注解配置的方式是使用了AOP动态的生成了一个 HystrixInvokable 对象，通过调用HystrixInvokable的方法实现了HystrixCommand的功能。
         */
       /* @HystrixCommand(fallbackMethod = "fallback_exception",
                commandKey = "exception_commandKey",
                commandProperties = {
                        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
                },
                threadPoolKey = "exception_threadPoolKey",
                threadPoolProperties = {
                        @HystrixProperty(name = "coreSize", value = "30"),
                        @HystrixProperty(name = "maxQueueSize", value = "101"),
                        @HystrixProperty(name = "keepAliveTimeMinutes", value = "2"),
                        @HystrixProperty(name = "queueSizeRejectionThreshold", value = "15"),
                        @HystrixProperty(name = "metrics.rollingStats.numBuckets", value = "12"),
                        @HystrixProperty(name = "metrics.rollingStats.timeInMdinilliseconds", value = "1440")
                })*/
        @GetMapping(value = "exception")
        public String exception() {
            return restTemplate.getForObject("http://service-provider/exception", String.class);
        }

        public String fallback_exception() {
            return "fallback_exception";
        }


        @GetMapping("date")
        public Map<String, Object> date() {
            Map<String, Object> map = Maps.newHashMap();
            map.put("date", feignService.now());
            map.put("localDate", feignService.localDate());
            map.put("localDateTime", feignService.localDateTime());
            return map;
        }
    }


    @FeignClient("service-provider")
    public interface FeignService {
        @GetMapping(value = "echo")
        String echo(@RequestParam("code") String code);


        @GetMapping(value = "date")
        Date now();

        @GetMapping(value = "localDate")
        LocalDate localDate();

        @GetMapping(value = "localDateTime")
        LocalDateTime localDateTime();
    }
}
