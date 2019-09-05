<!--system/user/user_list.jsp-->
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
<body >

<div class="easyui-layout" fit="true" scroll="no">

    <div region="center" >
        <div class="easyui-panel" style="padding:0px;border:0px" fit="true" border="false" id="roleListpanel">
            <div class="margin">
                <div class="" id="toolbar">
                    <button type="button"
                            title="添加用户"
                            class="btn btn-success btn-xs"
                            onClick="add()">
                        <span class="fa fa-plus"></span>
                        <span style="padding-top:2px">&nbsp;添&nbsp;加&nbsp;用&nbsp;户&nbsp;</span>
                    </button>
                    <button type="button"
                            title="删除用户"
                            class="btn btn-danger btn-xs"
                            onClick="removeAll()">
                        <span class="fa fa-trash"></span>
                        <span style="padding-top:2px">&nbsp;&nbsp;删&nbsp;除&nbsp;用&nbsp;户&nbsp;&nbsp;</span>
                    </button>
                    <%--<button type="button"
                            title="用户权限"
                            class="btn btn-info btn-xs"
                            onClick="permissions()">
                        <span class="fa fa-key"></span>
                        <span style="padding-top:2px">&nbsp;&nbsp;用&nbsp;户&nbsp;权&nbsp;限&nbsp;&nbsp;</span>
                    </button>--%>
                </div>
                <table id="table"></table>
            </div>
        </div>
    </div>

</div>


<script>
    var UserList = {
        $table : $('#table'),
        index:null,
    };
    var refreshs = top.EHM.Cache["refreshs"] = function(){
        UserList.$table.bootstrapTable("refresh");
    }
    function onloadFun(){
        UserList.$table.bootstrapTable('resetView', {
            height: getHeight(0.92)
        });
    }
    $(function () {
        initTable();

    });

    function initTable() {
        UserList.$table.bootstrapTable({
            url: '${basePath}/system/user/list',
            height: getHeight(0.92),
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
            idField: 'userId',
            maintainSelected: true,
            toolbar: '#toolbar',
            pageList: [20, 50, 100],
            pageSize: 20,
            queryParams: function queryParams(params) {
                params['organizationId']='${organizationId}';
                return params;
            },
            columns: [
                {field: 'ck', checkbox: true},
                {field: 'nickName', title: '昵称'},
                {field: 'phone', title: '手机号'},
                {field: 'email', title: '邮箱'},
                {field: 'lastIp', title: '最后登录IP'},
                {field: 'lastLogin', title: '最后登录时间',formatter: function(value, row, index) {
                    if(isEmpty(value)){
                        return value;
                    }
                    return new Date(value).format("yyyy-MM-dd HH:mm:ss");
                }},
                {field: 'userState', title: '状态' ,sortable:true,formatter: function(value, row, index) {
                    if (value == 1) {
                        return '<span class="label label-success">正常</span>';
                    } else if(value == 2){
                        return '<span class="label label-default">封禁</span>';
                    }else {
                        return '<span class="label label-warning">未激活</span>';
                    }
                }},
                {field: 'action', title: '操作', align: 'center', events: 'actionEvents', clickToSelect: false,width:'20%',
                    formatter: function(value, row, index) {
                        return [
                            '<a class="update" href="javascript:;" onclick="update(\''+row.userId+'\')" data-toggle="tooltip" title="修改"><i class="fa fa-edit"></i></a>　',
                            '<a class="delete" href="javascript:;" onclick="deleteOne(\''+row.userId+'\')" data-toggle="tooltip" title="删除"><i class="fa fa-trash"></i></a>',
                            '<a class="update" style="margin-left:17px;" href="javascript:;" onclick="view(\''+row.userId+'\')" data-toggle="tooltip" title="查看"><i class="fa fa-globe"></i></a>'

                        ].join('');
                    }}
            ]
        });
    }
    function add() {
        top.layer.open({
            type: 2,
            title: '添加用户',
            shadeClose: false,
            shade: 0.4,
            maxmin: true,
            area: ['50%', '60%'],
            content: '${basePath}/system/user/edit/0/create'
        });
    }
    function removeAll() {
        var rows = UserList.$table.bootstrapTable('getSelections');
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

        var i =layer.confirm('确认删除这些角色吗？', {
            shade: 0.4,
            btn: ['确认','取消'] //按钮
        }, function(){
            $.ajax({
                type: 'post',
                url: '${basePath}/system/user/deletes',
                data: {_method:"DELETE",ids:ids.join("-")},
                beforeSend: function() {
                    UserList.index = layer.load(1, {

                    });
                },
                success: function(result) {
                    if (result.code == 200) {
                        UserList.$table.bootstrapTable("refresh");
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
                    layer.close(UserList.index);
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

    function update(id) {
        top.layer.open({
            type: 2,
            title: '修改用户',
            shadeClose: false,
            shade: 0.4,
            maxmin: true,
            area: ['50%', '60%'],
            content: '${basePath}/system/user/edit/'+id+'/update'
        });
    }
    function deleteOne(id) {
        var i =layer.confirm('确认删除该用户吗？', {
            shade: 0.4,
            btn: ['确认','取消'] //按钮
        }, function(){
            $.ajax({
                type: 'post',
                url: '${basePath}/system/user/delete/'+id,
                data: {_method:"DELETE"},
                beforeSend: function() {
                    UserList.index = layer.load(1, {

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
                    layer.close(UserList.index);
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
    function view(id) {
        top.layer.open({
            type: 2,
            title: '查看用户',
            shadeClose: false,
            shade: 0.4,
            maxmin: true,
            area: ['50%', '60%'],
            content: '${basePath}/system/user/detail/'+id
        });
    }
</script>
</body>
</html>
