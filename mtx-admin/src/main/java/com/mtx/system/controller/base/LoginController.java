package com.mtx.system.controller.base;

import com.baidu.unbiz.fluentvalidator.ComplexResult;
import com.baidu.unbiz.fluentvalidator.FluentValidator;
import com.baidu.unbiz.fluentvalidator.ResultCollectors;
import com.mtx.common.base.BaseController;
import com.mtx.common.constant.SystemConstant;
import com.mtx.common.spring.SpringContextUtil;
import com.mtx.common.util.base.KaptchaUtil;
import com.mtx.common.util.base.PropertiesFileUtil;
import com.mtx.common.util.base.RedisUtil;
import com.mtx.common.util.validator.LengthValidator;
import com.mtx.common.util.validator.NotNullValidator;
import com.mtx.common.util.wrapper.WrapMapper;
import com.mtx.common.util.wrapper.Wrapper;
import com.mtx.system.common.bean.GlobalProperties;
import com.mtx.system.common.enums.PropertiesEnum;
import com.mtx.system.common.exception.ErrorCodeEnum;
import com.mtx.system.common.shiro.session.UpmsSession;
import com.mtx.system.common.shiro.session.UpmsSessionDao;
import com.mtx.system.dao.dto.SystemAttachDto;
import com.mtx.system.dao.dto.SystemUserDto;
import com.mtx.system.dao.model.SystemSystem;
import com.mtx.system.dao.model.SystemSystemExample;
import com.mtx.system.dao.model.SystemUser;
import com.mtx.system.rpc.api.SystemApiService;
import com.mtx.system.rpc.api.SystemSystemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.UUID;

@Slf4j
@Controller
@Api(value = "登录", description = "登录")
public class LoginController extends BaseController {

    // 全局会话key
    private final static String UPMS_SERVER_SESSION_ID = "upms-server-session-id";
    // 全局会话key列表
    private final static String UPMS_SERVER_SESSION_IDS = "upms-server-session-ids";
    // code keycon
    private final static String UPMS_SERVER_CODE = "upms-server-code";
    @Autowired
    private SystemApiService systemApiService;
    @Autowired
    UpmsSessionDao upmsSessionDao;
    @Autowired
    private SystemSystemService systemSystemService;

    @ApiOperation(value = "免密登录前置请求")
    @RequestMapping(value = "/pre", method = RequestMethod.GET)
    public String pre(HttpServletRequest request) throws Exception {
        String appid = request.getParameter("appid");
        String backurl = request.getParameter("backurl");
        if (StringUtils.isBlank(appid)) {
            throw new RuntimeException("无效访问！");
        }
        // 判断请求认证系统是否注册
        SystemSystemExample systemSystemExample = new SystemSystemExample();
        systemSystemExample.createCriteria()
                .andNameEqualTo(appid);
        int count = systemSystemService.countByExample(systemSystemExample);
        if (0 == count) {
            throw new RuntimeException(String.format("未注册的系统:%s", appid));
        }
        return "redirect:/login?backurl=" + URLEncoder.encode(backurl, "utf-8");
    }

    @ApiOperation(value = "登录页")
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView index(HttpServletRequest request){
        ModelAndView mv =this.getModelAndView();
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        String serverSessionId = session.getId().toString();
        // 判断是否已登录，如果已登录，则回跳
        String code = RedisUtil.get(UPMS_SERVER_SESSION_ID + "_" + serverSessionId);
        // code校验值
        if (StringUtils.isNotBlank(code)) {
            // 回跳
            String backurl = request.getParameter("backurl");
            String username = (String) subject.getPrincipal();
            if (StringUtils.isBlank(backurl)) {
                backurl = "/";
            } else {
                if (backurl.contains("?")) {
                    backurl += "&upms_code=" + code + "&upms_username=" + username;
                } else {
                    backurl += "?upms_code=" + code + "&upms_username=" + username;
                }
            }
            log.debug("认证中心帐号通过，带code回跳：{}", backurl);
            mv.setViewName("redirect:"+backurl);
        }else {
            mv.setViewName("/system/login/login");
        }

        return mv;
    }

