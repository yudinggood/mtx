<!--system/admin/index.jsp-->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="/easyui-tags" %>
<%@ taglib uri="http://www.springframework.org/tags"  prefix="spring" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="../../base/base.jsp"%>
    <!-- main相关 -->
    <link rel="stylesheet" href="${basePath}/resources/mtx-admin/AdminLTE/dist/css/skins/_all-skins.min.css">
    <script src="${basePath}/resources/mtx-admin/AdminLTE/plugins/slimScroll/jquery.slimscroll.min.js"></script>
    <script src="${basePath}/resources/mtx-admin/AdminLTE/dist/js/app.min.js"></script>
    <script src="${basePath}/resources/mtx-admin/js/plugins/jquery/jquery.fullscreen.js"></script>
    <link rel="stylesheet" href="${basePath}/resources/mtx-admin/js/plugins/pace/pace-theme-minimal.css">
    <script src="${basePath}/resources/mtx-admin/js/plugins/pace/pace.js"></script>
    <%--<script src="resources/fineui/jquery-plugs/storage/jquery.storageapi.min.js"></script>--%>


    <style>

    </style>
    <script language="javascript">
        EHM.ImportSmartMenuCss();
        EHM.ImportLayer();
    </script>
</head>
<body class="hold-transition skin-blue sidebar-mini" style="overflow: hidden;" scroll="no">

