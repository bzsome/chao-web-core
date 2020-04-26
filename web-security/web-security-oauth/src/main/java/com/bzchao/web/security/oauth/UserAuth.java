package com.bzchao.web.security.oauth;


import lombok.Data;

@Data
public class UserAuth {
    private Long userId;
    private String username;
    private String password;
}
