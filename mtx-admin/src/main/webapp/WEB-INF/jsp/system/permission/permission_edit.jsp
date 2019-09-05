<!--system/permission/permission_edit.jsp-->
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
        EHM.ImportSelect();
        EHM.ImportLayer();
        EHM.ImportZtree();
        EHM.ImportInputTree();
    </script>
</head>
<body>
<form id="form">
    <input type="hidden" name="page" id="page" value="${systemPermissionVO.page}" />
    <input type="hidden" name="permissionPid" id="permissionPid" value="${systemPermissionVO.permissionPid}" />
    <c:if test="${systemPermissionVO.page == 'update' }">
        <input type="hidden" name="_method" id="_method" value="PUT"/>
        <input type="hidden" name="icon" id="icon" value="${systemPermissionVO.icon}" />
    </c:if>
    <c:if test="${systemPermissionVO.page != 'update' }">
        <input type="hidden" name="icon" id="icon" value="fa fa-leaf" />
    </c:if>
    <table class="table table-bordered table-form" align="center" style="width: 98%">
        <c:if test="${systemPermissionVO.page != 'update' }">
            <tr>
                <th width="20%"><font color="red">*</font><label class="Validform_label">名称:</label></th>
                <td width="80%">
                    <div class="form-group">
                        <input class="form-control" type="text" name="name" id="name" placeholder="名称" datatype="s1-20">
                    </div>
                </td>
            </tr>
            <tr>
                <th><font color="red">*</font><label class="Validform_label">父权限:</label></th>
                <td>
                    <div class="form-group">
                        <input onfocus="this.blur();" class="form-control" type="text" name="parentName" id="parentName" placeholder="--请选择--" onclick="getSelect()">
                        <div id="pcodeTreeDiv" style="overflow:auto;height:20%;display: none; position: absolute; z-index: 200;background: white;border-width:1px;border-style: solid;">
                            <ul id="pcodeTree" class="ztree tree-box" style="width:244px !important;"></ul>
                        </div>
                    </div>
                </td>
            </tr>
            <tr>
                <th><font color="red">*</font><label class="Validform_label">所属系统:</label></th>
                <td>
                    <div class="form-group">
                    <select class="form-control select2" name="systemId" id="systemId"  datatype="*">
                        <option value="" style="">--请选择--</option>
                        <c:forEach items="${systemSelectVO}" var="selectVo">
                            <option value="${selectVo.code }" <c:if test="${selectVo.code == systemPermissionVO.systemId &&systemPermissionVO.page=='update'}">selected</c:if>>${selectVo.name }</option>
                        </c:forEach>
                    </select>
                    </div>
                </td>
            </tr>
            <tr>
                <th>权限值:</th>
                <td>
                    <div class="form-group">
                    <input maxlength="50" type="text" name="permissionValue" id="permissionValue" placeholder="权限值" class="form-control input80">
                    </div>
                </td>
            </tr>
            <tr>
                <th>路径:</th>
                <td>
                    <div class="form-group">
                    <input maxlength="100" type="text" name="uri" id="uri" placeholder="路径" class="form-control input80">
                    </div>
                </td>
            </tr>
            <tr>
                <th>图标:</th>
                <td>
                    <i id="iconName" name="iconName" class="fa fa-leaf" style="margin-right:10px;font-size:20px;"></i>
                    <a class="blue" onclick="editIcon('fa-leaf');" href="javascript:void(0);">
                        <i style="font-size:20px;" class="menu-icon fa fa-picture-o" title="编辑图标"></i>
                    </a>
                </td>
            </tr>
            <tr>
                <th>排序:</th>
                <td>
                    <div class="form-group">
                    <input maxlength="20" type="number" name="permissionOrder" id="permissionOrder" placeholder="排序" class="form-control input80">
                    </div>
                </td>
            </tr>
            <tr>
                <th>权限类型:</th>
                <td>
                    <div class="form-group">
                    <input type="radio" name="type" value="0" class="minimal" checked="checked" id="type0"/><label for="type0">根</label>
                    <input type="radio" name="type" value="1" class="minimal"  id="type1"/><label for="type1">目录</label>
                    <input type="radio" name="type" value="2" class="minimal"  id="type2"/><label for="type2">菜单</label>
                    <input type="radio" name="type" value="3" class="minimal"  id="type3"/><label for="type3">按钮</label>
                    </div>
                </td>
            </tr>
            <tr>
                <th>权限状态:</th>
                <td>
                    <div class="form-group">
                    <input type="radio" name="status" value="1" class="minimal" checked="checked" id="status1"/><label for="status1">正常</label>
                    <input type="radio" name="status" value="0" class="minimal"  id="status0"/><label for="status0">禁止</label>
                    </div>
                </td>
            </tr>
            <tr>
                <th>权限描述:</th>
                <td>
                    <div class="form-group">
                    <input maxlength="100" type="text" name="description" id="description" placeholder="权限描述" class="form-control input80">
                    </div>
                </td>
            </tr>
        </c:if>
        <c:if test="${systemPermissionVO.page == 'update' }">
            <tr>
                <th width="20%"><font color="red">*</font><label class="Validform_label">名称:</label></th>
                <td width="80%">
                    <div class="form-group">
                    <input value="${systemPermissionVO.name}" class="form-control" type="text" name="name" id="name" placeholder="名称" datatype="s1-20">
                    </div>
                </td>
            </tr>
            <tr>
                <th><font color="red">*</font><label class="Validform_label">父权限:</label></th>
                <td>
                    <div class="form-group">
                        <input value="${systemPermissionVO.parentName}" onfocus="this.blur();" class="form-control" type="text" name="parentName" id="parentName" placeholder="--请选择--" onclick="getSelect()">
                        <div id="pcodeTreeDiv" style="overflow:auto;height:20%;display: none; position: absolute; z-index: 200;background: white;border-width:1px;border-style: solid;">
                            <ul id="pcodeTree" class="ztree tree-box" style="width:244px !important;"></ul>
                        </div>
                    </div>
                </td>
            </tr>
            <tr>
                <th><font color="red">*</font><label class="Validform_label">所属系统:</label></th>
                <td>
                    <div class="form-group">
                    <select class="form-control select2" name="systemId" id="systemId"  datatype="*">
                        <option value="" style="">--请选择--</option>
                        <c:forEach items="${systemSelectVO}" var="selectVo">
                            <option value="${selectVo.code }" <c:if test="${selectVo.code == systemPermissionVO.systemId &&systemPermissionVO.page=='update'}">selected</c:if>>${selectVo.name }</option>
                        </c:forEach>
                    </select>
                    </div>
                </td>
            </tr>
            <tr>
                <th>权限值:</th>
                <td>
                    <div class="form-group">
                    <input value="${systemPermissionVO.permissionValue}" maxlength="50" type="text" name="permissionValue" id="permissionValue" placeholder="权限值" class="form-control input80">
                    </div>
                </td>
            </tr>
            <tr>
                <th>路径:</th>
                <td>
                    <div class="form-group">
                    <input value="${systemPermissionVO.uri}" maxlength="100" type="text" name="uri" id="uri" placeholder="路径" class="form-control input80">
                    </div>
                </td>
            </tr>
            <tr>
                <th>图标:</th>
                <td>
                    <i id="iconName" name="iconName" class="${systemPermissionVO.icon}" style="margin-right:10px;font-size:20px;"></i>
                    <a class="blue" onclick="editIcon('fa-leaf');" href="javascript:void(0);">
                        <i style="font-size:20px;" class="menu-icon fa fa-picture-o" title="编辑图标"></i>
                    </a>
                </td>
            </tr>
            <tr>
                <th>排序:</th>
                <td>
                    <div class="form-group">
                    <input value="${systemPermissionVO.permissionOrder}" maxlength="20" type="number" name="permissionOrder" id="permissionOrder" placeholder="排序" class="form-control input80">
                    </div>
                </td>
            </tr>
            <tr>
                <th>权限类型:</th>
                <td>
                    <div class="form-group">
                    <input type="radio" name="type" value="0" class="minimal" <c:if test="${systemPermissionVO.type == 0 }">checked="checked"</c:if> id="type0"/><label for="type0">根</label>
                    <input type="radio" name="type" value="1" class="minimal" <c:if test="${systemPermissionVO.type == 1 }">checked="checked"</c:if> id="type1"/><label for="type1">目录</label>
                    <input type="radio" name="type" value="2" class="minimal" <c:if test="${systemPermissionVO.type == 2 }">checked="checked"</c:if> id="type2"/><label for="type2">菜单</label>
                    <input type="radio" name="type" value="3" class="minimal" <c:if test="${systemPermissionVO.type == 3 }">checked="checked"</c:if> id="type3"/><label for="type3">按钮</label>
                    </div>
                </td>
            </tr>
            <tr>
                <th>权限状态:</th>
                <td>
                    <div class="form-group">
                    <input type="radio" name="status" value="1" class="minimal" <c:if test="${systemPermissionVO.status == 1 }">checked="checked"</c:if> id="status1"/><label for="status1">正常</label>
                    <input type="radio" name="status" value="0" class="minimal" <c:if test="${systemPermissionVO.status == 0 }">checked="checked"</c:if> id="status0"/><label for="status0">禁止</label>
                    </div>
                </td>
            </tr>
            <tr>
                <th>权限描述:</th>
                <td>
                    <div class="form-group">
                    <input value="${systemPermissionVO.description}" maxlength="100" type="text" name="description" id="description" placeholder="权限描述" class="form-control input80">
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
    var PermissionEdit = {
        ztree: null,
        index:null,
    };
    var doCallBackResult = top.EHM.Cache["doCallBackResult"] = function (values){
        $("#iconName").attr("class",values) ;
        $("#icon").val(values);
    }
    $(function () {
        var setting = {
            view: {
                showLayer: false,
                selectedMulti: false//是否允许多选
            },
            data: {
                simpleData: {
                    enable: true
                }
            } ,
            callback: {
                onClick: zTreeOnClick
            }
        };
        PermissionEdit.ztree = new $ZTree("pcodeTree", "${basePath}/system/permission/tree",setting);
        PermissionEdit.ztree.init();


        $('#form').bootstrapValidator({
            //container: 'tooltip',
            excluded: [':disabled', ':hidden', ':not(:visible)'],
            /*feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },*/

            fields: {
                name: {
                    validators: {
                        notEmpty: {
                            message: '名称不能为空'
                        },
                        stringLength:{
                            max : 20,
                            message: '不超过20个字符'
                        },
                    }
                },
                parentName: {
                    validators: {
                        notEmpty: {
                            message: '父权限不能为空'
                        },
                    }
                },
                systemId: {
                    validators: {
                        notEmpty: {
                            message: '所属系统不能为空'
                        },
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
        $("#CommitAndSave").click(function(){
            commit();

        });
    });

    function zTreeOnClick(e, treeId, treeNode) {
        $("#parentName").attr("value", treeNode.name);
        $("#permissionPid").attr("value", treeNode.id);
    }
    function getSelect() {
        Mytree.showInputTree("parentName", "pcodeTreeDiv", 0, 88);
    }


    function commit(){
        $('#form').bootstrapValidator('validate');
        if(!$("#form").data('bootstrapValidator').isValid()){
            return;
        }

        var url;
        <c:if test="${systemPermissionVO.page == 'update' }">
        url = '${basePath}/system/permission/update/${systemPermissionVO.permissionId}';
        </c:if>
        <c:if test="${systemPermissionVO.page != 'update' }">
        url = '${basePath}/system/permission/create';
        </c:if>
        $.ajax({
            type: 'post',
            url: url,
            data: 	$("#form").serialize(),
            beforeSend: function() {//防止重复提交数据 与loading 表单校验
                PermissionEdit.index = layer.load(1, {
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
                layer.close(PermissionEdit.index);
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
    
    function editIcon(icon) {
        layer.open({
            type: 2,
            title: '选择Icon',
            shadeClose: false,
            shade: 0.4,
            maxmin: false, //开启最大化最小化按钮
            area: ['750px', '500px'],
            content: '${basePath}/system/permission/icon/'+icon
        });
    }

</script>
</body>
</html>
