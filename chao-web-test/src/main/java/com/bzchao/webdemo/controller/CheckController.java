package com.bzchao.webdemo.controller;

import com.bzchao.webcore.common.vo.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 系统检查
 */
@RestController
public class CheckController {
    @Value("${server.servlet.context-path:}")
    private String contextPath;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 获取所有请求头
     */
    private static Map<String, Object> getHeaderMap(HttpServletRequest request) {
        Map<String, Object> headerMap = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headerMap.put(headerName, request.getHeader(headerName));
        }
        return headerMap;
    }

    /**
     * 这里的问路径为http://ip:port/webcore/webcore，用于验证代理服务是否多添加多个path，导致所有服务404
     * 如果真实的路径中只加了一个dms，也访问到了这个path，则说明代理服务多加了一个dms路径
     *
     * @param request
     * @return
     */
    @RequestMapping(value = {"/webcore", "/webcore/**"})
    @ResponseBody
    public Result<Object> agentPath(HttpServletRequest request) {
        return Result.error("Your path configuration is wrong");
    }

    /**
     * 系统参数检查，用于同时部署多个服务时，检查某个路径所映射的服务名
     */
    @RequestMapping(value = "/system-check")
    @ResponseBody
    public Result<Object> systemCheck(HttpServletRequest request) {
        Map<String, Object> checkMap = new HashMap<>();
        checkMap.put("getContextPath", request.getContextPath());
        checkMap.put("getServletPath", request.getServletPath());
        checkMap.put("getRequestURI", request.getRequestURI());
        checkMap.put("getRequestURL", request.getRequestURL());
        checkMap.put("contextPath", contextPath);

        // 返回请求头，可以查看host等（多层网络代理会导致header发生变化）
        Map<String, Object> headerMap = getHeaderMap(request);
        checkMap.put("headers", headerMap);

        return Result.data(checkMap);
    }

}
