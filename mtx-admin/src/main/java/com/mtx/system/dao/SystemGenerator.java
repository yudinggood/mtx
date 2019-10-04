package com.mtx.system.dao;

import com.mtx.common.util.base.PropertiesFileUtil;
import com.mtx.common.util.db.MybatisGeneratorUtil;

import java.util.HashMap;
import java.util.Map;

public class SystemGenerator {
    private static final String MODULE = "mtx-admin";
    private static final String DATABASE = "mtx";
    private static final String TABLE_PREFIX = "system_";
    private static final String PACKAGE_NAME = "com.mtx.system";
    private static String JDBC_DRIVER = PropertiesFileUtil.getInstance("generator").get("generator.jdbc.driver");
    private static String JDBC_URL = PropertiesFileUtil.getInstance("generator").get("generator.jdbc.url");
    private static String JDBC_USERNAME = PropertiesFileUtil.getInstance("generator").get("generator.jdbc.username");
    private static String JDBC_PASSWORD = PropertiesFileUtil.getInstance("generator").get("generator.jdbc.password");
    // 需要insert后返回主键的表配置，key:表名,value:主键名
    private static Map<String, String> LAST_INSERT_ID_TABLES = new HashMap<>();
    static {
        LAST_INSERT_ID_TABLES.put("system_user", "user_id");
        LAST_INSERT_ID_TABLES.put("system_dic", "dic_id");
        LAST_INSERT_ID_TABLES.put("system_task","task_id");


    }

    /**
     * 自动代码生成
     */
    public static void main(String[] args) throws Exception {
        MybatisGeneratorUtil.generator(JDBC_DRIVER, JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD, MODULE, DATABASE, TABLE_PREFIX, PACKAGE_NAME, LAST_INSERT_ID_TABLES);
    }
}
