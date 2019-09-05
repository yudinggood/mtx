<!--system/config/config_edit.jsp-->
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
    </script>
</head>
<body>

<form id="form">
    <input type="hidden" name="page" id="page" value="${systemConfigVo.page}" />
    <c:if test="${systemConfigVo.page == 'update' }">
        <input type="hidden" name="_method" id="_method" value="PUT"/>
    </c:if>
    <table class="table table-bordered table-form" align="center" style="width: 98%">
        <c:if test="${systemConfigVo.page != 'update' }">
            <tr>
                <th width="20%"><font color="red">*</font><label class="Validform_label">编码:</label></th>
                <td width="80%">
                    <div class="form-group">
                        <input class="form-control" type="text" name="code" id="code" placeholder="编码" maxlength="50">
                    </div>
                </td>
            </tr>
            <tr>
                <th><font color="red">*</font><label class="Validform_label">名称:</label></th>
                <td>
                    <div class="form-group">
                        <input class="form-control" type="text" name="name" id="name" placeholder="名称" maxlength="50">
                    </div>
                </td>
            </tr>
            <tr>
                <th><font color="red">*</font><label class="Validform_label">值:</label></th>
                <td>
                    <div class="form-group">
                        <input class="form-control" type="text" name="value" id="value" placeholder="值" maxlength="100">
                    </div>
                </td>
            </tr>
            <tr>
                <th><label class="Validform_label">描述:</label></th>
                <td>
                    <div class="form-group">
                        <input class="form-control" type="text" name="description" id="description" placeholder="描述" maxlength="100">
                    </div>
                </td>
            </tr>
        </c:if>
        <c:if test="${systemConfigVo.page == 'update' }">
            <tr>
                <th width="20%"><font color="red">*</font><label class="Validform_label">编码:</label></th>
                <td width="80%">
                    <div class="form-group">
                        <input value="${systemConfigVo.code}" class="form-control" type="text" name="code" id="code" placeholder="编码" maxlength="50">
                    </div>
                </td>
            </tr>
            <tr>
                <th><font color="red">*</font><label class="Validform_label">名称:</label></th>
                <td>
                    <div class="form-group">
                        <input value="${systemConfigVo.name}" class="form-control" type="text" name="name" id="name" placeholder="名称" maxlength="50">
                    </div>
                </td>
            </tr>
            <tr>
                <th><font color="red">*</font><label class="Validform_label">值:</label></th>
                <td>
                    <div class="form-group">
                        <input value="${systemConfigVo.value}" class="form-control" type="text" name="value" id="value" placeholder="值" maxlength="100">
                    </div>
                </td>
            </tr>
            <tr>
                <th><label class="Validform_label">描述:</label></th>
                <td>
                    <div class="form-group">
                        <input value="${systemConfigVo.description}" class="form-control" type="text" name="description" id="description" placeholder="描述" maxlength="100">
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
    var ConfigEdit = {
        index:null,
    };
    $(function () {
        $('#form').bootstrapValidator({
            excluded: [':disabled', ':hidden', ':not(:visible)'],

            fields: {
                code: {
                    validators: {
                        notEmpty: {
                            message: '编码不能为空'
                        },
                    }
                },
                name: {
                    validators: {
                        notEmpty: {
                            message: '名称不能为空'
                        },
                    }
                },
                value: {
                    validators: {
                        notEmpty: {
                            message: '值不能为空'
                        },
                    }
                },
            }
        })

    });
    function commit() {
        $('#form').bootstrapValidator('validate');
        if(!$("#form").data('bootstrapValidator').isValid()){
            return;
        }

        var url;
        <c:if test="${systemConfigVo.page == 'update' }">
        url = '${basePath}/system/config/update/${systemConfigVo.configId}';
        </c:if>
        <c:if test="${systemConfigVo.page != 'update' }">
        url = '${basePath}/system/config/create';
        </c:if>
        $.ajax({
            type: 'post',
            url: url,
            data: 	$("#form").serialize(),
            beforeSend: function() {
                ConfigEdit.index = layer.load(1, {
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
                layer.close(ConfigEdit.index);
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
