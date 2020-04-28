package com.bzchao.shiro.util;

import com.bzchao.shiro.config.ShiroUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

public class ShiroUtils {
    public static ShiroUser getUser() {
        Subject subject = SecurityUtils.getSubject();
        Object principal = subject.getPrincipal();
        PrincipalCollection principals = subject.getPrincipals();
        return (ShiroUser) subject.getPrincipal();
    }

    public static String encryptPassword(String password, String salt) {
        SimpleHash hash = new SimpleHash("SHA-256", password, salt, 1);
        return hash.toHex();
    }
}
