package com.bzchao.shiro.config;

import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
public class ShiroUser implements Serializable {
    private String id;
    private String username;
    //加密后的密文
    private String enPassword;
    /**
     * 用户对应的角色集合
     */
    private Set<String> perms;

    public ShiroUser() {
    }

    public ShiroUser(String id, String username, String enPassword, Set<String> perms) {
        this.id = id;
        this.username = username;
        this.enPassword = enPassword;
        this.perms = perms;
    }
}
