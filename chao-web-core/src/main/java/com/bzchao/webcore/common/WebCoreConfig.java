package com.bzchao.webcore.common;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan({"com.bzchao.webcore"})
public class WebCoreConfig {

}
