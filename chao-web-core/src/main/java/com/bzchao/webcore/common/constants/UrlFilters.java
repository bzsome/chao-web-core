package com.bzchao.webcore.common.constants;

import java.util.ArrayList;
import java.util.List;

/**
 * application.yml中过多的配置，且不美观，建议配置在此处
 */
public class UrlFilters {
    public static List<String> filterUrls = new ArrayList<>();
    public static List<String> swaggerUrls = new ArrayList<>();

    static {

        filterUrls.add("/sys/test/**");
        filterUrls.add("/sys/user/login");
        filterUrls.add("/sys/user/sendSms");
        filterUrls.add("/sys/user/loginSms");
        filterUrls.add("/dist/**");
        initSwaggerDocs();
        filterUrls.addAll(swaggerUrls);
    }

    public static void initSwaggerDocs() {
        swaggerUrls.add("/webjars/**");
        swaggerUrls.add("/swagger-resources");
        swaggerUrls.add("/swagger-resources/**");
        swaggerUrls.add("/v2/api-docs");
        swaggerUrls.add("/configuration/security");
    }

}
