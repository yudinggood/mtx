<!--system/admin/user_info.jsp-->
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
        EHM.ImportSelect();
        EHM.ImportHeadUpload();
    </script>
</head>
<body style="overflow: hidden;">

<form id="form" style="margin:5px auto;padding-top: 8px;width:98%;height:90%">
    <input type="hidden" name="_method" id="_method" value="PUT"/>
    <table  class="table  table-form" align="center" style="width: 98%;">
        <tr>
            <th width="20%" style="border:0px;">头像:</th>
            <td width="80%" colspan="2"  style="border:0px;">
                <div class="up-img-cover"  id="up-img-touch" >
                    <c:if test="${systemUser.avatar == null }">
                        <img style="float:left;width: 95px; height: 95px;border: 1px solid #999;" class="am-circle" alt="点击图片上传"
                             src="${basePath}/resources/mtx-admin/image/avatar5.png" data-am-popover="{content: '点击修改头像', trigger: 'hover focus'}" >
                    </c:if>
                    <c:if test="${systemUser.avatar != null }">
                        <c:if test="${systemUser.addressType == 1 }">
                            <img style="float:left;width: 95px; height: 95px;border: 1px solid #999;" class="am-circle" alt="点击图片上传" onerror="this.src='${basePath}/resources/mtx-admin/image/error.png'"
                                 src="${basePath}/upload/${systemUser.avatar}" data-am-popover="{content: '点击修改头像', trigger: 'hover focus'}" >
                        </c:if>
                        <c:if test="${systemUser.addressType == 2 }">
                            <img style="float:left;width: 95px; height: 95px;border: 1px solid #999;" class="am-circle" alt="点击图片上传" onerror="this.src='${basePath}/resources/mtx-admin/image/error.png'"
                                 src="${systemUser.yunPath}" data-am-popover="{content: '点击修改头像', trigger: 'hover focus'}" >
                        </c:if>
                    </c:if>
                </div>

            </td>
        </tr>
        <tr>
            <th  style="border:0px;"><font color="red">*</font><label class="Validform_label">昵称:</label></th>
            <td  style="border:0px;">
                <div class="form-group">
                    <input value="${systemUser.nickName}" class="form-control" type="text" name="nickName" id="nickName" placeholder="昵称" maxlength="20">
                </div>
            </td>
        </tr>
        <tr>
            <th style="border:0px;">姓名:</th>
            <td style="border:0px;">
                <div class="form-group">
                    <input value="${systemUser.realName}" maxlength="20" type="text" name="realName" id="realName" placeholder="姓名" class="form-control input80">
                </div>
            </td>
        </tr>
        <tr>
            <th style="border:0px;">性别:</th>
            <td style="border:0px;">
                <div class="form-group">
                    <input type="radio" name="sex" value="1" class="minimal" <c:if test="${systemUser.sex == 1 }">checked="checked"</c:if> id="sex1"/><label for="sex1">男</label>
                    <input type="radio" name="sex" value="2" class="minimal" <c:if test="${systemUser.sex == 2 }">checked="checked"</c:if> id="sex2"/><label for="sex2">女</label>
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

<!--图片上传框-->
<div class="am-modal am-modal-no-btn up-modal-frame" tabindex="-1" id="up-modal-frame">
    <div class="am-modal-dialog up-frame-parent up-frame-radius">
        <div class="am-modal-hd up-frame-header">
            <label>修改头像</label>
            <a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
        </div>
        <div class="am-modal-bd  up-frame-body">
            <div class="am-g am-fl">

                <div class="am-form-group am-form-file">
                    <div class="am-fl">
                        <button type="button" class="am-btn am-btn-default am-btn-sm">
                            <i class="am-icon-cloud-upload"></i> 选择要上传的文件</button>
                    </div>
                    <input type="file" class="up-img-file">
                </div>
            </div>
            <div class="am-g am-fl">
                <div class="up-pre-before up-frame-radius">
                    <img alt="" src="" class="up-img-show" id="up-img-show" >
                </div>
                <div class="up-pre-after up-frame-radius">
                </div>
            </div>
            <div class="am-g am-fl">
                <div class="up-control-btns">
                    <span class="am-icon-rotate-left"   id="up-btn-left"></span>
                    <span class="am-icon-rotate-right"  id="up-btn-right"></span>
                    <span class="am-icon-check up-btn-ok" url="/admin/user/upload.action"
                          parameter="{width:'100',height:'100'}">
    				</span>
                </div>
            </div>

        </div>
    </div>
</div>
<!--加载框-->
<div class="am-modal am-modal-loading am-modal-no-btn" tabindex="-1" id="up-modal-loading">
    <div class="am-modal-dialog">
        <div class="am-modal-hd">正在上传...</div>
        <div class="am-modal-bd">
            <span class="am-icon-spinner am-icon-spin"></span>
        </div>
    </div>
</div>

<!--警告框-->
<div class="am-modal am-modal-alert" tabindex="-1" id="up-modal-alert">
    <div class="am-modal-dialog">
        <div class="am-modal-hd">信息</div>
        <div class="am-modal-bd"  id="alert_content">
            成功了
        </div>
        <div class="am-modal-footer">
            <span class="am-modal-btn">确定</span>
        </div>
    </div>
</div>

<script>
    var UserInfo = {
        index:null,
    };
    $(function () {
        $('#form').bootstrapValidator({
            excluded: [':disabled', ':hidden', ':not(:visible)'],

            fields: {
                nickName: {
                    validators: {
                        notEmpty: {
                            message: '昵称不能为空'
                        },
                    }
                },
                realName: {
                    validators: {
                        regexp: {
                            regexp: /^[\u0391-\uFFE5A-Za-z]+$/,
                            message: '只能输入中英文'
                        }
                    }
                },
            }
        })
        $('input[type="checkbox"].minimal, input[type="radio"].minimal').iCheck({
            checkboxClass: 'icheckbox_minimal-blue',
            radioClass: 'iradio_minimal-blue'
        });
        $('input[type="radio"]').on('ifChanged', function(event){

        });

    });


    function save() {
        $('#form').bootstrapValidator('validate');
        if(!$("#form").data('bootstrapValidator').isValid()){
            return;
        }

        var url='${basePath}/system/updateUserInfo/${systemUser.userId}';
        $.ajax({
            type: 'post',
            url: url,
            data: 	$("#form").serialize(),
            beforeSend: function() {//防止重复提交数据 与loading 表单校验
                UserInfo.index = layer.load(1, {
                    //shade: [0.4,'#000'] //0.1透明度的白色背景
                });
            },
            success: function(result) {
                if (result.code == 200) {
                    location.reload();
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
                layer.close(UserInfo.index);
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
