<!--system/org/org_view.jsp-->
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
            <tr><th>父级名称</th><td>${systemOrganizationVo.parentName}</td></tr>
            <tr>
                <th width="20%">
                    名称
                </th>
                <td width="80%">
                    ${systemOrganizationVo.organizationName}
                </td>
            </tr>
            <tr><th>组织描述</th><td >${systemOrganizationVo.description}</td></tr>


            <tr><th>修改时间</th><td ><div class="editDate"></div></td></tr>
            <input type="hidden" id="editDate" value="${systemOrganizationVo.editDate}">
            <tfoot>
            <tr>
                <td colspan="4" align="center">
                    <button type="button"
                            title="添加组织"
                            class="btn btn-success btn-xs" id="addSameLvlTypeBut"
                            onClick="add()">
                        <span class="fa fa-plus"></span>
                        <span style="padding-top:2px">&nbsp;添&nbsp;加&nbsp;组&nbsp;织&nbsp;</span>
                    </button>
                    <button type="button"
                            title="修改组织"
                            class="btn btn-success btn-xs" id="modTypeBut"
                            onClick="modify()">
                        <span class="fa fa-edit"></span>
                        <span style="padding-top:2px">&nbsp;&nbsp;修&nbsp;改&nbsp;组&nbsp;织&nbsp;&nbsp;</span>
                    </button>
                    <button type="button"
                            title="删除组织"
                            class="btn btn-danger btn-xs" id="rmTypeBut"
                            onClick="remove()">
                        <span class="fa fa-trash"></span>
                        <span style="padding-top:2px">&nbsp;&nbsp;删&nbsp;除&nbsp;组&nbsp;织&nbsp;&nbsp;</span>
                    </button>

                </td>
            </tr>
            </tfoot>
        </table>
    </div>
</div>


<script>
    var OrgView = {
        index:null,
    };
    $(function () {
        if(!$("#editDate").val() == ""){
            var editDate=dateToGMT($("#editDate").val()).format("yyyy-MM-dd HH:mm:ss");
            $(".editDate").html(editDate);
        }


    });
    function add(){
        top.layer.open({
            type: 2,
            title: '添加组织',
            shadeClose: false,
            shade: 0.4,
            maxmin: true,
            area: ['50%', '60%'],
            content: '${basePath}/system/org/edit/${systemOrganizationVo.organizationId}/create'
        });

    }
    function modify(){
        top.layer.open({
            type: 2,
            title: '修改组织',
            shadeClose: false,
            shade: 0.4,
            maxmin: true, //开启最大化最小化按钮
            area: ['50%', '60%'],
            content: '${basePath}/system/org/edit/${systemOrganizationVo.organizationId}/update'
        });
    }

    function remove(){
        var i =layer.confirm('确认删除该组织吗？', {
            shade: 0.4,
            btn: ['确认','取消'] //按钮
        }, function(){
            $.ajax({
                type: 'post',
                url: '${basePath}/system/org/delete/${systemOrganizationVo.organizationId}',
                data: {_method:"DELETE"},
                beforeSend: function() {
                    OrgView.index = layer.load(1, {

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
                    layer.close(OrgView.index);
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
