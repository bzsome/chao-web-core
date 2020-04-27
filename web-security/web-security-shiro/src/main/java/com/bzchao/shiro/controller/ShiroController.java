package com.bzchao.shiro.controller;

import com.bzchao.shiro.service.JwtService;
import com.bzchao.shiro.web.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/shiro")
public class ShiroController {

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    @ResponseBody
    public Object login(String username, String password) {
        String token = jwtService.generate(username, password);
        if (token == null || "".equals(token)) {
            return Result.fail(401, "用户名或密码错误");
        }
        return Result.ok(token);
    }

    @GetMapping("/login")
    public Object login() {
        log.debug("访问登录页面");
        return "/static/login.html";
    }

    /**
     * shiro中配置的401页面
     *
     * @return
     */
    @GetMapping("/401")
    @ResponseBody
    public Object login401() {
        return Result.fail(401, "login401,未登录");
    }
}
