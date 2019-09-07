package com.mtx.system.controller.biz;

import com.baidu.unbiz.fluentvalidator.ComplexResult;
import com.baidu.unbiz.fluentvalidator.FluentValidator;
import com.baidu.unbiz.fluentvalidator.ResultCollectors;
import com.mtx.common.base.BaseController;
import com.mtx.common.util.validator.NotNullValidator;
import com.mtx.common.util.wrapper.WrapMapper;
import com.mtx.common.util.wrapper.Wrapper;
import com.mtx.system.dao.dto.SystemDicDto;
import com.mtx.system.dao.vo.SystemDicVo;
import com.mtx.system.rpc.api.SystemDicService;
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
@RequestMapping("/system/dic")
@Api(value = "SystemDic控制器", description = "字典管理")
public class SystemDicController extends BaseController {
    @Autowired
    private SystemDicService systemDicService;

    @ApiOperation(value = "字典首页")
    @RequiresPermissions("system:dic:read")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView goIndex() {
        ModelAndView mv =this.getModelAndView();
        mv.setViewName("/system/dic/dic_index");
        return mv;
    }

    @ApiOperation(value = "字典树")
    @RequiresPermissions("system:dic:read")
    @RequestMapping(value = "/tree", method = RequestMethod.POST)
    @ResponseBody
    public Object getTree(String keywords) {
        String json = systemDicService.selectForTree(keywords);
        return WrapMapper.ok(json);
    }

    @ApiOperation(value = "字典查看页")
    @RequiresPermissions("system:dic:read")
    @RequestMapping(value = "/view/{dicId}", method = RequestMethod.GET)
    public ModelAndView goView(@PathVariable("dicId") int dicId) {
        ModelAndView mv =this.getModelAndView();
        mv.addObject("dicId",dicId);
        mv.setViewName("/system/dic/dic_view");
        return mv;
    }

    @ApiOperation(value = "字典树表")
    @RequiresPermissions("system:dic:read")
    @RequestMapping(value = "/list/{dicId}", method = RequestMethod.GET)
    @ResponseBody
    public Object list(@PathVariable("dicId") int dicId) {
        String json = systemDicService.selectForTreeList(dicId);
        return WrapMapper.ok(json);
    }

    @ApiOperation(value = "字典增改页")
    @RequiresPermissions("system:dic:edit")
    @RequestMapping(value = "/edit/{dicId}/{page}", method = RequestMethod.GET)
    public ModelAndView goEdit(@PathVariable("dicId") int dicId,@PathVariable("page") String page) {
        ModelAndView mv = this.getModelAndView();
        SystemDicVo systemDicVo=systemDicService.selectById(dicId);
        systemDicVo.setPage(page);
        mv.addObject("systemDicVo",systemDicVo);
        List<SystemDicVo> list = systemDicService.selectByPid(dicId);
        mv.addObject("dicList",list);
        mv.setViewName("/system/dic/dic_edit");
        return mv;
    }

    @ApiOperation(value = "字典增加")
    @RequiresPermissions("system:dic:edit")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public Object create(SystemDicDto systemDicDto) {
        ComplexResult result = FluentValidator.checkAll()
                .on(systemDicDto.getDicName(), new NotNullValidator("名称"))
                .on(systemDicDto.getDicCode(),new NotNullValidator("编码"))
                .doValidate()
                .result(ResultCollectors.toComplex());
        if (!result.isSuccess()) {
            return WrapMapper.wrap(Wrapper.ILLEGAL_ARGUMENT_CODE_,errorListToString(result.getErrors()));
        }

        systemDicDto.setEditUser(super.getSystemUser().getUserId());
        int count = systemDicService.insertDto(systemDicDto);
        return WrapMapper.wrap(count);
    }

    @ApiOperation(value = "字典修改")
    @RequiresPermissions("system:dic:edit")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public Object update(@PathVariable("id") int id, SystemDicDto systemDicDto) {
        systemDicDto.setDicId(id);
        ComplexResult result = FluentValidator.checkAll()
                .on(systemDicDto.getDicName(), new NotNullValidator("名称"))
                .on(systemDicDto.getDicCode(),new NotNullValidator("编码"))
                .doValidate()
                .result(ResultCollectors.toComplex());
        if (!result.isSuccess()) {
            return WrapMapper.wrap(Wrapper.ILLEGAL_ARGUMENT_CODE_,errorListToString(result.getErrors()));
        }

        systemDicDto.setEditUser(super.getSystemUser().getUserId());
        int count = systemDicService.updateDto(systemDicDto);
        return WrapMapper.wrap(count);
    }

    @ApiOperation(value = "权限删除")
    @RequiresPermissions("system:dic:edit")
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)
    @ResponseBody
    public Object delete(@PathVariable("id") int id) {
        int count = systemDicService.deleteAll(id);
        return WrapMapper.wrap(count);
    }
}
