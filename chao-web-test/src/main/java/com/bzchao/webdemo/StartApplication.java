package com.bzchao.webdemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;

@Slf4j
@SpringBootApplication
public class StartApplication {
    public static void main(String[] args) throws Exception {
        ApplicationContext ac = SpringApplication.run(StartApplication.class, args);
        showUrls(ac);
    }

    public static void showUrls(ApplicationContext applicationContext) throws Exception {
        Environment env = applicationContext.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = env.getProperty("server.port");
        String path = env.getProperty("server.servlet.context-path");
        log.info("\n----------------------------------------------------------\n" +
                "\tApplication is running! Access URLs:\n" +
                "\tServer: \thttp://" + ip + ":" + port + path + "/\n" +
                "\tSwagger文档: \thttp://" + ip + ":" + port + path + "/doc.html\n" +
                "\t应用服务测试地址: \thttp://" + ip + ":" + port + path + "/dist/index.html\n" +
                "----------------------------------------------------------");
    }
}
