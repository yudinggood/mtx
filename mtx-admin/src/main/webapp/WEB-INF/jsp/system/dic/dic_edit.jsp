<!--system/dic/dic_edit.jsp-->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="../../base/base.jsp" %>


    <style>
        .form-control {
            width: 75%;
            float: left;
        }

    </style>
    <script>
        EHM.ImportEasyUI();
        EHM.ImportLayer();
    </script>
</head>
<body>
<div class="easyui-layout" fit="true" scroll="no">
    <div data-options="region:'north'" style="overflow: hidden;">
        <table id="form" class="table table-bordered table-form" align="center" style="width: 98%">
            <c:if test="${systemDicVo.page == 'update' }">
                <input type="hidden" name="_method" id="_method" value="PUT"/>
            </c:if>
            <c:if test="${systemDicVo.page != 'update' }">
                <input type="hidden" name="_method" id="_method" value="POST"/>
            </c:if>
            <c:if test="${systemDicVo.page != 'update' }">
                <tr>
                    <th width="10%"><font color="red">*</font><label class="Validform_label">编码:</label></th>
                    <td width="40%">
                        <div class="form-group">
                            <input class="form-control" style="width: 70%;" type="text" name="dicCode" id="dicCode"
                                   placeholder="编码">
                        </div>
                    </td>
                    <th width="10%"><font color="red">*</font><label class="Validform_label">名称:</label></th>
                    <td width="40%">
                        <div class="form-group">
                            <input class="form-control" style="width: 70%;" type="text" name="dicName" id="dicName"
                                   placeholder="名称">
                        </div>
                    </td>
                </tr>
            </c:if>
            <c:if test="${systemDicVo.page == 'update' }">
                <tr>
                    <th width="10%"><font color="red">*</font><label class="Validform_label">编码:</label></th>
                    <td width="40%">
                        <div class="form-group">
                            <input value="${systemDicVo.dicCode}" class="form-control" style="width: 70%;" type="text"
                                   name="dicCode" id="dicCode" placeholder="编码">
                        </div>
                    </td>
                    <th width="10%"><font color="red">*</font><label class="Validform_label">名称:</label></th>
                    <td width="40%">
                        <div class="form-group">
                            <input value="${systemDicVo.dicName}" class="form-control" style="width: 70%;" type="text"
                                   name="dicName" id="dicName" placeholder="名称">
                        </div>
                    </td>
                </tr>
            </c:if>
        </table>
    </div>
    <div data-options="region:'center'" style="height: 70%">
        <div class="ibox float-e-margins" style="position:relative;top:15px;overflow-x: hidden;">
            <div class="ibox-content">
                <div class="form-horizontal">
                    <div class="row">
                        <div class="col-sm-12" id="itemsArea">
                            <c:if test="${systemDicVo.page == 'update' }">
                            <c:forEach items="${dicList}" var="mList">
                                <div class="" name="dictItem" id="dictItem" style="height: 45px;">
                                    <label class="col-sm-2 control-label">值</label>
                                    <div class="col-sm-3">
                                        <div class="form-group">
                                            <input class="form-control" type="text" name="itemNum" value="${mList.dicCode}">
                                        </div>
                                    </div>
                                    <label class="col-sm-2 control-label" style="width: 8%;">名称</label>
                                    <div class="col-sm-3">
                                        <div class="form-group">
                                            <input class="form-control" type="text" name="itemName" value="${mList.dicName}">
                                        </div>
                                    </div>
                                    <div class="col-sm-2" style="position:relative;top:6px;">
                                        <button type="button"
                                                title="删除"
                                                class="btn btn-danger btn-xs"
                                                onClick="removeField(event)">
                                            <span class="fa fa-trash"></span>
                                            <span style="padding-top:2px">&nbsp;&nbsp;删&nbsp;除&nbsp;&nbsp;</span>
                                        </button>
                                    </div>
                                </div>
                            </c:forEach>
                            </c:if>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </div>
    <div data-options="region:'south'" style="height: 15%">
        <div align="center" style="position:relative;top:15px;">
            <button type="button" title="添加" class="btn btn-success btn-xs" onClick="addField()" style="width: 100px;">
                <span class="fa fa-plus"></span>
                <span style="padding-top:2px">&nbsp;添&nbsp;加&nbsp;</span>
            </button>
            <button type="button" title="保存" class="btn btn-success btn-xs" onClick="save()" style="width: 100px;">
                <span class="fa fa-save"></span>
                <span style="padding-top:2px">&nbsp;保&nbsp;存&nbsp;</span>
            </button>
            <button type="button" title="关闭" class="btn btn-primary btn-xs" onClick="top.layer.close(top.layer.index);"
                    style="width: 100px;">
                <span class="fa fa-close"></span>
                <span style="padding-top:2px">&nbsp;关&nbsp;闭&nbsp;</span>
            </button>
        </div>
    </div>
