package com.bzchao.web.security.oauth;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "security.oauth")
public class SpringSecurityOauthProperties {
    private String authClientId;
    private String authClientSecret;
}
