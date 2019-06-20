package com.pintec.springcloud.nacos.config.properties;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * 如果没有RefreshScope或者ConfigurationProperties，属性将不能自动刷新
 *
 * @date 2019.06.20.13. yang.zhou
 */
@Getter
@Component
@RefreshScope
public class UnAnnotationProperties {
    @Value("${unAnnotation.name}")
    private String name;
    @Value("${unAnnotation.code}")
    private String code;
    @Value("${unAnnotation.age}")
    private int age;
}
