<!--system/test/test.jsp-->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags"  prefix="spring" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="../../base/base.jsp"%>
    <link rel="stylesheet" href="${basePath}/resources/mtx-admin/js/plugins/login/user_info.css">

    <style>
        .form-control{
            width:75%;float: left;
        }
    </style>
    <script>
        EHM.ImportLayer();
    </script>
</head>
<body style="overflow: hidden;">


<form id="form" style="margin:5px auto;padding-top: 8px;width:70%;height:90%">
    <input type="hidden" name="_method" id="_method" value="PUT"/>
    <table  class="table  table-form" align="center" style="width: 98%;">
        <div class="layui-tab-item layui-show" style="padding: 6px 25px 30px 25px;">
            <div class="bd-list">
                <div class="bd-list-item">
                    <div class="bd-list-item-img">
                        <i class="aui-phone-img" style="color: #4DAF29;font-size: 48px;"></i>
                    </div>
                    <div class="bd-list-item-content">
                        <div class="bd-list-item-lable">绑定手机</div>
                        <c:choose>
                            <c:when test="${not empty systemUser.phone}">
                                <div class="bd-list-item-text" style="color: green;">已绑定：${systemUser.phone}</div>
                            </c:when>
                            <c:otherwise>
                                <div class="bd-list-item-text" style="color: red;">当前未绑定手机</div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <c:choose>
                        <c:when test="${not empty systemUser.phone}">
                            <a class="bd-list-item-oper">修改</a>
                        </c:when>
                        <c:otherwise>
                            <a class="bd-list-item-oper">绑定</a>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="bd-list-item">
                    <div class="bd-list-item-img">
                        <i class="aui-email-img" style="color: #4DAF29;font-size: 48px;"></i>
                    </div>
                    <div class="bd-list-item-content">
                        <div class="bd-list-item-lable">绑定邮箱</div>
                        <c:choose>
                            <c:when test="${not empty systemUser.email}">
                                <div class="bd-list-item-text" style="color: green;">已绑定：${systemUser.email}</div>
                            </c:when>
                            <c:otherwise>
                                <div class="bd-list-item-text" style="color: red;">当前未绑定邮箱</div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <c:choose>
                        <c:when test="${not empty systemUser.email}">
                            <a class="bd-list-item-oper"  onclick="bindEmail()">修改</a>
                        </c:when>
                        <c:otherwise>
                            <c:choose>
                                <c:when test="${not empty systemUser.emailToken}">
                                    <a class="bd-list-item-oper" style="margin-right: 50px;" onclick="goTo()" >前往邮箱</a>
                                    <a class="bd-list-item-oper" onclick="bindEmail()" style="color: red;">绑定</a>
                                </c:when>
                                <c:otherwise>
                                    <a class="bd-list-item-oper" onclick="bindEmail()" style="color: red;">绑定</a>
                                </c:otherwise>
                            </c:choose>

                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="bd-list-item">
                    <div class="bd-list-item-img">
                        <i class="aui-qq-img" style="color: #4DAF29;font-size: 48px;"></i>
                    </div>
                    <div class="bd-list-item-content">
                        <div class="bd-list-item-lable">绑定QQ</div>
                        <c:choose>
                            <c:when test="${not empty systemUser.qqOpenId}">
                                <div class="bd-list-item-text" style="color: green;">已绑定</div>
                            </c:when>
                            <c:otherwise>
                                <div class="bd-list-item-text" style="color: red;">当前未绑定QQ</div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <c:choose>
                        <c:when test="${not empty systemUser.qqOpenId}">
                            <a class="bd-list-item-oper" style="color: green;cursor:default">已绑定</a>
                        </c:when>
                        <c:otherwise>
                            <a class="bd-list-item-oper" style="color: red;">绑定</a>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="bd-list-item">
                    <div class="bd-list-item-img">
                        <i class="aui-wx-img" style="color: #4DAF29;font-size: 48px;"></i>
                    </div>
                    <div class="bd-list-item-content">
                        <div class="bd-list-item-lable">绑定微信</div>
                        <div class="bd-list-item-text" style="color: red;">当前未绑定微信</div>
                    </div>
                    <a class="bd-list-item-oper" style="color: red;">绑定</a>
                </div>
                <div class="bd-list-item">
                    <div class="bd-list-item-img">
                        <i class="aui-wb-img" style="color: #4DAF29;font-size: 48px;"></i>
                    </div>
                    <div class="bd-list-item-content">
                        <div class="bd-list-item-lable">绑定微博</div>
                        <div class="bd-list-item-text" style="color: red;">当前未绑定微博</div>
                    </div>
                    <a class="bd-list-item-oper" style="color: red;">绑定</a>
                </div>
            </div>
        </div>
    </table>
</form>




<script>
    var UserBind = {
        index:null,
    };
    $(function () {


    });

    function bindEmail() {

        top.layer.prompt({
            btn: ['绑定邮箱'],
            shadeClose: true,
            formType: 0,
            shade: 0.4,
            title: '发送绑定邮件',
            offset: '25%',
            btnAlign: 'c',
        }, function(value, index, elem){
            if(!ismail(value)){
                $(elem).val("");
                top.layer.msg('邮箱格式错误', {
                    time: 2000,
                    offset: '31%',
                });
                return;
            }

            var url='${basePath}/system/sendEmail/${systemUser.userId}';
            $.ajax({
                type: 'post',
                url: url,
                data: {"email":value},
                beforeSend: function() {//防止重复提交数据 与loading 表单校验
                    UserBind.index = layer.load(1, {
                        //shade: [0.4,'#000'] //0.1透明度的白色背景
                    });
                },
                success: function(result) {
                    if (result.code == 200) {
                        location.reload();
                        top.layer.close(index);
                        top.layer.msg('邮件发送成功,请去邮箱查看', {
                            time: 3000,
                            offset: '31%',
                        });
                    }else {
                        var i =layer.confirm(result.message, {
                            shade: 0,
                            btn: ['确认'] //按钮
                        }, function(){
                            layer.close(i);
                        });
                    }
                },
                complete:function (data) {
                    layer.close(UserBind.index);
                },
                error: function(XMLHttpRequest, textStatus, errorThrown) {
                    var i =layer.confirm(textStatus, {
                        shade: 0,
                        btn: ['确认'] //按钮
                    }, function(){
                        layer.close(i);
                    });
                }
            });


        });
    }
function goTo() {

    window.top.location.href= 'https://mail.qq.com';
}

</script>
</body>
</html>
