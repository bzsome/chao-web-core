package com.bzchao.core.config.security.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

/**
 * 认证服务器配置
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
    private static final String REALM = "MY_OAUTH_REALM";

    @Autowired
    DataSource dataSource;
    @Autowired
    private TokenStore tokenStore;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Value("${security.userOauth.clientId}")
    private String authClientId;
    @Value("${security.userOauth.clientSecret}")
    private String authClientSecret;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                //客户端ID //密码
                //新版本必须要对密码secret进行加密
                .withClient(authClientId).secret(passwordEncoder.encode(authClientSecret))
                .authorizedGrantTypes("password", "authorization_code", "refresh_token", "implicit")
                .authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")
                //授权用户的操作权限
                .scopes("read", "write", "trust")
                //token有效期为1小时
                .accessTokenValiditySeconds(3600 * 24);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore).authenticationManager(authenticationManager);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer.realm(REALM + "/client");
    }

    @Bean
    public TokenStore tokenStore() {
        //Tokenn储存方式，默认使用数据库的方式
        return new JdbcTokenStore(dataSource);
    }
}
