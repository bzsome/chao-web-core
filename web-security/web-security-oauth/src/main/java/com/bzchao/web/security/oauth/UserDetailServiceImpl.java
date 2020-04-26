package com.bzchao.web.security.oauth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
@Component
public class UserDetailServiceImpl implements UserDetailsService {
    /**
     * 根据用户名获取用户 - 用户的角色、权限等信息
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAuth userAuth = this.getByName(username);
        if (userAuth == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        Collection<GrantedAuthority> authList = getAuthorities(userAuth.getUserId());
        UserDetails userDetails = new User(username, "123456", authList);
        return userDetails;
    }

    /**
     * 根据用户名获得用户
     * 这里只是模拟获取
     *
     * @param userName
     * @return
     */
    public UserAuth getByName(String userName) {
        UserAuth userAuth = new UserAuth();
        userAuth.setUserId(11L);
        userAuth.setUsername("admin");
        userAuth.setPassword("123");
        return userAuth;
    }

    /**
     * 获得用户的角色权限
     * 这里只是模拟获取
     */
    private Collection<GrantedAuthority> getAuthorities(Long userId) {
        List<GrantedAuthority> authList = new ArrayList<>();
        authList.add(new SimpleGrantedAuthority("ROLE_USER"));
        authList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        return authList;
    }
}