package com.mtx.common.util.base;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 所有的数据类型转换类
 */
@Slf4j
public class TypeConversionUtil {

    /**
     * mysql的 blob转string
     */
    public static String blobToString(byte[] b)  {
        try {
            return new String(b);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * object转String
     */
    public static String objectToString(Object object) {
        return getString(object, "");
    }
    private static String getString(Object object, String defaultValue) {
        if (null == object) {
            return defaultValue;
        }
        try {
            return String.valueOf(object);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * object转Integer
     */
    public static int objectToInt(Object object) {
        return getInt(object, 0);
    }
    private static int getInt(Object object, Integer defaultValue) {
        if (null == object) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(object.toString());
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * object转long
     */
    public static long objectToLong(Object object) {
        return getLong(object, 0L);
    }
    private static long getLong(Object object, long defaultValue) {
        if (null == object) {
            return 0L;
        }
        try {
            return Long.parseLong(object.toString());
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * object转Boolean
     */
    public static boolean objectToBoolean(Object object) {
        return getBoolean(object, false);
    }
    private static boolean getBoolean(Object object, Boolean defaultValue) {
        if (null == object) {
            return defaultValue;
        }
        try {
            return Boolean.parseBoolean(object.toString());
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * object转byte
     */
    public static Byte objectToByte(Object object) {
        if (null == object) {
            return null;
        }
        try {
            return Byte.parseByte(object.toString());
        } catch (Exception e) {
            return null;
        }
    }

    //string转byte[]
    public static byte[] stringToBytes(String string){
        return string.getBytes();
    }

    //object转byte[]
    public static byte[] objectToJsonToBytes(Object object) {
        //map转json串
        try {
            String json = JsonUtil.toJson(object);
            //json转二进制
            return TypeConversionUtil.stringToBytes(json);

        } catch (IOException e) {
            log.error(e.getMessage(),e);
            return null;
        }

    }
    //byte[]转map
    public static Map bytesToJsonToObject(byte[] extendMap) {
        if(null==extendMap){
            return null;
        }
        String json = TypeConversionUtil.blobToString(extendMap);
        try {
            Map extProps = JsonUtil.parseJson(json,Map.class);
            return extProps;
        } catch (IOException e) {
            log.error(e.getMessage(),e);
            return null;
        }


    }
    //strings转List
    public static List<Object> stringsToList(String id) {
        String[] ids = id.split("-");
        List<Object> list = Arrays.asList(ids);
        return list;
    }
    //list转string[]
    public static String[] listToStrings(List<String> list){
        return list.toArray(new String[list.size()]);
    }
}
