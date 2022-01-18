package com.bzchao.webcore.common.exception;

import com.bzchao.webcore.common.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常处理器
 **/
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(Exception.class)
    public Result handleRRException(Exception e) {
        log.error(e.getMessage(), e);
        return Result.error(e.getMessage());
    }

    /**
     * 接口参数校验失败
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result handleException(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        return Result.error(e.getBindingResult().getFieldError().getDefaultMessage());
    }

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(TokenExpireException.class)
    public Result handleTokenExpireException(Exception e) {
        log.error(e.getMessage(), e);
        return Result.error(e.getMessage()).setCode(401);
    }

}
