<!--system/attach/attach_index.jsp-->
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
        <div class="easyui-panel" style="padding:0px;border:0px" fit="true" border="false" id="attachListpanel">
            <div class="margin">
                <div class="" id="toolbar">
                    <button type="button"
                            title="添加文件"
                            class="btn btn-success btn-xs"
                            onClick="add()">
                        <span class="fa fa-plus"></span>
                        <span style="padding-top:2px">&nbsp;添&nbsp;加&nbsp;文&nbsp;件&nbsp;</span>
                    </button>
                    <button type="button"
                            title="删除文件"
                            class="btn btn-danger btn-xs"
                            onClick="removeAll()">
                        <span class="fa fa-trash"></span>
                        <span style="padding-top:2px">&nbsp;&nbsp;删&nbsp;除&nbsp;文&nbsp;件&nbsp;&nbsp;</span>
                    </button>
                </div>
                <table id="table"></table>
            </div>
        </div>
    </div>
</div>


<script>
    var AttachIndex = {
        $table : $('#table'),
        index:null,
    };
    var refreshs = top.EHM.Cache["refreshs"] = function(){
        AttachIndex.$table.bootstrapTable("refresh");
    }
    $(function () {
        initTable();
    });
    function onloadFun(){
        AttachIndex.$table.bootstrapTable('resetView', {
            height: getHeight(0.95)
        });
    }
    function initTable() {
        AttachIndex.$table.bootstrapTable({
            url: '${basePath}/system/attach/list',
            height: getHeight(0.95),
            striped: true,
            search: true,
            showRefresh: true,
            showColumns: true,//
            minimumCountColumns: 2,
            clickToSelect: true,
            //detailView: true,
            //detailFormatter: 'detailFormatter',
            pagination: true,
            paginationLoop: false,
            sidePagination: 'server',
            silentSort: false,
            smartDisplay: false,
            escape: true,
            searchOnEnterKey: true,
            idField: 'attachId',
            maintainSelected: true,
            toolbar: '#toolbar',
            pageList: [20, 50, 100],
            pageSize: 20,
            columns: [
                {field: 'ck', checkbox: true},
                {field: 'id', title: '编号',width:'2%',formatter: function(value, row, index) {
                    //获取每页显示的数量
                    var pageSize=AttachIndex.$table.bootstrapTable('getOptions').pageSize;
                    //获取当前是第几页
                    var pageNumber=AttachIndex.$table.bootstrapTable('getOptions').pageNumber;
                    //返回序号，注意index是从0开始的，所以要加上1
                    return pageSize * (pageNumber - 1) + index + 1;
                }},
                {field: 'bizTypeName', title: '业务名称'},
                {field: 'fileName', title: '原文件名'},
                {field: 'newName', title: '存储文件名'},
                {field: 'filePath', title: '上传日期',formatter: function(value, row, index) {
                    return '<img  src='+${basePath}/upload/+value+' width="50" height="50" class="img-rounded" >';
                }},
                {field: 'fileSizeName', title: '文件大小'},
                {field: 'editDate', title: '上传日期',formatter: function(value, row, index) {
                    return new Date(value).format("yyyy-MM-dd HH:mm:ss");
                }},
                {field: 'editUser', title: '上传人'},
                {field: 'action', title: '操作', align: 'center', events: 'actionEvents', clickToSelect: false,width:'5%',
                    formatter: function(value, row, index) {
                        return [
                            '<a class="update" href="javascript:;" onclick="update(\''+row.attachId+'\')" data-toggle="tooltip" title="查看"><i class="fa fa-globe"></i></a>　',
                            '<a class="delete" href="javascript:;" onclick="deleteOne(\''+row.attachId+'\')" data-toggle="tooltip" title="删除"><i class="fa fa-trash"></i></a>'
                        ].join('');
                    }}
            ]
        });
    }
    function add() {
        top.layer.open({
            type: 2,
            title: '添加文件',
            shadeClose: false,
            shade: 0.4,
            maxmin: true,
            area: ['70%', '70%'],
            content: '${basePath}/system/attach/edit/0/create'
        });
    }
    function update(id) {
        top.layer.open({
            type: 2,
            title: '查看文件',
            shadeClose: false,
            shade: 0.4,
            maxmin: true,
            area: ['70%', '70%'],
            content: '${basePath}/system/attach/edit/'+id+'/update'
        });
    }
    function deleteOne(id) {
        var i =layer.confirm('确认删除该文件吗？', {
            shade: 0.4,
            btn: ['确认','取消'] //按钮
        }, function(){
            $.ajax({
                type: 'post',
                url: '${basePath}/system/attach/delete/'+id,
                data: {_method:"DELETE"},
                beforeSend: function() {
                    AttachIndex.index = layer.load(1, {

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
                    layer.close(AttachIndex.index);
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
    function removeAll() {
        var rows = AttachIndex.$table.bootstrapTable('getSelections');
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
            ids.push(rows[i].attachId);
        }

        var i =layer.confirm('确认删除这些文件吗？', {
            shade: 0.4,
            btn: ['确认','取消'] //按钮
        }, function(){
            $.ajax({
                type: 'post',
                url: '${basePath}/system/attach/deletes',
                data: {_method:"DELETE",ids:ids.join("-")},
                beforeSend: function() {
                    AttachIndex.index = layer.load(1, {

                    });
                },
                success: function(result) {
                    if (result.code == 200) {
                        AttachIndex.$table.bootstrapTable("refresh");
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
                    layer.close(AttachIndex.index);
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
