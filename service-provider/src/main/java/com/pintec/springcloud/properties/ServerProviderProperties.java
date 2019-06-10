package com.pintec.springcloud.properties;


import com.pintec.springcloud.constants.Constants;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Component
@RefreshScope
@ConfigurationProperties(prefix = Constants.PROPERTIES_PREFIX)
public class ServerProviderProperties {
    private String code;
    private String name;
    private List<String> myList = new ArrayList<>();
    private Map<String, String> myMap = new HashMap<>();
    private List<MyNestedObject> myNestedObjectList = new ArrayList<>();

    private CircuitBreaker circuitBreaker;


    @Data
    public static class MyNestedObject {
        private String name;
        private String email;
        private String address;
    }


    @Data
    public static class CircuitBreaker {
        private Long timeout;
    }


}
