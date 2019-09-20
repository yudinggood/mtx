package com.mtx.common.base;

import com.baidu.unbiz.fluentvalidator.ValidationError;
import com.mtx.common.constant.SystemConstant;
import com.mtx.common.util.base.RequestUtil;
import com.mtx.common.util.base.ThreadLocalUtil;
import com.mtx.system.dao.model.SystemUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * controller基础类
 */
@Slf4j
public class BaseController {
    protected static String REDIRECT = "redirect:";
    protected static String FORWARD = "forward:";
    /**
     * 获取视图
     */
    protected ModelAndView getModelAndView(){
        return new ModelAndView();
    }
    /**
     * 错误信息转换
     */
    protected String errorListToString(List<ValidationError> list){
        StringBuffer sb =new StringBuffer();
        for(ValidationError error:list){
            sb.append(error.getErrorMsg());
        }
        return String.valueOf(sb);
    }
    /**
     * 获取session
     */
    protected HttpSession getSession() {
        return getHttpServletRequest().getSession();
    }
    /**
     * 获取当前用户信息
     */
    protected SystemUser getSystemUser(){
        //SystemUser systemUser=(SystemUser) getSession().getAttribute(SystemConstant.SESSION_SYSTEM_USER);
        SystemUser systemUser=(SystemUser) ThreadLocalUtil.get(SystemConstant.SESSION_SYSTEM_USER);
        return systemUser;
    }
    /**
     * 获取request
     */
    protected HttpServletRequest getHttpServletRequest() {
        return RequestUtil.getRequest();
    }
    /**
     * 获取param
     */
    protected String getPara(String name) {
        return getHttpServletRequest().getParameter(name);
    }
    /**
     * 获取header
     */
    protected String getHead(String name) {
        return getHttpServletRequest().getHeader(name);
    }
}






