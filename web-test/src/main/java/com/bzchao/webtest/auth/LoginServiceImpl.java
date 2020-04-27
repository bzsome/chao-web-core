package com.bzchao.webtest.auth;


import com.bzchao.shiro.config.ShiroUser;
import com.bzchao.shiro.service.JwtService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class LoginServiceImpl extends JwtService {

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
        Set<String> permissionsSet = new HashSet<>();
        permissionsSet.add("query");
        permissionsSet.add("add");
        ShiroUser shiroUser = new ShiroUser("1", "admin", "123456",  permissionsSet);
        Map<String, ShiroUser> map = new HashMap<>();
        map.put(shiroUser.getUsername(), shiroUser);

        Set<String> permissionsSet1 = new HashSet<>();
        permissionsSet1.add("query");
        ShiroUser shiroUser1 = new ShiroUser("2", "zhangsan", "123456",  permissionsSet1);
        map.put(shiroUser1.getUsername(), shiroUser1);

        return map.get(username);
    }

    @Override
    public void saveCache(String username, String token) {
        super.saveCache(username, token);
    }

    @Override
    public boolean verifyByCache(String token) {
        return super.verifyByCache(token);
    }

    @Override
    public void removeByCache(String token) {
        super.removeByCache(token);
    }
}