package com.bzchao.webcore.common.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class JsonUtils {

    private static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        //序列化空值
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        //找不到的值报错（关闭）
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        OBJECT_MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        //bean内容为空报错(关闭)
        OBJECT_MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        //时间转为时间戳(关闭)
        OBJECT_MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    private JsonUtils() {
    }

    public static void setObjectMapper(ObjectMapper objectMapper) {
        //支持传入指定的objectMapper(用于设置某些特殊类型的对象，如时间，枚举等)
        OBJECT_MAPPER = objectMapper;
    }

    public static String toString(Object object) {
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public static JsonNode toJsonNode(Object object) {
        return OBJECT_MAPPER.valueToTree(object);
    }

    public static <T> T parseJsonNodeToObject(JsonNode jsonNode, Class<T> beanType) {
        try {
            return OBJECT_MAPPER.treeToValue(jsonNode, beanType);
        } catch (JsonProcessingException e) {
            return null;
        }

    }

    public static <T> T toObject(String jsonStr, Class<T> beanType) {
        try {
            return OBJECT_MAPPER.readValue(jsonStr, beanType);
        } catch (IOException e) {
            return null;
        }
    }

    public static <T> List<T> toList(String jsonArrayStr, Class<T> beanType) {
        try {
            List<T> list = OBJECT_MAPPER.readValue(jsonArrayStr, new TypeReference<List<T>>() {
            });
            return list;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将一个对象转为map
     */
    public static Map<String, Object> objectToMap(Object object) {
        try {
            return new ObjectMapper().convertValue(object, Map.class);
        } catch (Exception e) {
            throw new RuntimeException("数据转换失败", e);
        }
    }

    public static Map<String, Object> toMap(String jsonStr) {
        try {
            return OBJECT_MAPPER.readValue(jsonStr, new TypeReference<Map<String, Object>>() {
            });
        } catch (IOException e) {
            return null;
        }

    }

    public static JsonNode toJsonNode(String jsonStr) {
        try {
            return OBJECT_MAPPER.readTree(jsonStr);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ObjectNode createNode() {
        return OBJECT_MAPPER.createObjectNode();
    }

    public static ArrayNode createArrayNode() {
        return OBJECT_MAPPER.createArrayNode();
    }

}
