package com.bzchao.core.auth;

import java.util.List;

public class UserAuthImpl implements UserAuth {
    private Long userId;
    private String username;
    private String password;
    private String fullName;
    private List<String> roles;
    private List<String> permissions;

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    @Override
    public Long getUserId() {
        return null;
    }

    @Override
    public String getUserName() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getFullName() {
        return null;
    }

    @Override
    public List<String> getRoles() {
        return null;
    }

    @Override
    public List<String> getPermissions() {
        return null;
    }
}
