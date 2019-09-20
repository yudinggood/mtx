package com.mtx.system.common.interceptor;

import com.mtx.common.constant.SystemConstant;
import com.mtx.common.util.base.ThreadLocalUtil;
import com.mtx.system.dao.model.SystemUser;
import com.mtx.system.rpc.api.SystemApiService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class InfoInterceptor implements HandlerInterceptor {
    @Autowired
    private SystemApiService systemApiService;


    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        SystemUser systemUser=(SystemUser)  ThreadLocalUtil.get(SystemConstant.SESSION_SYSTEM_USER);
        if(null==systemUser){
            //保存用户信息到ThreadLocal
            systemUser = systemApiService.selectSystemUserByUsername((String) SecurityUtils.getSubject().getPrincipal());
            ThreadLocalUtil.put(SystemConstant.SESSION_SYSTEM_USER, systemUser);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
