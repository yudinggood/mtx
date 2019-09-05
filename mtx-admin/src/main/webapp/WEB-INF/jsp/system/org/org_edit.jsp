<!--system/org/org_edit.jsp-->
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
        EHM.ImportZtree();
        EHM.ImportInputTree();
    </script>
</head>
<body>

<form id="form">
    <input type="hidden" name="page" id="page" value="${systemOrganizationVo.page}" />
    <input type="hidden" name="organizationPid" id="organizationPid" value="${systemOrganizationVo.organizationPid}" />
    <c:if test="${systemOrganizationVo.page == 'update' }">
        <input type="hidden" name="_method" id="_method" value="PUT"/>
    </c:if>
    <table class="table table-bordered table-form" align="center" style="width: 98%">
        <c:if test="${systemOrganizationVo.page != 'update' }">
            <tr>
                <th width="20%"><font color="red">*</font><label class="Validform_label">组织名称:</label></th>
                <td width="80%">
                    <div class="form-group">
                        <input class="form-control" type="text" name="organizationName" id="organizationName" placeholder="组织名称" >
                    </div>
                </td>
            </tr>
            <tr>
                <th><font color="red">*</font><label class="Validform_label">父组织:</label></th>
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
                <th>组织描述:</th>
                <td>
                    <div class="form-group">
                        <input maxlength="100" type="text" name="description" id="description" placeholder="组织描述" class="form-control input80">
                    </div>
                </td>
            </tr>
        </c:if>
        <c:if test="${systemOrganizationVo.page == 'update' }">
            <tr>
                <th width="20%"><font color="red">*</font><label class="Validform_label">组织名称:</label></th>
                <td width="80%">
                    <div class="form-group">
                        <input value="${systemOrganizationVo.organizationName}" class="form-control" type="text" name="organizationName" id="organizationName" placeholder="组织名称" >
                    </div>
                </td>
            </tr>
            <tr>
                <th><font color="red">*</font><label class="Validform_label">父组织:</label></th>
                <td>
                    <div class="form-group">
                        <input value="${systemOrganizationVo.parentName}" onfocus="this.blur();" class="form-control" type="text" name="parentName" id="parentName" placeholder="--请选择--" onclick="getSelect()">
                        <div id="pcodeTreeDiv" style="overflow:auto;height:20%;display: none; position: absolute; z-index: 200;background: white;border-width:1px;border-style: solid;">
                            <ul id="pcodeTree" class="ztree tree-box" style="width:244px !important;"></ul>
                        </div>
                    </div>
                </td>
            </tr>
            <tr>
                <th>组织描述:</th>
                <td>
                    <div class="form-group">
                        <input value="${systemOrganizationVo.description}" maxlength="100" type="text" name="description" id="description" placeholder="组织描述" class="form-control input80">
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
    var OrgEdit = {
        ztree: null,
        index:null,
    };
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
        OrgEdit.ztree = new $ZTree("pcodeTree", "${basePath}/system/org/tree",setting);
        OrgEdit.ztree.init();


        $('#form').bootstrapValidator({
            excluded: [':disabled', ':hidden', ':not(:visible)'],

            fields: {
                organizationName: {
                    validators: {
                        notEmpty: {
                            message: '组织不能为空'
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
                            message: '父组织不能为空'
                        },
                    }
                },

            }
        })
        $("#CommitAndSave").click(function(){
            commit();

        });

    });
    function zTreeOnClick(e, treeId, treeNode) {
        $("#parentName").attr("value", treeNode.name);
        $("#organizationPid").attr("value", treeNode.id);
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
        <c:if test="${systemOrganizationVo.page == 'update' }">
        url = '${basePath}/system/org/update/${systemOrganizationVo.organizationId}';
        </c:if>
        <c:if test="${systemOrganizationVo.page != 'update' }">
        url = '${basePath}/system/org/create';
        </c:if>
        $.ajax({
            type: 'post',
            url: url,
            data: 	$("#form").serialize(),
            beforeSend: function() {//防止重复提交数据 与loading 表单校验
                OrgEdit.index = layer.load(1, {
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
                layer.close(OrgEdit.index);
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
