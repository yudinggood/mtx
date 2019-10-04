<!--system/task/task_edit.jsp-->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags"  prefix="spring" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="../../base/base.jsp"%>


    <style>
        .form-control{
            width:75%;float: left;
        }
    </style>
    <script>
        EHM.ImportLayer();
        EHM.ImportSelect();
        EHM.ImportTimepicker();
        EHM.ImportDateTimepicker();
    </script>
</head>
<body>

<form id="form">
    <input type="hidden" name="page" id="page" value="${systemTaskVo.page}" />
    <c:if test="${systemTaskVo.page == 'update' }">
        <input type="hidden" name="_method" id="_method" value="PUT"/>
    </c:if>
    <table class="table table-bordered table-form" align="center" style="width: 98%">
        <c:if test="${systemTaskVo.page != 'update' }">
            <tr>
                <th width="20%"><font color="red">*</font><label class="Validform_label">名称:</label></th>
                <td width="80%">
                    <div class="form-group">
                        <input class="form-control" type="text" name="name" id="name" placeholder="名称" maxlength="50">
                    </div>
                </td>
            </tr>
            <tr>
                <th><font color="red">*</font><label class="Validform_label">任务类型:</label></th>
                <td>
                    <div class="form-group">
                        <select class="select2 form-control" name="type" id="type" onchange="changeType()">
                            <c:forEach items="${typeSelectVO}" var="selectVo">
                                <option value="${selectVo.dicCode }">${selectVo.dicName }</option>
                            </c:forEach>
                        </select>
                    </div>
                </td>
            </tr>
            <tr>
                <th><font color="red">*</font><label class="Validform_label">任务时间:</label></th>
                <td>
                    <div class="form-group">
                        <input class="form-control" name="remind_time_day" id="task_time"  readonly="readonly" type="text"  style="display:none;"/>
                        <input class="span10 date-picker form-control" name="remind_time_year" id="task_time" value="" type="text" data-date-format="mm-dd hh:ii" readonly="readonly" placeholder="点击选择时间" title="提醒时间" style=""/>
                        <input class="span10 date-picker form-control" name="remind_time_month" id="task_time" value="" type="text" data-date-format="dd日 hh:ii" readonly="readonly" placeholder="点击选择时间" title="提醒时间" style=""/>
                        <div id="select_week" style="display: inline;">
                            <select class="form-control select2" name="select_week"  style="width: 30%;" >
                                <option value="MON" >星期一</option>
                                <option value="TUE" >星期二</option>
                                <option value="SAT" >星期三</option>
                                <option value="THU" >星期四</option>
                                <option value="FRI" >星期五</option>
                                <option value="SAT" >星期六</option>
                                <option value="SUN" >星期日</option>
                            </select>
                            <input class="form-control"  name="remind_time_week" id="task_time"  readonly="readonly" type="text"  style="width: 25%;float: right;margin-right: 25%;"/>
                    </div>
                    </div>
                </td>
            </tr>
            <tr>
                <th><font color="red">*</font><label class="Validform_label">任务状态:</label></th>
                <td>
                    <div class="form-group">
                        <select class="select2 form-control" name="state" id="state" style="">
                            <option value="1" >提醒</option>
                            <option value="2" >不提醒</option>
                        </select>
                    </div>
                </td>
            </tr>
        </c:if>
        <c:if test="${systemTaskVo.page == 'update' }">
            <tr>
                <th width="20%"><font color="red">*</font><label class="Validform_label">名称:</label></th>
                <td width="80%">
                    <div class="form-group">
                        <input value="${systemTaskVo.name}" class="form-control" type="text" name="name" id="name" placeholder="名称" maxlength="50">
                    </div>
                </td>
            </tr>
            <tr>
                <th><font color="red">*</font><label class="Validform_label">任务类型:</label></th>
                <td>
                    <div class="form-group">
                        <select class="select2 form-control" name="type" id="type" onchange="changeType()">
                            <c:forEach items="${typeSelectVO}" var="selectVo">
                                <option   <c:if test="${systemTaskVo.type == selectVo.dicCode }">selected</c:if> value="${selectVo.dicCode }">${selectVo.dicName }</option>
                            </c:forEach>
                        </select>
                    </div>
                </td>
            </tr>
            <tr>
                <th><font color="red">*</font><label class="Validform_label">任务时间:</label></th>
                <td>
                    <div class="form-group">
                        <input value="<c:if test="${systemTaskVo.type == 1||systemTaskVo.type == 5||systemTaskVo.type == 6 }">${systemTaskVo.taskTime}</c:if>" class="form-control" name="remind_time_day" id="task_time"  readonly="readonly" type="text"  style="display:none;"/>
                        <input value="<c:if test="${systemTaskVo.type == 4}">${systemTaskVo.taskTime}</c:if>" class="span10 date-picker form-control" name="remind_time_year" id="task_time" value="" type="text" data-date-format="mm-dd hh:ii" readonly="readonly" placeholder="点击选择时间" title="提醒时间" style=""/>
                        <input value="<c:if test="${systemTaskVo.type == 3}">${systemTaskVo.taskTime}</c:if>" class="span10 date-picker form-control" name="remind_time_month" id="task_time" value="" type="text" data-date-format="dd日 hh:ii" readonly="readonly" placeholder="点击选择时间" title="提醒时间" style=""/>
                        <div id="select_week" style="display: inline;">
                            <c:if test="${systemTaskVo.type == 2}">
                                <select class="form-control select2" name="select_week"  style="width: 30%;display: none;" >
                                    <option value="MON" <c:if test="${systemTaskVo.selectWeek=='MON'}">selected</c:if>>星期一</option>
                                    <option value="TUE" <c:if test="${systemTaskVo.selectWeek=='TUE'}">selected</c:if>>星期二</option>
                                    <option value="SAT" <c:if test="${systemTaskVo.selectWeek=='SAT'}">selected</c:if>>星期三</option>
                                    <option value="THU" <c:if test="${systemTaskVo.selectWeek=='THU'}">selected</c:if>>星期四</option>
                                    <option value="FRI" <c:if test="${systemTaskVo.selectWeek=='FRI'}">selected</c:if>>星期五</option>
                                    <option value="SAT" <c:if test="${systemTaskVo.selectWeek=='SAT'}">selected</c:if>>星期六</option>
                                    <option value="SUN" <c:if test="${systemTaskVo.selectWeek=='SUN'}">selected</c:if>>星期日</option>
                                </select>
                                <input value="${systemTaskVo.remindTimeWeek}" class="form-control"  name="remind_time_week" id="task_time"  readonly="readonly" type="text"  style="width: 25%;float: right;margin-right: 25%;"/>
                            </c:if>
                        </div>
                    </div>
                </td>
            </tr>
            <tr>
                <th><font color="red">*</font><label class="Validform_label">任务状态:</label></th>
                <td>
                    <div class="form-group">
                        <select class="select2 form-control" name="state" id="state" style="">
                            <option value="1"  <c:if test="${systemTaskVo.state == 1 }">selected</c:if>>提醒</option>
                            <option value="2"  <c:if test="${systemTaskVo.state == 2 }">selected</c:if>>不提醒</option>
                        </select>
                    </div>
                </td>
            </tr>
        </c:if>
        <tr>
            <td colspan="2">
                <div align="center">
                    <button type="button" title="保存" class="btn btn-success btn-xs" onclick="commit()" style="width: 100px;">
                        <span class="fa fa-save"></span>
                        <span style="padding-top:2px">&nbsp;保&nbsp;存&nbsp;</span>
                    </button>
                    <button type="button" title="关闭" class="btn btn-primary btn-xs" onClick="top.layer.close(top.layer.index);" style="width: 100px;">
                        <span class="fa fa-close"></span>
                        <span style="padding-top:2px">&nbsp;关&nbsp;闭&nbsp;</span>
                    </button>
                </div>
            </td>
        </tr>
    </table>
