<!--system/login/login.jsp-->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags"  prefix="spring" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="../../base/base.jsp"%>

    <link rel="stylesheet" href="resources/mtx-admin/js/plugins/login/style.css">
    <script>var BACK_URL = '${param.backurl}';</script>
    <style>

    </style>
    <script>
        EHM.ImportLayer();
    </script>
</head>
<body>

<div class="aui-register-popup">
    <div class="aui-register-box">

        <div class="aui-register-logo" style="width: 50px;height: 50px;">
            <img src="${basePath}/resources/mtx-admin/image/logomini_2.png" alt="">
        </div>
        <div class="aui-register-form" id="verifyCheck">
            <div class="register-wrap" id="register-wrap">
                <div class="register" id="register">

                    <div class=""  style="padding-top: 20px;">
                    </div>
                    <div class="register-top" id="reg-top" style="display: none;">
                        <h2 class="normal" id="normal">登录</h2>
                        <h2 class="nopassword" id="nopw">注册</h2>
                        <a id="qrcode" href="#">
                            <span class="aui-tag-size">扫码登录</span>
                        </a>
                    </div>
                    <div class="register-con" >

                        <div class="aui-register-form-item">
                            <input  type="text" name="username" maxlength="30" id="username" placeholder="手机号/邮箱" class="txt03 f-r3 required" tabindex="3" data-valid="isNonEmpty||isPhoneOrEmail" data-error="<i class='icon-tips'></i>您还没有输入账号||<i class='icon-tips'></i>只能手机号或邮箱" id="adminNo">
                            <label class="focus valid" id="userLabel"></label>
                        </div>
                        <div class="aui-register-form-item">
                            <input  type="password" name="password" placeholder="密码" id="password" maxlength="20" class="txt04 f-r3 required" tabindex="4" style="ime-mode:disabled;" onpaste="return  false" autocomplete="off" data-valid="isNonEmpty||between:6-20" data-error="<i class='icon-tips'></i>密码太短，最少6位||<i class='icon-tips'></i>密码长度6-20位">
                            <label class="focus valid" id="pwdLabel"></label>
                        </div>
                        <div class="aui-register-form-item">
                            <input type="text" name="code" maxlength="4" style="width: 50%;" placeholder="验证码" class="txt03 f-r3 required" tabindex="5" data-valid="isNonEmpty" data-error="<i class='icon-tips'></i>您还没有输入验证码" id="code">
                            <i><img style="height:28px;float:right;width: 20%;margin-top: 6px;" id="codeImg" alt="点击更换" title="点击更换" src="" /></i>
                            <label class="focus valid" id="codeLabel"></label>
                        </div>
                        <div class="aui-register-form-item">
                            <input id="aui-btn-reg" class="aui-btn-reg" placeholder=""  readonly="readonly" value="授权并登录" >
                        </div>

                        <div class="aui-protocol">
                            登录即同意
                            <a  href="#">《i助理使用协议》</a>&
                            <a  href="#">《隐私权条款》</a>
                        </div>


                        <!-- 扫码登录 -->
                        <div class="saoma" id="sm">
                            <div class="screen-tu" id="screen">
                                <span class="aui-tag-size">密码登录</span>
                            </div>
                            <div class="aui-text-item" style="margin-top: 40px;">
                                <h1><b>扫码登录</b></h1>
                            </div>
                            <div class="qr-code">
                                <span class="tips_img"></span>
                                <img src="${basePath}/resources/mtx-admin/image/qrcode.png" alt="">
                            </div>
                            <div class="aui-tab-footer">
                                <p>扫码登录更安全 | <a href="#">下载APP</a></p>
                            </div>

                        </div>
                    </div>

                </div>
            </div>
            <div class="clearfix"></div>
        </div>

    </div>
</div>

<script src="resources/mtx-admin/js/plugins/login/login.js"></script>
<script>
    var Login = {
        index:null,
    };
    $(function () {
        changeCode();
        $("#codeImg").bind("click", changeCode);

        $('input').iCheck({
            checkboxClass : 'icheckbox_square-blue',
            radioClass : 'iradio_square-blue',
            increaseArea : '20%'
        });
        $("#aui-btn-reg").click(function() {
            if (!verifyCheck._click()) return;

            $.ajax({
                type: 'post',
                url: '${basePath}/login',
                data:  {
                    loginId: $('#username').val(),
                    password: $('#password').val(),
                    rememberMe: $('#saveid').is(':checked'),
                    backurl: BACK_URL,
                    qqOpenId:'${OPENID_QQ}',
                    code:$('#code').val(),
                },
                beforeSend: function() {//防止重复提交数据 与loading 表单校验
                    Login.index = layer.load(1, {
                        //shade: [0.4,'#000'] //0.1透明度的白色背景
                    });
                },
                success: function(result) {
                    if (result.code == 200) {
                        window.close();
                        window.opener.location.href =result.message;
                    }else {
                        changeCode();
                        if (20000001 == result.code) {
                            $('#codeLabel').append('<span><i class="icon-tips"></i>'+result.message+'</span>');
                        }
                        if (20000007 == result.code) {
                            $('#userLabel').append('<span><i class="icon-tips"></i>'+result.message+'</span>');
                        }
                        if (20000004 == result.code) {
                            $('#codeLabel').append('<span><i class="icon-tips"></i>'+result.message+'</span>');
                        }
                        if (20000006 == result.code) {
                            $('#userLabel').append('<span><i class="icon-tips"></i>'+result.message+'</span>');
                        }
                    }
                },
                complete:function (data) {
                    layer.close(Login.index);
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
        $("#aui-btn-reg1").click(function() {
            if (!verifyCheck._click()) return;
            alert('恭喜你！登录成功')

        });

    });
    function changeCode() {
        $("#codeImg").attr("src", "${basePath}/code?t="+ genTimestamp()+"&type=sessionSecCode");
    }


    <c:if test="${param.forceLogout == 1}">
    alert('您已被强制下线！');
    top.location.href = '${basePath}/login';
    </c:if>
    //解决iframe下系统超时无法跳出iframe框架的问题
    if (window != top){
        top.location.href = location.href;
    }
</script>
</body>
</html>
