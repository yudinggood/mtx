function ismail(mail){//邮箱格式校验
	return(new RegExp(/^(?:[a-zA-Z0-9]+[_\-\+\.]?)*[a-zA-Z0-9]+@(?:([a-zA-Z0-9]+[_\-]?)*[a-zA-Z0-9]+\.)+([a-zA-Z]{2,})+$/).test(mail));
}
function isphone(phone){//电话校验
	if(phone.length!=11){
		return false;
	}
	return (new RegExp(/^(((13[0-9]{1})|159)+\d{8})$/).test(phone));
}
function ischina(cha){//是否包含中文
	if(escape(cha).indexOf("%u") >= 0){
		return false;
	}else
		return true;
}
function genTimestamp() {//得到当前时间戳
	return new Date().getTime();
}
function getContent() {//ueditor 方法
    var arr = [];
    arr.push(UE.getEditor('editor').getContent());
    return arr.join("");
}
//判断字符是否为空的方法
function isEmpty(obj){
    if(typeof obj == "undefined" || obj == null || obj == ""){
        return true;
    }else{
        return false;
    }
}
//获取点击事件的源对象
function eventParseObject(event){
    event = event ? event : window.event;
    var obj = event.srcElement ? event.srcElement : event.target;
    return $(obj);
}
//日期转换
Date.prototype.format = function(fmt) {
    var o = {
        "M+" : this.getMonth()+1, //月份
        "d+" : this.getDate(), //日
        "h+" : this.getHours()%12 == 0 ? 12 : this.getHours()%12, //小时
        "H+" : this.getHours(), //小时
        "m+" : this.getMinutes(), //分
        "s+" : this.getSeconds(), //秒
        "q+" : Math.floor((this.getMonth()+3)/3), //季度
        "S" : this.getMilliseconds() //毫秒
    };
    if(/(y+)/.test(fmt)){
        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
    }
    for(var k in o){
        if(new RegExp("("+ k +")").test(fmt)){
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
        }
    }
    return fmt;
};
//差了14h问题:后台是北京时间，js默认是标准时间
function dateToGMT(strDate){
    var dateStr=strDate.split(" ");
    var strGMT = dateStr[0]+" "+dateStr[1]+" "+dateStr[2]+" "+dateStr[5]+" "+dateStr[3]+" GMT+0800";
    var date = new Date(Date.parse(strGMT));
    return date;
}
//获取勾选的
function getCheckedNe(){
    var neArr = [];
    $("input[name=devCks]").each(function () {

        if (this.checked) {
            neArr.push(this.value);
        }
    });
    return neArr;
}
// 动态高度
function getHeight(muti) {
    return $(window).height() *muti;
}
//首页用户信息关闭,关闭父页面信息
document.onclick=function(){
    if($("#user-menu").length > 0){//判断元素是否存在
        return;
    }
    if($("#user-menu", window.top.document).hasClass("open")){
        $("#user-menu", window.top.document).removeClass("open");
    }

};










