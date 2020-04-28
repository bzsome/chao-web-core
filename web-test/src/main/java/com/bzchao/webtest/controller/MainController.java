package com.bzchao.webtest.controller;

import com.bzchao.shiro.util.ShiroUtils;
import com.bzchao.shiro.web.Result;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
public class MainController {
    @RequiresPermissions("test")
    @GetMapping("/test")
    public Object test(Map<String, String> paramMap) {
        log.info("启动完成xx");
        return "this is test," + new Gson().toJson(paramMap);
    }

    @GetMapping("/index")
    public Object index(Map<String, String> paramMap) {
        log.info("启动完成xx");
        return "this is test," + new Gson().toJson(paramMap);
    }

    @GetMapping("/user")
    public Object index() {
        return Result.ok(ShiroUtils.getUser());
    }
}
