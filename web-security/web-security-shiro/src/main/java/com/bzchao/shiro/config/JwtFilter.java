package com.bzchao.shiro.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class JwtFilter extends BasicHttpAuthenticationFilter {

    /**
     * 对跨域提供支持
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", "*");
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "*");
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equalsIgnoreCase("OPTIONS")) {
            httpServletResponse.setStatus(200);
            return false;
        }
        return super.preHandle(request, response);
    }


    /**
     * 判断是否试图登录(即是否携带了token)
     * 必须重新，具体可查询super方法
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        log.info("isLoginAttempt,{}", "request");
        // 个人优化，优先从url中获得Token。
        // 即携带token的方式有两种：1.请求头Authorization，2.url参数(表单也行)Authorization
        String[] authorizationParam = request.getParameterMap().get(AUTHORIZATION_HEADER);
        if (authorizationParam != null && authorizationParam.length > 0 && authorizationParam[0].length() > 0) {
            return true;
        }
        String authzHeader = this.getAuthzHeader(request);
        return authzHeader != null;
    }

    /**
     * 根据token找到对象实体，有缓存机制
     * isLoginAttempt执行后会执行此方法
     */
    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        log.debug("createToken");
        String authToken = this.getAuthzHeader(request);
        if (authToken != null && authToken.length() != 0) {
            return new UsernamePasswordToken(authToken, authToken);
        } else {
            return new UsernamePasswordToken();
        }
    }

    /**
     * 401未授权时执行
     */
    @Override
    protected boolean sendChallenge(ServletRequest request, ServletResponse response) {
        try {
            //未授权时，将请求转发到指定的url
            request.getRequestDispatcher("/shiro/401").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*
        //或者执行返回数据
        httpServletResponse.setCharacterEncoding("utf-8");
        httpServletResponse.setContentType("application/json");
        ObjectMapper objectMapper = new ObjectMapper();
        Result<Object> result = Result.fail(401, "未登录");
        try {
            httpServletResponse.getWriter().append(objectMapper.writeValueAsString(result));
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
        return false;
    }

}
