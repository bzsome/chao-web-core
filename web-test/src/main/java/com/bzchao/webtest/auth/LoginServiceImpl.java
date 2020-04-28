package com.bzchao.webtest.auth;


import com.bzchao.shiro.config.ShiroUser;
import com.bzchao.shiro.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl extends JwtService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private static final String tokenPrefix = "WebChao:token:";

    @Override
    public ShiroUser getUserByName(String username) {
        //模拟数据库查询，正常情况此处是从数据库或者缓存查询。
        return getMapByName(username);
    }

    /**
     * 模拟数据库查询
     *
     * @param username
     * @return
     */
    private ShiroUser getMapByName(String username) {
        //共添加两个用户，两个用户都是admin一个角色，
        //wsl有query和add权限，zhangsan只有一个query权限
        Map<String, ShiroUser> userMap = new HashMap<>();

        Set<String> permissionsSet = new HashSet<>();
        permissionsSet.add("query");
        permissionsSet.add("add");
        ShiroUser shiroUser = new ShiroUser("1", "admin", "123456", permissionsSet);
        userMap.put(shiroUser.getUsername(), shiroUser);

        Set<String> permissionsSet1 = new HashSet<>();
        permissionsSet1.add("query");
        ShiroUser shiroUser1 = new ShiroUser("2", "test", "123456", permissionsSet1);
        userMap.put(shiroUser1.getUsername(), shiroUser1);

        return userMap.get(username);
    }

    @Override
    public void saveCache(String username, String token) {
        // stringRedisTemplate.opsForValue().set(tokenPrefix + username, token, expireTime, TimeUnit.SECONDS);
    }

    //验证缓存中是否有token（可选实现）
    public boolean verifyByCache(String username, String token) {
      /*  String cacheToken = stringRedisTemplate.opsForValue().get(tokenPrefix + username);
        return token.equals(cacheToken);*/
        return true;
    }

    //验证缓存中删除有token（可选实现）
    public void removeByCache(String username) {
        stringRedisTemplate.delete(tokenPrefix + username);
    }
}