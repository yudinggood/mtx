<!--system/role/role_index.jsp-->
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


<div class="easyui-layout" fit="true" scroll="no">

    <div region="center" >
        <div class="easyui-panel" style="padding:0px;border:0px" fit="true" border="false" id="roleListpanel">
            <div class="margin">
            <div class="" id="toolbar">
                <button type="button"
                        title="添加角色"
                        class="btn btn-success btn-xs"
                        onClick="add()">
                    <span class="fa fa-plus"></span>
                    <span style="padding-top:2px">&nbsp;添&nbsp;加&nbsp;角&nbsp;色&nbsp;</span>
                </button>
                <button type="button"
                        title="删除角色"
                        class="btn btn-danger btn-xs"
                        onClick="removeAll()">
                    <span class="fa fa-trash"></span>
                    <span style="padding-top:2px">&nbsp;&nbsp;删&nbsp;除&nbsp;角&nbsp;色&nbsp;&nbsp;</span>
                </button>
                <button type="button"
                        title="角色权限"
                        class="btn btn-info btn-xs"
                        onClick="permissions()">
                    <span class="fa fa-key"></span>
                    <span style="padding-top:2px">&nbsp;&nbsp;角&nbsp;色&nbsp;权&nbsp;限&nbsp;&nbsp;</span>
                </button>
            </div>
            <table id="table"></table>
            </div>
        </div>
    </div>

</div>


<script>
    var RoleIndex = {
        $table : $('#table'),
        index:null,
        roleId:0,
    };
    var refreshs = top.EHM.Cache["refreshs"] = function(){
        RoleIndex.$table.bootstrapTable("refresh");
    }
    function onloadFun(){
        RoleIndex.$table.bootstrapTable('resetView', {
            height: getHeight(0.95)
        });
    }
    $(function () {
        initTable();

        $("#table").on('load-success.bs.table', function (e, name, args) {
            $('input[type="checkbox"].flat-red, input[type="radio"].flat-red').iCheck({//icheck
                checkboxClass: 'icheckbox_flat-green',
                radioClass: 'iradio_flat-green'
            });
            $('input[name="allcheck"]').on('ifChanged', function(event){
                if ($('input[name="allcheck"]').is(':checked')){
                    $('input[name="devCks"]').iCheck('check');
                }else {
                    $('input[name="devCks"]').iCheck('uncheck');
                }
            });
        });
    });

    function initTable() {
        RoleIndex.$table.bootstrapTable({
            url: '${basePath}/system/role/list',
            height: getHeight(0.95),
            striped: true,
            search: true,
            showRefresh: true,
            showColumns: false,//
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
            idField: 'roleId',
            maintainSelected: true,
            toolbar: '#toolbar',
            pageList: [20, 50, 100],
            pageSize: 20,
            columns: [
                {title:"<input type='checkbox' class='flat-red' name='allcheck' value='0'>", width:"1%",
                    formatter: function(value, row, index) {
                        var str = "<input type='checkbox' class='flat-red' name='devCks' value='"+row.roleId+"' style=''>";
                        return str;
                    }},
                {field: 'roleId', title: '编号',width:'10%'},
                {field: 'roleName', title: '角色名称'},
                {field: 'roleCode', title: '角色编码'},
                {field: 'description', title: '角色描述'},
                {field: 'action', title: '操作', align: 'center', events: 'actionEvents', clickToSelect: false,width:'20%',
                    formatter: function(value, row, index) {
                        return [
                            '<a class="update" href="javascript:;" onclick="update(\''+row.roleId+'\')" data-toggle="tooltip" title="修改"><i class="fa fa-edit"></i></a>　',
                            '<a class="delete" href="javascript:;" onclick="deleteOne(\''+row.roleId+'\')" data-toggle="tooltip" title="删除"><i class="fa fa-trash"></i></a>'
                        ].join('');
                    }}
            ]
        });
    }

    // 数据表格展开内容
    function detailFormatter(index, row) {
        var html = [];
        $.each(row, function (key, value) {
            if(isEmpty(value)){
                value="";
            }
            if(key=='description'){
                html.push('<p><b>角色描述:</b> ' + value + '</p>');
            }
            if(key=='editDate'){
                html.push('<p><b>修改时间:</b> ' + new Date(value).format("yyyy-MM-dd HH:mm:ss") + '</p>');
            }
            if(key=='editUser'){
                html.push('<p><b>修改人:</b> ' + value + '</p>');
            }

        });
        return html.join('');
    }

    function add(){
        top.layer.open({
            type: 2,
            title: '添加角色',
            shadeClose: false,
            shade: 0.4,
            maxmin: true,
            area: ['50%', '60%'],
            content: '${basePath}/system/role/edit/0/create'
        });

    }
    function update(id) {
        top.layer.open({
            type: 2,
            title: '修改角色',
            shadeClose: false,
            shade: 0.4,
            maxmin: true,
            area: ['50%', '60%'],
            content: '${basePath}/system/role/edit/'+id+'/update'
        });
    }
    function deleteOne(id) {
        var i =layer.confirm('确认删除该角色吗？', {
            shade: 0.4,
            btn: ['确认','取消'] //按钮
        }, function(){
            $.ajax({
                type: 'post',
                url: '${basePath}/system/role/delete/'+id,
                data: {_method:"DELETE"},
                beforeSend: function() {
                    RoleIndex.index = layer.load(1, {

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
                    layer.close(RoleIndex.index);
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
        var neArr = getCheckedNe();
        if(neArr.length==0){
            var j =layer.confirm('请选择要删除的内容', {
                shade: 0,
                btn: ['确认']
            }, function(){
                layer.close(j);
            });
            return ;
        }

        var i =layer.confirm('确认删除这些角色吗？', {
            shade: 0.4,
            btn: ['确认','取消'] //按钮
        }, function(){
            $.ajax({
                type: 'post',
                url: '${basePath}/system/role/deletes',
                data: {_method:"DELETE",ids:neArr.join("-")},
                beforeSend: function() {
                    RoleIndex.index = layer.load(1, {

                    });
                },
                success: function(result) {
                    if (result.code == 200) {
                        RoleIndex.$table.bootstrapTable("refresh");
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
                    layer.close(RoleIndex.index);
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
    function permissions() {
        var neArr = getCheckedNe();
        if (neArr.length==0||neArr.length>1) {
            var j =layer.confirm('请选择一条记录', {
                shade: 0,
                btn: ['确认']
            }, function(){
                layer.close(j);
            });
        }
        else {
            RoleIndex.roleId = neArr[0];
            top.layer.open({
                type: 2,
                title: '角色权限修改',
                shadeClose: false,
                shade: 0.4,
                maxmin: true,
                area: ['15%', '60%'],
                content: '${basePath}/system/role/permission/'+RoleIndex.roleId ,
            });
        }
    }
</script>
</body>
</html>
