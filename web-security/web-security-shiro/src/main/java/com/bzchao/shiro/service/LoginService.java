package com.bzchao.shiro.service;


import com.bzchao.shiro.config.JwtUser;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class LoginService {

    public JwtUser getUserByName(String getMapByName) {
        //模拟数据库查询，正常情况此处是从数据库或者缓存查询。
        return getMapByName(getMapByName);
    }

    /**
     * 模拟数据库查询
     *
     * @param userName
     * @return
     */
    private JwtUser getMapByName(String userName) {
        //共添加两个用户，两个用户都是admin一个角色，
        //wsl有query和add权限，zhangsan只有一个query权限
        Set<String> permissionsSet = new HashSet<>();
        permissionsSet.add("query");
        permissionsSet.add("add");
        JwtUser jwtUser = new JwtUser("1", "admin", "123456",  permissionsSet);
        Map<String, JwtUser> map = new HashMap<>();
        map.put(jwtUser.getUsername(), jwtUser);

        Set<String> permissionsSet1 = new HashSet<>();
        permissionsSet1.add("query");
        JwtUser jwtUser1 = new JwtUser("2", "zhangsan", "123456",  permissionsSet1);
        map.put(jwtUser1.getUsername(), jwtUser1);

        return map.get(userName);
    }
}