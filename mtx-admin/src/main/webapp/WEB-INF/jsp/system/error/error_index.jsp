<!--system/error/error_index.jsp-->
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
        EHM.ImportSelect();
        EHM.ImportDateRangepicker();
    </script>
</head>
<body onresize="onloadFun()">

<div class="easyui-layout" fit="true" scroll="no" >

    <div region="center" style="height: 100%;overflow: hidden;">
        <div class="easyui-panel" style="padding:0px;border:0px" fit="true" border="false" id="roleListpanel">
            <div class="margin" >

                <div class="" id="" style="">
                    <button type="button"
                            title="删除日志"
                            class="btn btn-danger btn-xs"
                            onClick="removeAll()">
                        <span class="fa fa-trash"></span>
                        <span style="padding-top:2px">&nbsp;&nbsp;删&nbsp;除&nbsp;日&nbsp;志&nbsp;&nbsp;</span>
                    </button>

                </div>
            </div>
            <div class="margin" style="">
                <div class="btn-group pull-left" style="position:relative;top:8px;margin-right:20px;">
                    <select class="select2" name="errorType" id="errorType" style="width: 150px;">
                        <option value="" style="">--请选择--</option>
                        <c:forEach items="${typeSelectVO}" var="selectVo">
                            <option value="${selectVo.dicCode }">${selectVo.dicName }</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="btn-group pull-left" style="position:relative;top:8px;">
                    <input onfocus="this.blur();" id="editDate"  placeholder="时间段搜索..." class="form-control" style="position:relative;top:-2px;">
                </div>
            <table id="table"></table>
            </div>
        </div>
    </div>
</div>


<script>
    var ErrorIndex = {
        $table : $('#table'),
        index:null,
    };
    $(function () {
        $("#editDate").daterangepicker({
            showDropdowns: false,
            ranges: {
                '今日': [moment().startOf('day'), moment()],
                '昨天': [moment().subtract('days', 1).startOf('day'), moment().subtract('days', 1).endOf('day')],
                '最近7日': [moment().subtract('days', 6), moment()],
                '最近30天': [moment().subtract('days', 29), moment()]
            },
            alwaysShowCalendars: true,
            format: "YYYY-MM-DD",
            opens: "right",
            buttonClasses: ['btn btn-primary'],
            applyClass: 'btn-small btn-success',
            cancelClass: 'btn-small btn-primary',
            locale: {
                applyLabel: '确认',
                cancelLabel: '取消',
                fromLabel: '开始',
                toLabel: '结束',
                customRangeLabel: '自定义'
            }
        });
        $('#editDate').on('cancel.daterangepicker', function (ev, picker) {
            $('#editDate').val('');
            ErrorIndex.$table.bootstrapTable("refresh");
        });
        $('#editDate').on('apply.daterangepicker', function (ev, picker) {
            ErrorIndex.$table.bootstrapTable("refresh");
        });


        $(".select2").select2();
        initTable();
        $("body").keydown(function() {
            var event=arguments.callee.caller.arguments[0]||window.event;
            if (event.keyCode == "13") {//keyCode=13是回车键
                ErrorIndex.$table.bootstrapTable("refresh");
            }
        });
    });
    function onloadFun(){
        ErrorIndex.$table.bootstrapTable('resetView', {
            height: getHeight(0.92)
        });
    }
    function initTable() {
        ErrorIndex.$table.bootstrapTable({
            url: '${basePath}/system/error/list',
            height: getHeight(0.92),
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
            idField: 'errorId',
            maintainSelected: true,
            toolbar: '#toolbar',
            pageList: [20, 50, 100],
            pageSize: 20,
            queryParams: function queryParams(params) {
                params['errorType']=$("#errorType").val();
                params['editDate']=$("#editDate").val();
                return params;
            },
            columns: [
                {field: 'ck', checkbox: true},
                {field: 'id', title: '编号',width:'2%',formatter: function(value, row, index) {
                    //获取每页显示的数量
                    var pageSize=ErrorIndex.$table.bootstrapTable('getOptions').pageSize;
                    //获取当前是第几页
                    var pageNumber=ErrorIndex.$table.bootstrapTable('getOptions').pageNumber;
                    //返回序号，注意index是从0开始的，所以要加上1
                    return pageSize * (pageNumber - 1) + index + 1;
                }},
                {field: 'errorType', title: '错误类型',width:'3%',formatter: function(value, row, index) {
                    if (value == '1') {
                        return '<span class="label label-primary">登录日志</span>';
                    } else if(value == '2'){
                        return '<span class="label label-danger">异常日志</span>';
                    } else if(value == '3'){
                        return '<span class="label label-warning">业务异常</span>';
                    }
                }},
                {field: 'className', title: '错误位置',formatter: function(value, row, index) {
                    if(null==value){
                        return null;
                    }
                    return value+"."+row.method;
                }},
                {field: 'code', title: '错误编码',formatter: function(value, row, index) {
                    return value!=null?value.substr(0,50):null;
                }},
                {field: 'editDate', title: '错误时间',formatter: function(value, row, index) {
                    return new Date(value).format("yyyy-MM-dd HH:mm:ss");
                }},
                {field: 'nickName', title: '用户'},
                {field: 'action', title: '操作', align: 'center', events: 'actionEvents', clickToSelect: false,width:'5%',
                    formatter: function(value, row, index) {
                        return [
                            '<a class="delete" href="javascript:;" onclick="deleteOne(\''+row.errorId+'\')" data-toggle="tooltip" title="删除"><i class="fa fa-trash"></i></a>'
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
            if(key=='code'){
                html.push('<p><b>错误编码:</b> ' + value + '</p>');
            }
            if(key=='message'){
                html.push('<p><b>详细信息:</b> ' + value + '</p>');
            }


        });
        return html.join('');
    }
    function removeAll() {
        var rows = ErrorIndex.$table.bootstrapTable('getSelections');
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
            ids.push(rows[i].errorId);
        }

        var i =layer.confirm('确认删除这些日志吗？', {
            shade: 0.4,
            btn: ['确认','取消'] //按钮
        }, function(){
            $.ajax({
                type: 'post',
                url: '${basePath}/system/error/deletes',
                data: {_method:"DELETE",ids:ids.join("-")},
                beforeSend: function() {
                    ErrorIndex.index = layer.load(1, {

                    });
                },
                success: function(result) {
                    if (result.code == 200) {
                        ErrorIndex.$table.bootstrapTable("refresh");
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
                    layer.close(ErrorIndex.index);
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
                url: '${basePath}/system/error/delete/'+id,
                data: {_method:"DELETE"},
                beforeSend: function() {
                    ErrorIndex.index = layer.load(1, {

                    });
                },
                success: function(result) {
                    if (result.code == 200) {
                        ErrorIndex.$table.bootstrapTable("refresh");
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
                    layer.close(ErrorIndex.index);
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
