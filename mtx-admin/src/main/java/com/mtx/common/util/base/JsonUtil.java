package com.mtx.common.util.base;

import com.baidu.unbiz.fluentvalidator.util.Preconditions;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * Json转换
 */
public class JsonUtil {//统一使用新版fasterxml，不用codehaus
    private static ObjectMapper defaultMapper;
    private static ObjectMapper formatedMapper;

    static {
        // 默认的ObjectMapper
        defaultMapper = new ObjectMapper();
        // 设置输入时--忽略--在JSON字符串中存在但Java对象实际没有的属性
        defaultMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        formatedMapper = new ObjectMapper();
        // 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
        formatedMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 所有日期格式都统一为固定格式
        formatedMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        formatedMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    /**
     * 将对象转化为json数据
     */
    public static String toJson(Object obj) throws IOException {
        Preconditions.checkArgument(obj != null, "对象不能为空!");
        return defaultMapper.writeValueAsString(obj);
    }

    /**
     * json数据转化为对象(Class)
     */
    public static <T> T parseJson(String jsonValue, Class<T> valueType) throws IOException {
        Preconditions.checkArgument(StringUtils.isNotEmpty(jsonValue), "字符串不能为空!");
        return defaultMapper.readValue(jsonValue, valueType);
    }
    /**
     * json数据转化为对象(JavaType)
     */
    @SuppressWarnings("unchecked")
    public static <T> T parseJson(String jsonValue, JavaType valueType) throws IOException {
        Preconditions.checkArgument(StringUtils.isNotEmpty(jsonValue), "字符串不能为空!");
        return (T) defaultMapper.readValue(jsonValue, valueType);
    }
    /**
     * json数据转化为对象(TypeReference)
     * List<String> list = JsonUtil.parseJson(jsonValue,new TypeReference<List<String>>(){});  调用
     */
    @SuppressWarnings("unchecked")//这个放在方法上就不会报黄色错误了
    public static <T> T parseJson(String jsonValue, TypeReference<T> valueTypeRef) throws IOException {
        Preconditions.checkArgument(StringUtils.isNotEmpty(jsonValue), "字符串不能为空!");
        return (T) defaultMapper.readValue(jsonValue, valueTypeRef);
    }


    /**
     * 将对象转化为json数据(时间转换格式： "yyyy-MM-dd HH:mm:ss")
     */
    public static String toJsonWithFormat(Object obj) throws IOException {
        Preconditions.checkArgument(obj != null, "对象不能为空!");
        return formatedMapper.writeValueAsString(obj);
    }

    /**
     * json数据转化为对象(时间转换格式： "yyyy-MM-dd HH:mm:ss")
     */
    public static <T> T parseJsonWithFormat(String jsonValue, Class<T> valueType) throws IOException {
        Preconditions.checkArgument(StringUtils.isNotEmpty(jsonValue), "字符串不能为空!");
        return formatedMapper.readValue(jsonValue, valueType);
    }
    /**
     * json数据转化为对象(JavaType)(时间转换格式： "yyyy-MM-dd HH:mm:ss")
     */
    public static <T> T parseJsonWithFormat(String jsonValue, JavaType valueType) throws IOException {
        Preconditions.checkArgument(StringUtils.isNotEmpty(jsonValue), "字符串不能为空!");
        return (T) formatedMapper.readValue(jsonValue, valueType);
    }
    /**
     * json数据转化为对象(TypeReference)(时间转换格式： "yyyy-MM-dd HH:mm:ss")
     */
    public static <T> T parseJsonWithFormat(String jsonValue, TypeReference<T> valueTypeRef) throws IOException {
        Preconditions.checkArgument(StringUtils.isNotEmpty(jsonValue), "字符串不能为空!");
        return (T) formatedMapper.readValue(jsonValue, valueTypeRef);
    }
}
