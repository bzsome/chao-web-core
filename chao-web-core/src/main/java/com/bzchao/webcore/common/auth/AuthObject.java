package com.bzchao.webcore.common.auth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel(value = "授权信息", description = "授权信息")
public class AuthObject {
    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID")
    private Long userId;
    /**
     * 用户编码
     */
    @ApiModelProperty(value = "用户编码")
    private String userCode;
    /**
     * 用户名称
     */
    @ApiModelProperty(value = "用户名称")
    private String userName;
}
