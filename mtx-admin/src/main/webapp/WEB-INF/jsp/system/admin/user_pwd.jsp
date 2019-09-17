<!--system/test/test.jsp-->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags"  prefix="spring" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="../../base/base.jsp"%>


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

<form id="form" style="margin:5px auto;padding-top: 8px;width:98%;height:90%">
    <input type="hidden" name="_method" id="_method" value="PUT"/>
    <table  class="table  table-form" align="center" style="width: 98%;">
        <tr>
            <th width="20%" style="border:0px;"><font color="red">*</font><label class="Validform_label">旧密码:</label></th>
            <td width="80%" style="border:0px;">
                <div class="form-group">
                    <input type="password" value="" maxlength="20" type="text" name="oldPwd" id="oldPwd" placeholder="旧密码" class="form-control input80">
                </div>
            </td>
        </tr>
        <tr>
            <th width="20%" style="border:0px;"><font color="red">*</font><label class="Validform_label">新密码:</label></th>
            <td width="80%" style="border:0px;">
                <div class="form-group">
                    <input type="password" value="" maxlength="20" type="text" name="newPwd" id="newPwd" placeholder="新密码" class="form-control input80">
                </div>
            </td>
        </tr>
        <tr>
            <th width="20%" style="border:0px;"><font color="red">*</font><label class="Validform_label">确认密码:</label></th>
            <td width="80%" style="border:0px;">
                <div class="form-group">
                    <input type="password" value="" maxlength="20" type="text" name="surPwd" id="surPwd" placeholder="确认密码" class="form-control input80">
                </div>
            </td>
        </tr>

        <tr>
            <td colspan="2"  style="border:0px;">
                <div align="center">
                    <button type="button" title="保存" class="btn btn-success btn-xs" onclick="save()" style="width: 100px;">
                        <span class="fa fa-save"></span>
                        <span style="padding-top:2px">&nbsp;保&nbsp;存&nbsp;</span>
                    </button>
                </div>
            </td>
        </tr>
    </table>
</form>


<script>
    var UserPwd = {
        index:null,
    };
    $(function () {
        $('#form').bootstrapValidator({
            excluded: [':disabled', ':hidden', ':not(:visible)'],

            fields: {
                oldPwd: {
                    validators: {
                        notEmpty: {
                            message: '旧密码不能为空'
                        },
                        regexp : {
                            regexp : /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$/,
                            message : '用户密码建议字母+数字的组合,长度6-20位'
                        },
                    }
                },
                newPwd: {
                    validators: {
                        notEmpty: {
                            message: '新密码不能为空'
                        },
                        regexp : {
                            regexp : /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$/,
                            message : '用户密码建议字母+数字的组合,长度6-20位'
                        },
                        identical: {
                            field: 'surPwd',
                            message: '用户新密码与确认密码不一致！'
                        },
                    }
                },
                surPwd: {
                    validators: {
                        notEmpty: {
                            message: '确认密码不能为空'
                        },
                        regexp : {
                            regexp : /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$/,
                            message : '用户密码建议字母+数字的组合,长度6-20位'
                        },
                        identical: {
                            field: 'newPwd',
                            message: '用户新密码与确认密码不一致！'
                        },
                    }
                },
            }
        })

    });
    function save() {
        $('#form').bootstrapValidator('validate');
        if(!$("#form").data('bootstrapValidator').isValid()){
            return;
        }

        var url='${basePath}/system/updateUserPwd/${systemUser.userId}';
        $.ajax({
            type: 'post',
            url: url,
            data: 	$("#form").serialize(),
            beforeSend: function() {//防止重复提交数据 与loading 表单校验
                UserPwd.index = layer.load(1, {
                    //shade: [0.4,'#000'] //0.1透明度的白色背景
                });
            },
            success: function(result) {
                if (result.code == 200) {
                    location.reload();
                    layer.msg('密码修改成功', {
                        time: 2000
                    });
                    top.layer.close(top.layer.index);
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
                layer.close(UserPwd.index);
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

    }

</script>
</body>
</html>
