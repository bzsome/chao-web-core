package com.bzchao.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@Slf4j
public class TestStartApp {


    public static void main(String[] args) {
        log.info("aaa");

        ApplicationContext ac = SpringApplication.run(TestStartApp.class, args);
    }


}
