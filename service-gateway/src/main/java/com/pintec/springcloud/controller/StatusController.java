package com.pintec.springcloud.controller;

import com.google.common.collect.ImmutableMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author yang.zhou
 * @date 2019.08.20.14.
 */
@RestController
public class StatusController {

    @GetMapping("/Status/Version")
    public Map<String, String> status() {
        return ImmutableMap.of("version", "1.0.1", "status", "ok");
    }
}
