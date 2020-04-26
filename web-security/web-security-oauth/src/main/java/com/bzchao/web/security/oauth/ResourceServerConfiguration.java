package com.bzchao.web.security.oauth;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

/**
 * 资源服务器
 */
/*@Configuration
@EnableResourceServer*/
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
    private static final String RESOURCE_ID = "my_rest_api";

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(RESOURCE_ID).stateless(false);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.anonymous().and()
                .requestMatchers().antMatchers("/**")
                .and()
                .authorizeRequests().antMatchers("/**").permitAll()
                .and()
                .authorizeRequests().antMatchers("/static/**", "/register/**", "/test/**").permitAll()
                .and()
                .authorizeRequests().antMatchers("/admin/**").access("hasRole('ADMIN')") // pass SPEL using access method
                .and()
                .exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler())
        ;
    }
}
