package com.bzchao.webcore.common.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * 接口返回数据格式
 *
 * @author 陈光超
 * @email admin@bzchao.com
 * @date 2021年04月13日
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "接口返回对象", description = "接口返回对象")
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 请求ID
     */
    @ApiModelProperty(value = "请求ID")
    private String requestId;
    /**
     * 成功标志
     */
    @ApiModelProperty(value = "成功标志")
    private boolean success = true;

    /**
     * 返回处理消息
     */
    @ApiModelProperty(value = "返回处理消息")
    private String message = "操作成功！";

    /**
     * 返回代码
     */
    @ApiModelProperty(value = "返回代码")
    private Integer code = 0;

    /**
     * 返回数据对象 data
     */
    @ApiModelProperty(value = "返回数据对象")
    private T data;

    /**
     * 时间戳
     */
    @ApiModelProperty(value = "时间戳")
    private long timestamp = System.currentTimeMillis();

    private Result() {
    }

    public Result(int code, String msg, T data) {
        this.requestId = getServletRequestId();
        this.setCode(code);
        this.setMessage(msg);
        this.setData(data);
        this.setSuccess(code == 200);
    }

    public static <T> Result<T> ok() {
        return new Result<>(200, "操作成功", null);
    }

    public static <T> Result<T> ok(String message) {
        return new Result<T>(200, message, null);
    }

    public static <T> Result<T> ok(String message, T data) {
        return new Result<T>(200, message, data);
    }

    public static <T> Result<T> data(T data) {
        return new Result<T>(200, "操作成功", data);
    }

    public static <T> Result<T> error() {
        return new Result<>(500, "操作失败", null);
    }

    public static <T> Result<T> error(String msg) {
        return new Result<T>(500, msg, null);
    }

    private static String getServletRequestId() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return (String) request.getAttribute("requestId");
    }
}
