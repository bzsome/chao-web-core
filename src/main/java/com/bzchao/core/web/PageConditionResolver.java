
package com.bzchao.core.web;

import org.springframework.beans.BeanUtils;
import org.springframework.core.MethodParameter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class PageConditionResolver implements HandlerMethodArgumentResolver {
    private static final String PARAMETER_PREFIX = "p_";

    public PageConditionResolver() {
    }

    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(Pageable.class) != null;
    }

    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        if (!this.supportsParameter(parameter)) {
            return null;
        } else {
            Map<String, Object> params = new HashMap(10);

            Object object = BeanUtils.instantiateClass(parameter.getParameterType());
            Map<String, String[]> parameterMap = webRequest.getParameterMap();
            Set<Entry<String, String[]>> parameterSet = parameterMap.entrySet();
            Iterator iterator = parameterSet.iterator();
            while (iterator.hasNext()) {
                Entry<String, String[]> entry = (Entry) iterator.next();
                String key = entry.getKey();
                String[] values = entry.getValue();
                if (values != null && StringUtils.hasText(values[0])) {
                    if (StringUtils.startsWithIgnoreCase(key, PARAMETER_PREFIX)) {
                        String name = key.substring(key.indexOf(PARAMETER_PREFIX));
                        params.put(name, values[0]);
                    }
                }
            }

            Field conditionField = parameter.getParameterType().getDeclaredField("params");
            conditionField.setAccessible(true);
            conditionField.set(object, params);
            return object;
        }
    }
}
