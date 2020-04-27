package com.bzchao.shiro.service;

import com.bzchao.shiro.config.ShiroUser;
import com.bzchao.shiro.util.JwtUtil;


public abstract class JwtService {

    //过期时间，默认60分钟
    private static long expireTime = 60 * 60;

    /**
     * 必须实现，根据用户名获得用户信息
     *
     * @param username
     * @return
     */
    public abstract ShiroUser getUserByName(String username);

    public String getUsername(String token) {
        return JwtUtil.getUsername(token);
    }

    public boolean verify(String token, String username, String secret) {
        return JwtUtil.verify(token, username, secret) && verifyByCache(token);
    }

    public String generate(String username, String password) {
        ShiroUser shiroUser = this.getUserByName(username);
        String enpassword = this.enpassword(shiroUser.getPassword());
        if (shiroUser != null && enpassword.equals(password)) {
            String token = JwtUtil.sign(username, password, expireTime);
            this.saveCache(username, token);
            return token;
        }
        return null;
    }

    /**
     * 加密算法
     *
     * @param password（可选实现）
     * @return
     */
    public String enpassword(String password) {
        return password;
    }

    //将token保存至缓存（可选实现）
    public void saveCache(String username, String token) {

    }

    //验证缓存中是否有token（可选实现）
    public boolean verifyByCache(String token) {
        return true;
    }

    //验证缓存中删除有token（可选实现）
    public void removeByCache(String token) {

    }

    protected void setExpireTime() {
    }

}
