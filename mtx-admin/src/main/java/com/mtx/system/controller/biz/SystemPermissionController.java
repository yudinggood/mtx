package com.mtx.system.controller.biz;

import com.baidu.unbiz.fluentvalidator.ComplexResult;
import com.baidu.unbiz.fluentvalidator.FluentValidator;
import com.baidu.unbiz.fluentvalidator.ResultCollectors;
import com.mtx.common.base.BaseController;
import com.mtx.common.util.validator.LengthValidator;
import com.mtx.common.util.wrapper.WrapMapper;
import com.mtx.common.util.wrapper.Wrapper;
import com.mtx.system.dao.dto.SystemPermissionDto;
import com.mtx.system.dao.vo.SystemPermissionVo;
import com.mtx.system.rpc.api.SystemPermissionService;
import com.mtx.system.rpc.api.SystemSystemService;
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

@Controller
@RequestMapping("/system/permission")
@Api(value = "SystemPermission控制器", description = "菜单权限管理")
public class SystemPermissionController extends BaseController{
    @Autowired
    private SystemPermissionService systemPermissionService;
    @Autowired
    private SystemSystemService systemSystemService;

    @ApiOperation(value = "权限首页")
    @RequiresPermissions("system:permission:read")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView goIndex() {
        ModelAndView mv =this.getModelAndView();
        mv.setViewName("/system/permission/permission_index");
        return mv;
    }

    @ApiOperation(value = "权限树")
    @RequiresPermissions("system:permission:read")
    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    @ResponseBody
    public Object getTree() {
        String json = systemPermissionService.selectForTree();
        return WrapMapper.ok(json);
    }

    @ApiOperation(value = "权限查看页")
    @RequiresPermissions("system:permission:read")
    @RequestMapping(value = "/view/{permissionId}", method = RequestMethod.GET)
    public ModelAndView goView(@PathVariable("permissionId") int permissionId) {
        ModelAndView mv =this.getModelAndView();
        SystemPermissionVo systemPermissionVO=systemPermissionService.selectByIdWithLeft(permissionId);
        mv.addObject("systemPermissionVO",systemPermissionVO);
        mv.setViewName("/system/permission/permission_view");
        return mv;
    }

    @ApiOperation(value = "权限增改页")
    @RequiresPermissions("system:permission:edit")
    @RequestMapping(value = "/edit/{permissionId}/{page}", method = RequestMethod.GET)
    public ModelAndView goEdit(@PathVariable("permissionId") int permissionId,@PathVariable("page") String page) {
        ModelAndView mv = this.getModelAndView();
        SystemPermissionVo systemPermissionVO=systemPermissionService.selectByIdWithLeft(permissionId);
        systemPermissionVO.setPage(page);
        mv.addObject("systemPermissionVO",systemPermissionVO);
        mv.addObject("systemSelectVO",systemSystemService.selectForSelect());
        mv.setViewName("/system/permission/permission_edit");
        return mv;
    }

    @ApiOperation(value = "权限增加")
    @RequiresPermissions("system:permission:edit")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public Object create(SystemPermissionDto systemPermissionDto) {
        ComplexResult result = FluentValidator.checkAll()
                .on(systemPermissionDto.getName(), new LengthValidator(1, 20, "名称"))
                .doValidate()
                .result(ResultCollectors.toComplex());
        if (!result.isSuccess()) {
            return WrapMapper.wrap(Wrapper.ILLEGAL_ARGUMENT_CODE_,errorListToString(result.getErrors()));
        }

        int count = systemPermissionService.insertDto(systemPermissionDto);
        return WrapMapper.wrap(count);
    }

    @ApiOperation(value = "icon选择页")
    @RequiresPermissions("system:permission:edit")
    @RequestMapping(value = "/icon/{icon}", method = RequestMethod.GET)
    public ModelAndView goIcon(@PathVariable("icon") String icon) {
        ModelAndView mv = this.getModelAndView();
        mv.addObject("icon",icon);
        mv.setViewName("/system/permission/permission_icon");
        return mv;
    }

    @ApiOperation(value = "权限修改")
    @RequiresPermissions("system:permission:edit")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public Object update(@PathVariable("id") int id, SystemPermissionDto systemPermissionDto) {
        systemPermissionDto.setPermissionId(id);
        ComplexResult result = FluentValidator.checkAll()
                .on(systemPermissionDto.getName(), new LengthValidator(1, 20, "名称"))
                .doValidate()
                .result(ResultCollectors.toComplex());
        if (!result.isSuccess()) {
            return WrapMapper.wrap(Wrapper.ILLEGAL_ARGUMENT_CODE_,errorListToString(result.getErrors()));
        }

        int count = systemPermissionService.updateDto(systemPermissionDto);
        return WrapMapper.wrap(count);
    }

    @ApiOperation(value = "权限删除")
    @RequiresPermissions("system:permission:edit")
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)
    @ResponseBody
    public Object delete(@PathVariable("id") int id) {
        int count = systemPermissionService.deleteByPrimaryKey(id);
        return WrapMapper.wrap(count);
    }

    @ApiOperation(value = "权限选择树")
    @RequiresPermissions("system:permission:read")
    @RequestMapping(value = "/chooseTree/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Object getChooseTree(@PathVariable("id") int id) {
        String json = systemPermissionService.selectForChooseTree(id);
        return WrapMapper.ok(json);
    }

}
