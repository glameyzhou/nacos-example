package com.pintec.springcloud.nacos.config.client;


import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 通过java客户端来自动更新日志
 */
@Component
@Slf4j
public class ConfigClient {
    @Value(value = "${spring.cloud.nacos.config.server-addr}")
    private String serverAddr;

    @Value(value = "${spring.cloud.nacos.config.group:DEFAULT_GROUP}")
    private String group;

    @Value(value = "${spring.application.name}")
    private String applicationName;

    @Value(value = "${spring.profiles.active}")
    private String activeProfile;

    @PostConstruct
    public void init() {
        if (StringUtils.isBlank(serverAddr)) {
            log.warn("检测到未配置nacos config server address，请确认是否开启nacos config配置");
            return;
        }

        String dataId = generateDataId();
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.SERVER_ADDR, serverAddr);

        log.info("server-addr:{}, dataId:{}, group:{}", serverAddr, dataId, group);

        try {
            ConfigService configService = NacosFactory.createConfigService(properties);
            String configContent = configService.getConfig(dataId, group, 5000); //获取服务器段的配置，超时设置5S
            log.info("get Config by interface -->\n {}", configContent);


            configService.addListener(dataId, group, new Listener() {
                @Override
                public Executor getExecutor() {
                    return Executors.newSingleThreadExecutor(r -> {
                        Thread thread = new Thread(r);
                        thread.setName("getRemoteConfigThread");
                        return thread;
                    });
                }

                @Override
                public void receiveConfigInfo(String configInfo) {
                    log.info("remote the content by listener ->\n{}", configInfo);
                    ConfigMap.flush(configInfo);
                }
            });

        } catch (NacosException e) {
            //terminal the application start...
            throw new RuntimeException("service client create NacosConfigService error", e);
        }
    }

    private String generateDataId() {
        if (StringUtils.isBlank(activeProfile)) {
            return applicationName + ".properties";
        }
        return applicationName + "-" + activeProfile + ".properties";
    }
}
