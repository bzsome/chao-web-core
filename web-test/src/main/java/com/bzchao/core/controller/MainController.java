package com.bzchao.core.controller;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/index")
public class MainController {
    @GetMapping("/test")
    public Object test(Map<String, String> paramMap) {
        log.info("启动完成xx");
        return "this is test," + new Gson().toJson(paramMap);
    }
}
