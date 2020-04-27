package com.bzchao.webtest.util;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
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
     * 获得返回数据类型
     * 原创代码
     *
     * @param mainClass 主类
     * @param tClass    主类中的泛型
     * @param <E>
     * @param <T>
     * @return
     */
    public <E, T> ParameterizedTypeReference<E> getTypeReference(Class<E> mainClass, Class<T> tClass) {
        return new ParameterizedTypeReference<E>() {
            @Override
            public Type getType() {
                //主类中的泛型，可以有多个
                Type[] responseWrapperTypes = {tClass};
                ParameterizedType responseWrapperType = ParameterizedTypeImpl.make(mainClass, responseWrapperTypes, null);
                return responseWrapperType;
            }
        };
    }

    /**
     * 知道主类的情况，例如主类为List
     *
     * @param tClass
     * @param <T>
     * @return
     */
    public <T> ParameterizedTypeReference getTypeReferenceList(Class<T> tClass) {
        //此代码不能正常使用，必须显式指定T，如List<MerchantOrder>不能使用List<T>
        ParameterizedTypeReference<List<T>> typeReference = new ParameterizedTypeReference<List<T>>() {
        };

        // 解决不能显示指定T的情况，
        // 参照 https://stackoverflow.com/questions/36915823/spring-resttemplate-and-generic-types-parameterizedtypereference-collections-lik
        return new ParameterizedTypeReference<List<T>>() {
            @Override
            public Type getType() {
                Type type = super.getType();
                if (type instanceof ParameterizedType) {
                    Type[] responseWrapperTypes = {tClass};
                    ParameterizedType responseWrapperType = ParameterizedTypeImpl.make(List.class, responseWrapperTypes, null);
                    return responseWrapperType;
                }
                return type;
            }
        };
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
