package com.pintec.springcloud.nacos.discovery.provider;


import org.springframework.context.ApplicationContext;

public class SpringContextUtil {
    private static ApplicationContext applicationContext;


    public static ApplicationContext getContext() {
        return applicationContext;
    }

    public static void setApplicationContext(ApplicationContext applicationContext) {
        SpringContextUtil.applicationContext = applicationContext;
    }


    public static <T> T getBean(String beanName) {
        return (T) applicationContext.getBean(beanName);
    }

    public static <T> T getBean(String beanName, Class<T> clazz) {
        return (T) applicationContext.getBean(beanName, clazz);
    }

    public static <T> T getBean(Class<T> clazz) {
        return (T) applicationContext.getBean(clazz);
    }


}
