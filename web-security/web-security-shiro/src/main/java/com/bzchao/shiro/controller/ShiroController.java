package com.bzchao.shiro.controller;

import com.bzchao.shiro.config.ShiroUser;
import com.bzchao.shiro.service.JwtService;
import com.bzchao.shiro.util.ShiroUtils;
import com.bzchao.shiro.web.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
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

    @RequiresAuthentication
    @GetMapping("/user")
    @ResponseBody
    public Object user() {
        ShiroUser shiroUser = ShiroUtils.getUser();
        return Result.ok(shiroUser);
    }

    @GetMapping("/anon")
    @ResponseBody
    public Object anon() {
        ShiroUser shiroUser = ShiroUtils.getUser();
        return Result.ok(shiroUser);
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

    /**
     * shiro中配置的401页面
     *
     * @return
     */
    @GetMapping("/403")
    @ResponseBody
    public Object login403() {
        return Result.fail(403, "login403,未登录");
    }
}
