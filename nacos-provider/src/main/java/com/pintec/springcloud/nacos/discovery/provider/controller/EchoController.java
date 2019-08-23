package com.pintec.springcloud.nacos.discovery.provider.controller;

import com.alibaba.metrics.MetricLevel;
import com.alibaba.metrics.MetricManager;
import com.alibaba.metrics.MetricName;
import com.alibaba.metrics.Timer;
import com.google.common.collect.Maps;
import com.pintec.springcloud.nacos.discovery.provider.monitor.MetricsUtils;
import com.pintec.springcloud.nacos.discovery.provider.properties.EnvProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Enumeration;
import java.util.Map;

/**
 * @author yang.zhou
 * @date 2019.08.15.12.
 */
@RestController
@Slf4j
public class EchoController {
    @Resource
    private EnvProperties envProperties;

    private static final Timer TIMER = MetricManager.getTimer(MetricsUtils.TIMER_GROUP, MetricName.build("nacos-provider.echo").level(MetricLevel.CRITICAL));

    @GetMapping(value = "echo")
    public String echo(@RequestParam(value = "code") String code) {
        Timer.Context time = TIMER.time();
        log.info("nacos provider --> echo {}", code);
        time.stop();
        return code;
    }

    @GetMapping(value = "getProperties")
    public String getProperties() {
        Timer.Context time = TIMER.time();
        String s = envProperties.toString();
        time.stop();
        return s;
    }

    @GetMapping("echoEnv")
    public Map<String, Map<String, String>> echoEnv() {
        Timer.Context time = TIMER.time();
        Map<String, Map<String, String>> map = Maps.newHashMap();
        map.put("env", System.getenv());

        Map<String, String> propertiesMap = Maps.newHashMap();
        Enumeration<?> enumeration = System.getProperties().propertyNames();
        while (enumeration.hasMoreElements()) {
            String k = enumeration.nextElement().toString();
            propertiesMap.put(k, System.getProperty(k));
        }
        map.put("properties", propertiesMap);
        time.stop();
        return map;
    }
}
