package com.bzchao.webcore.common.config;

import com.bzchao.webcore.common.auth.handler.LoginAuthInterceptor;
import com.bzchao.webcore.common.auth.handler.RequestIdInterceptor;
import com.bzchao.webcore.common.constants.UrlFilters;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

/**
 * Spring Boot 2.0 解决跨域问题
 */
@Slf4j
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Value("${spring.resource.excludeUrls}")
    private List<String> excludeUrls;
    @Value("${spring.resource.static-locations}")
    private List<String> staticLocations;

    @Bean
    public LoginAuthInterceptor myInterceptor() {
        return new LoginAuthInterceptor();
    }

    @Bean
    public RequestIdInterceptor requestIdInterceptor() {
        return new RequestIdInterceptor();
    }

    @Bean
    public CorsFilter corsFilter() {
        final CorsConfiguration corsConfiguration = new CorsConfiguration();
        /* 是否允许请求带有验证信息 */
        corsConfiguration.setAllowCredentials(true);
        /* 允许服务端访问的客户端请求头 */
        corsConfiguration.addAllowedHeader("*");
        /* 允许访问的方法名,GET POST等 */
        corsConfiguration.addAllowedMethod("*");
        /* 允许访问的客户端域名 */
        corsConfiguration.addAllowedOriginPattern("*");

        final UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);

        return new CorsFilter(urlBasedCorsConfigurationSource);
    }

    /**
     * 静态资源的配置 - 使得可以从磁盘中读取 Html、图片、视频、音频等
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations(staticLocations.toArray(new String[]{}));
    }

    /**
     * 添加登录控制权限拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //请求ID过滤器
        registry.addInterceptor(requestIdInterceptor());

        //登录过滤器
        log.info("url.excludes: {}", Arrays.toString(excludeUrls.toArray()));
        registry.addInterceptor(myInterceptor())
                .excludePathPatterns(excludeUrls)
                .excludePathPatterns(UrlFilters.filterUrls);
    }

}
