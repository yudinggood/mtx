package com.mtx.system.controller.biz;

import com.baomidou.mybatisplus.plugins.Page;
import com.mtx.common.base.BaseController;
import com.mtx.common.util.page.PageFactory;
import com.mtx.common.util.page.PageInfoBT;
import com.mtx.common.util.wrapper.WrapMapper;
import com.mtx.system.common.bean.DictCacheKit;
import com.mtx.system.common.enums.DictEnum;
import com.mtx.system.dao.dto.SystemErrorDto;
import com.mtx.system.dao.model.SystemError;
import com.mtx.system.rpc.api.SystemErrorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/system/error")
@Api(value = "SystemError控制器", description = "错误日志")
public class SystemErrorController extends BaseController {
    @Autowired
    SystemErrorService systemErrorService;

    @ApiOperation(value = "错误日志首页")
    @RequiresPermissions("system:error:read")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView goIndex() {
        ModelAndView mv =this.getModelAndView();
        mv.addObject("typeSelectVO", DictCacheKit.me().getBizDictByCode(DictEnum.ERROR_TYPE));
        mv.setViewName("/system/error/error_index");
        return mv;
    }

    @ApiOperation(value = "错误日志列表")
    @RequiresPermissions("system:error:read")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Object list(SystemErrorDto systemErrorDto){
        Page<SystemError> page = new PageFactory<SystemError>().defaultPage();
        List<SystemError> voList = systemErrorService.list(page,systemErrorDto);
        return new PageInfoBT<>(voList,page.getTotal());
    }

    @ApiOperation(value = "错误日志删除")
    @RequiresPermissions("system:error:edit")
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)
    @ResponseBody
    public Object delete(@PathVariable("id") int id) {
        int count = systemErrorService.deleteByPrimaryKey(id);
        return WrapMapper.wrap(count);
    }

    @ApiOperation(value = "多个错误日志删除")
    @RequiresPermissions("system:error:edit")
    @RequestMapping(value = "/deletes",method = RequestMethod.DELETE)
    @ResponseBody
    public Object delete(String ids) {
        int count = systemErrorService.deleteByPrimaryKeys(ids);
        return WrapMapper.wrap(count);
    }
}
