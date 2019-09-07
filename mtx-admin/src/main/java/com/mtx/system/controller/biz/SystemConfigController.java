package com.mtx.system.controller.biz;

import com.baidu.unbiz.fluentvalidator.ComplexResult;
import com.baidu.unbiz.fluentvalidator.FluentValidator;
import com.baidu.unbiz.fluentvalidator.ResultCollectors;
import com.baomidou.mybatisplus.plugins.Page;
import com.mtx.common.base.BaseController;
import com.mtx.common.util.page.PageFactory;
import com.mtx.common.util.page.PageInfoBT;
import com.mtx.common.util.validator.NotNullValidator;
import com.mtx.common.util.wrapper.WrapMapper;
import com.mtx.common.util.wrapper.Wrapper;
import com.mtx.system.dao.dto.SystemConfigDto;
import com.mtx.system.dao.model.SystemConfig;
import com.mtx.system.dao.vo.SystemConfigVo;
import com.mtx.system.rpc.api.SystemConfigService;
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
@RequestMapping("/system/config")
@Api(value = "SystemConfig控制器", description = "项目配置管理")
public class SystemConfigController extends BaseController {
    @Autowired
    SystemConfigService systemConfigService;

    @ApiOperation(value = "项目配置首页")
    @RequiresPermissions("system:config:read")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView goIndex() {
        ModelAndView mv =this.getModelAndView();
        mv.setViewName("/system/config/config_index");
        return mv;
    }

    @ApiOperation(value = "项目配置列表")
    @RequiresPermissions("system:config:read")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Object list(SystemConfigDto systemConfigDto){
        Page<SystemConfig> page = new PageFactory<SystemConfig>().defaultAll();
        List<SystemConfig> voList = systemConfigService.list(page,systemConfigDto);
        return new PageInfoBT<>(voList,voList.size());
    }

    @ApiOperation(value = "项目配置增改页")
    @RequiresPermissions("system:config:edit")
    @RequestMapping(value = "/edit/{configId}/{page}", method = RequestMethod.GET)
    public ModelAndView goEdit(@PathVariable("configId") int configId, @PathVariable("page") String page) {
        ModelAndView mv = this.getModelAndView();
        SystemConfigVo systemConfigVo=systemConfigService.selectByIdWithLeft(configId);
        systemConfigVo.setPage(page);
        mv.addObject("systemConfigVo",systemConfigVo);
        mv.setViewName("/system/config/config_edit");
        return mv;
    }

    @ApiOperation(value = "项目配置增加")
    @RequiresPermissions("system:config:edit")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public Object create(SystemConfigDto systemConfigDto) {
        ComplexResult result = FluentValidator.checkAll()
                .on(systemConfigDto.getName(), new NotNullValidator("名称"))
                .doValidate()
                .result(ResultCollectors.toComplex());
        if (!result.isSuccess()) {
            return WrapMapper.wrap(Wrapper.ILLEGAL_ARGUMENT_CODE_,errorListToString(result.getErrors()));
        }

        systemConfigDto.setEditUser(super.getSystemUser().getUserId());
        int count = systemConfigService.insertDto(systemConfigDto);
        return WrapMapper.wrap(count);
    }

    @ApiOperation(value = "项目配置修改")
    @RequiresPermissions("system:config:edit")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public Object update(@PathVariable("id") int id, SystemConfigDto systemConfigDto) {
        systemConfigDto.setConfigId(id);
        ComplexResult result = FluentValidator.checkAll()
                .on(systemConfigDto.getName(), new NotNullValidator("名称"))
                .doValidate()
                .result(ResultCollectors.toComplex());
        if (!result.isSuccess()) {
            return WrapMapper.wrap(Wrapper.ILLEGAL_ARGUMENT_CODE_,errorListToString(result.getErrors()));
        }

        systemConfigDto.setEditUser(super.getSystemUser().getUserId());
        int count = systemConfigService.updateDto(systemConfigDto);
        return WrapMapper.wrap(count);
    }

    @ApiOperation(value = "项目配置删除")
    @RequiresPermissions("system:config:edit")
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)
    @ResponseBody
    public Object delete(@PathVariable("id") int id) {
        int count = systemConfigService.deleteByPrimaryKey(id);
        return WrapMapper.wrap(count);
    }

    @ApiOperation(value = "多个项目配置删除")
    @RequiresPermissions("system:config:edit")
    @RequestMapping(value = "/deletes",method = RequestMethod.DELETE)
    @ResponseBody
    public Object delete(String ids) {
        int count = systemConfigService.deleteByPrimaryKeys(ids);
        return WrapMapper.wrap(count);
    }
}
