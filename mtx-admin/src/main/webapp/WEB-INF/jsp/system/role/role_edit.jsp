<!--system/role/role_edit.jsp-->
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
    <input type="hidden" name="page" id="page" value="${systemRoleVo.page}" />
    <c:if test="${systemRoleVo.page == 'update' }">
        <input type="hidden" name="_method" id="_method" value="PUT"/>
    </c:if>
    <table class="table table-bordered table-form" align="center" style="width: 98%">
        <c:if test="${systemRoleVo.page != 'update' }">
            <tr>
                <th width="20%"><font color="red">*</font><label class="Validform_label">角色名称:</label></th>
                <td width="80%">
                    <div class="form-group">
                        <input class="form-control" type="text" name="roleName" id="roleName" placeholder="角色名称" >
                    </div>
                </td>
            </tr>
            <tr>
                <th width="20%"><font color="red">*</font><label class="Validform_label">角色编码:</label></th>
                <td width="80%">
                    <div class="form-group">
                        <input class="form-control" type="text" name="roleCode" id="roleCode" placeholder="角色编码" >
                    </div>
                </td>
            </tr>
            <tr>
                <th width="20%"><label >描述:</label></th>
                <td width="80%">
                    <div class="form-group">
                        <input class="form-control" type="text" name="description" id="description" placeholder="描述" >
                    </div>
                </td>
            </tr>
        </c:if>
        <c:if test="${systemRoleVo.page == 'update' }">
            <tr>
                <th width="20%"><font color="red">*</font><label class="Validform_label">角色名称:</label></th>
                <td width="80%">
                    <div class="form-group">
                        <input value="${systemRoleVo.roleName}" class="form-control" type="text" name="roleName" id="roleName" placeholder="角色名称" >
                    </div>
                </td>
            </tr>
            <tr>
                <th width="20%"><font color="red">*</font><label class="Validform_label">角色编码:</label></th>
                <td width="80%">
                    <div class="form-group">
                        <input value="${systemRoleVo.roleCode}" class="form-control" type="text" name="roleCode" id="roleCode" placeholder="角色编码" >
                    </div>
                </td>
            </tr>
            <tr>
                <th width="20%"><label >描述:</label></th>
                <td width="80%">
                    <div class="form-group">
                        <input value="${systemRoleVo.description}" class="form-control" type="text" name="description" id="description" placeholder="描述" >
                    </div>
                </td>
            </tr>
        </c:if>
        <tr>
            <td colspan="2">
                <div align="center">
                    <button type="button" title="保存" class="btn btn-success btn-xs" id="CommitAndSave" style="width: 100px;">
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
    var RoleEdit = {
        index:null,
    };
    $(function () {

        $('#form').bootstrapValidator({
            excluded: [':disabled', ':hidden', ':not(:visible)'],

            fields: {
                roleName: {
                    validators: {
                        notEmpty: {
                            message: '角色不能为空'
                        },
                        stringLength:{
                            max : 20,
                            message: '不超过20个字符'
                        },
                    }
                },
                roleCode: {
                    validators: {
                        notEmpty: {
                            message: '角色编码不能为空'
                        },
                        stringLength:{
                            max : 20,
                            message: '不超过20个字符'
                        },
                    }
                },

            }
        })
        $("#CommitAndSave").click(function(){
            commit();

        });
    });

    function commit(){
        $('#form').bootstrapValidator('validate');
        if(!$("#form").data('bootstrapValidator').isValid()){
            return;
        }

        var url;
        <c:if test="${systemRoleVo.page == 'update' }">
        url = '${basePath}/system/role/update/${systemRoleVo.roleId}';
        </c:if>
        <c:if test="${systemRoleVo.page != 'update' }">
        url = '${basePath}/system/role/create';
        </c:if>
        $.ajax({
            type: 'post',
            url: url,
            data: 	$("#form").serialize(),
            beforeSend: function() {//防止重复提交数据 与loading 表单校验
                RoleEdit.index = layer.load(1, {
                    //shade: [0.4,'#000'] //0.1透明度的白色背景
                });
            },
            success: function(result) {
                if (result.code == 200) {
                    top.EHM.Cache["refreshs"]();
                    //top.EHM.PopWinFactory.close(window);
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
                layer.close(RoleEdit.index);
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