</form>


<script>
    var TaskEdit = {
        index:null,
    };
    $(function () {
        $(".select2").select2();
        $("input[name='remind_time_day']").timepicker({
            minuteStep: 1,
            showMeridian: false
        });
        $("input[name='remind_time_year']").datetimepicker({
            language: 'zh-CN',autoclose: true,todayHighlight: true,startView:3,minView:0,maxView:3
        });
        $("input[name='remind_time_month']").datetimepicker({
            language: 'zh-CN',autoclose: true,todayHighlight: true,startView:2,minView:0,maxView:2
        });
        $("input[name='remind_time_week']").timepicker({
            minuteStep: 1,
            showMeridian: false
        });
        changeType();

        $('#form').bootstrapValidator({
            excluded: [':disabled', ':hidden', ':not(:visible)'],

            fields: {
                name: {
                    validators: {
                        notEmpty: {
                            message: '名称不能为空'
                        },
                    }
                },
                type: {
                    validators: {
                        notEmpty: {
                            message: '任务类型不能为空'
                        },
                    }
                },

            }
        })

    });
    function changeType(){
        var v=$("#type").find("option:selected").val();
        if(v==1||v==5||v==6){//HH:mm
            $("input").css("display","none");
            $("#select_week").hide();
            $("input[name='name']").css("display","block");
            $("input[name='remind_time_day']").css("display","block");
        }if(v==4){//MM-dd HH:mm
            $("input").css("display","none");
            $("#select_week").css("display","none");
            $("input[name='name']").css("display","block");
            $("input[name='remind_time_year']").css("display","block");
        }if(v==3){//dd HH:mm
            $("input").css("display","none");
            $("#select_week").css("display","none");
            $("input[name='name']").css("display","block");
            $("input[name='remind_time_month']").css("display","block");
        }if(v==2){//week HH:mm
            $("input").css("display","none");
            $("input[name='name']").css("display","block");
            $("input[name='remind_time_week']").css("display","block");
            $("#select_week").css("display","block");
        }
    }
    function commit() {
        $('#form').bootstrapValidator('validate');
        if (!$("#form").data('bootstrapValidator').isValid()) {
            return;
        }

        var url;
        <c:if test="${systemTaskVo.page == 'update' }">
        url = '${basePath}/system/task/update/${systemTaskVo.taskId}';
        </c:if>
        <c:if test="${systemTaskVo.page != 'update' }">
        url = '${basePath}/system/task/create';
        </c:if>
        $.ajax({
            type: 'post',
            url: url,
            data: 	$("#form").serialize(),
            beforeSend: function() {
                TaskEdit.index = layer.load(1, {
                });
            },
            success: function(result) {
                if (result.code == 200) {
                    top.EHM.Cache["refreshs"]();
                    top.layer.close(top.layer.index);
                }else {
                    var i =layer.confirm(result.message, {
                        shade: 0,
                        btn: ['确认'] //按钮
                    }, function(){
                        layer.close(i);
                    });
                }
            },
            complete:function (data) {
                layer.close(TaskEdit.index);
            },
            error: function(XMLHttpRequest, textStatus, errorThrown) {
                var i =layer.confirm(textStatus, {
                    shade: 0,
                    btn: ['确认'] //按钮
                }, function(){
                    layer.close(i);
                });
            }
        });
    }
</script>
</body>
</html>
