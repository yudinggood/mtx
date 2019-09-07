package com.mtx.system.controller.biz;

import com.baomidou.mybatisplus.plugins.Page;
import com.mtx.common.base.BaseController;
import com.mtx.common.util.page.PageFactory;
import com.mtx.common.util.page.PageInfoBT;
import com.mtx.common.util.wrapper.WrapMapper;
import com.mtx.common.util.wrapper.Wrapper;
import com.mtx.system.common.bean.DeleteFileTask;
import com.mtx.system.dao.dto.SystemAttachDto;
import com.mtx.system.dao.model.SystemAttach;
import com.mtx.system.dao.vo.SystemAttachVo;
import com.mtx.system.rpc.api.SystemAttachService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/system/attach")
@Api(value = "SystemAttach控制器", description = "文件管理")
public class SystemAttachController extends BaseController {
    @Autowired
    SystemAttachService systemAttachService;
    @Autowired
    DeleteFileTask deleteFileTask;

    @ApiOperation(value = "文件管理首页")
    @RequiresPermissions("system:attach:read")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView goIndex() {
        ModelAndView mv =this.getModelAndView();
        mv.setViewName("/system/attach/attach_index");
        return mv;
    }

    @ApiOperation(value = "文件管理列表")
    @RequiresPermissions("system:attach:read")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Object list(SystemAttachDto systemAttachDto){
        Page<SystemAttach> page = new PageFactory<SystemAttach>().defaultPage();
        List<SystemAttachVo> voList = systemAttachService.list(page,systemAttachDto);
        return new PageInfoBT<>(voList,page.getTotal());
    }

    @ApiOperation(value = "文件管理增改页")
    @RequiresPermissions("system:attach:edit")
    @RequestMapping(value = "/edit/{attachId}/{page}", method = RequestMethod.GET)
    public ModelAndView goEdit(@PathVariable("attachId") int attachId, @PathVariable("page") String page) {
        ModelAndView mv = this.getModelAndView();
        SystemAttachVo systemAttachVo=systemAttachService.selectByIdWithLeft(attachId);
        systemAttachVo.setPage(page);
        mv.addObject("systemAttachVo",systemAttachVo);
        mv.setViewName("/system/attach/attach_edit");
        return mv;
    }

    @ApiOperation(value = "文件管理增加")
    @RequiresPermissions("system:attach:edit")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public Object create(SystemAttachDto systemAttachDto) {
        if (null == systemAttachDto.getFile()) {
            return WrapMapper.wrap(Wrapper.ILLEGAL_ARGUMENT_CODE_,Wrapper.ILLEGAL_ARGUMENT_MESSAGE);
        }

        systemAttachDto.setEditUser(super.getSystemUser().getUserId());
        int count = systemAttachService.insertDto(systemAttachDto);
        return WrapMapper.wrap(count);
    }

    @ApiOperation(value = "文件删除")
    @RequiresPermissions("system:attach:edit")
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)
    @ResponseBody
    public Object delete(@PathVariable("id") int id) {
        SystemAttachVo systemAttachVo = systemAttachService.selectByIdWithLeft(id);
        int count = systemAttachService.deleteByPrimaryKey(id);
        deleteFileTask.deleteFileByPaths(systemAttachVo.getFilePath());
        return WrapMapper.wrap(count);
    }

    @ApiOperation(value = "多个文件删除")
    @RequiresPermissions("system:attach:edit")
    @RequestMapping(value = "/deletes",method = RequestMethod.DELETE)
    @ResponseBody
    public Object delete(String ids) {
        String[] filePaths = systemAttachService.selectForFilePaths(ids);
        int count = systemAttachService.deleteByPrimaryKeys(ids);
        deleteFileTask.deleteFileByPaths(filePaths);
        return WrapMapper.wrap(count);
    }
}
