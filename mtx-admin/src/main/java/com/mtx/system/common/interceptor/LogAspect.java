package com.mtx.system.common.interceptor;

import com.alibaba.fastjson.JSON;
import com.mtx.common.util.base.DateUtil;
import com.mtx.common.util.base.RequestUtil;
import com.mtx.common.util.base.TypeConversionUtil;
import com.mtx.system.common.bean.GlobalProperties;
import com.mtx.system.common.enums.PropertiesEnum;
import com.mtx.system.dao.model.SystemLog;
import com.mtx.system.rpc.api.SystemLogService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ObjectUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * 日志切面编程
 */
@Aspect
@Slf4j
public class LogAspect {
    @Autowired
    SystemLogService systemLogService;
    // 开始时间
    private long startTime = 0L;
    // 结束时间
    private long endTime = 0L;
    //使用AOP注意在mvc的配置文件里开启注解,面向的是controller的切面，非service
    @Before("execution(* *..controller..*.*(..))")//前置方法
    public void doBeforeInServiceLayer(JoinPoint joinPoint) {
        log.debug("--doBeforeInServiceLayer--");
        startTime = System.currentTimeMillis();
    }

    @After("execution(* *..controller..*.*(..))")//不管是抛出异常或者正常退出都会执行
    public void doAfterInServiceLayer(JoinPoint joinPoint) {
        log.debug("--doAfterInServiceLayer--");
    }
    //这个AOP就是将一个请求信息写入DB的过程
    @Around("execution(* *..controller..*.*(..))")//环绕增强,相当于执行该方法，result是返回值
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        // 获取request
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        HttpServletRequest request = servletRequestAttributes.getRequest();

        SystemLog systemLog = new SystemLog();
        // 从注解中获取操作名称、获取响应结果
        Object result = pjp.proceed();//执行后台方法,result是执行结果

        String flag =GlobalProperties.me().getValueByCode(PropertiesEnum.LOG_ASPECT);//开关
        if(!TypeConversionUtil.objectToBoolean(flag)){
            return result;
        }

        Signature signature = pjp.getSignature();//获取被增强的方法相关信息
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (method.isAnnotationPresent(ApiOperation.class)) {//通过注解获取value的值
            ApiOperation log = method.getAnnotation(ApiOperation.class);
            systemLog.setDescription(log.value());
        }
        if (method.isAnnotationPresent(RequiresPermissions.class)) {
            RequiresPermissions requiresPermissions = method.getAnnotation(RequiresPermissions.class);
            String[] permissions = requiresPermissions.value();
            if (permissions.length > 0) {
                systemLog.setPermission(permissions[0]);
            }
        }
        endTime = System.currentTimeMillis();
        log.debug("doAround>>>result={},耗时：{} ms", result, endTime - startTime);//log动态写法

        systemLog.setDomain(RequestUtil.getBasePath(request));
        systemLog.setIp(RequestUtil.getIpAddr(request));
        systemLog.setMethod(request.getMethod());
        if ("GET".equalsIgnoreCase(request.getMethod())) {
            systemLog.setParameter(request.getQueryString());
        } else {
            systemLog.setParameter(ObjectUtils.toString(request.getParameterMap()));
        }
        String json =JSON.toJSONString(result);
        if(json.length()>10000){
            json=json.substring(0,10000);
        }
        systemLog.setResult(json);
        systemLog.setUsedTime((int) (endTime - startTime));
        systemLog.setEditDate(DateUtil.longToDate(startTime));
        systemLog.setUri(request.getRequestURI());
        //systemLog.setUrl(ObjectUtils.toString(request.getRequestURL()));
        systemLog.setUserAgent(request.getHeader("User-Agent"));
        //systemLog.setEditUser(ObjectUtils.toString(request.getUserPrincipal()));
        systemLogService.insertSelective(systemLog);
        return result;
    }

}
