package com.bzchao.core.web;

import lombok.Data;

@Data
public class Result {
    private int code;
    private String msg;
    private Object data;

    public Result() {
        this(200, null);
    }

    public Result(int code) {
        this(code, null);
    }

    public Result(int code, String msg) {
        this(code, msg, null);
    }

    public Result(Object data) {
        this(200, null, data);
    }

    public Result(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