<div >

    <header class="main-header">
        <!-- Logo -->
        <a href="${basePath}/system/index"  class="logo">
            <span class="logo-lg">
                <div class="back_left_logo" style="width: 100%;height: 50px;background-image: url(${basePath}/resources/mtx-admin/image/logo_f.png);background-position: left center;background-repeat: no-repeat;"></div>
            </span>
        </a>
        <nav class="navbar navbar-static-top">
            <a href="javascript:void(0);" onMouseOver="if(!this.contains(event.fromElement)){show(this);}" onMouseOut="if(!this.contains(event.toElement)){hide(this);}" class="top-menu" role="button" style=""></a>
            <a href="javascript:fullScreen('mainFrame')" class="fs-toggle" role="button"></a>
            <ul class="sysmenu" id="sysmenu" onMouseOver="if(!this.contains(event.fromElement)){show(this);}" onMouseOut="if(!this.contains(event.toElement)){hide(this);}">
                <c:forEach items="${topMenuList}" var="mList">
                        <li id="top${mList.permissionId}" class='<c:if test="${mList.permissionId == 1 }">active</c:if>' >
                            <a href="#" onclick="menuInit('${mList.permissionId }');"><i class="${mList.icon }" style="font-size:16px;"></i><p>${mList.name }</p></a>
                        </li>
                </c:forEach>
            </ul>

            <div class="navbar-custom-menu">
                <ul class="nav navbar-nav">

                    <li style="border-left: solid 1px rgb(54,127,169);" class="dropdown notifications-menu">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                            <i class="fa fa-bell-o"></i>
                            <span class="label label-warning">10</span>
                        </a>
                        <ul class="dropdown-menu">
                            <li class="header">You have 10 notifications</li>
                            <li>
                                <ul class="menu">
                                    <li>
                                        <a href="#">
                                            <i class="fa fa-users text-aqua"></i> 5 new members joined today
                                        </a>
                                    </li>

                                </ul>
                            </li>
                            <li class="footer"><a href="#">View all</a></li>
                        </ul>
                    </li>
                    <li style="border-left: solid 1px rgb(54,127,169);" class="dropdown user user-menu" id="user-menu">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                            <c:if test="${systemUser.avatar == null }">
                                <img id="headimg1" src="${basePath}/resources/mtx-admin/image/avatar5.png" class="user-image" alt="Image">
                            </c:if>
                            <c:if test="${systemUser.avatar != null }">
                                <c:if test="${systemUser.addressType == 1 }">
                                    <img id="headimg1" src="${basePath}/upload/${systemUser.avatar}" class="user-image" alt="Image">
                                </c:if>
                                <c:if test="${systemUser.addressType == 2 }">
                                    <img id="headimg1" src="${systemUser.yunPath}" class="user-image" alt="Image" onerror="this.src='${basePath}/resources/mtx-admin/image/error.png'">
                                </c:if>
                            </c:if>
                            <span class="hidden-xs" id="nickName1">${systemUser.nickName}</span>
                        </a>
                        <ul class="dropdown-menu">
                            <!-- User image -->
                            <li class="user-header">
                                <c:if test="${systemUser.avatar == null }">
                                    <img id="headimg2" src="${basePath}/resources/mtx-admin/image/avatar5.png" class="img-circle" alt="Image">
                                </c:if>
                                <c:if test="${systemUser.avatar != null }">
                                    <c:if test="${systemUser.addressType == 1 }">
                                        <img id="headimg2" src="${basePath}/upload/${systemUser.avatar}" class="img-circle" alt="Image">
                                    </c:if>
                                    <c:if test="${systemUser.addressType == 2 }">
                                        <img id="headimg2" src="${systemUser.yunPath}" class="img-circle" alt="Image" onerror="this.src='${basePath}/resources/mtx-admin/image/error.png'">
                                    </c:if>
                                </c:if>
                                <p id="nickName2">
                                    你好！${systemUser.nickName}
                                </p>
                            </li>

                            <li class="user-footer">
                                <div class="pull-left">
                                    <a href="#" onclick="personalSetting();" class="btn btn-default btn-flat"><spring:message code="index.profile"/></a>
                                </div>
                                <div class="pull-right">
                                    <a href="#" onclick="logout();" class="btn btn-default btn-flat"><spring:message code="index.logout"/></a>
                                </div>
                            </li>
                        </ul>
                    </li>
                    <li style="border-left: solid 1px rgb(54,127,169);border-right: solid 1px rgb(54,127,169);height: 50px;">
                        <a href="javascript:showAbout();">
                            <i class="icon iconfont icon-guanyu"></i>
                            <span style="padding-left: 6px">关 于</span>
                        </a>
                    </li>
                </ul>
            </div>
        </nav>
    </header>

    <div class="modal-body" style = "display : none;">
        <div style="padding-top:10px;">
            <div style="text-align: center;">
                <img src="${basePath}/resources/mtx-admin/image/logomini_2.png">
            </div>
            <div id="cpinfo" style="text-align:center">
                <p>Copyright (C) 2018-2020 浙江满天星网络技术有限公司</p>
                <p><a href='${basePath}/system/index' style="text-decoration:underline;"
                      target="_blank">http://www.mtx.com</a>
                </p>
                <p>13625538625</p>
            </div>
        </div>
    </div>

    <!-- 左侧菜单 -->
    <aside class="layout-side">
        <ul class="side-menu">
            <%--<t:menu style="fineui" menuFun="${menuMap}"></t:menu>--%>
        </ul>
    </aside>

    <!-- 右侧home -->
    <section class="layout-main">
        <div class="layout-main-tab">
            <button class="tab-btn btn-left"><i class="icon-font">&#xe628;</i></button>
            <nav class="tab-nav">
                <div class="tab-nav-content" id="tab-contents-div">
                    <div id="tytabbottomsepar" class="f-tabstrip-header-inkbar"></div>
                    <a href="javascript:void(0);" id="myhomeAtag" class="content-tab active" data-id="home.html">
                        <span class="fa fa-home colorgray"></span>首页</a>
                </div>
            </nav>

            <button id="activeTabToolRefresh" class="tab-btn mytabbtn" style="right:30px;" title="刷新本页"><i class="icon-font" style="font-size:16px;">&#xe60b;</i></button>
            <button class="tab-btn btn-right"><i class="icon-font">&#xe629;</i></button>
        </div>
        <div class="layout-main-body" style="margin:0;overflow-y: hidden;">
            <iframe class="body-iframe" name="mainFrame" id="mainFrame" width="100%" height="100%" style="display: inline;"
                    src="${basePath}${indexPage}" frameborder="0" data-id="home.html" seamless></iframe>

        </div>
    </section>


