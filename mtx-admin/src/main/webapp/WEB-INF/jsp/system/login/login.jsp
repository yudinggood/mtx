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
                    <div class="register-top" id="reg-top">
                        <h2 class="normal" id="normal"><spring:message code="login.login"/></h2>
                        <h2 class="nopassword" id="nopw"><spring:message code="login.register"/></h2>
                        <a id="qrcode" href="#">
                            <span class="aui-tag-size">扫码登录</span>
                        </a>
                    </div>
                    <!--账户密码登录   -->
                    <div class="register-con" id="rc">
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
                        <div class="row " style="">
                            <div class="col-sm-6" style="float:left;">
                                <input id="saveid" onclick="savePaw();" type="checkbox" style=""><label  style="margin-left:10px;font-size: 12px;font-weight: normal" for="saveid">保持登录7天</label>
                            </div>
                            <div class="col-sm-3" style="float:right;width: 100px;">
                                <%--<a style="cursor: pointer;">忘记密码?</a>--%>
                                    <a href="${basePath}/changeLang?langType=zh" style="cursor: pointer;">中文</a>
                                    <a>|</a>
                                    <a href="${basePath}/changeLang?langType=en" style="cursor: pointer;">英文</a>
                            </div>
                        </div>
                        </div>
                        <div class="aui-register-form-item">
                            <input id="aui-btn-reg" class="aui-btn-reg" placeholder=""  readonly="readonly" value="登录" >
                        </div>

                        <div class="aui-protocol">
                            登录即同意
                            <a target="view_window" href="${pageInfo.gitBookDomain}/test.html">《i助理使用协议》</a>&
                            <a target="view_window" href="${pageInfo.gitBookDomain}/chapter1.html">《隐私权条款》</a>
                        </div>
                        <div class="aui-thirds">
                            <a href="#"  onclick="qqlogin()">
                                <i class="aui-qq-img"></i>
                                <i>QQ登录</i>
                            </a>
                            <a href="#">
                                <i class="aui-wx-img"></i>
                                <i>微信登录</i>
                            </a>
                            <a href="#">
                                <i class="aui-wb-img"></i>
                                <i>微博登录</i>
                            </a>
                            <div class="clear"></div>
                        </div>
                    </div>

                    <!--手机动态码登录-->
                    <div class="login-con" id="lc">
                        <div class="aui-register-form-item">
                            <input type="text" value="" name="phone" id="username2" placeholder="手机号码" class="txt01 f-r3 required" keycodes="tel" tabindex="1" data-valid="isNonEmpty||isPhone" data-error="<i class='icon-tips'></i>请输入手机号||<i class='icon-tips'></i>手机号码格式不正确" maxlength="11" >
                            <label class="focus valid" id="userLabel2"><div class="msg" style="display:none"><i class='icon-tips'></i>您还未输入手机号</div></label>
                            <span class="aui-get-code btn btn-gray f-r3 f-ml5 f-size13" id="time_box" disabled style="display:none;"></span>
                            <span class="aui-get-code btn btn-gray f-r3 f-ml5 f-size13" id="verifyYz" >获取动态码</span>
                        </div>
                        <div class="aui-register-form-item">
                            <input type="text" name="code" maxlength="4" style="width: 50%;" placeholder="验证码" class="txt03 f-r3 required" tabindex="5" data-valid="isNonEmpty" data-error="<i class='icon-tips'></i>您还没有输入验证码" id="code2">
                            <i><img style="height:28px;float:right;width: 20%;margin-top: 6px;" id="codeImg2" alt="点击更换" title="点击更换" src="" /></i>
                            <label class="focus valid" id="codeLabel2"></label>
                        </div>
                        <div class="aui-register-form-item">
                            <input  type="password" name="password" placeholder="密码" id="password2" maxlength="20" class="txt04 f-r3 required" tabindex="4" style="ime-mode:disabled;" onpaste="return  false" autocomplete="off" data-valid="isNonEmpty||between:6-20||level:2" data-error="<i class='icon-tips'></i>密码太短，最少6位||<i class='icon-tips'></i>密码长度6-20位
                            ||<i class='icon-tips'></i>密码太简单，有被盗风险，建议字母+数字的组合">
                            <label class="focus valid" id="pwdLabel2"></label>
                        </div>
                        <div class="aui-register-form-item">
                            <input type="text" value="" placeholder="动态码" maxlength="6" id="verifyNo" class="txt02 f-r3 f-fl required" tabindex="2" data-valid="isNonEmpty||isInt" data-error="<i class='icon-tips'></i>请填写正确的手机动态码||<i class='icon-tips'></i>请填写6位手机动态码">
                            <label class="focus valid" id="pwdLabel3"></label>
                        </div>
                        <div class="aui-register-form-item">
                            <input id="aui-btn-reg1" class="aui-btn-reg" placeholder=""  readonly="readonly" value="注册" >
                        </div>
                        <div class="aui-protocol">
                            注册即同意
                            <a target="view_window" href="${pageInfo.gitBookDomain}/test.html">《i助理使用协议》</a>&
                            <a target="view_window" href="${pageInfo.gitBookDomain}/chapter1.html">《隐私权条款》</a>
                        </div>

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
            <div class="clearfix"></div>
        </div>

    </div>
