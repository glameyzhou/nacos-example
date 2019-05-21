package com.pintec.springcloud.nacos.discovery.provider.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class EchoController {


    @GetMapping(value = "echo")
    public String echo(@RequestParam(value = "code") String code) {
        log.info("nacos provider --> echo {}", code);
        return "nacos provider --> echo " + code;
    }
}
