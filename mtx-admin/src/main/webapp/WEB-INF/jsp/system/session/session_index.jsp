<!--system/test/test.jsp-->
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
        <div class="easyui-panel" style="padding:0px;border:0px" fit="true" border="false" id="roleListpanel">

            <div class="margin" style="">
                <table id="table"></table>
            </div>
        </div>
    </div>
</div>


<script>
    var SessionIndex = {
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
        SessionIndex.$table.bootstrapTable('resetView', {
            height: getHeight(0.95)
        });
    }

    function initTable() {
        SessionIndex.$table.bootstrapTable({
            method: 'get',
            contentType: "application/x-www-form-urlencoded",
            url: '${basePath}/system/session/list',
            height: getHeight(0.95),
            striped: true,
            search: false,
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
            idField: 'id',
            maintainSelected: true,
            toolbar: '#toolbar',
            pageList: [20, 50, 100],
            pageSize: 20,
            columns: [
                {field: 'ck', checkbox: true},
                {field: 'id', title: '编号',width:'2%',formatter: function(value, row, index) {
                    //获取每页显示的数量
                    var pageSize=SessionIndex.$table.bootstrapTable('getOptions').pageSize;
                    //获取当前是第几页
                    var pageNumber=SessionIndex.$table.bootstrapTable('getOptions').pageNumber;
                    //返回序号，注意index是从0开始的，所以要加上1
                    return pageSize * (pageNumber - 1) + index + 1;
                }},
                {field: 'id', title: 'id', align: 'center'},
                {field: 'startTimestamp', title: '创建时间',formatter: function(value, row, index) {
                    return new Date(value).format("yyyy-MM-dd HH:mm:ss");
                }},
                {field: 'lastAccessTime', title: '最后访问时间',formatter: function(value, row, index) {
                    return new Date(value).format("yyyy-MM-dd HH:mm:ss");
                }},
                {field: 'expired', title: '是否过期',width:'3%',formatter: function(value, row, index) {
                    if (value == true) {
                        return '<span class="label label-danger">是</span>';
                    } else {
                        return '<span class="label label-success">否</span>';
                    }
                }},
                {field: 'host', title: '访问者IP', align: 'center'},
                {field: 'status', title: '状态', align: 'center', formatter: 'statusFormatter'},
                {field: 'action', title: '操作', align: 'center', events: 'actionEvents', clickToSelect: false,width:'5%',
                    formatter: function(value, row, index) {
                        return [
                            '<a class="delete" href="javascript:;" onclick="focusLogout(\''+row.id+'\')" data-toggle="tooltip" title="强制退出"><i class="glyphicon glyphicon-log-out"></i></a>'
                        ].join('');
                    }}
            ]
        });
    }
    function statusFormatter(value, row, index) {
        if (value == 'on_line') {
            return '<span class="label label-success">在线</span>';
        }
        if (value == 'off_line') {
            return '<span class="label label-default">离线</span>';
        }
        if (value == 'force_logout') {
            return '<span class="label label-danger">踢离</span>';
        }
    }
    function detailFormatter(index, row) {
        var html = [];
        $.each(row, function (key, value) {
            if(isEmpty(value)){
                value="";
            }
            if(key=='id'){
                html.push('<p><b>id:</b> ' + value + '</p>');
            }
            if(key=='userAgent'){
                html.push('<p><b>用户标识:</b> ' + value + '</p>');
            }
            if(key=='startTimestamp'){
                html.push('<p><b>创建时间:</b> ' + new Date(value).format("yyyy-MM-dd HH:mm:ss") + '</p>');
            }
            if(key=='lastAccessTime'){
                html.push('<p><b>最后访问时间:</b> ' + new Date(value).format("yyyy-MM-dd HH:mm:ss") + '</p>');
            }



        });
        return html.join('');
    }
    
    function focusLogout(id) {
        var i =layer.confirm('确认强制退出该用户吗？', {
            shade: 0.4,
            btn: ['确认','取消'] //按钮
        }, function(){
            $.ajax({
                type: 'get',
                url: '${basePath}/system/session/forceout/'+id,
                beforeSend: function() {
                    SessionIndex.index = layer.load(1, {

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
                    layer.close(SessionIndex.index);
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