    @ApiOperation(value = "获取验证码")
    @RequestMapping(value = "/code", method = RequestMethod.GET)
    public void code(HttpServletResponse response){
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        String code = KaptchaUtil.drawImg(output);

        Subject currentUser = SecurityUtils.getSubject();//将验证码放入session中
        Session session = currentUser.getSession();
        session.setAttribute(SystemConstant.SESSION_SECURITY_CODE, code);

        try {
            ServletOutputStream out = response.getOutputStream();
            output.writeTo(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @ApiOperation(value = "校验upmsCode")
    @RequestMapping(value = "/upmsCode", method = RequestMethod.POST)
    @ResponseBody
    public Object upmsCode(HttpServletRequest request) {
        String codeParam = request.getParameter("code");
        String code = RedisUtil.get(UPMS_SERVER_CODE + "_" + codeParam);
        if (StringUtils.isBlank(codeParam) || !codeParam.equals(code)) {
            return WrapMapper.wrap(ErrorCodeEnum.INVALID_UPMS_CODE);
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, code);
    }

    @ApiOperation(value = "登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Object login(SystemUserDto systemUserDto) {
        ComplexResult result = FluentValidator.checkAll()
                .on(systemUserDto.getLoginId(), new NotNullValidator("账号"))
                .on(systemUserDto.getPassword(), new NotNullValidator("密码"))
                .on(systemUserDto.getCode(), new NotNullValidator("验证码"))
                .doValidate()
                .result(ResultCollectors.toComplex());
        if (!result.isSuccess()) {
            return WrapMapper.wrap(Wrapper.ILLEGAL_ARGUMENT_CODE_,errorListToString(result.getErrors()));
        }

        //判断验证码正确性
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        String sessionId = session.getId().toString();
        String sessionCode = (String)session.getAttribute(SystemConstant.SESSION_SECURITY_CODE);		//获取session中的验证码
        if(!sessionCode.equalsIgnoreCase(systemUserDto.getCode())){
            return WrapMapper.wrap(ErrorCodeEnum.INVALID_CODE);
        }

        // 判断是否已登录，如果已登录，则回跳，防止重复登录
        String hasCode = RedisUtil.get(UPMS_SERVER_SESSION_ID + "_" + sessionId);
        if (StringUtils.isBlank(hasCode)) {
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(systemUserDto.getLoginId(), systemUserDto.getPassword());
            try {
                if (BooleanUtils.toBoolean(systemUserDto.getRememberMe())) {
                    usernamePasswordToken.setRememberMe(true);
                } else {
                    usernamePasswordToken.setRememberMe(false);
                }
                subject.login(usernamePasswordToken);
            } catch (UnknownAccountException e) {
                return WrapMapper.wrap(ErrorCodeEnum.INVALID_USERNAME_PASSWORD);
            } catch (IncorrectCredentialsException e) {
                return WrapMapper.wrap(ErrorCodeEnum.INVALID_USERNAME_PASSWORD);
            } catch (LockedAccountException e) {
                return WrapMapper.wrap(ErrorCodeEnum.INVALID_ACCOUNT);
            } catch (ExcessiveAttemptsException e){
                return WrapMapper.wrap(ErrorCodeEnum.EXCESSIVE_ATTEMPTS);
            }
            // 更新session状态
            upmsSessionDao.updateStatus(sessionId, UpmsSession.OnlineStatus.on_line);
            // 全局会话sessionId列表，供会话管理
            RedisUtil.lpush(UPMS_SERVER_SESSION_IDS, sessionId.toString());
            // 默认验证帐号密码正确，创建code
            String code = UUID.randomUUID().toString();
            // 全局会话的code
            RedisUtil.set(UPMS_SERVER_SESSION_ID + "_" + sessionId, code, (int) subject.getSession().getTimeout() / 1000);
            // code校验值
            RedisUtil.set(UPMS_SERVER_CODE + "_" + code, code, (int) subject.getSession().getTimeout() / 1000);
        }

        // 回跳登录前地址
        String backurl = systemUserDto.getBackUrl();
        if (StringUtils.isBlank(backurl)) {
            SystemSystem systemSystem = systemSystemService.selectByName(PropertiesFileUtil.getInstance().get("app.name"));
            backurl = null == systemSystem ? "/" : systemSystem.getBasepath();
            return WrapMapper.wrap(Wrapper.SUCCESS_CODE, backurl);
        } else {
            return WrapMapper.wrap(Wrapper.SUCCESS_CODE, backurl);
        }

    }

    @ApiOperation(value = "退出登录")
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ModelAndView logout(HttpServletRequest request) {
        ModelAndView mv =this.getModelAndView();
        // shiro退出登录
        SecurityUtils.getSubject().logout();
        // 跳回原地址
        String redirectUrl = request.getHeader("Referer");
        if (null == redirectUrl) {
            redirectUrl = "/";
        }
        mv.setViewName("redirect:"+redirectUrl);
        return mv;
    }

}
