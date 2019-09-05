<!--system/permission/permission_icon.jsp-->
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
    </script>
</head>
<body class="hold-transition skin-blue layout-top-nav" style="overflow-y: hidden;">
<div class="row">
    <div class="col-xs-12">
        <form  name="menuForm" id="menuForm" method="post">
            <input type="hidden" name="icon" id="icon" />
            <table id="dynamic-table" class="table table-striped table-bordered">
                <tr class="center" style="cursor:pointer;">
                    <td><label onclick="seticon('fa fa-adjust ');"><i style="position:relative;left:2px;"style="position:relative;left:2px;" class="menu-icon fa fa-adjust "></i>&nbsp;<input class="minimal" name="form-field-radio" type="radio" value="menu-icon fa fa-adjust "><span class="lbl"></span></label></td>
                    <td><label onclick="seticon('fa fa-asterisk ');"><i style="position:relative;left:2px;"class="menu-icon fa fa-asterisk "></i>&nbsp;<input class="minimal" name="form-field-radio" type="radio" value="menu-icon fa fa-asterisk "><span class="lbl"></span></label></td>
                    <td><label onclick="seticon('fa fa-ban');"><i style="position:relative;left:2px;"class="menu-icon fa fa-ban"></i>&nbsp;<input class="minimal" name="form-field-radio" type="radio" value="menu-icon fa fa-ban "><span class="lbl"></span></label></td>
                    <td><label onclick="seticon('fa fa-bar-chart-o ');"><i style="position:relative;left:2px;"class="menu-icon fa fa-bar-chart-o "></i>&nbsp;<input class="minimal" name="form-field-radio" type="radio" value="menu-icon fa fa-bar-chart-o "><span class="lbl"></span></label></td>
                    <td><label onclick="seticon('fa fa-barcode ');"><i style="position:relative;left:2px;"class="menu-icon fa fa-barcode "></i>&nbsp;<input class="minimal" name="form-field-radio" type="radio" value="menu-icon fa fa-barcode "><span class="lbl"></span></label></td>
                    <td><label onclick="seticon('fa fa-flask ');"><i style="position:relative;left:2px;"class="menu-icon fa fa-flask "></i>&nbsp;<input class="minimal" name="form-field-radio" type="radio" value="menu-icon fa fa-flask "><span class="lbl"></span></label></td>
                    <td><label onclick="seticon('fa fa-beer ');"><i style="position:relative;left:2px;"class="menu-icon fa fa-beer "></i>&nbsp;<input class="minimal" name="form-field-radio" type="radio" value="menu-icon fa fa-beer "><span class="lbl"></span></label></td>
                    <td><label onclick="seticon('fa fa-bell-o ');"><i style="position:relative;left:2px;"class="menu-icon fa fa-bell-o "></i>&nbsp;<input class="minimal" name="form-field-radio" type="radio" value="menu-icon fa fa-bell-o "><span class="lbl"></span></label></td>
                    <td><label onclick="seticon('fa fa-bell ');"><i style="position:relative;left:2px;"class="menu-icon fa fa-bell "></i>&nbsp;<input class="minimal" name="form-field-radio" type="radio" value="menu-icon fa fa-bell "><span class="lbl"></span></label></td>
                    <td><label onclick="seticon('fa fa-bolt ');"><i style="position:relative;left:2px;"class="menu-icon fa fa-bolt "></i>&nbsp;&nbsp;<input class="minimal" name="form-field-radio" type="radio" value="menu-icon fa fa-bolt "><span class="lbl"></span></label></td>
                    <td><label onclick="seticon('fa fa-book ');"><i style="position:relative;left:2px;"class="menu-icon fa fa-book "></i>&nbsp;<input class="minimal" name="form-field-radio" type="radio" value="menu-icon fa fa-book "><span class="lbl"></span></label></td>
                    <td><label onclick="seticon('fa fa-bookmark ');"><i style="position:relative;left:2px;"class="menu-icon fa fa-bookmark "></i>&nbsp;<input class="minimal" name="form-field-radio" type="radio" value="menu-icon fa fa-bookmark "><span class="lbl"></span></label></td>
                    <td><label onclick="seticon('fa  fa-bookmark-o ');"><i style="position:relative;left:2px;"class="menu-icon fa  fa-bookmark-o "></i>&nbsp;<input class="minimal" name="form-field-radio" type="radio" value="menu-icon fa  fa-bookmark-o "><span class="lbl"></span></label></td>
                    <td><label onclick="seticon('fa fa-briefcase ');"><i style="position:relative;left:2px;"class="menu-icon fa fa-briefcase "></i>&nbsp;<input class="minimal" name="form-field-radio" type="radio" value="menu-icon fa fa-briefcase "><span class="lbl"></span></label></td>
                    <td><label onclick="seticon('fa fa-bullhorn ');"><i style="position:relative;left:2px;"class="menu-icon fa fa-bullhorn "></i>&nbsp;<input class="minimal" name="form-field-radio" type="radio" value="menu-icon fa fa-bullhorn "><span class="lbl"></span></label></td>
                    <td><label onclick="seticon('fa fa-calendar ');"><i style="position:relative;left:2px;"class="menu-icon fa fa-calendar "></i>&nbsp;<input class="minimal" name="form-field-radio" type="radio" value="menu-icon fa fa-calendar "><span class="lbl"></span></label></td>
                    <td><label onclick="seticon('fa fa-camera ');"><i style="position:relative;left:2px;"class="menu-icon fa fa-camera "></i>&nbsp;<input class="minimal" name="form-field-radio" type="radio" value="menu-icon fa fa-camera "><span class="lbl"></span></label></td>
                    <td><label onclick="seticon('fa fa-camera-retro ');"><i style="position:relative;left:2px;"class="menu-icon fa fa-camera-retro "></i>&nbsp;<input class="minimal" name="form-field-radio" type="radio" value="menu-icon fa fa-camera-retro "><span class="lbl"></span></label></td>
                    <td><label onclick="seticon('fa fa-certificate ');"><i style="position:relative;left:2px;"class="menu-icon fa fa-certificate "></i>&nbsp;<input class="minimal" name="form-field-radio" type="radio" value="menu-icon fa fa-certificate "><span class="lbl"></span></label></td>
                </tr>
                <tr class="center" style="cursor:pointer;">
                    <td><label onclick="seticon('fa fa-check-square-o ');"><i style="position:relative;left:2px;"class="menu-icon fa fa-check-square-o "></i>&nbsp;<input class="minimal" name="form-field-radio" type="radio" value="menu-icon fa fa-check-square-o "><span class="lbl"></span></label></td>
                    <td><label onclick="seticon('fa fa-square-o ');"><i style="position:relative;left:2px;"class="menu-icon fa fa-square-o "></i>&nbsp;<input class="minimal" name="form-field-radio" type="radio" value="menu-icon fa fa-square-o "><span class="lbl"></span></label></td>
                    <td><label onclick="seticon('fa fa-circle ');"><i style="position:relative;left:2px;"class="menu-icon fa fa-circle "></i>&nbsp;<input class="minimal" name="form-field-radio" type="radio" value="menu-icon fa fa-circle "><span class="lbl"></span></label></td>
                    <td><label onclick="seticon('fa fa-circle-o ');"><i style="position:relative;left:2px;"class="menu-icon fa fa-circle-o "></i>&nbsp;<input class="minimal" name="form-field-radio" type="radio" value="menu-icon fa fa-circle-o "><span class="lbl"></span></label></td>
                    <td><label onclick="seticon('fa fa-cloud ');"><i style="position:relative;left:2px;"class="menu-icon fa fa-cloud "></i>&nbsp;<input class="minimal" name="form-field-radio" type="radio" value="menu-icon fa fa-cloud "><span class="lbl"></span></label></td>
                    <td><label onclick="seticon('fa fa-cloud-download ');"><i style="position:relative;left:2px;"class="menu-icon fa fa-cloud-download "></i>&nbsp;<input class="minimal" name="form-field-radio" type="radio" value="menu-icon fa fa-cloud-download "><span class="lbl"></span></label></td>
                    <td><label onclick="seticon('fa fa-cloud-upload ');"><i style="position:relative;left:2px;"class="menu-icon fa fa-cloud-upload "></i>&nbsp;<input class="minimal" name="form-field-radio" type="radio" value="menu-icon fa fa-cloud-upload "><span class="lbl"></span></label></td>
                    <td><label onclick="seticon('fa  fa-coffee ');"><i style="position:relative;left:2px;"class="menu-icon fa  fa-coffee "></i>&nbsp;<input class="minimal" name="form-field-radio" type="radio" value="menu-icon fa  fa-coffee "><span class="lbl"></span></label></td>
                    <td><label onclick="seticon('fa fa-cog ');"><i style="position:relative;left:2px;"class="menu-icon fa fa-cog "></i>&nbsp;<input class="minimal" name="form-field-radio" type="radio" value="menu-icon fa fa-cog "><span class="lbl"></span></label></td>
                    <td><label onclick="seticon('fa fa-cogs ');"><i style="position:relative;left:2px;"class="menu-icon fa fa-cogs "></i>&nbsp;<input class="minimal" name="form-field-radio" type="radio" value="menu-icon fa fa-cogs "><span class="lbl"></span></label></td>
                    <td><label onclick="seticon('fa fa-comment ');"><i style="position:relative;left:2px;"class="menu-icon fa fa-comment "></i>&nbsp;<input class="minimal" name="form-field-radio" type="radio" value="menu-icon fa fa-comment "><span class="lbl"></span></label></td>
                    <td><label onclick="seticon('fa fa-comment-o ');"><i style="position:relative;left:2px;"class="menu-icon fa fa-comment-o "></i>&nbsp;<input class="minimal" name="form-field-radio" type="radio" value="menu-icon fa fa-comment-o "><span class="lbl"></span></label></td>
                    <td><label onclick="seticon('fa fa-comments ');"><i style="position:relative;left:2px;"class="menu-icon fa fa-comments "></i>&nbsp;<input class="minimal" name="form-field-radio" type="radio" value="menu-icon fa fa-comments "><span class="lbl"></span></label></td>
                    <td><label onclick="seticon('fa fa-comments-o ');"><i style="position:relative;left:2px;"class="menu-icon fa fa-comments-o "></i>&nbsp;<input class="minimal" name="form-field-radio" type="radio" value="menu-icon fa fa-comments-o "><span class="lbl"></span></label></td>
                    <td><label onclick="seticon('fa fa-credit-card ');"><i style="position:relative;left:2px;"class="menu-icon fa fa-credit-card "></i>&nbsp;<input class="minimal" name="form-field-radio" type="radio" value="menu-icon fa fa-credit-card "><span class="lbl"></span></label></td>
                    <td><label onclick="seticon('fa fa-tachometer ');"><i style="position:relative;left:2px;"class="menu-icon fa fa-tachometer "></i>&nbsp;<input class="minimal" name="form-field-radio" type="radio" value="menu-icon fa fa-tachometer "><span class="lbl"></span></label></td>
                    <td><label onclick="seticon('fa fa-desktop ');"><i style="position:relative;left:2px;"class="menu-icon fa fa-desktop "></i>&nbsp;<input class="minimal" name="form-field-radio" type="radio" value="menu-icon fa fa-desktop "><span class="lbl"></span></label></td>
                    <td><label onclick="seticon('fa fa-arrow-circle-o-down ');"><i style="position:relative;left:2px;"class="menu-icon fa fa-arrow-circle-o-down "></i>&nbsp;<input class="minimal" name="form-field-radio" type="radio" value="menu-icon fa fa-arrow-circle-o-down "><span class="lbl"></span></label></td>
                    <td><label onclick="seticon('fa fa-download ');"><i style="position:relative;left:2px;"class="menu-icon fa fa-download "></i>&nbsp;<input class="minimal" name="form-field-radio" type="radio" value="menu-icon fa fa-download "><span class="lbl"></span></label></td>
                </tr>
                <tr class="center" style="cursor:pointer;">
                    <td><label onclick="seticon('fa fa-pencil-square-o ');"><i style="position:relative;left:2px;"class="menu-icon fa fa-pencil-square-o "></i>&nbsp;<input class="minimal" name="form-field-radio" type="radio" value="menu-icon fa fa-pencil-square-o "><span class="lbl"></span></label></td>
                    <td><label onclick="seticon('fa fa-envelope ');"><i style="position:relative;left:2px;"class="menu-icon fa fa-envelope "></i>&nbsp;<input class="minimal" name="form-field-radio" type="radio" value="menu-icon fa fa-envelope "><span class="lbl"></span></label></td>
                    <td><label onclick="seticon('fa fa-envelope-o ');"><i style="position:relative;left:2px;"class="menu-icon fa fa-envelope-o "></i>&nbsp;<input class="minimal" name="form-field-radio" type="radio" value="menu-icon fa fa-envelope-o "><span class="lbl"></span></label></td>
                    <td><label onclick="seticon('fa fa-exchange ');"><i style="position:relative;left:2px;"class="menu-icon fa fa-exchange "></i>&nbsp;<input class="minimal" name="form-field-radio" type="radio" value="menu-icon fa fa-exchange "><span class="lbl"></span></label></td>
                    <td><label onclick="seticon('fa fa-exclamation-circle ');"><i style="position:relative;left:2px;"class="menu-icon fa fa-exclamation-circle "></i>&nbsp;<input class="minimal" name="form-field-radio" type="radio" value="menu-icon fa fa-exclamation-circle "><span class="lbl"></span></label></td>
                    <td><label onclick="seticon('fa fa-external-link ');"><i style="position:relative;left:2px;"class="menu-icon fa fa-external-link "></i>&nbsp;<input class="minimal" name="form-field-radio" type="radio" value="menu-icon fa fa-external-link "><span class="lbl"></span></label></td>
                    <td><label onclick="seticon('fa fa-eye-slash ');"><i style="position:relative;left:2px;"class="menu-icon fa fa-eye-slash "></i>&nbsp;<input class="minimal" name="form-field-radio" type="radio" value="menu-icon fa fa-eye-slash "><span class="lbl"></span></label></td>
                    <td><label onclick="seticon('fa fa-eye ');"><i style="position:relative;left:2px;"class="menu-icon fa fa-eye"></i>&nbsp;<input class="minimal" name="form-field-radio" type="radio" value="menu-icon fa fa-eye "><span class="lbl"></span></label></td>
                    <td><label onclick="seticon('fa fa-video-camera ');"><i style="position:relative;left:2px;"class="menu-icon fa fa-video-camera "></i>&nbsp;<input class="minimal" name="form-field-radio" type="radio" value="menu-icon fa fa-video-camera "><span class="lbl"></span></label></td>
                    <td><label onclick="seticon('fa fa-fighter-jet ');"><i style="position:relative;left:2px;"class="menu-icon fa fa-fighter-jet "></i>&nbsp;<input class="minimal" name="form-field-radio" type="radio" value="menu-icon fa fa-fighter-jet "><span class="lbl"></span></label></td>
                    <td><label onclick="seticon('fa fa-film ');"><i style="position:relative;left:2px;"class="menu-icon fa fa-film "></i>&nbsp;<input class="minimal" name="form-field-radio" type="radio" value="menu-icon fa fa-film "><span class="lbl"></span></label></td>
                    <td><label onclick="seticon('fa fa-filter ');"><i style="position:relative;left:2px;"class="menu-icon fa fa-filter "></i>&nbsp;<input class="minimal" name="form-field-radio" type="radio" value="menu-icon fa fa-filter "><span class="lbl"></span></label></td>
                    <td><label onclick="seticon('fa fa-fire ');"><i style="position:relative;left:2px;"class="menu-icon fa fa-fire "></i>&nbsp;<input class="minimal" name="form-field-radio" type="radio" value="menu-icon fa fa-fire "><span class="lbl"></span></label></td>
                    <td><label onclick="seticon('fa fa-flag ');"><i style="position:relative;left:2px;"class="menu-icon fa fa-flag "></i>&nbsp;<input class="minimal" name="form-field-radio" type="radio" value="menu-icon fa fa-flag "><span class="lbl"></span></label></td>
                    <td><label onclick="seticon('fa fa-folder ');"><i style="position:relative;left:2px;"class="menu-icon fa fa-folder "></i>&nbsp;<input class="minimal" name="form-field-radio" type="radio" value="menu-icon fa fa-folder "><span class="lbl"></span></label></td>
                    <td><label onclick="seticon('fa fa-folder-open ');"><i style="position:relative;left:2px;"class="menu-icon fa fa-folder-open "></i>&nbsp;<input class="minimal" name="form-field-radio" type="radio" value="menu-icon fa fa-folder-open "><span class="lbl"></span></label></td>
                    <td><label onclick="seticon('fa fa-folder-o ');"><i style="position:relative;left:2px;"class="menu-icon fa fa-folder-o "></i>&nbsp;<input class="minimal" name="form-field-radio" type="radio" value="menu-icon fa fa-folder-o "><span class="lbl"></span></label></td>
                    <td><label onclick="seticon('fa fa-folder-open-o ');"><i style="position:relative;left:2px;"class="menu-icon fa fa-folder-open-o "></i>&nbsp;<input class="minimal" name="form-field-radio" type="radio" value="menu-icon fa fa-folder-open-o "><span class="lbl"></span></label></td>
                    <td><label onclick="seticon('fa fa-cutlery ');"><i style="position:relative;left:2px;"class="menu-icon fa fa-cutlery "></i>&nbsp;<input class="minimal" name="form-field-radio" type="radio" value="menu-icon fa fa-cutlery "><span class="lbl"></span></label></td>
                </tr>
                <tr class="center" style="cursor:pointer;">
                    <td><label onclick="seticon('fa fa-gift ');"><i style="position:relative;left:2px;"class="menu-icon fa fa-gift "></i>&nbsp;<input class="minimal" name="form-field-radio" type="radio" value="menu-icon fa fa-gift "><span class="lbl"></span></label></td>
                    <td><label onclick="seticon('fa fa-glass ');"><i style="position:relative;left:2px;"class="menu-icon fa fa-glass "></i>&nbsp;<input class="minimal" name="form-field-radio" type="radio" value="menu-icon fa fa-glass "><span class="lbl"></span></label></td>
                    <td><label onclick="seticon('fa fa-globe ');"><i style="position:relative;left:2px;"class="menu-icon fa fa-globe "></i>&nbsp;<input class="minimal" name="form-field-radio" type="radio" value="menu-icon fa fa-globe "><span class="lbl"></span></label></td>
                    <td><label onclick="seticon('fa fa-users ');"><i style="position:relative;left:2px;"class="menu-icon fa 	fa-users "></i>&nbsp;<input class="minimal" name="form-field-radio" type="radio" value="menu-icon fa fa-users "><span class="lbl"></span></label></td>
                    <td><label onclick="seticon('fa fa-hdd-o ');"><i style="position:relative;left:2px;"class="menu-icon fa fa-hdd-o "></i>&nbsp;<input class="minimal" name="form-field-radio" type="radio" value="menu-icon fa fa-hdd-o "><span class="lbl"></span></label></td>
                    <td><label onclick="seticon('fa fa-headphones ');"><i style="position:relative;left:2px;"class="menu-icon fa fa-headphones "></i>&nbsp;<input class="minimal" name="form-field-radio" type="radio" value="menu-icon fa fa-headphones "><span class="lbl"></span></label></td>
                    <td><label onclick="seticon('fa fa-heart ');"><i style="position:relative;left:2px;"class="menu-icon fa fa-heart "></i>&nbsp;<input class="minimal" name="form-field-radio" type="radio" value="menu-icon fa fa-heart "><span class="lbl"></span></label></td>
                    <td><label onclick="seticon('fa fa-heart-o ');"><i style="position:relative;left:2px;"class="menu-icon fa fa-heart-o "></i>&nbsp;<input class="minimal" name="form-field-radio" type="radio" value="menu-icon fa fa-heart-o "><span class="lbl"></span></label></td>
                    <td><label onclick="seticon('fa fa-home ');"><i style="position:relative;left:2px;"class="menu-icon fa fa-home "></i>&nbsp;<input class="minimal" name="form-field-radio" type="radio" value="menu-icon fa fa-home "><span class="lbl"></span></label></td>
                    <td><label onclick="seticon('fa fa-inbox ');"><i style="position:relative;left:2px;"class="menu-icon fa  fa-inbox "></i>&nbsp;<input class="minimal" name="form-field-radio" type="radio" value="menu-icon fa fa-inbox "><span class="lbl"></span></label></td>
                    <td><label onclick="seticon('fa fa-info-circle ');"><i style="position:relative;left:2px;"class="menu-icon fa fa-info-circle "></i>&nbsp;<input class="minimal" name="form-field-radio" type="radio" value="menu-icon fa fa-info-circle "><span class="lbl"></span></label></td>
                    <td><label onclick="seticon('fa fa-key ');"><i style="position:relative;left:2px;"class="menu-icon fa fa-key "></i>&nbsp;<input class="minimal" name="form-field-radio" type="radio" value="menu-icon fa fa-key "><span class="lbl"></span></label></td>
                    <td><label onclick="seticon('fa fa-leaf ');"><i style="position:relative;left:2px;"class="menu-icon fa fa-leaf "></i>&nbsp;<input class="minimal" name="form-field-radio" type="radio" value="menu-icon fa fa-leaf"><span class="lbl"></span></label></td>
                    <td><label onclick="seticon('fa fa-laptop ');"><i style="position:relative;left:2px;"class="menu-icon fa fa-laptop "></i>&nbsp;<input class="minimal" name="form-field-radio" type="radio" value="menu-icon fa fa-laptop "><span class="lbl"></span></label></td>
                    <td><label onclick="seticon('fa fa-gavel ');"><i style="position:relative;left:2px;"class="menu-icon fa fa-gavel "></i>&nbsp;<input class="minimal" name="form-field-radio" type="radio" value="menu-icon fa fa-gavel "><span class="lbl"></span></label></td>
                    <td><label onclick="seticon('fa fa-lemon-o ');"><i style="position:relative;left:2px;"class="menu-icon fa fa-lemon-o "></i>&nbsp;<input class="minimal" name="form-field-radio" type="radio" value="menu-icon fa fa-lemon-o "><span class="lbl"></span></label></td>
                    <td><label onclick="seticon('fa fa-lightbulb-o ');"><i style="position:relative;left:2px;"class="menu-icon fa fa-lightbulb-o "></i>&nbsp;&nbsp;<input class="minimal" name="form-field-radio" type="radio" value="menu-icon fa fa-lightbulb-o "><span class="lbl"></span></label></td>
                    <td><label onclick="seticon('fa fa-lock ');"><i style="position:relative;left:2px;"class="menu-icon fa fa-lock "></i>&nbsp;<input class="minimal" name="form-field-radio" type="radio" value="menu-icon fa fa-lock "><span class="lbl"></span></label></td>
                    <td><label onclick="seticon('fa fa-unlock ');"><i style="position:relative;left:2px;"class="menu-icon fa fa-unlock "></i>&nbsp;<input class="minimal" name="form-field-radio" type="radio" value="menu-icon fa fa-unlock "><span class="lbl"></span></label></td>
                </tr>
                <tr>
                    <td colspan="19">
                        <div align="center">
                            <button type="button" title="保存" onclick="save()" class="btn btn-success btn-xs" id="CommitAndSave" style="width: 100px;">
                                <span class="fa fa-save"></span>
                                <span style="padding-top:2px">&nbsp;保&nbsp;存&nbsp;</span>
                            </button>
                            <button type="button" title="关闭" class="btn btn-primary btn-xs" onClick="parent.layer.close(parent.layer.index);" style="width: 100px;">
                                <span class="fa fa-close"></span>
                                <span style="padding-top:2px">&nbsp;关&nbsp;闭&nbsp;</span>
                            </button>
                        </div>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>



<script>

    $(function () {
        $('input[type="checkbox"].minimal, input[type="radio"].minimal').iCheck({
            checkboxClass: 'icheckbox_minimal-blue',
            radioClass: 'iradio_minimal-blue'
        });
        $('input[type="radio"]').on('ifChanged', function(event){
            var i = this.value.slice(10,this.value.length);
            $("#icon").val(i);
        });

        //var icon = $("#icon").val();
        //$("input:radio[value='menu-icon fa fa-leaf']").prop('checked',true);

    });
    function save(){
        if(isEmpty($("#icon").val())){
            var i =layer.confirm('保存失败！请先选择图标', {
                shade: 0,
                btn: ['确认'] //按钮
            }, function(){
                layer.close(i);
                return ;
            });
        }else {
            top.EHM.Cache["doCallBackResult"]($("#icon").val())
            parent.layer.close(parent.layer.index);
        }

    }
    function seticon(icon){
        $("#icon").val(icon);
    }

</script>
</body>
</html>
