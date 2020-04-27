package com.bzchao.shiro.config;


import com.bzchao.shiro.service.LoginService;
import com.bzchao.shiro.util.JwtUtil;
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
@Component
public class JwtRealm extends AuthorizingRealm {

    @Autowired
    private LoginService loginService;
    /**
     * 每次访问时，均会执行此方法
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.debug("doGetAuthorizationInfo");
        //获取登录用户名
        JwtUser jwtUser = (JwtUser) principalCollection.getPrimaryPrincipal();
        //添加角色和权限
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        for (String role : jwtUser.getPerms()) {
            //添加角色
            simpleAuthorizationInfo.addRole(role);
            simpleAuthorizationInfo.addStringPermission(role);
        }
        return simpleAuthorizationInfo;
    }

    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     * 此方法有缓存，即同一token只会查询一次
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authToken) throws AuthenticationException {
        log.debug("doGetAuthenticationInfo");
        String token = (String) authToken.getPrincipal();
        String username = JwtUtil.getUsername(token);
        JwtUser jwtUser = loginService.getUserByName(username);
        if (jwtUser == null) {
            log.warn("invalid token：" + token);
            throw new AuthenticationException("用户名不存在");
        }
        //签名时使用用户密码，这样没有固定的签名秘钥，不存在秘钥泄露安全性更高
        boolean verify = JwtUtil.verify(token, username, jwtUser.getPassword());
        if (!verify) {
            log.warn("invalid token2：" + token);
            throw new AuthenticationException("token不可用");
        }
        return new SimpleAuthenticationInfo(jwtUser, token, JwtRealm.class.getSimpleName());
    }
}
