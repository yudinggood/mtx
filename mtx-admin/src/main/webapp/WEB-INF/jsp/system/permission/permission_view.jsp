<!--system/permission/permission_view.jsp-->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags"  prefix="spring" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="../../base/base.jsp"%>


    <style>

    </style>
    <script>
        EHM.ImportEasyUI();


    </script>
</head>
<body>

<div class="easyui-layout" fit="true" >
    <div region="center"  style="padding:0px;border:0px">
        <table border="0" cellspacing="1" cellpadding="1" class="form_table" align="center">
            <tr><th>父级名称</th><td>${systemPermissionVO.parentName}</td></tr>
            <tr>
                <th width="20%">
                    名称
                </th>
                <td width="80%">
                    ${systemPermissionVO.name}
                </td>
            </tr>
            <tr><th>所属系统</th><td >${systemPermissionVO.systemName}</td></tr>
            <tr><th>权限值</th><td >${systemPermissionVO.permissionValue}</td></tr>
            <tr><th>路径</th><td >${systemPermissionVO.uri}</td></tr>
            <tr><th>图标</th><td ><c:if test="${systemPermissionVO.type != '3' }"><i id="icon" class="fa ${systemPermissionVO.icon}"></i></c:if></td></tr>
            <tr><th>权限类型</th><td >${systemPermissionVO.typeName}</td></tr>
            <tr><th>权限状态</th><td >${systemPermissionVO.statusName}</td></tr>
            <tr><th>排序</th><td >${systemPermissionVO.permissionOrder}</td></tr>
            <tr><th>权限描述</th><td >${systemPermissionVO.description}</td></tr>

            <tfoot>
            <tr>
                <td colspan="4" align="center">
                    <button type="button"
                            title="添加权限"
                            class="btn btn-success btn-xs" id="addSameLvlTypeBut"
                            onClick="addSameLvlPermission()">
                        <span class="fa fa-plus"></span>
                        <span style="padding-top:2px">&nbsp;添&nbsp;加&nbsp;权&nbsp;限&nbsp;</span>
                    </button>
                    <button type="button"
                            title="修改权限"
                            class="btn btn-success btn-xs" id="modTypeBut"
                            onClick="modifyPermission()">
                        <span class="fa fa-edit"></span>
                        <span style="padding-top:2px">&nbsp;&nbsp;修&nbsp;改&nbsp;权&nbsp;限&nbsp;&nbsp;</span>
                    </button>
                    <button type="button"
                            title="删除权限"
                            class="btn btn-danger btn-xs" id="rmTypeBut"
                            onClick="removePermission()">
                        <span class="fa fa-trash"></span>
                        <span style="padding-top:2px">&nbsp;&nbsp;删&nbsp;除&nbsp;权&nbsp;限&nbsp;&nbsp;</span>
                    </button>

                </td>
            </tr>
            </tfoot>
        </table>
    </div>
</div>


<script>

    var PermissionView = {
        index:null,
    };
    $(function () {


    });
    function addSameLvlPermission(){
        //top.PopWin.showNew('添加同级权限', '${basePath}/system/permission/edit/${systemPermissionVO.permissionId}/createsame', 700, 500, null, true, true);
        top.layer.open({
            type: 2,
            title: '添加权限',
            shadeClose: false,
            shade: 0.4,
            maxmin: true, //开启最大化最小化按钮
            area: ['50%', '60%'],
            content: '${basePath}/system/permission/edit/${systemPermissionVO.permissionId}/create'
        });

    }
    function modifyPermission(){
        top.layer.open({
            type: 2,
            title: '修改权限',
            shadeClose: false,
            shade: 0.4,
            maxmin: true, //开启最大化最小化按钮
            area: ['50%', '60%'],
            content: '${basePath}/system/permission/edit/${systemPermissionVO.permissionId}/update'
        });
    }

    function removePermission(){
        var i =layer.confirm('确认删除该权限吗？', {
            shade: 0.4,
            btn: ['确认','取消'] //按钮
        }, function(){
            $.ajax({
                type: 'post',
                url: '${basePath}/system/permission/delete/${systemPermissionVO.permissionId}',
                data: {_method:"DELETE"},
                beforeSend: function() {
                    PermissionView.index = layer.load(1, {

                    });
                },
                success: function(result) {
                    if (result.code == 200) {
                        top.EHM.Cache["refreshs"]();
                        layer.close(i);
                    }else {
                        var j =layer.confirm(result.message, {
                            shade: 0,
                            btn: ['确认']
                        }, function(){
                            layer.close(j);
                        });
                    }
                },
                complete:function (data) {
                    layer.close(PermissionView.index);
                },
                error: function(XMLHttpRequest, textStatus, errorThrown) {
                    var j =layer.confirm(textStatus, {
                        shade: 0,
                        btn: ['确认']
                    }, function(){
                        layer.close(j);
                    });
                }
            });
        }, function(){
            layer.close(i);
        });


    }

</script>
</body>
</html>
