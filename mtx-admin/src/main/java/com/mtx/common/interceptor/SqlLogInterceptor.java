package com.mtx.common.interceptor;

import com.mtx.common.constant.SystemConstant;
import com.mtx.common.util.base.PropertiesFileUtil;
import com.mtx.common.util.base.ThreadLocalUtil;
import com.mtx.common.util.base.TypeConversionUtil;
import com.mtx.system.common.bean.NotDisplaySqlAspect;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.springframework.core.annotation.Order;

import java.text.DateFormat;
import java.util.*;

/**
 * mybatis sql 拦截器
 * 1.配合注解NotDisplaySql ，可以禁止指定的方法的SQL 不打印控制台。
 * 2.SQL 执行时间超过 关注时间 noticeTime ,以error级别打印到控制台
 * 使用时需要把mybatis logger级别设置为 INFO级别
 */
@Slf4j
@Intercepts({//mybatis的拦截器,要在它的配置文件中加入该拦截器
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
@Order(1)
public class SqlLogInterceptor implements Interceptor {

    private boolean enableSqlLogInterceptor = TypeConversionUtil.objectToBoolean(
            PropertiesFileUtil.getInstance("base").get("common.enableSqlLogInterceptor"));;

    /**
     * 关注时间 单位秒，默认值 5
     * 如果 执行SQL 超过时间 就会打印error 日志
     */
    private Double noticeTime = 5.0;


    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        long start = System.currentTimeMillis();

        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];// 获取原始sql语句
        Object parameter = null;
        if (invocation.getArgs().length > 1) {
            parameter = invocation.getArgs()[1];
        }
        BoundSql boundSql = mappedStatement.getBoundSql(parameter);
        Configuration configuration = mappedStatement.getConfiguration();
        String sql = boundSql.getSql().replaceAll("[\\s]+", " ");//匹配任何空白字符，包括空格、制表符、换页符等等
        List<String> paramList = getParamList(configuration, boundSql);
        Object proceed = invocation.proceed();//执行sql
        int result = 0;
        if (proceed instanceof ArrayList) {
            ArrayList resultList = (ArrayList) proceed;
            result = resultList.size();
        }
        if (proceed instanceof Integer) {
            result = (Integer) proceed;
        }
        if (enableSqlLogInterceptor) {
            long end = System.currentTimeMillis();
            long time = end - start;
            Boolean flag = (Boolean) ThreadLocalUtil.get(NotDisplaySqlAspect.DISPLAY_SQL);
            if (time >= noticeTime * SystemConstant.Number.THOUSAND_INT) {
                log.error("执行超过{}秒,sql={}", noticeTime, sql);
                log.error("result={}, time={}ms, params={}", result, time, paramList);
                return proceed;
            }
            if (flag == null || Objects.equals(flag, true)) {
                log.info("sql={}", sql);
                log.info("result={},time={}ms, params={}", result, time, paramList);
            }
        }
        return proceed;
    }

    @Override
    public Object plugin(Object o) {// 生成代理对象
        return Plugin.wrap(o, this);
    }

    @Override
    public void setProperties(Properties properties) {// 可以获取配置文件中配置的属性

    }

    /**
     * 获取sql参数集合。
     *
     * @param configuration the configuration
     * @param boundSql      the bound sql
     *
     * @return the param list
     */
    private List<String> getParamList(Configuration configuration, BoundSql boundSql) {
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        List<String> params = new ArrayList<>();
        if (parameterMappings.size() > 0 && parameterObject != null) {
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                params.add(getParameterValue(parameterObject));
            } else {
                MetaObject metaObject = configuration.newMetaObject(parameterObject);
                for (ParameterMapping parameterMapping : parameterMappings) {
                    String propertyName = parameterMapping.getProperty();
                    if (metaObject.hasGetter(propertyName)) {
                        Object obj = metaObject.getValue(propertyName);
                        params.add(getParameterValue(obj));
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        Object obj = boundSql.getAdditionalParameter(propertyName);
                        params.add(getParameterValue(obj));
                    }
                }
            }
        }
        return params;
    }

    private String getParameterValue(Object obj) {
        String value;
        if (obj instanceof String) {
            value = "'" + String.valueOf(obj) + "'";
        } else if (obj instanceof Date) {
            DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA);
            value = "'" + formatter.format(obj) + "'";
        } else {
            if (obj != null) {
                value = String.valueOf(obj);
            } else {
                value = "";
            }

        }
        return value;
    }
}
