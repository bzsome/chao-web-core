package com.bzchao.shiro.config;

import lombok.Data;

import java.util.Set;

@Data
public class Role {
    private String roleName;
    /**
     * 角色对应权限集合
     */
    private Set<String> permissions;

    public Role(String roleName, Set<String> permissions) {
        this.roleName = roleName;
        this.permissions = permissions;
    }
}