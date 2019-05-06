package com.bzchao.core.util;

import com.bzchao.core.auth.UserAuth;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class UserUtils {
    public static UserAuth getUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        return (UserAuth) userDetails;
    }
}
