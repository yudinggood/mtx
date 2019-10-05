<!--system/admin/main.jsp-->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags"  prefix="spring" %>
<!DOCtype html>
<html>
<head>
    <%@ include file="../../base/base.jsp"%>


    <style>

    </style>
    <script>

    </script>
</head>
<body>

<section class="content">
    <div class="row">
        <div class="col-sm-9">
            <div class="tab-content">
                <ul class="timeline " id="activeView" style="margin-top: 0px;margin-left: 0px;font-size: 12px;color: #000;">

                </ul>
            </div>
        </div>
        <div class="col-sm-3">

        </div>
    </div>
</section>


<script>
    var Main = {

    };
    $(function () {
        initView();

    });

    function initView(){
        var url='${basePath}/system/timeline';
        $.get(url,function(data){
            var today = data.today;
            var yesterday =data.yesterday;
            var thisWeek = data.thisWeek;
            var lastWeek = data.lastWeek;
            var thisMonth= data.thisMonth;
            var lastMonth = data.lastMonth;
            var more =  data.more;
            if(today.length>0){
                var timeLi = $("<li class='time-label' style='margin: 10px 0px;'><span class='bg-red' style='width: 70px;text-align: center'>今&nbsp;天<i class='fa fa-fw  fa-caret-down' status='0,today' onclick='showOrHide(this)'></i></span></li>");
                var li = $("<li style='margin: 0' data-index='today' ></li>")
                var div =$("<div class='timeline-item row' style='margin-left:2px;background: #F0F0F0;'></div>")
                initInsideView(today,0,div);
                li.append(div);
                $("#activeView").append(timeLi);
                $("#activeView").append(li);
            }if(yesterday.length>0){
                var timeLi = $("<li class='time-label' style='margin: 10px 0px;'><span class='bg-green' style='width: 70px;text-align: center'>昨&nbsp;天<i class='fa fa-fw  fa-caret-down' status='0,yesterday' onclick='showOrHide(this)'></i></span></li>");
                var li = $("<li style='margin: 0' data-index='yesterday' ></li>");
                var div =$("<div class='timeline-item row' style='margin-left:2px;background: #F0F0F0;'></div>")
                initInsideView(yesterday,0,div);
                li.append(div);
                $("#activeView").append(timeLi);
                $("#activeView").append(li);
            }if(thisWeek.length>0){
                timeLi = $("<li class='time-label' style='margin: 10px 0px;'><span style='background-color: #D2D6DE;width: 70px;text-align: center'>本&nbsp;周<i class='fa fa-fw  fa-caret-down' status='0,thisWeek' onclick='showOrHide(this)'></i></span></li>");
                var li = $("<li style='margin: 0' data-index='thisWeek' ></li>")
                var div =$("<div class='timeline-item row' style='margin-left:2px;background: #F0F0F0;'></div>")
                initInsideView(thisWeek,1,div);
                li.append(div);
                $("#activeView").append(timeLi);
                $("#activeView").append(li);
            }if(lastWeek.length>0){
                timeLi = $("<li class='time-label' style='margin: 10px 0px;'><span style='background-color: #D2D6DE;width: 70px;text-align: center'>上&nbsp;周<i class='fa fa-fw  fa-caret-down' status='0,lastWeek' onclick='showOrHide(this)'></i></span></li>");
                var li = $("<li style='margin: 0' data-index='lastWeek' ></li>")
                var div =$("<div class='timeline-item row' style='margin-left:2px;background: #F0F0F0;'></div>")
                initInsideView(lastWeek,1,div);
                li.append(div);
                $("#activeView").append(timeLi);
                $("#activeView").append(li);
            }if(thisMonth.length>0){
                timeLi = $("<li class='time-label' style='margin: 10px 0px;'><span style='background-color: #D2D6DE;width: 70px;text-align: center'>本&nbsp;月<i class='fa fa-fw  fa-caret-down' status='0,thisMonth' onclick='showOrHide(this)'></i></span></li>");
                var li = $("<li style='margin: 0' data-index='thisMonth' ></li>")
                var div =$("<div class='timeline-item row' style='margin-left:2px;background: #F0F0F0;'></div>")
                initInsideView(thisMonth,1,div);
                li.append(div);
                $("#activeView").append(timeLi);
                $("#activeView").append(li);
            }if(lastMonth.length>0){
                timeLi = $("<li class='time-label' style='margin: 10px 0px;'><span style='background-color: #D2D6DE;width: 70px;text-align: center'>上&nbsp;月<i class='fa fa-fw  fa-caret-down' status='0,lastMonth' onclick='showOrHide(this)'></i></span></li>");
                var li = $("<li style='margin: 0' data-index='lastMonth' ></li>")
                var div =$("<div class='timeline-item row' style='margin-left:2px;background: #F0F0F0;border: solid 1px #DDDDDD'></div>")
                initInsideView(lastMonth,1,div);
                li.append(div);
                $("#activeView").append(timeLi);
                $("#activeView").append(li);
            }if(more.length>0){
                timeLi = $("<li class='time-label' style='margin: 10px 0px;'><span style='background-color: #D2D6DE;width: 70px;text-align: center'>更&nbsp;多<i class='fa fa-fw  fa-caret-down' status='0,more' onclick='showOrHide(this)'></span></i></li>");
                var li = $("<li style='margin: 0' data-index='more' ></li>")
                var div =$("<div class='timeline-item row' style='margin-left:2px;background: #F0F0F0;'></div>")
                initInsideView(more,1,div);
                li.append(div);
                $("#activeView").append(timeLi);
                $("#activeView").append(li);
            }

        },"json");
    }
    function  initInsideView(dataArray,index,div){
        for(var i=0;i<dataArray.length;i++){
            var leveCode = dataArray[i].alarm_level_code;
            var pImg = $("<div class='col-sm-1'  style='width: 5%;text-align: center;' ></div>");
            var pInfo = $("<div class='col-sm-11' style='width: 94%;border-left:solid 3px;margin-bottom: 5px'></div>");
            if(dataArray[i].type==5){
                pInfo.css("border-left-color","red");
            }



            var alarm_icon="glyphicon glyphicon-exclamation-sign";

            var text =  $("<h4 class='timeline-header no-border' style='padding-left: 0px;padding-right: 0px;'></h4>");
            var pInfo_a = $("<a style='text-overflow:ellipsis;cursor: hand;color:#000;font-weight: normal;font-family: 微软雅黑;font-size: 12px;'  data-neid = '"+dataArray[i].ne_id+"' id='alert_"+dataArray[i].ne_id+"' href='javascript:void(0);' class='product-title' ></a>");
            pInfo_a.text("明细："+dataArray[i].detail);
            if(dataArray[i].type==1){//文章类型
                pInfo_a.attr("href","javascript:goDetail('"+dataArray[i].bizId+"')");
            }

            var pInfo_div_a=$("<div class='col-sm-7' style='overflow:hidden;  text-overflow:ellipsis; white-space:nowrap;margin-bottom: 5px;margin-top: 5px'></div>");


            pInfo_div_a.append(pInfo_a);

            var pInfo_desc_ip_div=$("<div class='col-sm-3' style='width: 20%;margin-bottom: 5px;margin-top: 5px;'></div>");
            var pInfo_desc_ip_a=$("<a style='color: #000000;font-size: 12px;cursor: hand;' id='alertSpan_"+dataArray[i].ne_id +"' onclick='deviceView(\"" + dataArray[i].top_ne_id + "\")'>"+dataArray[i].title+"</a>");
            pInfo_desc_ip_div.append(pInfo_desc_ip_a);

            if(index==0){
                var time = $("<span style='font-size: 12px;'>&nbsp;"+dataArray[i].dateString.split(" ")[1]+"</span>");
            }else{
                var time = $("<span style='font-size: 12px;'>&nbsp;"+dataArray[i].dateString+"</span>");
            }
            text.append(pImg);
            text.append(pInfo);
            var pInfo_text = $("<div class='col-sm-10' style='width: word-wrap:break-word;word-break:break-all;'></div>");
            var pInfo_time =$("<div class='col-sm-2 text-right' style='margin-bottom: 5px;margin-top: 5px'></div>");
            $(pInfo_time).append(time);
            $(pInfo_text).append(pInfo_desc_ip_div);
            $(pInfo_text).append(pInfo_div_a);

            pInfo.append(pInfo_text);
            pInfo.append(pInfo_time);

            var alarmStatus = dataArray[i].alarm_status;
            var innerDiv=$("<div style='width: 100%;padding: 0 auto;margin: 1px 0px;min-height: 24px;'></div>")
            innerDiv.append(text);
            div.append(innerDiv);
            div.css({background:"rgba(200, 200, 200, 0.25)"})
        }
    }
    function showOrHide(button){
        var status = $(button).attr("status").split(',')[0];
        var time = $(button).attr("status").split(',')[1];
        if(status=='0'){
            $("[data-index='"+time+"']").hide();
            $(button).attr("class","fa fa-fw fa-caret-right");
            $(button).attr("status","1,"+time);
        }
        if(status=='1'){
            $("[data-index='"+time+"']").show();
            $(button).attr("class","fa fa-fw  fa-caret-down");
            $(button).attr("status","0,"+time);
        }
    }

</script>
</body>
</html>
