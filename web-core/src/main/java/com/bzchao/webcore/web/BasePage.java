package com.bzchao.webcore.web;

import lombok.Data;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.WebUtils;

import java.util.Map;
import java.util.TreeMap;

@Data
public class BasePage<T> extends Page<T> {
    private static final String CONDITION_PARAMETER_PREFIX = "p_";
    private Map<Object, Object> params = new TreeMap<>();

    public BasePage() {
        ServletRequestAttributes request = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (request != null) {
            Map<String, Object> p = WebUtils.getParametersStartingWith(request.getRequest(), CONDITION_PARAMETER_PREFIX);
            if (p != null) {
                params.putAll(p);
            }
        }
    }

    public BasePage setParam(Object key, Object value) {
        params.put(key, value);
        return this;
    }

    @Override
    public Map<Object, Object> condition() {
        return params;
    }
}
