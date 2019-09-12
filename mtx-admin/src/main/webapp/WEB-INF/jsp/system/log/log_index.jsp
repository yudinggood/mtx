<!--system/log/log_index.jsp-->
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
        <div class="easyui-panel" style="padding:0px;border:0px" fit="true" border="false" id="logListpanel">
            <div class="margin">
                <div class="" id="toolbar">
                    <button type="button"
                            title="删除日志"
                            class="btn btn-danger btn-xs"
                            onClick="removeAll()">
                        <span class="fa fa-trash"></span>
                        <span style="padding-top:2px">&nbsp;&nbsp;删&nbsp;除&nbsp;日&nbsp;志&nbsp;&nbsp;</span>
                    </button>
                </div>
                <table id="table"></table>
            </div>
        </div>
    </div>
</div>


<script>
    var LogIndex = {
        $table : $('#table'),
        index:null,
    };
    $(function () {
        initTable();


    });
    function onloadFun(){
        LogIndex.$table.bootstrapTable('resetView', {
            height: getHeight(0.95)
        });
    }

    function initTable() {
        LogIndex.$table.bootstrapTable({
            method: 'get',
            contentType: "application/x-www-form-urlencoded",
            url: '${basePath}/system/log/list',
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
            idField: 'logId',
            maintainSelected: true,
            toolbar: '#toolbar',
            pageList: [20, 50, 100],
            pageSize: 20,
            columns: [
                {field: 'ck', checkbox: true},
                {field: 'id', title: '编号',width:'2%',formatter: function(value, row, index) {
                    //获取每页显示的数量
                    var pageSize=LogIndex.$table.bootstrapTable('getOptions').pageSize;
                    //获取当前是第几页
                    var pageNumber=LogIndex.$table.bootstrapTable('getOptions').pageNumber;
                    //返回序号，注意index是从0开始的，所以要加上1
                    return pageSize * (pageNumber - 1) + index + 1;
                }},
                {field: 'description', title: '日志描述'},
                {field: 'method', title: '类型',width:'3%',formatter: function(value, row, index) {
                    if (value == 'GET') {
                        return '<span class="label label-primary">GET</span>';
                    } else if(value == 'PUT'){
                        return '<span class="label label-warning">PUT</span>';
                    } else if(value == 'POST'){
                        return '<span class="label label-success">POST</span>';
                    } else if(value == 'DELETE'){
                        return '<span class="label label-danger">DELETE</span>';
                    }
                }},
                {field: 'uri', title: '路径' ,formatter: function(value, row, index) {
                    return row.domain+value;
                }},
                {field: 'parameter', title: '请求参数'},
                {field: 'permission', title: '权限值'},
                {field: 'usedTime', title: '消耗时间',sortable: true,formatter: function(value, row, index) {
                    return value+" ms";
                }},
                {field: 'action', title: '操作', align: 'center', events: 'actionEvents', clickToSelect: false,width:'5%',
                    formatter: function(value, row, index) {
                        return [
                            '<a class="delete" href="javascript:;" onclick="deleteOne(\''+row.logId+'\')" data-toggle="tooltip" title="删除"><i class="fa fa-trash"></i></a>'
                        ].join('');
                    }}
            ]
        });
    }
    function detailFormatter(index, row) {
        var html = [];
        $.each(row, function (key, value) {
            if(isEmpty(value)){
                value="";
            }
            if(key=='userAgent'){
                html.push('<p><b>用户标识:</b> ' + value + '</p>');
            }
            if(key=='ip'){
                html.push('<p><b>用户IP:</b> ' + value + '</p>');
            }
            if(key=='result'){
                html.push('<p><b>响应结果:</b> ' + value + '</p>');
            }
            if(key=='editDate'){
                html.push('<p><b>修改时间:</b> ' + new Date(value).format("yyyy-MM-dd HH:mm:ss") + '</p>');
            }
            if(key=='nickName'){
                html.push('<p><b>修改人:</b> ' + value + '</p>');
            }

        });
        return html.join('');
    }
    function removeAll() {
        var rows = LogIndex.$table.bootstrapTable('getSelections');
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
            ids.push(rows[i].logId);
        }

        var i =layer.confirm('确认删除这些日志吗？', {
            shade: 0.4,
            btn: ['确认','取消'] //按钮
        }, function(){
            $.ajax({
                type: 'post',
                url: '${basePath}/system/log/deletes',
                data: {_method:"DELETE",ids:ids.join("-")},
                beforeSend: function() {
                    LogIndex.index = layer.load(1, {

                    });
                },
                success: function(result) {
                    if (result.code == 200) {
                        LogIndex.$table.bootstrapTable("refresh");
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
                    layer.close(LogIndex.index);
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
        var i =layer.confirm('确认删除该日志吗？', {
            shade: 0.4,
            btn: ['确认','取消'] //按钮
        }, function(){
            $.ajax({
                type: 'post',
                url: '${basePath}/system/log/delete/'+id,
                data: {_method:"DELETE"},
                beforeSend: function() {
                    LogIndex.index = layer.load(1, {

                    });
                },
                success: function(result) {
                    if (result.code == 200) {
                        LogIndex.$table.bootstrapTable("refresh");
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
                    layer.close(LogIndex.index);
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
