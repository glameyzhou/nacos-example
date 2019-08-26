package com.pintec.springcloud.nacos.discovery.consumer.config;

import com.alibaba.cloud.nacos.ribbon.NacosRule;
import com.netflix.loadbalancer.IRule;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author yang.zhou
 * @date 2019.08.15.13.
 */
@Configuration
public class BeanConfig {

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


    //指定对应的路由规则
    @Bean
    public IRule nacosRue() {
        return new NacosRule();
    }
}
