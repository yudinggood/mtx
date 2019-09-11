package com.mtx.system.controller.base;

import com.mtx.common.base.BaseController;
import com.mtx.common.constant.SystemConstant;
import com.mtx.common.util.base.RedisUtil;
import com.mtx.common.util.base.TypeConversionUtil;
import com.mtx.common.util.tag.Menu;
import com.mtx.system.common.bean.GlobalProperties;
import com.mtx.system.common.enums.PropertiesEnum;
import com.mtx.system.common.shiro.session.UpmsSessionDao;
import com.mtx.system.dao.model.SystemRole;
import com.mtx.system.dao.model.SystemUser;
import com.mtx.system.rpc.api.SystemApiService;
import com.mtx.system.rpc.api.SystemPermissionService;
import com.mtx.system.rpc.api.SystemUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import redis.clients.jedis.Jedis;

import java.util.Collection;
import java.util.Date;
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

    @ApiOperation(value = "后台首页")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index(){
        SystemUser systemUser =  super.getSystemUser();
        if(null==systemUser){
            //保存用户信息到session
            Subject subject = SecurityUtils.getSubject();
            systemUser = systemApiService.selectSystemUserByUsername((String) subject.getPrincipal());
            super.getSession().setAttribute(SystemConstant.SESSION_SYSTEM_USER, systemUser);

            //更新用户的登录信息
            systemUser.setLastIp(subject.getSession().getHost());
            systemUserService.updateByUser(systemUser);
        }


        ModelAndView mv =this.getModelAndView();
        //mv.addObject("menuMap",systemPermissionService.selectForMenu());
        mv.addObject("topMenuList",systemPermissionService.selectForTopMenu());
        mv.addObject("indexPage", GlobalProperties.me().getValueByCode(PropertiesEnum.INDEX_PAGE));
        mv.addObject("systemVersion", GlobalProperties.me().getValueByCode(PropertiesEnum.SYSTEM_VERSION));
        mv.addObject("systemUser",systemUser);
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



}
