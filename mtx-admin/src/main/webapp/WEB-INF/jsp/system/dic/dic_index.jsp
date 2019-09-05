<!--system/dic/dic_index.jsp-->
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
<body>
<input type="hidden" name="keywords" id="keywords" value="" />
<div class="easyui-layout" fit="true" scroll="no">

    <div data-options="region:'west'" style="width:20%;">

        <div class="easyui-panel" data-options="title:'字典树',border:false,fit:true" id="dicTreepanel">
            <ul id="tree" class="ztree"></ul>
        </div>

    </div>
    <div region="center" title="详细信息" >
        <div class="easyui-panel" style="padding:0px;border:0px" fit="true" border="false" id="dicListpanel"></div>
    </div>


</div>



<script>
    var refreshs = top.EHM.Cache["refreshs"] = function(){
        location.reload();
    }
    var DicIndex = {
        ztree: null,
        index:null,
    };
    $(function () {
        initTree();

    });
    function initTree(val){
        $("#keywords").val(val);
        $.ajax({
            type: 'post',
            url: '${basePath}/system/dic/tree',
            data: {"keywords":val},
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
        if(isEmpty(treeNode)){
            $('#dicListpanel').panel("refresh", "${basePath}/system/dic/view/0");
        }else {
            $('#dicListpanel').panel("refresh", "${basePath}/system/dic/view/"+treeNode.id);
        }
    }

</script>
</body>
</html>
