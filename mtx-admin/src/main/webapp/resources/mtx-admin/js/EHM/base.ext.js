
EHM.ImportJquery223 = function () {//jQuery 2.2.3
	EHM.Import("/resources/mtx-admin/AdminLTE/plugins/jQuery/jquery-2.2.3.min.js");
}
//使用的地方：tab 
EHM.ImportJquery172 = function () {//jQuery 1.7.2
	EHM.Import("/resources/mtx-admin/js/plugins/jquery/jquery-1.7.2.js");
}
//使用的地方：   

EHM.ImportError = function () {//error相关
    EHM.Import("/resources/mtx-admin/js/plugins/error/ChangeSkin.js");
    EHM.Import("/resources/mtx-admin/js/plugins/error/cookie.js");
    EHM.ImportCss("/resources/mtx-admin/js/plugins/error/css/style.css");
}

EHM.ImportBootStrapPlugins = function(){//bootstrap相关的一些插件
	EHM.ImportCss("/resources/mtx-admin/js/plugins/table/css/bootstrap-table.min.css");
    EHM.Import("/resources/mtx-admin/js/plugins/table/js/bootstrap-table.min.js");
    EHM.Import("/resources/mtx-admin/js/plugins/table/js/bootstrap-table-filter-control.js");
    EHM.Import("/resources/mtx-admin/js/plugins/table/js/bootstrap-table-zh-CN.min.js");//表格
    
    
}
EHM.ImportBootStrapTab = function(){//bootstrap tab
    EHM.Import("/resources/mtx-admin/js/plugins/bootstraptab/bootstrapTabForYu.js");
}
EHM.ImportTab = function () {//tab相关
	EHM.Import("/resources/mtx-admin/js/plugins/tab/js/framework.js");
	EHM.Import("/resources/mtx-admin/js/plugins/tab/js/tab.js");
	EHM.ImportCss("/resources/mtx-admin/js/plugins/tab/css/import_basic.css");
}

