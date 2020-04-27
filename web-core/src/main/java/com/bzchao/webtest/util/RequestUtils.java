package com.bzchao.webtest.util;

import java.util.Map;
import java.util.stream.Collectors;

public class RequestUtils {
    /**
     * 将请求参数拼接到url中
     *
     * @param url 请求url
     * @param map url参数
     * @return
     */
    public static String getUrl(String url, Map<String, Object> map) {
        if (map != null && map.size() > 0) {
            return url + "?" + getFormEncode(map);
        }
        return url;
    }

    /**
     * 获得表单请求编码
     * 主要用途：url参数编码，body请求编码
     *
     * @param map
     * @return
     */
    public static String getFormEncode(Map<String, Object> map) {
        if (map != null && map.size() > 0) {
            String urlParams = map.entrySet().stream().map(entry -> entry.getKey() + "=" + entry.getValue())
                    .collect(Collectors.joining("&"));
            return urlParams;
        }
        return "";
    }
}
