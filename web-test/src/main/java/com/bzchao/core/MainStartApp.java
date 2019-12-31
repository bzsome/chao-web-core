package com.bzchao.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@Slf4j
@SpringBootApplication
public class MainStartApp {
    public static void main(String[] args) {
        ApplicationContext ac = SpringApplication.run(MainStartApp.class, args);
        log.info("启动完成");
    }
}
