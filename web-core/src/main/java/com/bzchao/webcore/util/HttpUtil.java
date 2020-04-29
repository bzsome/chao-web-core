package com.bzchao.webcore.util;

import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * <p>类名: HttpUtils</p>
 * <p>描述: http请求工具类</p>
 * <p>修改时间: 2019年04月30日 上午10:12:35</p>
 *
 * @author lidongyang
 */
public class HttpUtil {

    public static String defaultEncoding = "utf-8";

    /**
     * 发送http post请求，并返回响应实体
     *
     * @param url 请求地址
     * @return url响应实体
     */
    public static String postRequest(String url) {
        return postRequest(url, null, null);
    }

    /**
     * <p>方法名: postRequest</p>
     * <p>描述: 发送httpPost请求</p>
     *
     * @param url
     * @param params
     * @return
     */
    public static String postRequest(String url, Map<String, Object> params) {
        return postRequest(url, params, null);
    }


    /**
     * 发送http post请求，并返回响应实体（表单数据）
     *
     * @param url     访问的url
     * @param headers 请求需要添加的请求头
     * @param params  请求参数
     * @return
     */
    public static String postRequest(String url, Map<String, Object> params, Map<String, String> headers) {
        String result = null;
        CloseableHttpClient httpClient = buildHttpClient();
        HttpPost httpPost = new HttpPost(url);


        if (null != headers && headers.size() > 0) {
            for (Entry<String, String> entry : headers.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                httpPost.addHeader(new BasicHeader(key, value));
            }
        }
        if (null != params && params.size() > 0) {
            List<NameValuePair> pairList = new ArrayList<NameValuePair>(params.size());
            for (Entry<String, Object> entry : params.entrySet()) {
                NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry.getValue().toString());
                pairList.add(pair);
            }
            httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName(defaultEncoding)));
        }

        try {
            CloseableHttpResponse response = httpClient.execute(httpPost);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    result = EntityUtils.toString(entity,
                            Charset.forName(defaultEncoding));
                }
            } finally {
                response.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    /**
     * 发送http post请求，并返回响应实体（Json数据）
     *
     * @param url     访问的url
     * @param headers 请求需要添加的请求头
     * @param string  请求参数
     * @return
     */
    public static String postRequestJson(String url, String string, Map<String, String> headers) {
        String result = null;
        CloseableHttpClient httpClient = buildHttpClient();
        HttpPost httpPost = new HttpPost(url);


        if (null != headers && headers.size() > 0) {
            for (Entry<String, String> entry : headers.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                httpPost.addHeader(new BasicHeader(key, value));
            }
        }

        try {
            StringEntity httpEntity = new StringEntity(string);
            httpEntity.setContentEncoding("UTF-8");
            httpEntity.setContentType("application/json");//发送json数据需要设置contentType
            httpPost.setEntity(httpEntity);

            CloseableHttpResponse response = httpClient.execute(httpPost);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    result = EntityUtils.toString(entity,
                            Charset.forName(defaultEncoding));
                }
            } finally {
                response.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    /**
     * 发送http get请求
     *
     * @param url 请求url
     * @return url返回内容
     */
    public static String getRequest(String url) {
        return getRequest(url, null);
    }


    /**
     * 发送http get请求
     *
     * @param url    请求的url
     * @param params 请求的参数
     * @return
     */
    public static String getRequest(String url, Map<String, Object> params) {
        return getRequest(url, params, null);
    }

    /**
     * 发送http get请求
     *
     * @param url        请求的url
     * @param params     请求的参数
     * @param headersMap 请求头
     * @return
     */
    public static String getRequest(String url, Map<String, Object> params, Map<String, String> headersMap) {
        String result = null;
        CloseableHttpClient httpClient = buildHttpClient();
        try {
            String apiUrl = url;
            if (null != params && params.size() > 0) {
                StringBuffer param = new StringBuffer();
                int i = 0;
                for (String key : params.keySet()) {
                    if (i == 0) {
                        param.append("?");
                    } else {
                        param.append("&");
                    }
                    param.append(key).append("=").append(params.get(key));
                    i++;
                }
                apiUrl += param;
            }

            HttpGet httpGet = new HttpGet(apiUrl);
            if (null != headersMap && headersMap.size() > 0) {
                for (Entry<String, String> entry : headersMap.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    httpGet.addHeader(new BasicHeader(key, value));
                }
            }
            CloseableHttpResponse response = httpClient.execute(httpGet);
            try {
                HttpEntity entity = response.getEntity();
                if (null != entity) {
                    result = EntityUtils.toString(entity, defaultEncoding);
                }
            } finally {
                response.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 创建httpclient
     *
     * @return
     */
    public static CloseableHttpClient buildHttpClient() {
        return HttpClientBuilder.create().build();
    }

    public static void main(String[] args) {
        String url = "http://120.78.8.150/mtoken";

        Map params = new HashMap();
        params.put("userName", "test");
        params.put("password", "123456");

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json;charset=UTF-8");
        headers.put("Referer", url);

        String toJson = new Gson().toJson(params);
        String result = HttpUtil.postRequestJson(url, toJson, headers);
        System.out.println(result);
    }

}
