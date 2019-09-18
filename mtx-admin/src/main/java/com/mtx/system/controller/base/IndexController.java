package com.mtx.system.controller.base;

import com.baidu.unbiz.fluentvalidator.ComplexResult;
import com.baidu.unbiz.fluentvalidator.FluentValidator;
import com.baidu.unbiz.fluentvalidator.ResultCollectors;
import com.mtx.common.base.BaseController;
import com.mtx.common.constant.SystemConstant;
import com.mtx.common.interceptor.NotDisplaySql;
import com.mtx.common.util.base.StringUtil;
import com.mtx.common.util.base.ToolUtil;
import com.mtx.common.util.secret.MD5Util;
import com.mtx.common.util.tag.Menu;
import com.mtx.common.util.validator.LengthValidator;
import com.mtx.common.util.wrapper.WrapMapper;
import com.mtx.common.util.wrapper.Wrapper;
import com.mtx.system.common.bean.GlobalProperties;
import com.mtx.system.common.email.SimpleMailSender;
import com.mtx.system.common.enums.PropertiesEnum;
import com.mtx.system.common.exception.BusinessException;
import com.mtx.system.common.exception.ErrorCodeEnum;
import com.mtx.system.common.shiro.session.UpmsSessionDao;
import com.mtx.system.common.socket.MyMessageHandler;
import com.mtx.system.dao.dto.SystemUserDto;
import com.mtx.system.dao.model.SystemRole;
import com.mtx.system.dao.model.SystemUser;
import com.mtx.system.dao.vo.SystemUserVo;
import com.mtx.system.rpc.api.SystemApiService;
import com.mtx.system.rpc.api.SystemPermissionService;
import com.mtx.system.rpc.api.SystemUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/system")
@Api(value = "后台管理", description = "后台管理")
public class IndexController extends BaseController {
    @Autowired
    private SystemPermissionService systemPermissionService;
    @Autowired
    private SystemApiService systemApiService;
    @Autowired
    UpmsSessionDao upmsSessionDao;
    @Autowired
    private SystemUserService systemUserService;
    @Autowired
    private MyMessageHandler handler;

    @ApiOperation(value = "后台首页")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    @NotDisplaySql
    public ModelAndView index(){
        SystemUser systemUser =  super.getSystemUser();
        if(null==systemUser){
            //保存用户信息到session
            Subject subject = SecurityUtils.getSubject();
            systemUser = systemApiService.selectSystemUserByUsername((String) subject.getPrincipal());
            super.getSession().setAttribute(SystemConstant.SESSION_SYSTEM_USER, systemUser);
        }


        ModelAndView mv =this.getModelAndView();
        //mv.addObject("menuMap",systemPermissionService.selectForMenu());
        mv.addObject("topMenuList",systemPermissionService.selectForTopMenu());
        mv.addObject("indexPage", GlobalProperties.me().getValueByCode(PropertiesEnum.INDEX_PAGE));
        mv.addObject("systemVersion", GlobalProperties.me().getValueByCode(PropertiesEnum.SYSTEM_VERSION));
        SystemUserVo systemUserVo=systemUserService.selectByIdWithLeft(systemUser.getUserId());
        mv.addObject("systemUser",systemUserVo);
        mv.setViewName("/system/admin/index");
        return mv;
    }

    @ApiOperation(value = "后台首页菜单")
    @RequestMapping(value = "/menu/{nowMenuId}", method = RequestMethod.GET)
    @ResponseBody
    public Object menu(@PathVariable("nowMenuId") int nowMenuId){
        SystemUser systemUser = (SystemUser) super.getSession().getAttribute(SystemConstant.SESSION_SYSTEM_USER);
        List<SystemRole> roleList = systemApiService.selectSystemRoleBySystemUserId(systemUser.getUserId());

        List<Menu> menuList = systemPermissionService.selectForMenuUseJs(nowMenuId,roleList);
        return menuList;
    }

    @ApiOperation(value = "个人设置页")
    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public ModelAndView profile(){
        ModelAndView mv =this.getModelAndView();
        mv.setViewName("/system/admin/profile");
        return mv;
    }

    @ApiOperation(value = "个人信息页")
    @RequiresPermissions("system:user:edit")
    @RequestMapping(value = "/userInfo", method = RequestMethod.GET)
    public ModelAndView userInfo(){
        SystemUserVo systemUserVo=systemUserService.selectByIdWithLeft(super.getSystemUser().getUserId());
        ModelAndView mv =this.getModelAndView();
        mv.addObject("systemUser",systemUserVo);
        mv.setViewName("/system/admin/user_info");
        return mv;
    }

    @ApiOperation(value = "账号绑定页")
    @RequestMapping(value = "/userBind", method = RequestMethod.GET)
    public ModelAndView userBind(){
        SystemUserVo systemUserVo=systemUserService.selectByIdWithLeft(super.getSystemUser().getUserId());
        ModelAndView mv =this.getModelAndView();
        mv.addObject("systemUser",systemUserVo);
        mv.setViewName("/system/admin/user_bind");
        return mv;
    }

