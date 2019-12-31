package com.bzchao.core.common;

import lombok.Data;

@Data
public class UserAuth {
    private Long userId;
    private String username;
    private String password;
}
