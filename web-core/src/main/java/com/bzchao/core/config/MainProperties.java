package com.bzchao.core.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "main.config", ignoreUnknownFields = true)
public class MainProperties {
    String name;
    Integer age;
}
