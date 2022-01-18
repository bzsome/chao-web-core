package com.bzchao.webcore.common.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.bzchao.**.mapper")
public class MybatisPlusConfig {

}
