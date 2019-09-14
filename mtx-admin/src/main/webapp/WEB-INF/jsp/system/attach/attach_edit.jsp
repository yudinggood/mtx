<!--system/attach/attach_edit.jsp-->
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
        EHM.ImportFileInput();
    </script>
</head>
<body>

<form id="form" >
    <c:if test="${systemAttachVo.page == 'update' }">
        <input type="hidden" name="_method" id="_method" value="PUT"/>
    </c:if>
    <table class="table table-bordered table-form" align="center" style="width: 98%">
        <c:if test="${systemAttachVo.page != 'update' }">
            <tr>

                <td width="100%" >
                    <div class="form-group">
                        <input class="form-control" type="file" name="file" id="file"  data-min-file-count="1" >
                    </div>
                </td>
            </tr>
        </c:if>
        <c:if test="${systemAttachVo.page == 'update' }">
            <tr>
                <td width="100%">
                    <div class="form-group">
                        <input class="form-control" type="file" name="file" id="file"  data-min-file-count="1">
                    </div>
                </td>
            </tr>
        </c:if>
        <tr>
            <td colspan="2">
                <div align="center">
                    <c:if test="${systemAttachVo.page != 'update' }">
                        <button type="button" title="保存" class="btn btn-success btn-xs" onclick="commit()" style="width: 100px;">
                            <span class="fa fa-save"></span>
                            <span style="padding-top:2px">&nbsp;保&nbsp;存&nbsp;</span>
                        </button>
                    </c:if>
                    <c:if test="${systemAttachVo.page == 'update' }">
                        <button type="button" title="下载" class="btn btn-success btn-xs" onclick="download()" style="width: 100px;">
                            <span class="fa fa-save"></span>
                            <span style="padding-top:2px">&nbsp;下&nbsp;载&nbsp;</span>
                        </button>
                    </c:if>
                    <button type="button" title="关闭" class="btn btn-primary btn-xs" onClick="top.layer.close(top.layer.index);" style="width: 100px;">
                        <span class="fa fa-close"></span>
                        <span style="padding-top:2px">&nbsp;关&nbsp;闭&nbsp;</span>
                    </button>
                </div>
            </td>
        </tr>
    </table>
</form>


<script>


    var AttachEdit = {
        index:null,
    };
    $(function () {
        <c:if test="${systemAttachVo.page != 'update' }">
        $("#file").fileinput({
            uploadUrl: '#', // you must set a valid URL here else you will get an error
            language : 'zh',
            uploadAsync:false,
            showUpload : false, //是否显示上传按钮
            showCaption : true,//是否显示标题
            dropZoneEnabled : true,//是否显示拖拽区域，
            maxFileSize : 1024*5,//上传文件不能大于5M
            layoutTemplates:{
                actionZoom:'',
                actionUpload:''
            },
        });
        </c:if>
        <c:if test="${systemAttachVo.page == 'update' }">
        if(!'${systemAttachVo.suffix}'.indexOf('image')){
            $("#file").fileinput({
                language : 'zh',
                showUpload : false, //是否显示上传按钮
                initialPreviewFileType:'image',
                initialPreview:'${basePath}/upload/${systemAttachVo.filePath}',
                initialPreviewConfig: [
                    {key:1,showDelete: false,size:'${systemAttachVo.fileSize}',url:'${basePath}/upload/${systemAttachVo.filePath}',caption:'${systemAttachVo.fileName}'}
                ],
                initialPreviewAsData: true,
                showUpload : false,
                showRemove : false,
                showBrowse: false,
                showClose: false,
                showCaption : true,//是否显示标题
                dropZoneEnabled : true,//是否显示拖拽区域，
                layoutTemplates:{
                    actionZoom:''
                },
            });
        }else {
            $("#file").fileinput({
                language : 'zh',
                showUpload : false, //是否显示上传按钮
                initialPreviewFileType:'object',
                initialPreview:'<div class=\'file-preview-other\'><h2><i class=\'glyphicon glyphicon-file\'></i></h2></div>',
                initialPreviewConfig: [
                    {key:1,showDelete: false,size:'${systemAttachVo.fileSize}',url:'${basePath}/upload/${systemAttachVo.filePath}',caption:'${systemAttachVo.fileName}'}
                ],
                initialPreviewAsData: true,
                showUpload : false,
                showRemove : false,
                showBrowse: false,
                showClose: false,
                showCaption : true,//是否显示标题
                dropZoneEnabled : true,//是否显示拖拽区域，
                layoutTemplates:{
                    actionZoom:''
                },
            });
        }

        </c:if>


        $('#form').bootstrapValidator({
            excluded: [':disabled', ':hidden', ':not(:visible)'],

            fields: {
                file: {
                    validators: {
                        notEmpty: {
                            message: '上传文件不能为空'
                        },
                    }
                },

            }
        })

    });
    function download() {
        var a = document.createElement('a');
        a.href = '${basePath}/upload/${systemAttachVo.filePath}'; //图片地址
        a.download = '${systemAttachVo.fileName}'; //图片名及格式
        document.body.appendChild(a);
        a.click()
    }
    function commit() {
        $('#form').bootstrapValidator('validate');
        if(!$("#form").data('bootstrapValidator').isValid()){
            return;
        }
        var url;
        <c:if test="${systemAttachVo.page == 'update' }">
        url = '${basePath}/system/attach/update/${systemAttachVo.attachId}';
        </c:if>
        <c:if test="${systemAttachVo.page != 'update' }">
        url = '${basePath}/system/attach/create';
        </c:if>

        var formData = new FormData();
        formData.append('file', $('#file')[0].files[0]);

        $.ajax({
            type: 'post',
            url: url,
            data: 	formData,
            processData: false,
            contentType: false,
            beforeSend: function() {
                AttachEdit.index = layer.load(1, {
                });
            },
            success: function(result) {
                if (result.code == 200) {
                    top.EHM.Cache["refreshs"]();
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
                layer.close(AttachEdit.index);
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
