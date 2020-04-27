package com.bzchao.shiro.web;

import lombok.Data;

@Data
public class Result<T> {
    /**
     * http 状态码
     */
    private int code;

    /**
     * 返回信息
     */
    private String msg;

    /**
     * 返回的数据
     */
    private T data;

    public Result() {
    }

    public Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    private Result(IErrorCode errorCode, T data) {
        this(errorCode.getCode(), errorCode.getMsg(), data);
    }

    public static <T> Result<T> ok() {
        return new Result(ErrorCode.OK, null);
    }

    public static <T> Result<T> ok(T data) {
        return new Result(ErrorCode.OK, data);
    }

    public static <T> Result<T> fail(int code, String msg) {
        return new Result(code, msg, null);
    }

    public static <T> Result<T> fail(IErrorCode errorCode) {
        return new Result(errorCode, null);
    }

}
