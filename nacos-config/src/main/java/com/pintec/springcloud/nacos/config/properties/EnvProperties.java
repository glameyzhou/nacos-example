package com.pintec.springcloud.nacos.config.properties;

import lombok.Data;

import java.util.List;

/**
 * @date 2019.06.28.17. yang.zhou
 */
@Data
public class EnvProperties {
    private String envName;
    private String address;
    private List<String> emailList;
}
