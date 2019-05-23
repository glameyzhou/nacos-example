package com.pintec.springcloud.nacos.config.client;

import com.google.common.base.Preconditions;
import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ByteArrayResource;

import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class ConfigMap {
    private static final ConcurrentHashMap<String, String> GLOBAL = new ConcurrentHashMap<>(128);

    public final static Long getLong(String key) {
        Preconditions.checkArgument(Strings.isNotBlank(key));
        String value = StringUtils.trim(GLOBAL.get(key));
        return NumberUtils.isDigits(value) ? Longs.tryParse(value) : null;
    }

    public final static Long getLong(String key, Long defaultValue) {
        return ObjectUtils.defaultIfNull(getLong(key), defaultValue);
    }

    public final static Integer getInt(String key) {
        Preconditions.checkArgument(Strings.isNotBlank(key));
        String value = StringUtils.trim(GLOBAL.get(key));
        return NumberUtils.isDigits(value) ? Ints.tryParse(value) : null;
    }

    public final static Integer getInt(String key, Integer defaultValue) {
        return ObjectUtils.defaultIfNull(getInt(key), defaultValue);
    }

    public final static String getString(String key) {
        Preconditions.checkArgument(Strings.isNotBlank(key));
        return GLOBAL.get(key);
    }

    public final static String getString(String key, String defaultValue) {
        return ObjectUtils.defaultIfNull(getString(key), defaultValue);
    }


    public static void flush(String dataId, String data, String fileExtension) {
        if (StringUtils.isBlank(data)) {
            log.warn("remote config content is empty.......");
            GLOBAL.clear();
        }

        Properties properties = new Properties();
        try {
            if (fileExtension.equalsIgnoreCase("properties")) {
                properties.load(new StringReader(data));
            } else if (fileExtension.equalsIgnoreCase("yml") || fileExtension.equalsIgnoreCase("yaml")) {
                YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
                factory.setResources(new ByteArrayResource(data.getBytes()));
                properties = factory.getObject();
            } else {
                log.warn("unsupport the config file extension {}, dataId={}", fileExtension, dataId);
                return;
            }
        } catch (IOException e) {
            log.error("parse data from nacos error,dataId:{},data:{},", dataId, data, e);
            return;
        }

        log.info("new config list is : \n");
        Set<String> keys = properties.stringPropertyNames();
        for (String key : keys) {
            Object valObj = properties.get(key);
            String val = valObj != null ? valObj.toString() : "";
            log.info("{} : {}", key, val);
            String oldVal = GLOBAL.get(key);
            if (!StringUtils.equals(oldVal, val)) {
                GLOBAL.put(key, val);
            }
        }
        log.info("local config map content is {}", GLOBAL);
    }
}
