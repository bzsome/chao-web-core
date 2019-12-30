package com.bzchao.core.util;

import java.util.Map;

/**
 * @Classname MapUtils
 * @Description Map工具类型
 * @Date 2019/10/10 上午 11:22
 * @Created by admin
 */
public class MapUtils {

    /**
     * 获得多层map的值
     *
     * @param map
     * @param keyNames
     * @return
     */
    public static Object getByNames(Map map, String... keyNames) {
        Object tempObj = null;
        try {
            tempObj = map;
            for (String key : keyNames) {
                tempObj = ((Map) tempObj).get(key);
            }
        } catch (Exception e) {
            throw new RuntimeException("key值错误");
        }
        return tempObj;
    }
}
