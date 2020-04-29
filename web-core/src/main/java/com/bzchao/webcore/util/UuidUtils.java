package com.bzchao.webcore.util;

import java.util.UUID;

public class UuidUtils {
    public static String random() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
