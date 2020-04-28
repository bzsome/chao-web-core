package com.bzchao.shiro.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@ConfigurationProperties("shiro")
@Configuration
public class shiroConfig {
    private String excludeUrls;


    //不加这个注解不生效，具体不详
    @Bean
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAAP = new DefaultAdvisorAutoProxyCreator();
        defaultAAP.setProxyTargetClass(true);
        return defaultAAP;
    }

    //权限管理，配置主要是Realm的管理认证
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(shiroRealm());
        securityManager.setSubjectDAO(subjectDAO());
        securityManager.setSubjectFactory(subjectFactory());
        return securityManager;
    }

    private DefaultSubjectDAO subjectDAO() {
        //禁用sessionId，否则可能导致使用了不同的token，却读取session判断为上一个用户
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        return subjectDAO;
    }

    //Filter工厂，设置对应的过滤条件和跳转条件
    @Bean
    public ShiroFilterFactoryBean factoryBean(SecurityManager securityManager) {
        log.info("factoryBean");
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(securityManager);

        // 添加自己的过滤器并且取名为jwt
        Map<String, Filter> filterMap = new HashMap<>();
        filterMap.put("jwt", new ShiroFilter());
        factoryBean.setFilters(filterMap);
        factoryBean.setFilterChainDefinitionMap(filterChainMap());

        // 未授权界面返回JSON
        factoryBean.setUnauthorizedUrl("/shiro/403");
        factoryBean.setLoginUrl("/shiro/403");
        return factoryBean;
    }

    private Map<String, String> filterChainMap() {
        Map<String, String> filterChainMap = new HashMap<>();
        //所有请求默认使用我们的过滤器
        filterChainMap.put("/**", "jwt");
        // 不通过我们的Filter
        filterChainMap.put("/shiro/**", "anon");
        filterChainMap.put("/static/**", "anon");
        filterChainMap.put("/druid/**", "anon");
        filterChainMap.put("/actuator/**", "anon");
        filterChainMap.put("/swagger-ui.html", "anon");
        filterChainMap.put("/swagger-resources/**", "anon");
        filterChainMap.put("/swagger/**", "anon");
        filterChainMap.put("/v2/api-docs", "anon");
        filterChainMap.put("/favicon.ico", "anon");
        filterChainMap.put("/webjars/springfox-swagger-ui/**", "anon");
        // 拦截器
        if (!StringUtils.isEmpty(excludeUrls)) {
            String[] permissionUrl = excludeUrls.split(",");
            for (String url : permissionUrl) {
                filterChainMap.put(url, "anon");
            }
        }
        return filterChainMap;
    }

    //加入注解的使用，不加入这个注解不生效
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    @Bean
    public StatelessDefaultSubjectFactory subjectFactory() {
        StatelessDefaultSubjectFactory subjectFactory = new StatelessDefaultSubjectFactory();
        return subjectFactory;
    }


    /**
     * 配置shiro redisManager
     * <p>
     * 使用的是shiro-redis开源插件
     *
     * @return
     */
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost("alivps.bzchao.com");
        return redisManager;
    }

    /**
     * cacheManager 缓存 redis实现
     * <p>
     * 使用的是shiro-redis开源插件
     *
     * @return
     */
    @Bean
    public RedisCacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        // 必须要设置主键名称，shiro-redis 插件用过这个缓存用户信息
        redisCacheManager.setPrincipalIdFieldName("username");
        return redisCacheManager;
    }

    @Bean
    public AuthorizingRealm shiroRealm() {
        ShiroRealm shiroRealm = new ShiroRealm();
        shiroRealm.setCachingEnabled(true);
        shiroRealm.setAuthenticationCachingEnabled(true);
        shiroRealm.setAuthorizationCachingEnabled(true);
        shiroRealm.setCacheManager(cacheManager());
        return shiroRealm;
    }

}