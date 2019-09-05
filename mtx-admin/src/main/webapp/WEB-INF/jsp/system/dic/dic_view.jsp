<!--system/dic/dic_view.jsp-->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags"  prefix="spring" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="../../base/base.jsp"%>
    <style>

    </style>
    <script>

    </script>
</head>
<body class="hold-transition skin-blue layout-top-nav" >

<div class="margin">
    <input type="text" id="dicName" name="dicName" size="30" maxlength="50"
           class="input70" placeholder="字典名称搜索..."/>

    <div class="pull-right" ><button type="button"
                                     title="添加字典"
                                     class="btn btn-success btn-xs" id="addSameLvlTypeBut"
                                     onClick="add()">
        <span class="fa fa-plus"></span>
        <span style="padding-top:2px">&nbsp;添&nbsp;加&nbsp;字&nbsp;典&nbsp;</span>
    </button>
        <button type="button"
                title="修改字典"
                class="btn btn-success btn-xs" id="modTypeBut"
                onClick="modify()">
            <span class="fa fa-edit"></span>
            <span style="padding-top:2px">&nbsp;&nbsp;修&nbsp;改&nbsp;字&nbsp;典&nbsp;&nbsp;</span>
        </button>
        <button type="button"
                title="删除字典"
                class="btn btn-danger btn-xs" id="rmTypeBut"
                onClick="remove()">
            <span class="fa fa-trash"></span>
            <span style="padding-top:2px">&nbsp;&nbsp;删&nbsp;除&nbsp;字&nbsp;典&nbsp;&nbsp;</span>
        </button></div>
</div>
<div class="margin">
    <table id="bootstrapTable">
        <thead></thead>
        <tbody></tbody>
    </table>
</div>
<link href="${basePath}/resources/mtx-admin/js/plugins/jquery-treegrid/css/jquery.treegrid.css" rel="stylesheet"/>
<script src="${basePath}/resources/mtx-admin/js/plugins/jquery-treegrid/js/jquery.treegrid.min.js"></script>
<script src="${basePath}/resources/mtx-admin/js/plugins/jquery-treegrid/js/jquery.treegrid.bootstrap3.js"></script>
<script src="${basePath}/resources/mtx-admin/js/plugins/jquery-treegrid/extension/jquery.treegrid.extension.js"></script>
<script src="${basePath}/resources/mtx-admin/js/plugins/jquery-treegrid/tree-table-object.js"></script>

<script>
    var DicView = {
        id: "bootstrapTable",	//表格id
        table:null,
        index:null,
    };
    $(function () {
        $("#dicName").val($("#keywords").val());

        var defaultColunms = initTable();
        var table = new BSTreeTable(DicView.id, "${basePath}/system/dic/list/"+${dicId}, defaultColunms);
        table.setExpandColumn(0);
        table.setIdField("dicId");
        table.setCodeField("dicId");
        table.setParentCodeField("dicPid");
        table.setExpandAll(true);
        table.init();
        DicView.table = table;

        $("body").keydown(function() {
            var event=arguments.callee.caller.arguments[0]||window.event;
            if (event.keyCode == "13") {//keyCode=13是回车键
                initTree($("#dicName").val());
            }
        });
    });

    function initTable(){
        var columns = [
            {title: 'id', field: 'dicId', align: 'center', valign: 'middle', sortable: true,width:'70px'},
            {title: '字典名称', field: 'dicName', align: 'center', valign: 'middle', sortable: true,width:'15%'},
            {title: '字典编码', field: 'dicCode', align: 'center', valign: 'middle', sortable: true,width:'15%'},
            ]
        return columns;
    }
    function add(){
        top.layer.open({
            type: 2,
            title: '添加字典',
            shadeClose: false,
            shade: 0.4,
            maxmin: true,
            area: ['50%', '60%'],
            content: '${basePath}/system/dic/edit/${dicId}/create'
        });

    }
    function modify(){
        top.layer.open({
            type: 2,
            title: '修改字典',
            shadeClose: false,
            shade: 0.4,
            maxmin: true,
            area: ['50%', '60%'],
            content: '${basePath}/system/dic/edit/${dicId}/update'
        });
    }
    function remove() {
        var i =layer.confirm('确认删除该字典吗？', {
            shade: 0.4,
            btn: ['确认','取消'] //按钮
        }, function(){
            $.ajax({
                type: 'post',
                url: '${basePath}/system/dic/delete/${dicId}',
                data: {_method:"DELETE"},
                beforeSend: function() {
                    DicView.index = layer.load(1, {

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
                    layer.close(DicView.index);
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
