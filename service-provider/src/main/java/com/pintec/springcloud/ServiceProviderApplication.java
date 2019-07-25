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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
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
            sleepTime();
            return "server provider echo : " + code;
        }


        @GetMapping(value = "getConfig")
        public String getNacosConfig() {
            return serverProviderProperties.toString();
        }


        @GetMapping(value = "gateway/dpemo")
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
            sleepTime();
            return "success";
        }

        /***************************
         *
         *
         * 以下时间，在序列化的过程中，使用的是Jackson序列化，我们统一在里边注册Date LocalDate LocalDateTime三种类型的时间格式化即可
         *
         *
         *
         * ***********************************/
        /**
         * 默认的序列化是jackson，格式是格林威治时间 0时区，时间小了8个小时
         * "2019-06-28T06:55:20.633+0000"
         *
         * @return
         */
        @GetMapping(value = "date")
        public Date now() {
            return new Date();
        }

        /**
         * 时间格式没问题，准确度没问题。
         *
         * @return
         */
        @GetMapping(value = "localDate")
        public LocalDate localDate() {
            return LocalDate.now();
        }

        /**
         * 时间格式调整，时间准确度没问题。
         * 2019-06-28T15:01:05.86
         *
         * @return
         */
        @GetMapping(value = "localDateTime")
        public LocalDateTime localDateTime() {
            return LocalDateTime.now();
        }

        private void sleepTime() {
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
        }
    }
}
