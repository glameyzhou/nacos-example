package com.pintec.springcloud.nacos.config.client;

import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.util.Strings;

import java.util.Iterator;
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
            GLOBAL.clear();
        }

        String[] strings = StringUtils.split(configInfo, "\n");
        if (strings.length == 0) {
            throw new RuntimeException("配置信息解析失败");
        }

        log.info("new config list is : \n");
        for (String string : strings) {
            Iterator<String> iterator = SPLITTER.split(string).iterator();
            String key = iterator.next();
            String value = iterator.next();
            log.info("{} : {}", key, value);
            String oldVal = GLOBAL.get(key);
            if (!StringUtils.equals(value, oldVal)) {
                GLOBAL.put(key, value);
            }
        }
    }
}
