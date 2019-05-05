package com.dtouding.samples.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.type.JavaType;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * 基于Jackson Mapper封装的Json工具类
 */
@Slf4j
public class JsonUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();

    private static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    static {
        /** 序列化：对象的全部字段. */
        objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.ALWAYS);
        /** 序列化：取消默认转化为Timestamp形式.*/
        objectMapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);
        /** 序列化：忽略空bean转json的错误. */
        objectMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
        /** 序列化：设定日期格式. */
        objectMapper.setDateFormat(new SimpleDateFormat(DATE_FORMAT));

        /** 反序列化：忽略在Json字符串中存在，但在bean中不存在的情形. */
        objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * 对象转化为json字符串
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> String bean2Json(T obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof String) {
            return (String) obj;
        }
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            log.error("Parse object to json error.", e);
            return null;
        }
    }

    /**
     * 序列化为json并格式化
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> String bean2JsonPretty(T obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof String) {
            return (String) obj;
        }
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (Exception e) {
            log.error("Parse object to json error.", e);
            return null;
        }
    }

    /**
     * Json字符串转化为对象（简单对象，不适用集合）
     * @param str
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T json2Bean(String str, Class<T> clazz) {
        if (StringUtils.isBlank(str) || clazz == null) {
            return null;
        }
        try {
            return clazz.equals(String.class)? (T) str : objectMapper.readValue(str, clazz);
        } catch (Exception e) {
            log.error("Parse json to object error.", e);
            return null;
        }
    }

    /**
     * json字符串转化为对象（支持集合）
     * 调用方式：JsonUtil.json2Bean(str, new TypeReference<List<SwordMan>>() {})
     * @param str
     * @param typeReference
     * @param <T>
     * @return
     */
    public static <T> T json2Bean(String str, TypeReference<T>  typeReference) {
        if (StringUtils.isBlank(str) || typeReference == null) {
            return null;
        }
        try {
            return (T) (typeReference.getType().equals(String.class)?str: objectMapper.readValue(str, typeReference));
        } catch (IOException e) {
            log.error("Parse json to object error.", e);
            return null;
        }
    }

    /**
     * json字符串转化为对象，集合
     *
     * @param str
     * @param collectionClazz
     * @param eleClazz
     * @param <T>
     * @return
     */
    public static <T> T json2Bean(String str, Class<?> collectionClazz, Class<?> ... eleClazz) {
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(collectionClazz, eleClazz);
        try {
            return objectMapper.readValue(str, javaType);
        } catch (IOException e) {
            log.error("Parse json to object error.", e);
            return null;
        }
    }
}
