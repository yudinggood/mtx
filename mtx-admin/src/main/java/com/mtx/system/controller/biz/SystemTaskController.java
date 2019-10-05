package com.mtx.system.controller.biz;

import com.baidu.unbiz.fluentvalidator.ComplexResult;
import com.baidu.unbiz.fluentvalidator.FluentValidator;
import com.baidu.unbiz.fluentvalidator.ResultCollectors;
import com.baomidou.mybatisplus.plugins.Page;
import com.mtx.common.base.BaseController;
import com.mtx.common.constant.SystemConstant;
import com.mtx.common.util.page.PageFactory;
import com.mtx.common.util.page.PageInfoBT;
import com.mtx.common.util.validator.NotNullValidator;
import com.mtx.common.util.wrapper.WrapMapper;
import com.mtx.common.util.wrapper.Wrapper;
import com.mtx.system.common.bean.DictCacheKit;
import com.mtx.system.common.enums.DictEnum;
import com.mtx.system.dao.dto.SystemTaskDto;
import com.mtx.system.dao.model.SystemTask;
import com.mtx.system.dao.vo.SystemTaskVo;
import com.mtx.system.rpc.api.QuartzService;
import com.mtx.system.rpc.api.SystemTaskService;
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
@RequestMapping("/system/task")
@Api(value = "SystemTask控制器", description = "任务管理")
public class SystemTaskController extends BaseController {
    @Autowired
    SystemTaskService systemTaskService;
    @Autowired
    QuartzService quartzService;

    @ApiOperation(value = "任务管理首页")
    @RequiresPermissions("system:task:read")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView goIndex() {
        ModelAndView mv =this.getModelAndView();
        mv.setViewName("/system/task/task_index");
        return mv;
    }

    @ApiOperation(value = "任务列表")
    @RequiresPermissions("system:task:read")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Object list(SystemTaskDto systemTaskDto){
        Page<SystemTask> page = new PageFactory<SystemTask>().defaultAll();
        List<SystemTaskVo> voList = systemTaskService.list(page,systemTaskDto);
        return new PageInfoBT<>(voList,voList.size());
    }

    @ApiOperation(value = "任务管理增改页")
    @RequiresPermissions("system:task:edit")
    @RequestMapping(value = "/edit/{taskId}/{page}", method = RequestMethod.GET)
    public ModelAndView goEdit(@PathVariable("taskId") int taskId, @PathVariable("page") String page) {
        ModelAndView mv = this.getModelAndView();
        SystemTaskVo systemTaskVo=systemTaskService.selectByIdWithLeft(taskId);
        systemTaskVo.setPage(page);
        mv.addObject("systemTaskVo",systemTaskVo);
        mv.addObject("typeSelectVO", DictCacheKit.me().getBizDictByCode(DictEnum.TASK_WAY));
        mv.setViewName("/system/task/task_edit");
        return mv;
    }

    @ApiOperation(value = "任务增加")
    @RequiresPermissions("system:task:edit")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public Object create(SystemTaskDto systemTaskDto) {
        ComplexResult result = FluentValidator.checkAll()
                .on(systemTaskDto.getName(), new NotNullValidator("名称"))
                .doValidate()
                .result(ResultCollectors.toComplex());
        if (!result.isSuccess()) {
            return WrapMapper.wrap(Wrapper.ILLEGAL_ARGUMENT_CODE_,errorListToString(result.getErrors()));
        }

        systemTaskDto.setEditUser(super.getSystemUser().getUserId());
        int count = systemTaskService.insertDto(systemTaskDto);
        return WrapMapper.wrap(count);
    }

    @ApiOperation(value = "任务修改")
    @RequiresPermissions("system:task:edit")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public Object update(@PathVariable("id") int id, SystemTaskDto systemTaskDto) {
        systemTaskDto.setTaskId(id);
        ComplexResult result = FluentValidator.checkAll()
                .on(systemTaskDto.getName(), new NotNullValidator("名称"))
                .doValidate()
                .result(ResultCollectors.toComplex());
        if (!result.isSuccess()) {
            return WrapMapper.wrap(Wrapper.ILLEGAL_ARGUMENT_CODE_,errorListToString(result.getErrors()));
        }

        systemTaskDto.setEditUser(super.getSystemUser().getUserId());
        systemTaskDto.setDeleted((byte) 0);
        int count = systemTaskService.updateDto(systemTaskDto);
        return WrapMapper.wrap(count);
    }

    @ApiOperation(value = "任务删除")
    @RequiresPermissions("system:task:edit")
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)
    @ResponseBody
    public Object delete(@PathVariable("id") int id) {
        //删除quartz任务
        SystemTask systemTask=systemTaskService.selectByPrimaryKey(id);
        quartzService.removeJob(systemTask.getTaskId()+ SystemConstant.REMIND_TASK_GROUP,
                SystemConstant.REMIND_TASK_GROUP,
                systemTask.getTaskId()+SystemConstant.REMIND_TRIGGER_GROUP,
                SystemConstant.REMIND_TRIGGER_GROUP);

        int count = systemTaskService.deleteByPrimaryKey(id);
        return WrapMapper.wrap(count);
    }

    @ApiOperation(value = "多个任务删除")
    @RequiresPermissions("system:task:edit")
    @RequestMapping(value = "/deletes",method = RequestMethod.DELETE)
    @ResponseBody
    public Object delete(String ids) {
        int count = systemTaskService.deleteByPrimaryKeys(ids);
        return WrapMapper.wrap(count);
    }


}
