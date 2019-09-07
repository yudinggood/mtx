package com.mtx.common.base;

import com.baidu.unbiz.fluentvalidator.ValidationError;
import com.mtx.common.constant.SystemConstant;
import com.mtx.common.util.base.RequestUtil;
import com.mtx.system.dao.model.SystemUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * controller基础类
 */
@Slf4j
public class BaseController {

    public ModelAndView getModelAndView(){
        return new ModelAndView();
    }

    public String errorListToString(List<ValidationError> list){
        StringBuffer sb =new StringBuffer();
        for(ValidationError error:list){
            sb.append(error.getErrorMsg());
        }
        return sb.toString();
    }

    protected HttpSession getSession() {
        return RequestUtil.getRequest().getSession();
    }

    protected SystemUser getSystemUser(){
        return (SystemUser) getSession().getAttribute(SystemConstant.SESSION_SYSTEM_USER);
    }
}
