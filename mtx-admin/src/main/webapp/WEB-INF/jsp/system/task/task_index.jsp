<!--system/task/task_index.jsp-->
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
                            title="添加任务"
                            class="btn btn-success btn-xs"
                            onClick="add()">
                        <span class="fa fa-plus"></span>
                        <span style="padding-top:2px">&nbsp;添&nbsp;加&nbsp;任&nbsp;务&nbsp;</span>
                    </button>
                    <%--<button type="button"
                            title="删除任务"
                            class="btn btn-danger btn-xs"
                            onClick="removeAll()">
                        <span class="fa fa-trash"></span>
                        <span style="padding-top:2px">&nbsp;&nbsp;删&nbsp;除&nbsp;任&nbsp;务&nbsp;&nbsp;</span>
                    </button>--%>
                </div>
                <table id="table"></table>
            </div>
        </div>
    </div>
</div>


<script>
    var TaskIndex = {
        $table : $('#table'),
        index:null,
    };
    var refreshs = top.EHM.Cache["refreshs"] = function(){
        location.reload();
    }
    $(function () {
        initTable();

    });
    function onloadFun(){
        TaskIndex.$table.bootstrapTable('resetView', {
            height: getHeight(0.95)
        });
    }

    function initTable() {
        TaskIndex.$table.bootstrapTable({
            method: 'get',
            contentType: "application/x-www-form-urlencoded",
            url: '${basePath}/system/task/list',
            height: getHeight(0.95),
            striped: true,
            search: true,
            showRefresh: true,
            showColumns: true,//
            minimumCountColumns: 2,
            clickToSelect: true,
            detailView: true,
            detailFormatter: 'detailFormatter',
            pagination: true,
            paginationLoop: false,
            sidePagination: 'server',
            silentSort: false,
            smartDisplay: false,
            escape: true,
            searchOnEnterKey: true,
            idField: 'taskId',
            maintainSelected: true,
            toolbar: '#toolbar',
            pageList: [20, 50, 100],
            pageSize: 20,
            columns: [
                {field: 'ck', checkbox: true},
                {field: 'name', title: '名称', align: 'center'},
                {field: 'typeName', title: '任务类型', align: 'center'},
                {field: 'taskTime', title: '任务时间', align: 'center'},
                {field: 'state', title: '任务状态', align: 'center', formatter: fmtType},
                {field: 'nickName', title: '任务人', align: 'center'},
                {field: 'action', title: '操作', align: 'center', events: 'actionEvents', clickToSelect: false,width:'20%',
                    formatter: function(value, row, index) {
                        return [
                            '<a class="update" href="javascript:;" onclick="update(\''+row.taskId+'\')" data-toggle="tooltip" title="修改"><i class="fa fa-edit"></i></a>　',
                            '<a class="delete" href="javascript:;" onclick="deleteOne(\''+row.taskId+'\')" data-toggle="tooltip" title="删除"><i class="fa fa-trash"></i></a>'
                        ].join('');
                    }}
            ]
        });
    }
    function fmtType(value, row, index){
        var value =row.state;
        var lastValue;
        if (value=="1"){
            lastValue='<span class="label bg-green">开启</span>';
        }else if (value=="2"){
            lastValue='<span class="label bg-red">关闭</span>';
        }
        return lastValue;
    }
    function add() {
        top.layer.open({
            type: 2,
            title: '添加任务',
            shadeClose: false,
            shade: 0.4,
            maxmin: true,
            area: ['50%', '60%'],
            content: '${basePath}/system/task/edit/0/create'
        });
    }
    function update(id) {
        top.layer.open({
            type: 2,
            title: '修改任务',
            shadeClose: false,
            shade: 0.4,
            maxmin: true,
            area: ['50%', '60%'],
            content: '${basePath}/system/task/edit/'+id+'/update'
        });
    }
    function detailFormatter(index, row) {
        var html = [];
        $.each(row, function (key, value) {
            if(isEmpty(value)){
                value="";
            }
            if(key=='taskId'){
                html.push('<p><b>id:</b> ' + value + '</p>');
            }
            if(key=='taskClass'){
                html.push('<p><b>任务执行类:</b> ' + value + '</p>');
            }
            if(key=='cron'){
                html.push('<p><b>CRON表达式:</b> ' + value + '</p>');
            }
            if(key=='editDate'){
                html.push('<p><b>修改时间:</b> ' + new Date(value).format("yyyy-MM-dd HH:mm:ss") + '</p>');
            }


        });
        return html.join('');
    }

    function removeAll() {
        var rows = TaskIndex.$table.bootstrapTable('getSelections');
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
            ids.push(rows[i].taskId);
        }

        var i =layer.confirm('确认删除这些任务吗？', {
            shade: 0.4,
            btn: ['确认','取消'] //按钮
        }, function(){
            $.ajax({
                type: 'post',
                url: '${basePath}/system/task/deletes',
                data: {_method:"DELETE",ids:ids.join("-")},
                beforeSend: function() {
                    TaskIndex.index = layer.load(1, {

                    });
                },
                success: function(result) {
                    if (result.code == 200) {
                        TaskIndex.$table.bootstrapTable("refresh");
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
                    layer.close(TaskIndex.index);
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
                url: '${basePath}/system/task/delete/'+id,
                data: {_method:"DELETE"},
                beforeSend: function() {
                    TaskIndex.index = layer.load(1, {

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
                    layer.close(TaskIndex.index);
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
