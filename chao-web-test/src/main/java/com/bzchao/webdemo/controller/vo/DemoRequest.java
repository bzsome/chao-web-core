package com.bzchao.webdemo.controller.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel(description = "发送短信-请求体")
public class DemoRequest {
    /**
     * 短信类型
     */
    @ApiModelProperty(value = "短信类型")
    private String type;

    @ApiModelProperty(value = "手机号(可选)")
    private String phone;
}