</div>

    <script language="javascript">
        EHM.ImportSmartMenu();
        var PermissionEdit = {
            initTopID:1,
            websocket:null,
        };

        $(function(){
            menuInit(PermissionEdit.initTopID);
            //刷新本页面
            $("#activeTabToolRefresh").click(function(){
                var dataId = $("ul.side-menu").find("li.active").find("a").attr("href");
                if(!dataId){
                    dataId = "home.html";
                }
                var obj = $('.body-iframe[data-id="'+dataId+'"]');
                var obj_none = obj.css('display');
                if(obj_none=='none'){
                    obj = $('.body-iframe[data-id="home.html"]');
                }
                obj.attr('src', obj.attr('src'));
            });

            $("body").css("height",document.documentElement.clientHeight);

            // 首先判断是否 支持 WebSocket
            if('WebSocket' in window) {
                PermissionEdit.websocket = new WebSocket("ws://${domain}/refreshInfo?userId=${systemUser.userId}");
            } else if('MozWebSocket' in window) {
                PermissionEdit.websocket = new MozWebSocket("ws://${domain}/refreshInfo?userId=${systemUser.userId}");
            } else {
                PermissionEdit.websocket = new SockJS("${basePath}/socketJs/refreshInfo?userId=${systemUser.userId}");
            }
            // 打开连接时
            PermissionEdit.websocket.onopen = function(evnt) {

            };

            // 收到消息时
            PermissionEdit.websocket.onmessage = function(evnt) {
                $("#nickName1").html(evnt.data);
                $("#nickName2").html(evnt.data);
            };

            PermissionEdit.websocket.onerror = function(evnt) {

            };

            PermissionEdit.websocket.onclose = function(evnt) {

            };


        });
        function show(obj){
            $("#sysmenu").addClass("open");
        }
        function hide(obj){
            $("#sysmenu").removeClass("open");
        }
        function showAbout() {
            top.layer.open({
                type: 1,
                title: 'i助理--v${systemVersion}',
                shadeClose: true,
                shade: 0.4,
                maxmin: true,
                area: ['30%', '30%'],
                offset: '25%',
                content: $(".modal-body").html()
            });

        }
        function fullScreen(id) {
            if ($.support.fullscreen) {
                var src = document.getElementById(id).contentWindow.location.href;//保留内部跳转地址
                $('#' + id).fullScreen({
                    'background': '#fff',
                    'callback': function (isFullScreen) {
                        if (document.getElementById(id).src != src) {
                            document.getElementById(id).contentWindow.location.href = src;
                        }
                    }
                });
            }
            else {
                layer.msg("您的浏览器不支持全屏显示!");
            }
        }
        function logout(){
            location.href="${basePath}/logout";
        }

        function personalSetting() {
            addOneTab('个人设置','${basePath}/system/profile','default');
        }

        function clearLocalstorage(){
            var storage=$.localStorage;
            if(!storage)
                storage=$.cookieStorage;
            storage.removeAll();
            layer.msg("浏览器缓存清除成功!");
        }

        function menuInit(nowMenuID) {
            $(".side-menu").empty(); //清空html内容都是这么写
            $("#top"+nowMenuID).addClass("active");
            $("#top"+PermissionEdit.initTopID).removeClass("active");
            PermissionEdit.initTopID=nowMenuID;
            if(nowMenuID==PermissionEdit.initTopID){
                $("#top"+nowMenuID).addClass("active");
            }//以上是top的样式判断

            var url="${basePath}/system/menu/"+nowMenuID;
            $.get(url,function(menuJson){

                var menu=null;
                var child=null;
                var num=null;
                var html=null;
                var childmenu=null;
                for (var i = 0; i < menuJson.length; i++) {
                    menu = menuJson[i];
                    num=menu.subMenu.length;
                    if(num==0){//没有子菜单的li
                        html=$('<li class="menu-item" ><a href="'+menu.uri+'"><i class="'+menu.icon+'"></i> <span class="menu-text">'+menu.name+'</span></a></li>');
                    }else{
                        html=$('<li class="menu-item" ><a href=""><i class="'+menu.icon+'"></i> <span class="menu-text">'+menu.name+'</span>' +
                            '<i class="icon-right fa fa-caret-right" ></i></a><ul menu-id="'+menu.permissionId+'" class="menu-item-child" id="menu-child-1" style="display: none;"></ul></li>');
                    }
                    $(".side-menu").append(html);
                    if(num!=0){
                        for (var j=0;j<num;j++){
                            childmenu = menu.subMenu[j];
                            child=$('<li> <a class="F_menuItem" href="'+childmenu.uri+'" ><span>'+childmenu.name+'</span></a></li>');
                        }
                        $('[menu-id="'+menu.permissionId+'"]').append(child);
                    }


                }

            });
        }
    </script>
</body>
</html>