    @ApiOperation(value = "修改密码页")
    @RequestMapping(value = "/userPwd", method = RequestMethod.GET)
    public ModelAndView userPwd(){
        ModelAndView mv =this.getModelAndView();
        mv.setViewName("/system/admin/user_pwd");
        return mv;
    }

    @ApiOperation(value = "个人信息修改")
    @RequiresPermissions("system:user:edit")
    @RequestMapping(value = "/updateUserInfo/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public Object update(@PathVariable("id") int id, SystemUserDto systemUserDto) {
        systemUserDto.setUserId(id);
        ComplexResult result = FluentValidator.checkAll()
                .on(systemUserDto.getNickName(), new LengthValidator(1, 20, "名称"))
                .doValidate()
                .result(ResultCollectors.toComplex());
        if (!result.isSuccess()) {
            return WrapMapper.wrap(Wrapper.ILLEGAL_ARGUMENT_CODE_,errorListToString(result.getErrors()));
        }
        SystemUserVo systemUserVo = systemUserService.selectByIdWithLeft(id);
        systemUserDto.setExtProps(systemUserVo.getExtProps());

        int count = systemUserService.updateDtoOnly(systemUserDto);
        if(count>0){
            Subject subject = SecurityUtils.getSubject();
            SystemUser systemUser = systemApiService.selectSystemUserByUsername((String) subject.getPrincipal());
            //刷新用户信息
            super.getSession().setAttribute(SystemConstant.SESSION_SYSTEM_USER, systemUser);
            //发送websocket信息
            handler.sendMessageToUser(String.valueOf(id), systemUser.getNickName());
        }
        return WrapMapper.wrap(count);
    }

    @ApiOperation(value = "密码修改")
    @RequiresPermissions("system:user:edit")
    @RequestMapping(value = "/updateUserPwd/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public Object updateUserPwd(@PathVariable("id") int id, SystemUserDto systemUserDto) {
        systemUserDto.setUserId(id);
        ComplexResult result = FluentValidator.checkAll()
                .on(systemUserDto.getOldPwd(), new LengthValidator(6, 20, "旧密码"))
                .on(systemUserDto.getNewPwd(), new LengthValidator(6, 20, "新密码"))
                .doValidate()
                .result(ResultCollectors.toComplex());
        if (!result.isSuccess()) {
            return WrapMapper.wrap(Wrapper.ILLEGAL_ARGUMENT_CODE_,errorListToString(result.getErrors()));
        }
        //判断旧密码是否正确
        SystemUserVo systemUserVo = systemUserService.selectByIdWithLeft(id);
        String old = StringUtil.toSecretUncommonString(systemUserDto.getOldPwd(),systemUserVo.getSalt());
        if(!old.equals(systemUserVo.getPassword())){
            return new BusinessException(ErrorCodeEnum.INVALID_PASSWORD_EDIT);
        }

        systemUserDto.setSalt(ToolUtil.getUuid())
                .setPassword(StringUtil.toSecretUncommonString(systemUserDto.getNewPwd(),systemUserDto.getSalt()));
        int count = systemUserService.updateDtoOnly(systemUserDto);
        return WrapMapper.wrap(count);
    }

    @ApiOperation(value = "发送邮件")
    @RequiresPermissions("system:user:edit")
    @RequestMapping(value = "/sendEmail/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object sendEmail(@PathVariable("id") int id, SystemUserDto systemUserDto) {
        //判断当前网络状态
        if(!ToolUtil.isNetConnect()){
            throw new BusinessException(ErrorCodeEnum.SYS99990200);
        }

        SystemUserVo systemUserVo = systemUserService.selectByIdWithLeft(id);
        String token = MD5Util.md5(systemUserDto.getEmail()+System.currentTimeMillis());
        systemUserDto.setEmailToken(token);
        systemUserDto.setExtProps(systemUserVo.getExtProps());
        systemUserDto.setUserId(id);
        systemUserDto.setNickName(systemUserVo.getNickName());
        SimpleMailSender sender=new SimpleMailSender();
        sender.sendEmail(systemUserDto);

        systemUserDto.setEmail(null);
        int count = systemUserService.updateDtoOnly(systemUserDto);
        return WrapMapper.wrap(count);
    }

    @ApiOperation(value = "邮件确认")
    @RequestMapping(value = "/activeEmail", method = RequestMethod.GET)
    public ModelAndView activeEmail(SystemUserDto systemUserDto){
        //判断token
        systemUserService.activeEmail(systemUserDto);

        ModelAndView mv =this.getModelAndView();
        mv.setViewName(REDIRECT+"/");
        return mv;
    }
}
