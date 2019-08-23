package com.pintec.springcloud.controller;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.pintec.springcloud.interceptor.Log;
import com.pintec.springcloud.model.DemoDomain;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Map;

/**
 * @date 2019.07.24.13. yang.zhou
 */
@RestController
@RequestMapping("service-provider")
public class DemoController {

    private static final Logger logger = LoggerFactory.getLogger(DemoController.class);


    @GetMapping("echoGet")
    public String echoGet(String message) {
        return "echo get " + message;
    }

    @PostMapping("echoPost")
    public String echoPost(String message) {
        return "echo post " + message;
    }


    //测试demo,不用关注filePath的安全性问题
    @Log
    @GetMapping("demo/fileDownload")
    public void fileDownload(String filePath, HttpServletRequest request, HttpServletResponse response) {
        logger.info("[file download] {}", filePath);

        File file = new File(filePath);
        if (!file.exists()) {
            response.setHeader("message", filePath + " is not exist");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            OutputStream outputStream = null;
            try {
                response.setContentType("application/octet-stream;charset=UTF-8");
                String filename = new String(file.getName().getBytes("UTF-8"), "ISO8859-1");
                response.addHeader("Content-Disposition", "attachment;filename=" + filename);
                outputStream = response.getOutputStream();
                outputStream.write(FileUtils.readFileToByteArray(file));
                outputStream.flush();
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            } finally {
                if (outputStream != null) {
                    IOUtils.closeQuietly(outputStream);
                }
            }
        }
    }


    //    @Log
    @GetMapping("printMessage")
    public Map<String, Object> printMessage(String message) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("date", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        map.put("message", message);
        return map;
    }


    @Log
    @GetMapping("printByteData")
    public Map<String, Object> printByteData(String message) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("message", message);
        map.put("bytes", new byte[10]);
        return map;
    }

    @Log
    @GetMapping("printBody")
    public Map<String, Object> printByteData(@RequestBody DemoDomain domain) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("message", new Gson().toJson(domain));
        return map;
    }

    /**
     * post application/json;charsets=UTF-8
     * 如果拦截器中消费了request.getInputStream的内容，那么后续将无法获取到任何东西
     *
     * @param request
     * @return
     * @throws IOException
     */
    @Log
    @GetMapping("extractRequest")
    public Map<String, Object> extractRequest(HttpServletRequest request) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String requestBody = IOUtils.toString(inputStream);
        Map<String, Object> map = Maps.newHashMap();
        map.put("message", requestBody);
        return map;
    }


    @Log
    @GetMapping("/path/{name}/{code}")
    public Map<String, Object> path(@PathVariable(value = "name") String name, @PathVariable(value = "code") String code) {
        logger.info("path {}/{}", name, code);
        return ImmutableMap.of("name", name, "code", code);
    }


    @Log
    @GetMapping("viewFile")
    public void viewFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletOutputStream outputStream = response.getOutputStream();
        InputStream inputStream = IOUtils.toInputStream("asbasdfasdf", Charsets.UTF_8.name());
        IOUtils.copy(inputStream, outputStream);
        IOUtils.closeQuietly(inputStream);
        IOUtils.closeQuietly(outputStream);
    }
}
