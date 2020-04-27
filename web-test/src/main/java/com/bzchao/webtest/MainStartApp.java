package com.bzchao.webtest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@Slf4j
//扫描shiro包，加载包下的config，bean
@ComponentScan(basePackages = {"com.bzchao.webtest", "com.bzchao.shiro"})
@SpringBootApplication
public class MainStartApp {
    public static void main(String[] args) {
        ApplicationContext ac = SpringApplication.run(MainStartApp.class, args);
        log.info("启动完成");
    }
}
