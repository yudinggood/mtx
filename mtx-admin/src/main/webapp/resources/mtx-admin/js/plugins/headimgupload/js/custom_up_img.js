$(document).ready(function(){
        $("#up-img-touch").click(function(){
        		  $("#up-modal-frame").modal({});

        });
});
$(function() {
    'use strict';
    // 初始化
    var $image = $('#up-img-show');
    $image.cropper({
        aspectRatio: '1',
        autoCropArea:0.8,
        preview: '.up-pre-after',
        responsive:true,
    });

    // 上传图片
    var $inputImage = $('.up-modal-frame .up-img-file');
    var URL = window.URL || window.webkitURL;
    var blobURL;

    if (URL) {
        $inputImage.change(function () {
        	
            var files = this.files;
            var file;

            if (files && files.length) {
               file = files[0];

               if (/^image\/\w+$/.test(file.type)&&!includeStr(file.type,"gif")) {
                    blobURL = URL.createObjectURL(file);
                    $image.one('built.cropper', function () {
                        // Revoke when load complete
                       URL.revokeObjectURL(blobURL);
                    }).cropper('reset').cropper('replace', blobURL);
                    $inputImage.val('');
                } else {
                   var i =layer.confirm("请选择jpg或png图片上传", {
                       shade: 0,
                       btn: ['确认'] //按钮
                   }, function(){
                       layer.close(i);
                   });
                }
            }
        });
    } else {
        $inputImage.prop('disabled', true).parent().addClass('disabled');
    }
    
    //绑定上传事件
    $('.up-modal-frame .up-btn-ok').on('click',function(){
    	var $modal_loading = $('#up-modal-loading');
    	var $modal_alert = $('#up-modal-alert');
    	var img_src=$image.attr("src");
    	if(img_src==""){
            var i =layer.confirm("没有选择上传的图片", {
                shade: 0,
                btn: ['确认'] //按钮
            }, function(){
                layer.close(i);
            });

    		return false;
    	}
    	

    	var parameter=$(this).attr("parameter");
    	var parame_json = eval('(' + parameter + ')');
    	var width=parame_json.width;
    	var height=parame_json.height;


    	//控制裁剪图片的大小
    	var canvas=$image.cropper('getCroppedCanvas',{width: width,height: height});
    	var data=canvas.toDataURL(); //转成base64


        var formData = new FormData();
        formData.append('file',  dataURLtoFile(data,"head.jpg"));
        formData.append('bizType' , "HEAD_ATTACHMENT");
        var url = '/system/attach/create';
        $.ajax({
            type: 'post',
            url: url,
            data: 	formData,
            processData: false,
            contentType: false,
            beforeSend: function() {
                UserInfo.index = layer.load(1, {
                });
            },
            success: function(result) {
                if (result.code == 200) {
                    top.location.reload();
                    $("#up-modal-frame").modal('close');
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

    	
    });
    
    $('#up-btn-left').on('click',function(){
    	$("#up-img-show").cropper('rotate', 90);
    });
    $('#up-btn-right').on('click',function(){
    	$("#up-img-show").cropper('rotate', -90);
    });
});


function set_alert_info(content){
	$("#alert_content").html(content);
}



 
