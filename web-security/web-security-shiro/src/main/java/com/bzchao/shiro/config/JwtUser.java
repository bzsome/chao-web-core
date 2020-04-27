package com.bzchao.shiro.config;

import lombok.Data;

import java.util.Set;

@Data
public class JwtUser {
    private String id;
    private String username;
    private String password;
    /**
     * 用户对应的角色集合
     */
    private Set<String> perms;

    public JwtUser(String id, String username, String password, Set<String> perms) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.perms = perms;
    }
}
