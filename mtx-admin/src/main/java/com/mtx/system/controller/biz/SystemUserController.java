package com.mtx.system.controller.biz;

import com.baidu.unbiz.fluentvalidator.ComplexResult;
import com.baidu.unbiz.fluentvalidator.FluentValidator;
import com.baidu.unbiz.fluentvalidator.ResultCollectors;
import com.baomidou.mybatisplus.plugins.Page;
import com.mtx.common.base.BaseController;
import com.mtx.common.util.base.StringUtil;
import com.mtx.common.util.base.ToolUtil;
import com.mtx.common.util.model.SelectVo;
import com.mtx.common.util.page.PageFactory;
import com.mtx.common.util.page.PageInfoBT;
import com.mtx.common.util.validator.LengthValidator;
import com.mtx.common.util.wrapper.WrapMapper;
import com.mtx.common.util.wrapper.Wrapper;
import com.mtx.system.dao.dto.SystemUserDto;
import com.mtx.system.dao.vo.SystemUserVo;
import com.mtx.system.rpc.api.SystemOrganizationService;
import com.mtx.system.rpc.api.SystemRoleService;
import com.mtx.system.rpc.api.SystemUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/system/user")
@Api(value = "SystemUser控制器", description = "用户管理")
public class SystemUserController extends BaseController {
    @Autowired
    private SystemUserService systemUserService;
    @Autowired
    private SystemOrganizationService systemOrganizationService;
    @Autowired
    private SystemRoleService systemRoleService;

    @ApiOperation(value = "用户首页")
    @RequiresPermissions("system:user:read")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView goIndex() {
        ModelAndView mv =this.getModelAndView();
        mv.setViewName("/system/user/user_index");
        return mv;
    }

    @ApiOperation(value = "用户查看页")
    @RequiresPermissions("system:user:read")
    @RequestMapping(value = "/view/{organizationId}", method = RequestMethod.GET)
    public ModelAndView goView(@PathVariable("organizationId") int organizationId) {
        ModelAndView mv =this.getModelAndView();
        mv.addObject("organizationId",organizationId);
        mv.setViewName("/system/user/user_list");
        return mv;
    }

    @ApiOperation(value = "用户详情页")
    @RequiresPermissions("system:user:read")
    @RequestMapping(value = "/detail/{userId}", method = RequestMethod.GET)
    public ModelAndView goDetail(@PathVariable("userId") int userId) {
        ModelAndView mv =this.getModelAndView();
        SystemUserVo systemUserVo=systemUserService.selectByIdWithLeft(userId);
        mv.addObject("systemUserVo",systemUserVo);

        //组织与角色
        List<SelectVo> orgList =systemOrganizationService.selectForSelect();
        mv.addObject("orgList",orgList);
        List<SelectVo> roleList =systemRoleService.selectForSelect();
        mv.addObject("roleList",roleList);
        List<SelectVo> orgListSelect =systemOrganizationService.selectForSelect(userId);
        mv.addObject("orgListSelect",orgListSelect);
        List<SelectVo> roleListSelect  =systemRoleService.selectForSelect(userId);
        mv.addObject("roleListSelect",roleListSelect);

        mv.setViewName("/system/user/user_view");
        return mv;
    }

    @ApiOperation(value = "用户列表")
    @RequiresPermissions("system:user:read")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Object list(@RequestParam int organizationId,@RequestParam(required = false) String search){
        Page<SystemUserVo> page = new PageFactory<SystemUserVo>().defaultPage();
        List<SystemUserVo> voList = systemUserService.list(page,organizationId,search);
        return new PageInfoBT<>(voList,page.getTotal());
    }

    @ApiOperation(value = "用户增改页")
    @RequiresPermissions("system:user:edit")
    @RequestMapping(value = "/edit/{userId}/{page}", method = RequestMethod.GET)
    public ModelAndView goEdit(@PathVariable("userId") int userId, @PathVariable("page") String page) {
        ModelAndView mv = this.getModelAndView();
        SystemUserVo systemUserVo=systemUserService.selectByIdWithLeft(userId);
        systemUserVo.setPage(page);
        mv.addObject("systemUserVo",systemUserVo);
        //组织与角色
        List<SelectVo> orgList =systemOrganizationService.selectForSelect();
        mv.addObject("orgList",orgList);
        List<SelectVo> roleList =systemRoleService.selectForSelect();
        mv.addObject("roleList",roleList);
        List<SelectVo> orgListSelect =systemOrganizationService.selectForSelect(userId);
        mv.addObject("orgListSelect",orgListSelect);
        List<SelectVo> roleListSelect  =systemRoleService.selectForSelect(userId);
        mv.addObject("roleListSelect",roleListSelect);
        mv.setViewName("/system/user/user_edit");
        return mv;
    }

    @ApiOperation(value = "用户增加")
    @RequiresPermissions("system:user:edit")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public Object create(SystemUserDto systemUserDto) {
        ComplexResult result = FluentValidator.checkAll()
                .on(systemUserDto.getNickName(), new LengthValidator(1, 20, "名称"))
                .doValidate()
                .result(ResultCollectors.toComplex());
        if (!result.isSuccess()) {
            return WrapMapper.wrap(Wrapper.ILLEGAL_ARGUMENT_CODE_,errorListToString(result.getErrors()));
        }

        systemUserDto.setEditUser(super.getSystemUser().getUserId());
        systemUserDto.setSalt(ToolUtil.getUuid());
        systemUserDto.setPassword(StringUtil.toSecretString(systemUserDto.getSalt()));
        int count = systemUserService.insertDto(systemUserDto);
        return WrapMapper.wrap(count);
    }

    @ApiOperation(value = "用户修改")
    @RequiresPermissions("system:user:edit")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
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

        systemUserDto.setEditUser(super.getSystemUser().getUserId());
        int count = systemUserService.updateDto(systemUserDto);
        return WrapMapper.wrap(count);
    }

    @ApiOperation(value = "角色删除")
    @RequiresPermissions("system:user:edit")
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)
    @ResponseBody
    public Object delete(@PathVariable("id") int id) {
        int count = systemUserService.deleteByPrimaryKeyFaker(id);
        return WrapMapper.wrap(count);
    }

    @ApiOperation(value = "多个角色删除")
    @RequiresPermissions("system:user:edit")
    @RequestMapping(value = "/deletes",method = RequestMethod.DELETE)
    @ResponseBody
    public Object delete(String ids) {
        int count = systemUserService.deleteByPrimaryKeysFaker(ids);
        return WrapMapper.wrap(count);
    }

}
