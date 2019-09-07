package com.mtx.system.common.bean;

import com.mtx.common.constant.SystemConstant;
import com.mtx.common.util.base.RequestUtil;
import com.mtx.common.util.exception.ErrorManager;
import com.mtx.common.util.wrapper.WrapMapper;
import com.mtx.system.common.exception.ErrorCodeEnum;
import com.mtx.system.dao.model.SystemUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.session.InvalidSessionException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 统一异常处理   只要controller层有错误就到这里   try catch除外
     */
    @ExceptionHandler
    @ResponseBody
    public Object exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception exception) {
        log.error("统一异常处理：", exception);
        //先存入DB
        SystemUser systemUser=(SystemUser) RequestUtil.getRequest().getSession().getAttribute(SystemConstant.SESSION_SYSTEM_USER);
        ErrorManager.me().executeLog(ErrorTaskFactory.me().exceptionLog(systemUser.getUserId(),exception));
        ModelAndView mv =this.getModelAndView();
        mv.addObject("ex",exception);
        if (null != request.getHeader("X-Requested-With") && "XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"))) {
            request.setAttribute("requestHeader", "ajax");//当ajax请求时，返回错误

            if (exception instanceof UnauthorizedException) {
                return WrapMapper.error(ErrorCodeEnum.SYS99990401.message());
            }
            return WrapMapper.error(exception.getMessage());
        }
        // shiro没有权限异常
        if (exception instanceof UnauthorizedException) {
            mv.setViewName("/base/403");
            return mv;
        }
        // shiro会话已过期异常
        if (exception instanceof InvalidSessionException) {
            mv.setViewName("/base/error");
            return mv;
        }
        mv.setViewName("/base/error");
        return mv;
    }

    public ModelAndView getModelAndView(){
        return new ModelAndView();
    }
}
