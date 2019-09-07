package com.mtx.system.controller.biz;

import com.baidu.unbiz.fluentvalidator.ComplexResult;
import com.baidu.unbiz.fluentvalidator.FluentValidator;
import com.baidu.unbiz.fluentvalidator.ResultCollectors;
import com.mtx.common.base.BaseController;
import com.mtx.common.util.validator.LengthValidator;
import com.mtx.common.util.wrapper.WrapMapper;
import com.mtx.common.util.wrapper.Wrapper;
import com.mtx.system.dao.dto.SystemOrgDto;
import com.mtx.system.dao.vo.SystemOrganizationVo;
import com.mtx.system.rpc.api.SystemOrganizationService;
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
@RequestMapping("/system/org")
@Api(value = "SystemOrg控制器", description = "组织管理")
public class SystemOrgController extends BaseController {
    @Autowired
    private SystemOrganizationService systemOrganizationService;

    @ApiOperation(value = "组织首页")
    @RequiresPermissions("system:org:read")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView goIndex() {
        ModelAndView mv =this.getModelAndView();
        mv.setViewName("/system/org/org_index");
        return mv;
    }

    @ApiOperation(value = "组织树")
    @RequiresPermissions("system:org:read")
    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    @ResponseBody
    public Object getTree() {
        String json = systemOrganizationService.selectForTree();
        return WrapMapper.ok(json);
    }

    @ApiOperation(value = "组织查看页")
    @RequiresPermissions("system:org:read")
    @RequestMapping(value = "/view/{organizationId}", method = RequestMethod.GET)
    public ModelAndView goView(@PathVariable("organizationId") int organizationId) {
        ModelAndView mv =this.getModelAndView();
        SystemOrganizationVo systemOrganizationVo=systemOrganizationService.selectByIdWithLeft(organizationId);
        mv.addObject("systemOrganizationVo",systemOrganizationVo);
        mv.setViewName("/system/org/org_view");
        return mv;
    }

    @ApiOperation(value = "组织增改页")
    @RequiresPermissions("system:org:edit")
    @RequestMapping(value = "/edit/{organizationId}/{page}", method = RequestMethod.GET)
    public ModelAndView goEdit(@PathVariable("organizationId") int organizationId,@PathVariable("page") String page) {
        ModelAndView mv = this.getModelAndView();
        SystemOrganizationVo systemOrganizationVo=systemOrganizationService.selectByIdWithLeft(organizationId);
        systemOrganizationVo.setPage(page);
        mv.addObject("systemOrganizationVo",systemOrganizationVo);
        mv.setViewName("/system/org/org_edit");
        return mv;
    }

    @ApiOperation(value = "组织增加")
    @RequiresPermissions("system:org:edit")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public Object create(SystemOrgDto systemOrgDto) {
        ComplexResult result = FluentValidator.checkAll()
                .on(systemOrgDto.getOrganizationName(), new LengthValidator(1, 20, "名称"))
                .doValidate()
                .result(ResultCollectors.toComplex());
        if (!result.isSuccess()) {
            return WrapMapper.wrap(Wrapper.ILLEGAL_ARGUMENT_CODE_,errorListToString(result.getErrors()));
        }

        systemOrgDto.setEditUser(super.getSystemUser().getUserId());
        int count = systemOrganizationService.insertDto(systemOrgDto);
        return WrapMapper.wrap(count);
    }

    @ApiOperation(value = "组织修改")
    @RequiresPermissions("system:org:edit")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public Object update(@PathVariable("id") int id, SystemOrgDto systemOrgDto) {
        systemOrgDto.setOrganizationId(id);
        ComplexResult result = FluentValidator.checkAll()
                .on(systemOrgDto.getOrganizationName(), new LengthValidator(1, 20, "名称"))
                .doValidate()
                .result(ResultCollectors.toComplex());
        if (!result.isSuccess()) {
            return WrapMapper.wrap(Wrapper.ILLEGAL_ARGUMENT_CODE_,errorListToString(result.getErrors()));
        }

        systemOrgDto.setEditUser(super.getSystemUser().getUserId());
        int count = systemOrganizationService.updateDto(systemOrgDto);
        return WrapMapper.wrap(count);
    }

    @ApiOperation(value = "组织删除")
    @RequiresPermissions("system:org:edit")
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)
    @ResponseBody
    public Object delete(@PathVariable("id") int id) {
        //父子结构有子就不能删除
        systemOrganizationService.selectSubResult(id);

        int count = systemOrganizationService.deleteByPrimaryKey(id);
        return WrapMapper.wrap(count);
    }
}