</div>
<script type="text/template" id="itemTemplate">
    <div class="" name="dictItem" id="dictItem" style="height: 45px;">
        <label class="col-sm-2 control-label">值</label>
        <div class="col-sm-3">
            <div class="form-group">
                <input class="form-control" type="text" name="itemNum">
            </div>
        </div>
        <label class="col-sm-2 control-label" style="width: 8%;">名称</label>
        <div class="col-sm-3">
            <div class="form-group">
                <input class="form-control" type="text" name="itemName">
            </div>
        </div>
        <div class="col-sm-2" style="position:relative;top:6px;">
            <button type="button"
                    title="删除"
                    class="btn btn-danger btn-xs"
                    onClick="removeField(event)">
                <span class="fa fa-trash"></span>
                <span style="padding-top:2px">&nbsp;&nbsp;删&nbsp;除&nbsp;&nbsp;</span>
            </button>
        </div>
    </div>

</script>

<script>
    var DicEdit = {
        index: null,
        dicName: '',			//字典的名称
        dicCode: '',			//字典的惟一code
        mutiString: '',		//拼接字符串内容(拼接字典条目)
        itemTemplate: $("#itemTemplate").html(),
    };
    $(function () {
        $('#form').bootstrapValidator({
            excluded: [':disabled', ':hidden', ':not(:visible)'],
            fields: {
                dicCode: {
                    validators: {
                        notEmpty: {
                            message: '编码不能为空'
                        },
                        regexp: {
                            /* 只需加此键值对，包含正则表达式，和提示 */
                            regexp: /^[a-zA-Z0-9_\.]+$/,
                            message: '只能是数字和字母'
                        },
                    }
                },
                dicName: {
                    validators: {
                        notEmpty: {
                            message: '名称不能为空'
                        },
                    }
                },
            }
        })
    });

    function addField() {
        $("#itemsArea").append(DicEdit.itemTemplate);
    }

    function removeField(event) {//通过event对象操作页面元素
        var obj = eventParseObject(event);
        if (event.target.nodeName == 'SPAN') {//判断标签类型
            obj.parent().parent().parent().remove();
        } else {
            obj.parent().parent().remove();
        }

    }

    function save() {
        $('#form').bootstrapValidator('validate');
        if (!$("#form").data('bootstrapValidator').isValid()) {
            return;
        }

        DicEdit.collectMyData();
        var method = $("#_method").val();
        var url;
        <c:if test="${systemDicVo.page != 'update' }">
        url = '${basePath}/system/dic/create';
        </c:if>
        <c:if test="${systemDicVo.page == 'update' }">
        url = '${basePath}/system/dic/update/${systemDicVo.dicId}';
        </c:if>
        $.ajax({
            type: 'post',
            url: url,
            data: {dicName: DicEdit.dicName, dicCode: DicEdit.dicCode, dictValues: DicEdit.mutiString,_method:method},
            beforeSend: function () {
                DicEdit.index = layer.load(1, {});
            },
            success: function (result) {
                if (result.code == 200) {
                    top.EHM.Cache["refreshs"]();
                    top.layer.close(top.layer.index);
                } else {
                    var i = layer.confirm(result.message, {
                        shade: 0,
                        btn: ['确认']
                    }, function () {
                        layer.close(i);
                    });
                }
            },
            complete: function (data) {
                layer.close(DicEdit.index);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                var i = layer.confirm(textStatus, {
                    shade: 0,
                    btn: ['确认'] //按钮
                }, function () {
                    layer.close(i);
                });
            }
        });
    }

    /**
     * 清除为空的item Dom
     */
    DicEdit.clearNullDom = function () {
        $("[name='dictItem']").each(function () {
            var num = $(this).find("[name='itemNum']").val();
            var name = $(this).find("[name='itemName']").val();
            if (num == '' || name == '') {
                $(this).remove();
            }
        });
    };
    /**
     * 收集添加字典的数据
     */
    DicEdit.collectMyData = function () {
        this.clearNullDom();
        var mutiString = "";
        $("[name='dictItem']").each(function () {
            var code = $(this).find("[name='itemNum']").val();
            var name = $(this).find("[name='itemName']").val();
            mutiString = mutiString + (code + ":" + name + ";");
        });
        this.dicName = $("#dicName").val();
        this.dicCode = $("#dicCode").val();
        this.mutiString = mutiString;
    };
</script>
</body>
</html>
