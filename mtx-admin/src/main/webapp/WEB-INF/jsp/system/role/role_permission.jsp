<!--system/role/role_permission.jsp-->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags"  prefix="spring" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="../../base/base.jsp"%>


    <style>

    </style>
    <script>
        EHM.ImportLayer();
        EHM.ImportEasyUI();
        EHM.ImportZtree();
    </script>
</head>
<body>


<div class="easyui-layout" fit="true" scroll="no">

    <div region="center" >
        <div class="easyui-panel" style="padding:0px;border:0px;" fit="true" border="false" id="roleListpanel">
            <div class="margin " style="" >
                <ul id="tree" class="ztree" ></ul>
            </div>
        </div>
    </div>
    <div data-options="region:'south'" style="height: 15%">
        <div align="center" style="position:relative;top:15px;">
            <button type="button" title="保存" class="btn btn-success btn-xs" onClick="save()" style="width: 100px;">
                <span class="fa fa-save"></span>
                <span style="padding-top:2px">&nbsp;保&nbsp;存&nbsp;</span>
            </button>
            <button type="button" title="关闭" class="btn btn-primary btn-xs" onClick="top.layer.close(top.layer.index);"
                    style="width: 100px;">
                <span class="fa fa-close"></span>
                <span style="padding-top:2px">&nbsp;关&nbsp;闭&nbsp;</span>
            </button>
        </div>
    </div>
</div>


<script>
    var RolePermission = {
        ztree: null,
        changeDatas : [],
        index:null,
    };
    $(function () {
        initTree();

    });
    function initTree(){
        $.ajax({
            type: 'get',
            url: '${basePath}/system/permission/chooseTree/${id}',
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
            check: {
                enable: true,
                // 勾选关联父，取消关联子
                chkboxType: { "Y" : "p", "N" : "s" }
            },
            callback: {//收集数据
                onCheck: function() {
                    var zTree = $.fn.zTree.getZTreeObj("tree")
                    var changeNodes = zTree.getChangeCheckedNodes();
                    RolePermission.changeDatas = [];
                    for (var i = 0; i < changeNodes.length; i ++) {
                        var changeData = {};
                        changeData.id = changeNodes[i].id;
                        changeData.checked = changeNodes[i].checked;
                        RolePermission.changeDatas.push(changeData);
                    }
                }
            }
        };
        $.fn.zTree.init($("#tree"), setting,zn);
        var treeObj = $.fn.zTree.getZTreeObj("tree");
        treeObj.expandAll(true);

    }

    function getRealZn(zn) {
        $.each(zn,function(i,item){
            if(item.type=="0"){
                item.icon="${basePath}/resources/mtx-admin/image/icon/tree_system.png";
            }
            if(item.type=="1"){
                item.icon="${basePath}/resources/mtx-admin/image/icon/tree_category.png";
            }
            if(item.type=="2"){
                item.icon="${basePath}/resources/mtx-admin/image/icon/tree_menu.png";
            }
            if(item.type=="3"){
                item.icon="${basePath}/resources/mtx-admin/image/icon/tree_btn.png";
            }
        });
        return zn;
    }
    function save() {
        var url  = '${basePath}/system/role/savePermission/${id}';
        $.ajax({
            type: 'post',
            url: url,
            data: {datas: JSON.stringify(RolePermission.changeDatas),_method:'PUT'},
            beforeSend: function () {
                RolePermission.index = layer.load(1, {});
            },
            success: function (result) {
                if (result.code == 200) {
                    top.EHM.Cache["refreshs"]();
                    top.layer.close(top.layer.index);
                } else {
                    var i = layer.confirm(result.message, {
                        shade: 0,
                        btn: ['确认']
                    }, function () {
                        layer.close(i);
                    });
                }
            },
            complete: function (data) {
                layer.close(RolePermission.index);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                var i = layer.confirm(textStatus, {
                    shade: 0,
                    btn: ['确认'] //按钮
                }, function () {
                    layer.close(i);
                });
            }
        });
    }
</script>
</body>
</html>
