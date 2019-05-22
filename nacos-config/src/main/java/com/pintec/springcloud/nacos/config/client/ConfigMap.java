package com.pintec.springcloud.nacos.config.client;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.util.Strings;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class ConfigMap {
    private static final ConcurrentHashMap<String, String> GLOBAL = new ConcurrentHashMap<>(128);
    private static final Splitter SPLITTER = Splitter.on("=");

    public final static Long getLong(String key) {
        Preconditions.checkArgument(Strings.isNotBlank(key));
        String value = StringUtils.trim(GLOBAL.get(key));
        return NumberUtils.isDigits(value) ? Longs.tryParse(value) : null;
    }

    public final static Long getLong(String key, Long defaultValue) {
        Long value = getLong(key);
        return value == null ? defaultValue : value;
    }

    public final static Integer getInt(String key) {
        Preconditions.checkArgument(Strings.isNotBlank(key));
        String value = StringUtils.trim(GLOBAL.get(key));
        return NumberUtils.isDigits(value) ? Ints.tryParse(value) : null;
    }

    public final static Integer getInt(String key, Integer defaultValue) {
        Integer value = getInt(key);
        return value == null ? defaultValue : value;
    }

    public final static String getString(String key) {
        Preconditions.checkArgument(Strings.isNotBlank(key));
        return GLOBAL.get(key);
    }

    public final static String getString(String key, String defaultValue) {
        String value = getString(key);
        return StringUtils.isBlank(value) ? defaultValue : value;
    }


    public static void flush(String configInfo) {
        if (StringUtils.isBlank(configInfo)) {
            log.warn("remote config content is empty.......");
            GLOBAL.clear();
        }
        //通过properties来接收对应的字符串
        Properties properties = new Properties();
        try {
            log.info("new config list is : \n");
            properties.load(new InputStreamReader(new ByteArrayInputStream(configInfo.getBytes(Charsets.UTF_8))));

        } catch (IOException e) {
            log.error("read the remote config info error[string convert to properties]", e);
        }
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
