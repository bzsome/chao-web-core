package com.bzchao.shiro.config;


import com.bzchao.shiro.service.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Jwt权限控制
 */
@Slf4j
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private JwtService jwtService;



    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     * 此方法有缓存，sessionId管理器，判断是同一sessionId则不再执行此段代码
     * 之前执行方法：createToken(ServletRequest request, ServletResponse response)
     * 之后只执行doGetAuthorizationInfo(PrincipalCollection principalCollection)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authToken) throws AuthenticationException {
        String token = (String) authToken.getPrincipal();
        String username = jwtService.getUsername(token);
        log.debug("doGetAuthenticationInfo:authToken, {} 新的sessionId，重新查询用户信息、权限信息", username);
        ShiroUser shiroUser = jwtService.getUserByName(username);
        if (shiroUser == null) {
            log.warn("invalid token：" + token);
            throw new AuthenticationException("用户名不存在");
        }
        //签名时使用用户密码，这样没有固定的签名秘钥，不存在秘钥泄露安全性更高
        boolean verify = jwtService.verify(token, username, shiroUser.getEnPassword());
        if (!verify) {
            log.warn("invalid token2：" + token);
            throw new AuthenticationException("token不可用");
        }

        return new SimpleAuthenticationInfo(shiroUser, token, ShiroRealm.class.getSimpleName());
    }

    /**
     * 授权模块，获取用户角色和权限
     * 每次访问时需要权限控制的url时,执行此方法
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.debug("doGetAuthorizationInfo:principal");
        //获取登录用户名
        ShiroUser shiroUser = (ShiroUser) principalCollection.getPrimaryPrincipal();
        //添加角色和权限
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        for (String role : shiroUser.getPerms()) {
            //添加角色
            simpleAuthorizationInfo.addRole(role);
            simpleAuthorizationInfo.addStringPermission(role);
        }
        return simpleAuthorizationInfo;
    }

}