</div>

<script src="resources/mtx-admin/js/plugins/login/login.js"></script>
<script>
    var Login = {
        index:null,
        langType:cookie.getCookie("org.springframework.web.servlet.i18n.CookieLocaleResolver.LOCALE"),
    };
    $(function () {
        setLang(Login.langType);

        changeCode();
        $("#codeImg").bind("click", changeCode);
        changeCode2();
        $("#codeImg2").bind("click", changeCode2);

        $('input').iCheck({
            checkboxClass : 'icheckbox_square-blue',
            radioClass : 'iradio_square-blue',
            increaseArea : '20%'
        });
        $("#aui-btn-reg1").click(function() {
            if (!verifyCheck._clickSms2()) return;

            $.ajax({
                type: 'post',
                url: '${basePath}/register',
                data:  {
                    loginId: $('#username2').val(),
                    phone: $('#username2').val(),
                    password: $('#password2').val(),
                    backurl: BACK_URL,
                    verifyNo:$('#verifyNo').val(),
                },
                beforeSend: function() {//防止重复提交数据 与loading 表单校验
                    Login.index = layer.load(1, {
                        //shade: [0.4,'#000'] //0.1透明度的白色背景
                    });
                },
                success: function(result) {
                    if (result.code == 200) {
                        location.href =result.message;
                    }else {
                        $('#userLabel2').empty();
                        $('#pwdLabel3').empty();
                        if (20000010 == result.code) {
                            $('#userLabel2').append('<span><i class="icon-tips"></i>'+result.message+'</span>');
                        }
                        if (20000012 == result.code) {
                            $('#pwdLabel3').append('<span><i class="icon-tips"></i>'+result.message+'</span>');
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
                    code:$('#code').val(),
                },
                beforeSend: function() {//防止重复提交数据 与loading 表单校验
                    Login.index = layer.load(1, {
                        //shade: [0.4,'#000'] //0.1透明度的白色背景
                    });
                },
                success: function(result) {
                    if (result.code == 200) {
                        location.href =result.message;
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
                        if (20000009 == result.code) {
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


    });
    function changeCode() {
        $("#codeImg").attr("src", "${basePath}/code?t="+ genTimestamp()+"&type=sessionSecCode");
    }
    function changeCode2() {
        $("#codeImg2").attr("src", "${basePath}/code?t="+ genTimestamp()+"&type=registersessionSecCode");
    }

    function qqlogin(width, height){
        var qqAppId = '${pageInfo.qqAppId}'; // 上面申请得到的appid
        var qqAuthPath = '${pageInfo.qqAuthPath}'; // 前面设置的回调地址
        var state = 'test';
        var url='https://graph.qq.com/oauth2.0/authorize?response_type=code&client_id='+qqAppId+'&redirect_uri='+qqAuthPath+'&state='+state;

        width = width || 600;
        height = height || 500;
        var left = ($(window).width() - width+100) / 2;
        var top = ($(window).height() - height) / 2;
        window.open(url, "_blank", "toolbar=yes, location=yes, directories=no, status=no, menubar=yes, scrollbars=yes, resizable=no, copyhistory=yes, left="+left+", top="+top+", width="+width+", height="+height);
    }
    
    function setLang(lang) {
        if(!isEmpty(lang)){
            cookie.setCookie("org.springframework.web.servlet.i18n.CookieLocaleResolver.LOCALE", lang, 30);
        }
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
