package com.bzchao.core.auth;

import java.util.List;

public interface UserAuth {
    Long getUserId();

    String getUserName();

    String getPassword();

    String getFullName();

    List<String> getRoles();

    List<String> getPermissions();
}
