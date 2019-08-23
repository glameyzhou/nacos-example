package com.pintec.springcloud.controller;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author yang.zhou
 * @date 2019.08.15.13.
 */
@RestController
public class EchoController {

    @GetMapping("echo/hasArgs")
    public String hasArgs(@RequestParam("message") String message) {
        return message;
    }


    @GetMapping("echo/noArgs")
    public String noArgs() {
        return DateFormatUtils.format(new Date(), "yyyy-MM-dd");
    }
}
