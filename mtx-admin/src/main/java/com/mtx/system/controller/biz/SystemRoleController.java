package com.mtx.system.controller.biz;

import com.alibaba.fastjson.JSONArray;
import com.baidu.unbiz.fluentvalidator.ComplexResult;
import com.baidu.unbiz.fluentvalidator.FluentValidator;
import com.baidu.unbiz.fluentvalidator.ResultCollectors;
import com.baomidou.mybatisplus.plugins.Page;
import com.mtx.common.base.BaseController;
import com.mtx.common.util.page.PageFactory;
import com.mtx.common.util.page.PageInfoBT;
import com.mtx.common.util.validator.LengthValidator;
import com.mtx.common.util.wrapper.WrapMapper;
import com.mtx.common.util.wrapper.Wrapper;
import com.mtx.system.dao.dto.SystemRoleDto;
import com.mtx.system.dao.model.SystemRole;
import com.mtx.system.dao.vo.SystemRoleVo;
import com.mtx.system.rpc.api.SystemRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/system/role")
@Api(value = "SystemRole控制器", description = "角色管理")
public class SystemRoleController extends BaseController {
    @Autowired
    private SystemRoleService systemRoleService;

    @ApiOperation(value = "角色首页")
    @RequiresPermissions("system:role:read")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView goIndex() {
        ModelAndView mv =this.getModelAndView();
        mv.setViewName("/system/role/role_index");
        return mv;
    }

    @ApiOperation(value = "角色列表")
    @RequiresPermissions("system:role:read")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Object list(@RequestParam(required = false) String search){
        Page<SystemRole> page = new PageFactory<SystemRole>().defaultPage();
        List<SystemRole> voList = systemRoleService.list(page,search);
        return new PageInfoBT<>(voList,page.getTotal());
    }

    @ApiOperation(value = "角色增改页")
    @RequiresPermissions("system:role:edit")
    @RequestMapping(value = "/edit/{roleId}/{page}", method = RequestMethod.GET)
    public ModelAndView goEdit(@PathVariable("roleId") int roleId, @PathVariable("page") String page) {
        ModelAndView mv = this.getModelAndView();
        SystemRoleVo systemRoleVo=systemRoleService.selectByIdWithLeft(roleId);
        systemRoleVo.setPage(page);
        mv.addObject("systemRoleVo",systemRoleVo);
        mv.setViewName("/system/role/role_edit");
        return mv;
    }

    @ApiOperation(value = "角色增加")
    @RequiresPermissions("system:role:edit")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public Object create(SystemRoleDto systemRoleDto) {
        ComplexResult result = FluentValidator.checkAll()
                .on(systemRoleDto.getRoleName(), new LengthValidator(1, 20, "名称"))
                .doValidate()
                .result(ResultCollectors.toComplex());
        if (!result.isSuccess()) {
            return WrapMapper.wrap(Wrapper.ILLEGAL_ARGUMENT_CODE_,errorListToString(result.getErrors()));
        }

        int count = systemRoleService.insertDto(systemRoleDto);
        return WrapMapper.wrap(count);
    }

    @ApiOperation(value = "角色修改")
    @RequiresPermissions("system:role:edit")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public Object update(@PathVariable("id") int id, SystemRoleDto systemRoleDto) {
        systemRoleDto.setRoleId(id);
        ComplexResult result = FluentValidator.checkAll()
                .on(systemRoleDto.getRoleName(), new LengthValidator(1, 20, "名称"))
                .doValidate()
                .result(ResultCollectors.toComplex());
        if (!result.isSuccess()) {
            return WrapMapper.wrap(Wrapper.ILLEGAL_ARGUMENT_CODE_,errorListToString(result.getErrors()));
        }

        int count = systemRoleService.updateDto(systemRoleDto);
        return WrapMapper.wrap(count);
    }

    @ApiOperation(value = "角色删除")
    @RequiresPermissions("system:role:edit")
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)
    @ResponseBody
    public Object delete(@PathVariable("id") int id) {
        int count = systemRoleService.deleteByPrimaryKey(id);
        return WrapMapper.wrap(count);
    }

    @ApiOperation(value = "多个角色删除")
    @RequiresPermissions("system:role:edit")
    @RequestMapping(value = "/deletes",method = RequestMethod.DELETE)
    @ResponseBody
    public Object delete(String ids) {
        int count = systemRoleService.deleteByPrimaryKeys(ids);
        return WrapMapper.wrap(count);
    }

    @ApiOperation(value = "角色权限修改页")
    @RequiresPermissions("system:role:edit")
    @RequestMapping(value = "/permission/{id}", method = RequestMethod.GET)
    public ModelAndView goPermission(@PathVariable("id") int id) {
        ModelAndView mv =this.getModelAndView();
        mv.addObject("id",id);
        mv.setViewName("/system/role/role_permission");
        return mv;
    }

    @ApiOperation(value = "角色权限修改")
    @RequiresPermissions("system:role:edit")
    @RequestMapping(value = "/savePermission/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public Object savePermission(@PathVariable("id") int id, HttpServletRequest request) {
        JSONArray datas = JSONArray.parseArray(request.getParameter("datas"));
        int count = systemRoleService.savePermission(datas, id);
        return WrapMapper.wrap(count);
    }
}
