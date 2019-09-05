<!--system/user/user_edit.jsp-->
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
    </script>
</head>
<body>

<form id="form">
    <input type="hidden" name="page" id="page" value="${systemUserVo.page}" />
    <c:if test="${systemUserVo.page == 'update' }">
        <input type="hidden" name="_method" id="_method" value="PUT"/>
    </c:if>
    <table class="table table-bordered table-form" align="center" style="width: 98%">
        <c:if test="${systemUserVo.page != 'update' }">
            <tr>
                <th width="20%"><font color="red">*</font><label class="Validform_label">昵称:</label></th>
                <td width="80%">
                    <div class="form-group">
                        <input class="form-control" type="text" name="nickName" id="nickName" placeholder="昵称" maxlength="20">
                    </div>
                </td>
            </tr>
            <tr>
                <th><font color="red">*</font><label class="Validform_label">手机号:</label></th>
                <td>
                    <div class="form-group">
                        <input value="" maxlength="20" type="text" name="phone" id="phone" placeholder="手机号" class="form-control input80">
                    </div>
                </td>
            </tr>
            <tr>
                <th><font color="red">*</font><label class="Validform_label">用户组织:</label></th>
                <td>
                    <div class="form-group">
                        <select id="organizationId" name="organizationId" multiple="multiple" class="form-control select2">
                            <c:forEach var="orgLists" items="${orgList}">
                                <option value="${orgLists.code}">
                                        ${orgLists.name}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                </td>
            </tr>
            <tr>
                <th><font color="red">*</font><label class="Validform_label">用户角色:</label></th>
                <td>
                    <div class="form-group">
                        <select id="roleId" name="roleId" multiple="multiple" class="form-control select2">
                            <c:forEach var="roleLists" items="${roleList}">
                                <option value="${roleLists.code}">
                                        ${roleLists.name}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                </td>
            </tr>
            <tr>
                <th>邮箱:</th>
                <td>
                    <div class="form-group">
                        <input value="" maxlength="50" type="text" name="email" id="email" placeholder="邮箱" class="form-control input80">
                    </div>
                </td>
            </tr>
            <tr>
                <th>姓名:</th>
                <td>
                    <div class="form-group">
                        <input maxlength="20" type="text" name="realName" id="realName" placeholder="姓名" class="form-control input80">
                    </div>
                </td>
            </tr>
            <tr>
                <th>性别:</th>
                <td>
                    <div class="form-group">
                        <input type="radio" name="sex" value="1" class="minimal" checked="checked" id="sex1"/><label for="sex1">男</label>
                        <input type="radio" name="sex" value="2" class="minimal"  id="sex2"/><label for="sex2">女</label>
                    </div>
                </td>
            </tr>
            <tr>
                <th>状态:</th>
                <td>
                    <div class="form-group">
                        <%--<input type="radio" name="userState" value="1" class="minimal" checked="checked" id="userState1"/><label for="userState1">正常</label>
                        <input type="radio" name="userState" value="2" class="minimal"  id="userState2"/><label for="userState2">封禁</label>--%>
                        <input type="radio" name="userState" value="3" class="minimal" checked="checked" id="userState3"/><label for="userState3">未激活</label>
                    </div>
                </td>
            </tr>
        </c:if>
        <c:if test="${systemUserVo.page == 'update' }">
            <tr>
                <th width="20%"><font color="red">*</font><label class="Validform_label">昵称:</label></th>
                <td width="80%">
                    <div class="form-group">
                        <input value="${systemUserVo.nickName}" class="form-control" type="text" name="nickName" id="nickName" placeholder="昵称" maxlength="20">
                    </div>
                </td>
            </tr>
            <tr>
                <th><font color="red">*</font><label class="Validform_label">手机号:</label></th>
                <td>
                    <div class="form-group">
                        <input value="${systemUserVo.phone}" maxlength="20" type="text" name="phone" id="phone" placeholder="手机号" class="form-control input80">
                    </div>
                </td>
            </tr>
            <tr>
                <th><font color="red">*</font><label class="Validform_label">用户组织:</label></th>
                <td>
                    <div class="form-group">
                        <select id="organizationId" name="organizationId" multiple="multiple" class="form-control select2">
                            <c:forEach var="orgLists" items="${orgList}">
                                <option value="${orgLists.code}"
                                    <c:forEach var="orgListSelects" items="${orgListSelect}">
                                        <c:if test="${orgListSelects.code==orgLists.code}">selected="selected"</c:if>
                                    </c:forEach>>
                                        ${orgLists.name}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                </td>
            </tr>
            <tr>
                <th><font color="red">*</font><label class="Validform_label">用户角色:</label></th>
                <td>
                    <div class="form-group">
                        <select id="roleId" name="roleId" multiple="multiple" class="form-control select2">
                            <c:forEach var="roleLists" items="${roleList}">
                                <option value="${roleLists.code}"
                                    <c:forEach var="roleListSelects" items="${roleListSelect}">
                                        <c:if test="${roleListSelects.code==roleLists.code}">selected="selected"</c:if>
                                    </c:forEach>>
                                        ${roleLists.name}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                </td>
            </tr>
            <tr>
                <th>邮箱:</th>
                <td>
                    <div class="form-group">
                        <input value="${systemUserVo.email}" maxlength="50" type="text" name="email" id="email" placeholder="邮箱" class="form-control input80">
                    </div>
                </td>
            </tr>
            <tr>
                <th>姓名:</th>
                <td>
                    <div class="form-group">
                        <input value="${systemUserVo.realName}" maxlength="20" type="text" name="realName" id="realName" placeholder="姓名" class="form-control input80">
                    </div>
                </td>
            </tr>
            <tr>
                <th>性别:</th>
                <td>
                    <div class="form-group">
                        <input type="radio" name="sex" value="1" class="minimal" <c:if test="${systemUserVo.sex == 1 }">checked="checked"</c:if> id="sex1"/><label for="sex1">男</label>
                        <input type="radio" name="sex" value="2" class="minimal" <c:if test="${systemUserVo.sex == 2 }">checked="checked"</c:if> id="sex2"/><label for="sex2">女</label>
                    </div>
                </td>
            </tr>
            <tr>
                <th>状态:</th>
                <td>
                    <div class="form-group">
                        <c:if test="${systemUserVo.userState == 3 }">
                            <input type="radio" name="userState" value="3" class="minimal" checked="checked" id="userState3"/><label for="userState3">未激活</label>
                        </c:if>
                        <c:if test="${systemUserVo.userState != 3 }">
                            <input type="radio" name="userState" value="1" class="minimal" <c:if test="${systemUserVo.userState == 2 }">checked="checked"</c:if> id="userState1"/><label for="userState1">正常</label>
                            <input type="radio" name="userState" value="2" class="minimal" <c:if test="${systemUserVo.userState == 3 }">checked="checked"</c:if> id="userState2"/><label for="userState2">封禁</label>
                        </c:if>

                    </div>
                </td>
            </tr>
        </c:if>
        <tr>
            <td colspan="2">
                <div align="center">
                    <button type="button" title="保存" class="btn btn-success btn-xs" onclick="save()" style="width: 100px;">
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
    var UserEdit = {
        index:null,
    };
    $(function () {
        $('#form').bootstrapValidator({
            excluded: [':disabled', ':hidden', ':not(:visible)'],

            fields: {
                nickName: {
                    validators: {
                        notEmpty: {
                            message: '昵称不能为空'
                        },
                    }
                },
                phone: {
                    validators: {
                        notEmpty: {
                            message: '手机号不能为空'
                        },
                        regexp: {
                            regexp: /^1[3|5|8]{1}[0-9]{9}$/,
                            message: '请输入正确的手机号码'
                        }
                    }
                },
                organizationId: {
                    validators: {
                        notEmpty: {
                            message: '用户组织不能为空'
                        },
                    }
                },
                roleId: {
                    validators: {
                        notEmpty: {
                            message: '用户角色不能为空'
                        },
                    }
                },
                email: {
                    validators: {
                        emailAddress: {
                            message: '请输入正确的邮件地址'
                        }
                    }
                },
                realName: {
                    validators: {
                        regexp: {
                            regexp: /^[\u0391-\uFFE5A-Za-z]+$/,
                            message: '只能输入中英文'
                        }
                    }
                },
            }
        })
        $(".select2").select2();
        $('input[type="checkbox"].minimal, input[type="radio"].minimal').iCheck({
            checkboxClass: 'icheckbox_minimal-blue',
            radioClass: 'iradio_minimal-blue'
        });
        $('input[type="radio"]').on('ifChanged', function(event){

        });

    });

    function save() {
        $('#form').bootstrapValidator('validate');
        if(!$("#form").data('bootstrapValidator').isValid()){
            return;
        }

        var url;
        <c:if test="${systemUserVo.page == 'update' }">
        url = '${basePath}/system/user/update/${systemUserVo.userId}';
        </c:if>
        <c:if test="${systemUserVo.page != 'update' }">
        url = '${basePath}/system/user/create';
        </c:if>
        $.ajax({
            type: 'post',
            url: url,
            data: 	$("#form").serialize(),
            beforeSend: function() {//防止重复提交数据 与loading 表单校验
                UserEdit.index = layer.load(1, {
                    //shade: [0.4,'#000'] //0.1透明度的白色背景
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
                layer.close(UserEdit.index);
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
