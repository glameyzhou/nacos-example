package com.pintec.springcloud.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yang.zhou
 * @date 2019.08.20.14.
 */
@RestController
public class GatewayController {

    @GetMapping("echoGet")
    public String echoGet(String message) {
        return String.format("%s -> %s", "get", message);
    }


    @PostMapping("echoPost")
    public String echoPost(String message) {
        return String.format("%s -> %s", "post", message);
    }
}
