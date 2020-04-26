package com.bzchao.shiro.controller;

import com.bzchao.shiro.config.LoginService;
import com.bzchao.shiro.config.User;
import com.bzchao.shiro.util.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public Object login(String username, String password) {
        User user = loginService.getUserByName(username);
        if (user != null && user.getPassword().equals(password)) {
            return new ResponseEntity<>(JWTUtil.sign(username, password), HttpStatus.OK);
        } else {
            throw new UnauthorizedException();
        }
    }

}
