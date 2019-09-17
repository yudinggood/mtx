<!--system/admin/profile.jsp-->
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
        EHM.ImportBootStrapTab();
    </script>
</head>
<body >

<div class="easyui-layout" fit="true" scroll="no" >

    <div region="center" style="height: 100%;overflow: hidden;">
        <div class="easyui-panel" style="padding:0px;border:0px" fit="true" border="false" id="logListpanel">
            <div id="tabbarobj" style="margin:5px auto;padding-top: 8px;width:98%;height:90%"></div>
        </div>
    </div>
</div>




<script>
    var Profile = {
        tabData : []
    };
    $(function () {
        doOnLoad();
        BootStrapTab.createTab("tabbarobj", {
            tabs: Profile.tabData
        });

    });
    function doOnLoad() {
        Profile.tabData.push({
            id: "summary",
            title: "个人信息",
            active: "active",
            url: '${basePath}/system/userInfo',
            content: "con1",
            iframeLoad: "iframes-on-demand",
            onchecked: function () {
            }
        });
        Profile.tabData.push({
            id: "summary2",
            title: "账号绑定",
            url: '${basePath}/system/userBind',
            content: "con1",
            iframeLoad: "iframes-on-demand",
            onchecked: function () {
            }
        });
        Profile.tabData.push({
            id: "summary3",
            title: "修改密码",
            url: '${basePath}/system/userPwd',
            content: "con1",
            iframeLoad: "iframes-on-demand",
            onchecked: function () {
            }
        });
    }

</script>
</body>
</html>
