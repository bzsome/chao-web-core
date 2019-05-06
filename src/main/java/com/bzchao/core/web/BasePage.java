package com.bzchao.core.web;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class BasePage<T> extends Page<T> {
    private Map<Object, Object> params = new HashMap<>();

    public void setParam(Object key, Object value) {
        params.put(key, value);
    }

    public Object getParam(Object key) {
        return params.get(key);
    }

    @Override
    public Map<Object, Object> condition() {
        return params;
    }
}
