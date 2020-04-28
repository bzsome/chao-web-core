package com.bzchao.shiro.exception;

import com.bzchao.shiro.web.ErrorCode;
import com.bzchao.shiro.web.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.List;

/**
 * 200 OK - [GET]：服务器成功返回用户请求的数据，该操作是幂等的（Idempotent）。
 * 201 CREATED - [POST/PUT/PATCH]：用户新建或修改数据成功。
 * 202 Accepted - [*]：表示一个请求已经进入后台排队（异步任务）
 * 204 NO CONTENT - [DELETE]：用户删除数据成功。
 * 400 INVALID REQUEST - [POST/PUT/PATCH]：用户发出的请求有错误，服务器没有进行新建或修改数据的操作，该操作是幂等的。
 * 401 Unauthorized - [*]：表示用户没有权限（令牌、用户名、密码错误）。
 * 403 Forbidden - [*] 表示用户得到授权（与401错误相对），但是访问是被禁止的。
 * 404 NOT FOUND - [*]：用户发出的请求针对的是不存在的记录，服务器没有进行操作，该操作是幂等的。
 * 406 Not Acceptable - [GET]：用户请求的格式不可得（比如用户请求JSON格式，但是只有XML格式）。
 * 410 Gone -[GET]：用户请求的资源被永久删除，且不会再得到的。
 * 422 Unprocesable entity - [POST/PUT/PATCH] 当创建一个对象时，发生一个验证错误。
 * 500 INTERNAL SERVER ERROR - [*]：服务器发生错误，用户将无法判断发出的请求是否成功。
 * Created by kosh on 2017/5/18.
 */
@Slf4j
@RestControllerAdvice
public class ShiroExceptionHandler {

    /**
     * Bean Validation Exception
     */
    @ExceptionHandler(BindException.class)
    public Result handleBindException(BindException e) {
        List<ObjectError> errors = e.getAllErrors();
        StringBuilder msgSb = new StringBuilder();
        if (errors != null) {
            for (ObjectError error : errors) {
                if (msgSb.length() > 0) {
                    msgSb.append(", ");
                }
                msgSb.append(error.getObjectName());
                if (error instanceof FieldError) {
                    FieldError fieldError = (FieldError) error;
                    msgSb.append('.').append(fieldError.getField());
                }
                msgSb.append(": ").append(error.getDefaultMessage());
            }
        }
        return Result.fail(ErrorCode.BAD_REQUEST.getCode(), msgSb.length() == 0 ? e.toString() : msgSb.toString());
    }

    /**
     * 权限异常
     */
    @ExceptionHandler({ShiroException.class})
    public Result handleShiroException(ShiroException e) {
        log.debug("shiro异常");
        return Result.fail(ErrorCode.FORBIDDEN.getCode(), e.getMessage() == null ? e.toString() : e.getMessage());
    }

    /**
     * 权限异常
     */
    @ExceptionHandler({NoHandlerFoundException.class})
    public Result handleNoHandlerFoundException(NoHandlerFoundException e) {
        return Result.fail(ErrorCode.NOT_FOUND.getCode(), e.getMessage() == null ? e.toString() : e.getMessage());
    }

    /**
     * 其他异常
     */
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        log(e);
        return Result.fail(ErrorCode.INTERNAL_SERVER_ERROR.getCode(), e.getMessage() == null ? e.toString() : e.getMessage());
    }

    private void log(Throwable e) {
        log.error(e.getMessage() == null ? e.toString() : e.getMessage(), e);
    }

}

