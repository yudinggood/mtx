package com.mtx.system.controller.biz;

import com.baomidou.mybatisplus.plugins.Page;
import com.mtx.common.base.BaseController;
import com.mtx.common.util.page.PageFactory;
import com.mtx.common.util.page.PageInfoBT;
import com.mtx.common.util.wrapper.WrapMapper;
import com.mtx.system.common.bean.DictCacheKit;
import com.mtx.system.common.enums.DictEnum;
import com.mtx.system.common.shiro.session.UpmsSessionDao;
import com.mtx.system.dao.dto.SystemErrorDto;
import com.mtx.system.dao.model.SystemError;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


import com.mtx.common.base.BaseController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/system/session")
public class SystemSessionController extends BaseController {
    @Autowired
    private UpmsSessionDao sessionDAO;

    @ApiOperation(value = "会话设置首页")
    @RequiresPermissions("system:session:read")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView goIndex() {
        ModelAndView mv =this.getModelAndView();
        mv.setViewName("/system/session/session_index");
        return mv;
    }

    @ApiOperation(value = "会话设置列表")
    @RequiresPermissions("system:session:read")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Object list(){
        Page<Session> page = new PageFactory<Session>().defaultPage();
        List<Session> voList = sessionDAO.list(page);
        return new PageInfoBT<>(voList,page.getTotal());
    }

    @ApiOperation(value = "强制退出")
    @RequiresPermissions("system:session:edit")
    @RequestMapping(value = "/forceout/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Object forceout(@PathVariable("id") String id) {
        int count = sessionDAO.forceout(id);
        return WrapMapper.wrap(count);
    }

}
