package com.mtx.system.controller.base;

import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.baidu.unbiz.fluentvalidator.ComplexResult;
import com.baidu.unbiz.fluentvalidator.FluentValidator;
import com.baidu.unbiz.fluentvalidator.ResultCollectors;
import com.mtx.common.base.BaseController;
import com.mtx.common.constant.SystemConstant;
import com.mtx.common.util.base.*;
import com.mtx.common.util.validator.NotNullValidator;
import com.mtx.common.util.wrapper.WrapMapper;
import com.mtx.common.util.wrapper.Wrapper;
import com.mtx.system.common.bean.GlobalProperties;
import com.mtx.system.common.enums.PropertiesEnum;
import com.mtx.system.common.exception.BusinessException;
import com.mtx.system.common.exception.ErrorCodeEnum;
import com.mtx.system.common.shiro.EasyTypeToken;
import com.mtx.system.common.shiro.session.UpmsSession;
import com.mtx.system.common.shiro.session.UpmsSessionDao;
import com.mtx.system.dao.dto.SystemUserDto;
import com.mtx.system.dao.model.SystemError;
import com.mtx.system.dao.model.SystemSystem;
import com.mtx.system.dao.model.SystemSystemExample;
import com.mtx.system.dao.model.SystemUser;
import com.mtx.system.dao.vo.PageInfo;
import com.mtx.system.rpc.api.*;
import com.mtx.system.rpc.factory.SystemLogFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

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
    @Autowired
    private SystemUserService systemUserService;
    @Autowired
    private SystemRoleService systemRoleService;
    @Autowired
    SystemErrorService systemErrorService;
    @Autowired
    SystemAttachService systemAttachService;

    @ApiOperation(value = "免密登录前置请求")
    @RequestMapping(value = "/pre", method = RequestMethod.GET)
    public String pre() throws Exception {
        String appid = getPara("appid");
        String backurl = getPara("backurl");
        if (StringUtils.isBlank(appid)) {
            throw new RuntimeException("无效访问！");
        }
        // 判断请求认证系统是否注册
        SystemSystemExample     systemSystemExample = new SystemSystemExample();
        systemSystemExample.createCriteria()
                .andNameEqualTo(appid);
        int count = systemSystemService.countByExample(systemSystemExample);
        if (0 == count) {
            throw new RuntimeException(String.format("未注册的系统:%s", appid));
        }
        return REDIRECT+"/login?backurl=" + URLEncoder.encode(backurl, "utf-8");
    }

    @ApiOperation(value = "登录页")
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView index(){
        ModelAndView mv =this.getModelAndView();
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        String serverSessionId = session.getId().toString();
        // 判断是否已登录，如果已登录，则回跳
        String code = RedisUtil.get(UPMS_SERVER_SESSION_ID + "_" + serverSessionId);
        String backurl = getPara("backurl");
        // code校验值
        if (StringUtils.isNotBlank(code)) {
            // 回跳
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
            mv.setViewName(REDIRECT+backurl);
        }else {
            //页面授权要用的
            PageInfo pageInfo = new PageInfo();
            pageInfo.setGitBookDomain(GlobalProperties.me().getValueByCode(PropertiesEnum.GIT_BOOK));
            mv.addObject("pageInfo",pageInfo);
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
        session.setAttribute(getPara("type"), code);

        try {
            ServletOutputStream out = response.getOutputStream();
            output.writeTo(out);
        } catch (IOException e) {
            log.error(e.getMessage(),e);
        }
    }

    @ApiOperation(value = "校验upmsCode")
    @RequestMapping(value = "/upmsCode", method = RequestMethod.POST)
    @ResponseBody
    public Object upmsCode() {
        String codeParam = getPara("code");
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
        //未激活判断等
        SystemUser systemUser = systemApiService.selectSystemUserByUsername(systemUserDto.getLoginId());
        if(systemUser==null){
            return WrapMapper.wrap(ErrorCodeEnum.INVALID_USERNAME_PASSWORD);
        }

        // 判断是否已登录，如果已登录，则回跳，防止重复登录
        String hasCode = RedisUtil.get(UPMS_SERVER_SESSION_ID + "_" + sessionId);
        if (StringUtils.isBlank(hasCode)) {
            EasyTypeToken usernamePasswordToken = new EasyTypeToken(systemUserDto.getLoginId(), systemUserDto.getPassword());
            try {
                usernamePasswordToken.setRememberMe(BooleanUtils.toBoolean(systemUserDto.getRememberMe()));
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
            //登录成功
            loginSuccess(systemUserDto.getLoginId());
        }
        //如果有openId则保存到DB
        if (StringUtils.isNotBlank(systemUserDto.getQqOpenId())){
            systemUser.setQqOpenId(systemUserDto.getQqOpenId());
            systemUser.setNickName(systemUserDto.getNickName());
            systemUserService.updateOpenId(systemUser);
            //保存头像
            systemUserDto.setUserId(systemUser.getUserId());
            systemAttachService.insertDtoQqHead(systemUserDto);
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
    public ModelAndView logout() {
        ModelAndView mv =this.getModelAndView();
        // shiro退出登录
        SecurityUtils.getSubject().logout();
        // 跳回原地址
        String redirectUrl = getHead("Referer");
        if (null == redirectUrl) {
            redirectUrl = "/";
        }
        mv.setViewName(REDIRECT+redirectUrl);
        return mv;
    }

    @ApiOperation(value = "QQ授权")
    @RequestMapping(value = "/qqAuthorization", method = RequestMethod.GET)
    public ModelAndView qqAuthorization() throws UnsupportedEncodingException {
        ModelAndView mv =this.getModelAndView();
        PageInfo pageInfo=new PageInfo();
        String result = RequestUtil.getHtml("https://graph.qq.com/oauth2.0/token?grant_type=authorization_code&client_id=" + pageInfo.getQqAppId()+
                "&client_secret="+pageInfo.getQqClientSecret()+"&redirect_uri="+URLEncoder.encode(pageInfo.getQqAuthPath(), "utf-8")+"&code="+getPara("code"));
        if(result.contains("callback")){
            throw new BusinessException(ErrorCodeEnum.INVALID_QQ_AUTHO);
        }
        String accessToken= StringUtil.getParam(result,"access_token");
        String result2=RequestUtil.getHtml("https://graph.qq.com/oauth2.0/me?access_token="+accessToken);
        String openid=RegularUtil.getRegularByString("(.*)openid\":\"(.*)\"}(.*)",result2).get(2);
        String result3=RequestUtil.getHtml("https://graph.qq.com/user/get_user_info?access_token="+accessToken+"&oauth_consumer_key="+pageInfo.getQqAppId()+"&openid="+openid);
        Map<String, Object> resp = JsonUtil.json2map(result3);
        String nickname = StringUtil.filterOffUtf8Mb4((String)resp.get("nickname")).trim();
        String avatar = (String)resp.get("figureurl_2");
        String gender = (String)resp.get("gender");

        SystemUser systemUser = systemUserService.selectByQqOpenId(openid);
        if(systemUser==null) {//需要绑定 账号
            mv.addObject("OPENID_QQ",openid);
            mv.addObject("nickname",nickname);
            mv.addObject("avatar",avatar);
            mv.setViewName("/system/login/login_oauth");
        }else {//直接登录
            Subject subject = SecurityUtils.getSubject();
            Session session = subject.getSession();
            String sessionId = session.getId().toString();
            //把免密登录存入Redis
            RedisUtil.set(SystemConstant.UPMS_WITHOUT_PASSWORD + "_" + sessionId, ToolUtil.getUuid(), (int) subject.getSession().getTimeout() / 1000);
            String backurl = resolveLogin(systemUser.getPhone());

            mv.addObject("backurl",backurl);
            mv.setViewName("/system/login/login_success");
        }
        return mv;
    }
    //登录处理方法
    private String resolveLogin(String loginId) {
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        String sessionId = session.getId().toString();

        // 判断是否已登录，如果已登录，则回跳，防止重复登录
        String hasCode = RedisUtil.get(UPMS_SERVER_SESSION_ID + "_" + sessionId);
        if (StringUtils.isBlank(hasCode)) {
            EasyTypeToken usernamePasswordToken = new EasyTypeToken(loginId);
            try {
                subject.login(usernamePasswordToken);
            } catch (UnknownAccountException e) {
                throw new BusinessException(ErrorCodeEnum.INVALID_USERNAME_PASSWORD);
            } catch (IncorrectCredentialsException e) {
                throw new BusinessException(ErrorCodeEnum.INVALID_USERNAME_PASSWORD);
            } catch (LockedAccountException e) {
                throw new BusinessException(ErrorCodeEnum.INVALID_ACCOUNT);
            } catch (ExcessiveAttemptsException e){
                throw new BusinessException(ErrorCodeEnum.EXCESSIVE_ATTEMPTS);
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
            //登录成功
            loginSuccess(loginId);
        }

        // 回跳登录前地址
        SystemSystem systemSystem = systemSystemService.selectByName(PropertiesFileUtil.getInstance().get("app.name"));
        String backurl = null == systemSystem ? "/" : systemSystem.getBasepath();
        return backurl;
    }
    //登录成功的处理
    private void loginSuccess(String loginId) {
        SystemUser systemUser = systemApiService.selectSystemUserByUsername(loginId);
        //同时只允许一个账号登录    获取在线的session
        upmsSessionDao.otherFouceOut(systemUser.getUserId());
        //更新用户的登录信息
        systemUser.setLastIp(SecurityUtils.getSubject().getSession().getHost());
        systemUserService.updateByUser(systemUser);
        //登录log插入
        SystemError systemError = SystemLogFactory.createErrorLog(systemUser.getUserId());
        systemErrorService.insertSelective(systemError);
    }

    @ApiOperation(value = "校验验证码后发送短信")
    @RequestMapping(value = "/sendSms", method = RequestMethod.POST)
    @ResponseBody
    public Object sendSms(SystemUserDto systemUserDto) {
        //判断当前网络状态
        if(!ToolUtil.isNetConnect()){
            throw new BusinessException(ErrorCodeEnum.SYS99990200);
        }

        ComplexResult result = FluentValidator.checkAll()
                .on(systemUserDto.getLoginId(), new NotNullValidator("账号"))
                .on(systemUserDto.getCode(), new NotNullValidator("验证码"))
                .doValidate()
                .result(ResultCollectors.toComplex());
        if (!result.isSuccess()) {
            return WrapMapper.wrap(Wrapper.ILLEGAL_ARGUMENT_CODE_, errorListToString(result.getErrors()));
        }
        //限制某个IP一天内验证码发送次数


        //判断验证码正确性
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        String sessionId = session.getId().toString();
        String sessionCode = (String)session.getAttribute(SystemConstant.REGISTER_SESSION_SECURITY_CODE);
        if(!sessionCode.equalsIgnoreCase(systemUserDto.getCode())){
            return WrapMapper.wrap(ErrorCodeEnum.INVALID_CODE);
        }
        //校验手机号是否已经存在
        SystemUser systemUser = systemApiService.selectSystemUserByUsername(systemUserDto.getLoginId());
        if(systemUser!=null){
            return WrapMapper.wrap(ErrorCodeEnum.ACCOUNT_IS_EXIST);
        }

        //发送短信
        SendSmsResponse sendSmsResponse =SendSmsUtil.sendSms(systemUserDto.getLoginId(),SendSmsUtil.getCaptcha());
        if("OK".equals(sendSmsResponse.getCode())){
            return WrapMapper.ok();
        }else {
            return WrapMapper.wrap(ErrorCodeEnum.SMS_ERROR.code(),sendSmsResponse.getMessage()+ErrorCodeEnum.SMS_ERROR.message());
        }
    }

    @ApiOperation(value = "注册后登录")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public Object register(SystemUserDto systemUserDto) {
        ComplexResult result = FluentValidator.checkAll()
                .on(systemUserDto.getLoginId(), new NotNullValidator("账号"))
                .on(systemUserDto.getPassword(), new NotNullValidator("密码"))
                .on(systemUserDto.getVerifyNo(), new NotNullValidator("动态码"))
                .doValidate()
                .result(ResultCollectors.toComplex());
        if (!result.isSuccess()) {
            return WrapMapper.wrap(Wrapper.ILLEGAL_ARGUMENT_CODE_,errorListToString(result.getErrors()));
        }

        //校验手机号是否已经存在
        SystemUser systemUser = systemApiService.selectSystemUserByUsername(systemUserDto.getLoginId());
        if(systemUser!=null){
            return WrapMapper.wrap(ErrorCodeEnum.ACCOUNT_IS_EXIST);
        }

        //判断动态码正确性
        QuerySendDetailsResponse querySendDetailsResponse = SendSmsUtil.querySendDetails(systemUserDto.getLoginId());
        List<QuerySendDetailsResponse.SmsSendDetailDTO> dto = (List<QuerySendDetailsResponse.SmsSendDetailDTO>) querySendDetailsResponse.getSmsSendDetailDTOs();
        if("OK".equals(querySendDetailsResponse.getCode())&&ToolUtil.isNotEmpty(dto)){
            Date receiveDate = DateUtil.strToDate(dto.get(0).getReceiveDate());
            boolean isExpired = DateUtil.inMinute(receiveDate,5);
            boolean isSamePhone = querySendDetailsResponse.getSmsSendDetailDTOs().get(0).getPhoneNum().equalsIgnoreCase(systemUserDto.getLoginId());
            boolean isSameCode = querySendDetailsResponse.getSmsSendDetailDTOs().get(0).getOutId().equalsIgnoreCase(systemUserDto.getVerifyNo());
            if(isSamePhone&&true&&isSameCode){
                //注册  绑定到普通用户
                systemUserDto.setSalt(ToolUtil.getUuid());
                systemUserDto.setPassword(StringUtil.toSecretUncommonString(systemUserDto.getPassword(),systemUserDto.getSalt()));
                systemUserDto.setUserState((byte) 1);
                systemUserDto.setNickName(StringUtil.getStringRandom());
                Integer roleId =systemRoleService.selectByCode(SystemConstant.COMMON_ROLE);
                systemUserDto.setRoleId(new String[]{TypeConversionUtil.objectToString(roleId)});
                systemUserDto.setOrganizationId(new String[]{TypeConversionUtil.objectToString(1)});
                int count = systemUserService.insertDto(systemUserDto);
                if(count!=0){
                    Subject subject = SecurityUtils.getSubject();
                    Session session = subject.getSession();
                    String sessionId = session.getId().toString();
                    //把免密登录存入Redis
                    RedisUtil.set(SystemConstant.UPMS_WITHOUT_PASSWORD + "_" + sessionId, ToolUtil.getUuid(), (int) subject.getSession().getTimeout() / 1000);
                    String backurl = resolveLogin(systemUserDto.getLoginId());

                    return WrapMapper.wrap(Wrapper.SUCCESS_CODE, backurl);
                }else {
                    return WrapMapper.wrap(count);
                }

            }

        }

        return WrapMapper.wrap(ErrorCodeEnum.SMS_INFO_ERROR);
    }

    @ApiOperation(value = "国际化切换")
    @RequestMapping(value = "/changeLang", method = RequestMethod.GET)
    public ModelAndView changeLang(HttpServletRequest request, HttpServletResponse response, @RequestParam(value="langType", defaultValue="zh") String langType) {
        if("zh".equals(langType)){
            Locale locale = new Locale("zh", "CN");
            (new CookieLocaleResolver()).setLocale (request, response, locale);
        }else if("en".equals(langType)){
            Locale locale = new Locale("en", "US");
            (new CookieLocaleResolver()).setLocale (request, response, locale);
        }else{
            (new CookieLocaleResolver()).setLocale (request, response, LocaleContextHolder.getLocale());
        }

        ModelAndView mv =this.getModelAndView();
        mv.setViewName(REDIRECT+"/");
        return mv;
    }

}





