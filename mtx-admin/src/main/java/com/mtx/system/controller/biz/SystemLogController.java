package com.mtx.system.controller.biz;

import com.baomidou.mybatisplus.plugins.Page;
import com.mtx.common.base.BaseController;
import com.mtx.common.util.page.PageFactory;
import com.mtx.common.util.page.PageInfoBT;
import com.mtx.common.util.wrapper.WrapMapper;
import com.mtx.system.dao.model.SystemLog;
import com.mtx.system.dao.vo.SystemLogVo;
import com.mtx.system.rpc.api.SystemLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/system/log")
@Api(value = "SystemLog控制器", description = "日志管理")
public class SystemLogController extends BaseController {

    @Autowired
    SystemLogService systemLogService;

    @ApiOperation(value = "日志首页")
    @RequiresPermissions("system:log:read")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView goIndex() {
        ModelAndView mv =this.getModelAndView();
        mv.setViewName("/system/log/log_index");
        return mv;
    }

    @ApiOperation(value = "日志列表")
    @RequiresPermissions("system:log:read")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Object list(@RequestParam(required = false) String search){
        Page<SystemLog> page = new PageFactory<SystemLog>().defaultPage();
        List<SystemLogVo> voList = systemLogService.list(page,search);
        return new PageInfoBT<>(voList,page.getTotal());
    }

    @ApiOperation(value = "日志删除")
    @RequiresPermissions("system:log:edit")
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)
    @ResponseBody
    public Object delete(@PathVariable("id") int id) {
        int count = systemLogService.deleteByPrimaryKey(id);
        return WrapMapper.wrap(count);
    }

    @ApiOperation(value = "多个日志删除")
    @RequiresPermissions("system:log:edit")
    @RequestMapping(value = "/deletes",method = RequestMethod.DELETE)
    @ResponseBody
    public Object delete(String ids) {
        int count = systemLogService.deleteByPrimaryKeys(ids);
        return WrapMapper.wrap(count);
    }
}
