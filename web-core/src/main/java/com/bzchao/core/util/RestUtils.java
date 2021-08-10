package com.bzchao.core.util;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Classname RestUtils
 * @Description Rest请求工具类，新增了get请求
 * @Date 2019/7/17 下午 3:21
 * @Created by admin
 */

@Component
public class RestUtils extends RestTemplate {
    /**
     * 对get方法进行了加强，原get不支持设置请求头
     *
     * @param url            请求url
     * @param requestHeaders 请求头
     * @param responseType   返回请求实体类型
     * @param urlParams      url请求参数
     * @param <T>
     * @return
     * @throws RestClientException
     */
    public <T> ResponseEntity<T> getForEntity(String url, MultiValueMap<String, String> requestHeaders, Class<T> responseType, Map urlParams) throws RestClientException {
        //TODO 优化数据返回，处理有T的情况
        HttpEntity<T> requestEntity = new HttpEntity<>(null, requestHeaders);
        ResponseEntity<T> exchange = super.exchange(getUrl(url, urlParams), HttpMethod.GET, requestEntity, responseType);
        return exchange;

    }

    /**
     * post方法增强，支持泛型
     *
     * @param reqUrl
     * @param requestEntity
     * @param typeReference 指定返回数据类型。解决有T的数据，返回类型为linkedHashMap
     * @param <T>
     * @return
     */
    public <T> ResponseEntity<T> postForEntity(String reqUrl, HttpEntity requestEntity, ParameterizedTypeReference<T> typeReference) {
        ResponseEntity<T> responseEntity = super.exchange(reqUrl, HttpMethod.POST, requestEntity, typeReference);
        return responseEntity;
    }

    /**
     * 将请求参数拼接到url中
     *
     * @param url 请求url
     * @param map url参数
     * @return
     */
    private static String getUrl(String url, Map<String, Object> map) {
        if (map == null) {
            return url;
        }
        String urlParams = map.entrySet().stream().map(entry -> entry.getKey() + "=" + entry.getValue()).collect(Collectors.joining("&"));
        return url + "?" + urlParams;
    }

}
