package com.bzchao.core.web;

import lombok.Data;

/**
 * REST请求返回实体
 */
@Data
public class Result<T> {
    private int code;
    private String msg;
    private T data;

    public Result() {
        this(200, null);
    }

    public Result(int code) {
        this(code, null);
    }

    public Result(int code, String msg) {
        this(code, msg, null);
    }

    public Result(T data) {
        this(200, null, data);
    }

    public Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
