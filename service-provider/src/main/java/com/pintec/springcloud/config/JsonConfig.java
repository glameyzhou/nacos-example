package com.pintec.springcloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @date 2019.06.28.15. yang.zhou
 */
@Configuration
public class JsonConfig {

    /*@Bean
    public MappingJackson2HttpMessageConverter restMappingJackson2HttpMessageConverter() {
        JavaTimeModule module = new JavaTimeModule();
        module
                .addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern("HH:mm:ss")));

        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter(Jackson2ObjectMapperBuilder.json().modules(module).build());
        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(
                Lists.newArrayList(MediaType.APPLICATION_JSON_UTF8, MediaType.APPLICATION_OCTET_STREAM));
        return mappingJackson2HttpMessageConverter;
    }*/


    /*@Bean
    public Converter<Date, String> addDateConvert() {
        return source -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(source);
    }

    @Bean
    public Converter<LocalDate, String> addLocalDateConvert() {
        return source -> source.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    @Bean
    public Converter<LocalDateTime, String> addLocalDateTimeConvert() {
        return source -> source.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @Bean
    public Converter<LocalTime, String> addLocalTimeConvert() {
        return source -> source.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }*/
}
