package com.bzchao.shiro.controller;

import com.bzchao.shiro.config.JwtUser;
import com.bzchao.shiro.service.LoginService;
import com.bzchao.shiro.util.JwtUtil;
import com.bzchao.shiro.web.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/shiro")
public class ShiroController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    @ResponseBody
    public Object login(String username, String password) {
        JwtUser jwtUser = loginService.getUserByName(username);
        if (jwtUser != null && jwtUser.getPassword().equals(password)) {
            return new ResponseEntity<>(JwtUtil.sign(username, password), HttpStatus.OK);
        } else {
            throw new UnauthorizedException();
        }
    }

    @GetMapping("/login")
    public Object login() {
        log.debug("访问登录页面");
        return "/static/login.html";
    }

    /**
     * shiro中配置401时，显示的页面
     *
     * @return
     */
    @GetMapping("/401")
    @ResponseBody
    public Object login401() {
        return Result.fail(401, "login401,未登录");
    }
}
