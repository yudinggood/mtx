<!--system/user/user_index.jsp-->
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
        EHM.ImportZtree();
        EHM.ImportLayer();
    </script>
</head>
<body onresize="onloadFun()">

<div class="easyui-layout" fit="true" scroll="no">

    <div data-options="region:'west'" style="width:20%;">

        <div class="easyui-panel" data-options="title:'组织树',border:false,fit:true">
            <ul id="tree" class="ztree"></ul>
        </div>

    </div>
    <div region="center" title="用户信息" >
        <div class="easyui-panel" style="padding:0px;border:0px" fit="true" border="false" id="userListpanel"></div>
    </div>


</div>


<script>
    var UserIndex = {
        ztree: null,
    };
    var refreshs = top.EHM.Cache["refreshs"] = function(){
        UserList.$table.bootstrapTable("refresh");
    }
    $(function () {
        initTree();

    });
    function initTree(){
        $.ajax({
            type: 'get',
            url: '${basePath}/system/org/tree',
            data: {},
            success: function(result) {
                setTree(result.result);
            },
            error: function(XMLHttpRequest, textStatus, errorThrown) {
                var i =layer.confirm(textStatus, {
                    shade: 0,
                    btn: ['确认']
                }, function(){
                    layer.close(i);
                });
            }
        });
    }
    function setTree(data){
        var zn = JSON.parse(data);
        zn = getRealZn(zn);
        var setting = {
            view: {
                dblClickExpand: true,//双击展开
                showLayer: false,
                selectedMulti: false//是否允许多选
            },
            data: {
                simpleData: {
                    enable: true
                }
            } ,
            callback: {
                onClick: zTreeOnClick
            }
        };
        $.fn.zTree.init($("#tree"), setting,zn);
        var treeObj = $.fn.zTree.getZTreeObj("tree");
        treeObj.expandAll(true);
        //默认点击第一个节点
        var node = treeObj.getNodes()[0];
        treeObj.selectNode(node);
        setting.callback.onClick(null, treeObj.setting.treeId, node);
    }
    function zTreeOnClick(event, treeId, treeNode){
        $('#userListpanel').panel("refresh", "${basePath}/system/user/view/"+treeNode.id);
    }
    function getRealZn(zn) {
        $.each(zn,function(i,item){
            if(item.pId=="0"){
                item.icon="${basePath}/resources/mtx-admin/image/icon/tree_system.png";
            }else {
                item.icon="${basePath}/resources/mtx-admin/image/icon/tree_org.png";
            }

        });
        return zn;
    }

</script>
</body>
</html>