EHM.ImportBootstrap = function () {//Bootstrap 3.3.6
	EHM.ImportCss("/resources/mtx-admin/AdminLTE/bootstrap/css/bootstrap.min.css");
	EHM.ImportCss("/resources/mtx-admin/AdminLTE/dist/css/AdminLTE.min.css");//lte主题
	EHM.Import("/resources/mtx-admin/AdminLTE/bootstrap/js/bootstrap.min.js");
}
EHM.ImportCheck = function () {//checkbox
	EHM.ImportCss("/resources/mtx-admin/AdminLTE/plugins/iCheck/square/blue.css");
	EHM.Import("/resources/mtx-admin/AdminLTE/plugins/iCheck/icheck.min.js");
}
EHM.ImportBootStrapPopWin = function () {//BootStrapPopWin 弹出框
	EHM.ImportCss("/resources/mtx-admin/js/plugins/popwindow/bootstrapModalForYu.css");
	EHM.Import("/resources/mtx-admin/js/plugins/popwindow/BootStrapPopWin.js");
    EHM.Import("/resources/mtx-admin/js/plugins/popwindow/PopWin.js");
}
EHM.ImportZtree = function () {//ztree
	EHM.ImportCss("/resources/mtx-admin/js/plugins/ztree/zTreeStyle.css");
	EHM.Import("/resources/mtx-admin/js/plugins/ztree/jquery.ztree.core-3.5.js");
	EHM.Import("/resources/mtx-admin/js/plugins/ztree/jquery.ztree.excheck.min.js");
}
EHM.ImportIcheck = function () {//icheck 
	EHM.Import("/resources/mtx-admin/AdminLTE/plugins/iCheck/icheck.min.js");
	EHM.ImportCss("/resources/mtx-admin/AdminLTE/plugins/iCheck/all.css");
}
EHM.ImportSelect = function () {//select 选择框
	EHM.ImportCss("/resources/mtx-admin/AdminLTE/plugins/select2/select2.min.css");
	EHM.Import("/resources/mtx-admin/AdminLTE/plugins/select2/select2.full.min.js");
}
EHM.ImportUeditor = function () {//ueditor 
	EHM.Import("/resources/mtx-admin/js/plugins/ueditor/ueditor.config.js");
	EHM.Import("/resources/mtx-admin/js/plugins/ueditor/ueditor.all.js");
}
EHM.ImportCode = function () {//代码编辑器 

}
EHM.ImportDatepicker = function () {//日期选择控件
    EHM.ImportCss("/resources/mtx-admin/AdminLTE/plugins/datepicker/datepicker3.css");
    EHM.Import("/resources/mtx-admin/AdminLTE/plugins/datepicker/bootstrap-datepicker.js");
    EHM.Import("/resources/mtx-admin/AdminLTE/plugins/datepicker/locales/bootstrap-datepicker.zh-CN.js");
}
EHM.ImportTimepicker =function(){//时间选择控件
    EHM.ImportCss("/resources/mtx-admin/AdminLTE/plugins/timepicker/bootstrap-timepicker.css");
    EHM.Import("/resources/mtx-admin/AdminLTE/plugins/timepicker/bootstrap-timepicker.js");
}
EHM.ImportDateTimepicker =function(){//日期时间选择控件
    EHM.ImportCss("/resources/mtx-admin/AdminLTE/plugins/datetimepicker/bootstrap-datetimepicker.min.css");
    EHM.ImportCss("/resources/mtx-admin/AdminLTE/plugins/datetimepicker/bootstrap-datetimepicker-ext.css");
    EHM.Import("/resources/mtx-admin/AdminLTE/plugins/datetimepicker/bootstrap-datetimepicker.min.js");
    EHM.Import("/resources/mtx-admin/AdminLTE/plugins/datetimepicker/locales/bootstrap-datetimepicker.zh-CN.js");
}
EHM.ImportDateRangepicker =function(){//日期范围选择控件
    EHM.ImportCss("/resources/mtx-admin/AdminLTE/plugins/daterangepicker/daterangepicker-bs3.css");
    EHM.Import("/resources/mtx-admin/AdminLTE/plugins/daterangepicker/ext/moment.min.itim.js");
    EHM.Import("/resources/mtx-admin/AdminLTE/plugins/daterangepicker/daterangepicker.js");
}
EHM.ImportHighchart =function(){//highchart控件
    EHM.Import("/resources/mtx-admin/js/plugins/highchart/highcharts.js");
    EHM.Import("/resources/mtx-admin/js/plugins/highchart/highcharts-3d.js");
    EHM.Import("/resources/mtx-admin/js/plugins/highchart/highcharts-more.js");
    EHM.Import("/resources/mtx-admin/js/plugins/highchart/exporting.js");
    EHM.Import("/resources/mtx-admin/js/plugins/highchart/offline-exporting.js");
    EHM.Import("/resources/mtx-admin/js/plugins/highchart/no-data-to-display.js");
    EHM.Import("/resources/mtx-admin/js/plugins/highchart/highcharts-zh_CN.js");
    EHM.Import("/resources/mtx-admin/js/plugins/highchart/grid-light.js");
}
EHM.ImportEchart=function() {//echart控件
    EHM.Import("/resources/mtx-admin/js/plugins/echart/echarts.js");
}
EHM.ImportHeadUpload =function(){//头像上传插件
    EHM.ImportCss("/resources/mtx-admin/js/plugins/headimgupload/css/amazeui.min.css");
    EHM.ImportCss("/resources/mtx-admin/js/plugins/headimgupload/css/amazeui.cropper.css");
    EHM.ImportCss("/resources/mtx-admin/js/plugins/headimgupload/css/custom_up_img.css");
    EHM.Import("/resources/mtx-admin/js/plugins/headimgupload/js/amazeui.min.js");
    EHM.Import("/resources/mtx-admin/js/plugins/headimgupload/js/cropper.min.js");
    EHM.Import("/resources/mtx-admin/js/plugins/headimgupload/js/custom_up_img.js");
}
EHM.ImportFileInput =function(){//上传文件与单个图片
    EHM.ImportCss("/resources/mtx-admin/js/plugins/fileinput/css/fileinput.css");
    EHM.Import("/resources/mtx-admin/js/plugins/fileinput/js/fileinput.js");
    EHM.Import("/resources/mtx-admin/js/plugins/fileinput/js/locales/zh.js");
}
EHM.ImportWeather =function(){//天气插件
    EHM.ImportCss("/resources/mtx-admin/js/plugins/weather/css/weather.css");
    EHM.Import("/resources/mtx-admin/js/plugins/weather/js/jquery.leoweather.min.js");
}
EHM.ImportCalendar =function(){//首页日历插件
    EHM.ImportCss("/resources/mtx-admin/AdminLTE/plugins/fullcalendar/fullcalendar.min.css");
    EHM.Import("/resources/mtx-admin/AdminLTE/plugins/fullcalendar/moment.min.js");
    EHM.Import("/resources/mtx-admin/AdminLTE/plugins/fullcalendar/fullcalendar.min.js");
    EHM.Import("/resources/mtx-admin/AdminLTE/plugins/fullcalendar/zh-cn.js");
}
EHM.ImportSmartMenuCss =function(){
    EHM.ImportCss("/resources/mtx-admin/js/plugins/smartMenu/common/css/sccl.css");
    EHM.ImportCss("/resources/mtx-admin/js/plugins/smartMenu/common/skin/qingxin/skin.css");
    EHM.ImportCss("/resources/mtx-admin/js/plugins/smartMenu/smart-menu/smartMenu.css");
}
EHM.ImportSmartMenu =function(){//首页的menu与tab
    EHM.Import("/resources/mtx-admin/js/plugins/smartMenu/common/js/sccl.js");
    EHM.Import("/resources/mtx-admin/js/plugins/smartMenu/common/js/sccl-util.js");
    EHM.Import("/resources/mtx-admin/js/plugins/smartMenu/smart-menu/jquery-smartMenu.js");
}
EHM.ImportEasyUI =function(){//EasyUI
    EHM.ImportCss("/resources/mtx-admin/js/plugins/easyui/themes/metro/easyui.css");
    EHM.Import("/resources/mtx-admin/js/plugins/easyui/jquery.easyui.min.js");
}
EHM.ImportLayer =function(){//弹出框,loading等
    EHM.ImportCss("/resources/mtx-admin/js/plugins/layer/theme/default/layer.css");
    EHM.Import("/resources/mtx-admin/js/plugins/layer/layer.js");
}
EHM.ImportValidate =function(){//输入框校验
    EHM.ImportCss("/resources/mtx-admin/js/plugins/validate/css/bootstrapValidator.css");
    EHM.Import("/resources/mtx-admin/js/plugins/validate/js/bootstrapValidator.min.js");
    EHM.Import("/resources/mtx-admin/js/plugins/validate/js/language/zh_CN.js");
}
EHM.ImportInputTree =function() {//树形输入框
    EHM.Import("/resources/mtx-admin/js/plugins/inputtree/ajax-object.js");
    EHM.Import("/resources/mtx-admin/js/plugins/inputtree/ztree-object.js");
    EHM.Import("/resources/mtx-admin/js/plugins/inputtree/mytree.js");
}
EHM.ImportJquery223();//jquery要提前导入，不然 弹出框 就不能使用jQuery的内容了
EHM.ImportBootStrapPopWin();
EHM.ImportBootstrap();
EHM.ImportBootStrapPlugins();
EHM.ImportIcheck();
EHM.ImportValidate();