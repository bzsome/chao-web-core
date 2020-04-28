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
public class ShiroFilter extends BasicHttpAuthenticationFilter {

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
        // 即携带token的方式有两种：1.请求头Authorization，2.url参数(表单也行)Authorization
        String paramToken = getParamToken(request);
        String authzHeader = this.getAuthzHeader(request);
        return paramToken != null || authzHeader != null;
    }

    /**
     * 根据token找到对象实体
     * isLoginAttempt执行后会执行此方法
     */
    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        // 个人优化，优先从url中获得Token。
        String paramToken = this.getParamToken(request);
        if (paramToken != null && paramToken.length() != 0) {
            return new UsernamePasswordToken(paramToken, paramToken);
        }

        String authToken = this.getAuthzHeader(request);
        if (authToken != null && authToken.length() != 0) {
            return new UsernamePasswordToken(authToken, authToken);
        }

        return new UsernamePasswordToken();
    }

    /**
     * 401未授权时执行，filter层的异常不受exceptionAdvice控制
     * 原生为认证弹窗
     */
    @Override
    protected boolean sendChallenge(ServletRequest request, ServletResponse response) {
        try {
            //未授权时，将请求转发到指定的url
            request.getRequestDispatcher("/shiro/401").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 从url(表单中获得token)
     *
     * @param request
     * @return
     */
    private String getParamToken(ServletRequest request) {
        String[] authorizationParam = request.getParameterMap().get(AUTHORIZATION_HEADER);
        if (authorizationParam != null && authorizationParam.length > 0 && authorizationParam[0].length() > 0) {
            return authorizationParam[0];
        }
        return null;
    }
}
