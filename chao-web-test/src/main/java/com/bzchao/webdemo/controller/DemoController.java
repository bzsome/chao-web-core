package com.bzchao.webdemo.controller;

import com.bzchao.webcore.common.util.JsonUtils;
import com.bzchao.webcore.common.vo.Result;
import com.bzchao.webdemo.controller.vo.DemoRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@Api(tags = "Demo测试")
@RestController
@RequestMapping("/demo")
public class DemoController {

    @ApiOperation(value = "map接收参数")
    @GetMapping("/map")
    public Result testMap(Map<String, String> paramMap) {
        log.info("启动完成xx");
        return Result.data("this is test," + JsonUtils.toString(paramMap));
    }

    @ApiOperation(value = "DTO接收参数")
    @PostMapping("/dto")
    public Object testDto(@RequestBody DemoRequest demoRequest) {
        log.info("启动完成xx");
        return Result.data("this is test," + JsonUtils.toString(demoRequest));
    }

}
