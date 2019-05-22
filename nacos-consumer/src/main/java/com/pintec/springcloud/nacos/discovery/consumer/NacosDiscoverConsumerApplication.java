package com.pintec.springcloud.nacos.discovery.consumer;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class NacosDiscoverConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(NacosDiscoverConsumerApplication.class, args);
        /*Properties properties = new Properties();
        try {
            NamingService namingService = NamingFactory.createNamingService(properties);
            namingService.registerInstance("serviceName_1", "1.1.1.1", 9091, "test");
            namingService.registerInstance("serviceName_1", "1.1.1.2", 9091, "test");

            namingService.getAllInstances("serviceName_1");


            properties.load(new InputStreamReader(new ByteArrayInputStream("".getBytes(Charsets.UTF_8))));

        } catch (NacosException | IOException e) {
            e.printStackTrace();
        }*/
    }


    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
