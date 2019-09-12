<!--system/config/config_index.jsp-->
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
        EHM.ImportLayer();
    </script>
</head>
<body onresize="onloadFun()">

<div class="easyui-layout" fit="true" scroll="no" >

    <div region="center" style="height: 100%;overflow: hidden;">
        <div class="easyui-panel" style="padding:0px;border:0px" fit="true" border="false" id="configListpanel">
            <div class="margin">
                <div class="" id="toolbar">
                    <button type="button"
                            title="添加配置"
                            class="btn btn-success btn-xs"
                            onClick="add()">
                        <span class="fa fa-plus"></span>
                        <span style="padding-top:2px">&nbsp;添&nbsp;加&nbsp;配&nbsp;置&nbsp;</span>
                    </button>
                    <button type="button"
                            title="删除配置"
                            class="btn btn-danger btn-xs"
                            onClick="removeAll()">
                        <span class="fa fa-trash"></span>
                        <span style="padding-top:2px">&nbsp;&nbsp;删&nbsp;除&nbsp;配&nbsp;置&nbsp;&nbsp;</span>
                    </button>
                </div>
                <table id="table"></table>
            </div>
        </div>
    </div>
</div>


<script>
    var ConfigIndex = {
        $table : $('#table'),
        index:null,
    };
    var refreshs = top.EHM.Cache["refreshs"] = function(){
        ConfigIndex.$table.bootstrapTable("refresh");
    }
    $(function () {
        initTable();

    });
    function onloadFun(){
        ConfigIndex.$table.bootstrapTable('resetView', {
            height: getHeight(0.95)
        });
    }
    function initTable() {
        ConfigIndex.$table.bootstrapTable({
            url: '${basePath}/system/config/list',
            height: getHeight(0.95),
            striped: true,
            search: true,
            showRefresh: true,
            showColumns: true,//
            minimumCountColumns: 2,
            clickToSelect: true,
            //detailView: true,
            //detailFormatter: 'detailFormatter',
            pagination: false,
            paginationLoop: false,
            sidePagination: 'server',
            silentSort: false,
            smartDisplay: false,
            escape: true,
            searchOnEnterKey: true,
            idField: 'configId',
            maintainSelected: true,
            toolbar: '#toolbar',
            pageList: [20, 50, 100],
            pageSize: 20,
            columns: [
                {field: 'ck', checkbox: true},
                {field: 'id', title: '编号',width:'2%',formatter: function(value, row, index) {
                    //获取每页显示的数量
                    var pageSize=ConfigIndex.$table.bootstrapTable('getOptions').pageSize;
                    //获取当前是第几页
                    var pageNumber=ConfigIndex.$table.bootstrapTable('getOptions').pageNumber;
                    //返回序号，注意index是从0开始的，所以要加上1
                    return pageSize * (pageNumber - 1) + index + 1;
                }},
                {field: 'name', title: '名称'},
                {field: 'code', title: '编码'},
                {field: 'value', title: '值'},
                {field: 'description', title: '描述'},
                {field: 'editDate', title: '修改日期',formatter: function(value, row, index) {
                    return new Date(value).format("yyyy-MM-dd HH:mm:ss");
                }},
                {field: 'nickName', title: '修改人'},
                {field: 'action', title: '操作', align: 'center', events: 'actionEvents', clickToSelect: false,width:'5%',
                    formatter: function(value, row, index) {
                        return [
                            '<a class="update" href="javascript:;" onclick="update(\''+row.configId+'\')" data-toggle="tooltip" title="修改"><i class="fa fa-edit"></i></a>　',
                            '<a class="delete" href="javascript:;" onclick="deleteOne(\''+row.configId+'\')" data-toggle="tooltip" title="删除"><i class="fa fa-trash"></i></a>'
                        ].join('');
                    }}
            ]
        });
    }
    function add() {
        top.layer.open({
            type: 2,
            title: '添加配置',
            shadeClose: false,
            shade: 0.4,
            maxmin: true,
            area: ['50%', '60%'],
            content: '${basePath}/system/config/edit/0/create'
        });
    }
    function update(id) {
        top.layer.open({
            type: 2,
            title: '修改配置',
            shadeClose: false,
            shade: 0.4,
            maxmin: true,
            area: ['50%', '60%'],
            content: '${basePath}/system/config/edit/'+id+'/update'
        });
    }
    function removeAll() {
        var rows = ConfigIndex.$table.bootstrapTable('getSelections');
        if(rows.length==0){
            var j =layer.confirm('请选择要删除的内容', {
                shade: 0,
                btn: ['确认']
            }, function(){
                layer.close(j);
            });
            return ;
        }
        var ids = new Array();
        for (var i in rows) {
            ids.push(rows[i].userId);
        }

        var i =layer.confirm('确认删除这些配置吗？', {
            shade: 0.4,
            btn: ['确认','取消'] //按钮
        }, function(){
            $.ajax({
                type: 'post',
                url: '${basePath}/system/config/deletes',
                data: {_method:"DELETE",ids:ids.join("-")},
                beforeSend: function() {
                    ConfigIndex.index = layer.load(1, {

                    });
                },
                success: function(result) {
                    if (result.code == 200) {
                        ConfigIndex.$table.bootstrapTable("refresh");
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
                    layer.close(ConfigIndex.index);
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
    function deleteOne(id) {
        var i =layer.confirm('确认删除该配置吗？', {
            shade: 0.4,
            btn: ['确认','取消'] //按钮
        }, function(){
            $.ajax({
                type: 'post',
                url: '${basePath}/system/config/delete/'+id,
                data: {_method:"DELETE"},
                beforeSend: function() {
                    ConfigIndex.index = layer.load(1, {

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
                    layer.close(ConfigIndex.index);
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
