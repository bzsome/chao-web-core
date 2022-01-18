package com.bzchao.webcore.common.config;

import com.bzchao.webcore.common.util.JsonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JsonConfig {
    @Autowired
    private ObjectMapper objectMapper;

    @Bean
    public void jsonConfiguration() {
        JsonUtils.setObjectMapper(objectMapper);
    }
}
