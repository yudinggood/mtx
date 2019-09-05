//窗体对象
//EHM.initLazyCss("subModal.css");

//EHM.ImportCss("/resource/bootstrap/css/bootstrap.min.css");
EHM.ImportCss("/resources/mtx-admin/js/plugins/popwindow/subModal.css");
EHM.Import("/resources/mtx-admin/js/plugins/popwindow/HistoryStack.js");
EHM.Import("/resources/mtx-admin/js/plugins/popwindow/PopWinDragDrop.js");
EHM.Import("/resources/mtx-admin/js/plugins/popwindow/dmUtils.js");
//EHM.Import("EHM/Toolkit/PopWin/CloseButtonEvent.js");
var $$PopWin = function (id) {
    this.parentBody = document.getElementsByTagName('BODY')[0];
    this.parentElement = document.getElementsByTagName('BODY')[0];
    if(this.parentElement.clientHeight<10){
        this.parentElement=document.documentElement;
    }
    this.closeTimeout=null;
    this.popMask = null;
    this.returnCallBack = {};
    this.id = id;
    this.eventListeners = {};
    this.coordinate = {moveable: false, enableMove: true, x0: 0, y0: 0, x1: 0, y1: 0};
    this.visable = true;
    this.indexStep=100;
    this.singleSelect=false;
}
$$PopWin.prototype.init = function (id, index) {
    this.initMask(id, index);
    this.initWindow(id, index);
}
$$PopWin.prototype.initMask = function (id, index) {
    this.popMask = $$('div', "popupMask" + id, "popupMask text-unselectable");
    var ifrm =this.iframe= $$("iframe");
    ifrm.className="text-unselectable"
    ifrm.src = "javascript:false;";
    ifrm.scrolling = "no";
    ifrm.style.position = "absolute";
    ifrm.style.visibility = "inherit";
    ifrm.style.top = "0px";
    ifrm.style.left = "0px";
    ifrm.style.width = "100%";
    ifrm.style.height = "100%";
    ifrm.style.overflowX= "hidden";
    this.popMask.style.zIndex = (parseInt(index) * this.indexStep + 1000) + "";
    ifrm.style.zIndex = (parseInt(index) * this.indexStep + 1001) + "";
    domBase.dom.setOpacity(ifrm,0.91);
    //domBase.dom.setOpacity(this.popMask,0.91);
    this.popMask.appendChild(ifrm);
    this.parentBody.appendChild(this.popMask)
    //this.popMask.style.backgroundColor = "#ff0000";
}
$$PopWin.prototype.restoreIndex = function (zIndex) {
    this.popMain.style.zIndex = (parseInt(zIndex) * this.indexStep + 1002) + "";
    this.popMask.style.zIndex = (parseInt(zIndex) * this.indexStep + 1000) + "";
}
$$PopWin.prototype.initWindow = function (id, index) {
//    this.popWindow = $$('div', "popupContainer" + id,"popup-Container");
//    this.popWindow.innerHTML = '';
    //-----以下是头部构造
    this.popMain = $$('div', "ext-comp-1001" + id, "panel panel-default panel-popwin");
    this.popMain.style.zIndex = (parseInt(index) * this.indexStep + 1002) + "";
    this.popMain.style.display = "block";
    this.popMain.style.visibility = "visible";
    this.popMain.style.position = "absolute";

    var popBackGround = $$('div', "", "panel-background");
    this.popMain.appendChild(popBackGround);

    var popMainTop = $$('div', "", "panel-heading-tin panel-heading ");
    //var popMainTopTr = $$('div', "", "x-window-tr");
    //popMainTop.appendChild(popMainTopTr);
    //var popMainTopTc = $$('div', "", "x-window-tc");
    //popMainTopTr.appendChild(popMainTopTc);

    var popMainTopTcHeader =this.popMainTopTcHeader= $$('div', "ext-gen15" + id, "x-window-header text-unselectable x-window-draggable");
//    popMainTopTcHeader.style = "MozUserSelect: none; KhtmlUserSelect: none;cursor: move;";
    popMainTopTcHeader.style.cursor = "move";
    popMainTopTcHeader.unselectable = "on";
    popMainTopTcHeader.style.position="relative"
    //$("create").style.position="relative";
    popMainTopTcHeader.style.top="-1px";
    popMainTopTcHeader.style.left="1px";
    //popMainTopTcHeader.style.padding="1px";
    popMainTop.appendChild(popMainTopTcHeader);

    this.popMainTopTcHeaderTool = $$('div', "popupTool" + id, "x-tool");
    popMainTopTcHeader.appendChild(this.popMainTopTcHeaderTool);
//    if(showCloseBox == null || showCloseBox == true){
    this.createCloseButton();
//    }
//    if(showMinBox == null || showMinBox == true){
    this.createMinButton();
//    }


    this.popMainTopTcHeaderTitle = $$('div', "popupTitle" + id, "x-window-header-text text-unselectable", "---");
    this.popMainTopTcHeaderTitle.style.cursor = "move";
    this.popMainTopTcHeaderTitle.unselectable = "on";
    popMainTopTcHeader.appendChild(this.popMainTopTcHeaderTitle);
   ___PopWinDragDrop.Register(this.popMainTopTcHeaderTitle, this.popMain)


    var self=this;
    addEvent(this.popMainTopTcHeaderTitle, "mouseup", function (e) {
      if(!!!self.singleSelect)  self.setMaskMinSize();
    });
    addEvent(this.popMainTopTcHeaderTitle, "mousedown", function (e) {
        if(!!!self.singleSelect) self.setMaskMaxSize();
        //domBase.util.Event.setCapture(self.popMainTopTcHeaderTitle);
        //(self.iframe.contentWindow).captureEvents(Event.MOUSEMOVE|Event.MOUSEUP);
    });
    //-----以下是内容展示区构造
    var popMainBody = $$('div', "ext-gen16" + id, "panel-body");
    this.popMainBodyContent = $$('div', "popBodyBox" + id);
    this.popMainBodyContent.style.padding="1px auto"
    popMainBody.appendChild(this.popMainBodyContent);
    popMainBody.style.padding="1px auto";
    popMainBody.style.paddingLeft=popMainBody.style.paddingRight="1px";
    popMainBody.style.paddingTop=popMainBody.style.paddingBottom="1px";
    //-----以下是底部展示区构造

    this.popMain.appendChild(popMainTop);
    this.popMain.appendChild(popMainBody);
    //this.popMain.appendChild(popMainBottom);
//    this.popWindow.appendChild(this.popMain);


    this.parentBody.appendChild(this.popMain)
}
$$PopWin.prototype.createCloseButton=function(){//btn-danger
     this.popMainTopTcHeaderClose = $$('div', "ext-gen59" + this.id, "quick-tool-btn ","<a><span class='fa fa-times' style='font-weight:500;'></span></a>");
    this.popMainTopTcHeaderTool.appendChild(this.popMainTopTcHeaderClose);
    var handle_close = dmUtils.bind(this, function (evt) {
        this.close();
    });
    this.eventListeners["close_click"] = handle_close;
    addEvent(this.popMainTopTcHeaderClose, "click", handle_close);
}
$$PopWin.prototype.createMinButton=function(){
    this.popMainTopTcHeaderMinBt = $$('div', "ext-gen60" + this.id, "quick-tool-btn btn-info","<a><span class='fa fa-minus' style='line-height: 40px;font-weight:normal;'></span></a>");
    this.popMainTopTcHeaderTool.appendChild(this.popMainTopTcHeaderMinBt);
    var handle_min = dmUtils.bind(this, function (evt) {this.hidden();});
    this.eventListeners["min_click"] = handle_min;
    addEvent(this.popMainTopTcHeaderMinBt, "click", handle_min);

}
$$PopWin.prototype.show = function (Title, url, width, height, returnFunc, showCloseBox, showScrolling, showMinBox) {
    //动态插入iframe
    var scrolling = (showScrolling != null && showScrolling) ? "scroll" : "no";
    var iframestring = '<iframe  src="" style="width:100%;height:100%;background-color:transparent;" scrolling="' + scrolling + '" frameborder="0" allowtransparency="true" id="popupFrame' + this.id + '" name="popupFrame" width="100%" height="100%"></iframe>';
    this.popMainBodyContent.innerHTML = iframestring;
    this.gPopFrame = document.getElementById("popupFrame" + this.id);
    if (width >= this.parentElement.clientWidth) {
        width = this.parentElement.clientWidth * 0.95
    }
    else {
        width = width + 30;
    }
    if (height >= document.documentElement.clientHeight) {
        height = document.documentElement.clientHeight * 0.95
    }
    this.popMainTopTcHeaderTitle.innerHTML = Title;
    // show or hide the window close widget
    showMinBox=false;
    this.popMainTopTcHeaderClose.style.display = (showCloseBox == null || showCloseBox == true)?"block":"none";
    this.popMainTopTcHeaderMinBt.style.display = (showMinBox != null && showMinBox == true)?"block":"none";
    this.popMask.style.display = "block";
    this.popMain.style.display = "block";

    this.centerPopWin(width, height);
    this.popMain.style.width = width + "px";
    //alert(width)
    this.gPopFrame.style.height = height + "px";
    this.setMaskMinSize();
    this.gPopFrame.contentWindow.location.href=url;
    //alert(height)
//    this.gPopFrame.src = url;
}
$$PopWin.prototype.changeTitle = function (title) {
    this.popMainTopTcHeaderTitle.innerHTML = title;
}
$$PopWin.prototype.setCloseTimeout = function (sc) {
    var self=this;
    this.closeTimeout=window.setTimeout(function(){self.close();},sc*100);
};
$$PopWin.prototype.cancelCloseTimeout = function () {
    var self=this;
   if(self.closeTimeout) window.clearTimeout(self.closeTimeout);
}
$$PopWin.prototype.destroyEvent = function () {
    removeEvent(this.popMainTopTcHeaderClose, "click", this.eventListeners["close_click"]);
    ___PopWinDragDrop.unRegister(this.popMainTopTcHeaderTitle);
}
$$PopWin.prototype.close = function () {
    this.cancelCloseTimeout();
    PopWin.deletePopWinListItem(this.id);
    this.destroy();
}
$$PopWin.prototype.destroy = function () {
    this.destroyEvent();
    this.gPopFrame.src = "javascript:false";
    this.parentBody.removeChild(this.popMask);
    this.parentBody.removeChild(this.popMain);
    PopWin.removePopwin(this);
}
$$PopWin.prototype.hidden = function () {
    PopWin.checkSecondPopWinListItem();
    this.doVisible(false);
}
$$PopWin.prototype.doVisible = function (f) {
    this.visable = f;
    //alert(this.popMask)
    if (this.visable) {
        this.popMain.style.display = "block";
        this.popMain.style.visibility = "visible";
        this.popMask.style.display = "block";
    } else {
        this.popMain.style.display = "none";
        this.popMain.style.visibility = "hidden";
        this.popMask.style.display = "none";
    }
}
$$PopWin.prototype.centerPopWin = function (width, height) {
    if (width == null || isNaN(width)) {
        width = this.popMain.offsetWidth;
    }
    if (height == null) {
        height = this.popMain.offsetHeight;
    }

    //var theBody = document.documentElement;
    //this.parentBody.style.overflow = "hidden";
    this.parentBody.style.overflowY = "hidden";
    var scTop = parseInt(this.parentElement.scrollTop, 10);
    var scLeft = parseInt(this.parentElement.scrollLeft, 10);
    this.popMask.style.top = scTop + "px";
    this.popMask.style.left = scLeft + "px";
//this.setMaskSize();
    scTop = 0;

    var fullHeight = this.getViewportHeight();
    var fullWidth = this.getViewportWidth();

    this.popMain.style.top = (scTop + ((fullHeight - (height + 60)) / 2)) + "px";
    this.popMain.style.left = (scLeft + ((fullWidth - width) / 2)) + "px";

    this.parentBody.style.overflowY = "auto";
}
$$PopWin.prototype.getViewportHeight = function () {
    if (window.innerHeight != window.undefined) return window.innerHeight;
    if (document.compatMode == 'CSS1Compat') return document.documentElement.clientHeight;
    if (this.parentBody) return this.parentBody.clientHeight;
    return window.undefined;
}
$$PopWin.prototype.getViewportWidth = function () {
    if (window.innerWidth != window.undefined) return window.innerWidth;
    if (document.compatMode == 'CSS1Compat') return document.documentElement.clientWidth;
    if (this.parentBody) return this.parentBody.clientWidth;
    return window.undefined;
}
$$PopWin.prototype.adjustPopWin = function (x, y) {
    var left = parseInt(this.popMain.style.left);
    var top = parseInt(this.popMain.style.top);
    this.popMain.style.left = (left + x) + "px";
    this.popMain.style.top = (top + y) + "px";
}

$$PopWin.prototype.setMaskSize = function () {
    var popHeight = 0, popWidth = 0;
    var fullHeight = this.getViewportHeight();
    var fullWidth = this.getViewportWidth();
    // Determine what's bigger, scrollHeight or fullHeight / width
    if (fullHeight > this.parentElement.scrollHeight) {
        popHeight = fullHeight;
    }
    else {
        popHeight = this.parentElement.scrollHeight;
    }

    if (fullWidth > this.parentElement.scrollWidth) {
        popWidth = fullWidth;
    }
    else {
        popWidth = this.parentElement.scrollWidth;
    }

//    this.popMask.style.height = popHeight + "px";
//    this.popMask.style.width = popWidth + "px";

    //this.popMask.style.height = this.popMain.clientHeight + "px";
    //this.popMask.style.width = this.popMain.style.width;
    //this.popMask.style.top = this.popMain.style.top;
    //this.popMask.style.left = this.popMain.style.left;
    this.popMask.style.height =  "100%";
    this.popMask.style.width =  "100%";
    //this.popMask.style.display = "none";
    this.popMask.style.display = "block";
}
$$PopWin.prototype.setMaskMaxSize=function(){
    this.popMask.style.top = "0px";
    this.popMask.style.left = "0px";
    this.popMask.style.height =  "100%";
    this.popMask.style.width =  "100%";
}
$$PopWin.prototype.setSingleSelect=function(f){
    this.singleSelect=!!f;
    (!!f)?this.setMaskMaxSize():this.setMaskMinSize();
}
$$PopWin.prototype.setMaskMinSize=function(){
    this.popMask.style.height = this.popMain.clientHeight + "px";
    this.popMask.style.width = this.popMain.style.width;
    this.popMask.style.top = this.popMain.style.top;
    this.popMask.style.left = this.popMain.style.left;
}
var PopWin = EHM.PopWinFactory = new function () {
    var popWinStack = {}, self = this, index = 0;
    var popWinId = new Date().valueOf() + "";
    var createNewPopWin = function (f,Title,popId) {
        //alert(document.compatMode)
        var genId =popId||( new Date().valueOf() + "");
        var pop = new $$PopWin(genId);
        index = index + 1;
        popWinStack[genId] = {id: genId, index: index, obj: pop};
        pop.init(genId, index);
        pop.restoreIndex(index);
        addEvent(pop.popMain, "mousedown", function (e) {
            var ev = domBase.util.Event.getEvent(e);
            var ele = domBase.util.Event.getTarget(ev);
            var popID = "";
            while (ele.id.indexOf("ext-comp-1001") < 0) {
                if (ele.id.indexOf("ext-comp-1001") < 0)ele = ele.parentNode;
                popID = (ele.id);
            }
            var popId = popID.replace("ext-comp-1001", "");
            self.checkPopWinListItem(popId);//将此弹出框对象所对应列表条目选中
            self.visiblePopWin(popId);//将此弹出框对象置为可见，并置前；

//            domBase.util.Event.stopEvent(e);
        });

        //if (f) {
        //    var popList = self.getPopWinListInst();
        //    popList.createItem({id: pop.id + "_Itm", name: Title});
        //    self.checkPopWinListItem(pop.id);
        //}
        return pop;
    }
    //将传入的弹出框包装对象与当前页面的最上层对象比对，并置前
    this.exchangePositionWithFirst = function (popObj) {
        var top = self.getCurrentPop(1);
        if (popObj.obj.id == top.obj.id)return;
//        popObj.obj.popMain.style.zIndex = top.obj.popMain.style.zIndex + 1;
//        popObj.obj.popMain.index = top.obj.popMain.index + 1;
        var zIndex =index= top.index + 1;

        popObj.obj.restoreIndex(zIndex)
        popObj.index = zIndex;

    }
    this.removePopwin = function (pop) {
        popWinStack[pop.id] = null;
        delete popWinStack[pop.id];
        pop = null;
        delete  pop;
        domBase.util.GC();
    }

    this.close = function (win) {
        var pop = null;
        if (win) {
            for (var ob in popWinStack) {
                var _pop = popWinStack[ob];
                var _popWin = _pop.obj
                if (_popWin.gPopFrame.contentWindow == win) {
                    pop = _popWin;
                    break;
                }
            }
        } else {
            pop = self.getCurrentPop();
        }
        if (!!pop) {
            pop.close();
        }
    }
    this.getPopWinStackArr = function () {
        var arr = [];
        for (var index in popWinStack) {
            arr.push(popWinStack[index])
        }
        return arr;
    }
    this.getCurrentPop = function (f) {
        var max = null;
        for (var ob in popWinStack) {
            if (max == null)max = popWinStack[ob];
            if (popWinStack[ob].index > max.index)max = popWinStack[ob];
        }
        if (f) {
            return max == null ? null : max;
        } else {
            return max == null ? null : max.obj;
        }

    }
    //获取位置第二高的弹出框对象
    this.getSecondPop = function () {
        var max = self.getCurrentPop();
        var sec = null;
        var arr = [];
        for (var index in popWinStack) {
            if (max.id != index) arr.push(popWinStack[index])
        }
        for (var k = 0; k < arr.length; k++) {
            if (sec == null)sec = arr[k];
            if (arr[k].index > sec.index)sec = arr[k];
        }

        return sec == null ? null : sec;

    }
    this.getPopWin = function (id) {
        return popWinStack[id];
    };

    this.getPopWinListInst = function () {
        if (self.popWinList == null) {
            //self.popWinList = new $$popWinList(self);
            //self.popWinList.init("popWinList_" + popWinId);
        }
        return self.popWinList;
    };
    this.getPopWinList = function () {
        return self.popWinList;
    };
    //调用展示列表，获取列表项，并将列表项置为已选中状态
    this.checkPopWinListItem = function (popId) {
        var popList = self.getPopWinList();
        if(popList)popList.checkItem(popId + "_Itm")

    }
    this.checkSecondPopWinListItem = function () {
        var sec = self.getSecondPop();

        var itemId = "";
        if (sec) {
            var popId = sec.id;
            if (sec.obj.visable) {
                itemId = popId + "_Itm";
            }
        }
        var popList = self.getPopWinList();
        if(popList)popList.checkItem(itemId);

    }
    this.changeTitle = function (win, title) {
        var pop = null;
        if (win) {
            for (var ob in popWinStack) {
                var _pop = popWinStack[ob];
                var _popWin = _pop.obj
                if (_popWin.gPopFrame.contentWindow == win) {
                    pop = _popWin;
                    break;
                }
            }
        } else {
            pop = self.getCurrentPop();
        }
        if (!!pop) {
            pop.changeTitle(title);
            var popList = self.getPopWinList();
           if(popList) popList.changeItemTitle(pop.id + "_Itm", title)
        }

    }
    //调用展示列表，获取列表项，并将列表项置删除
    this.deletePopWinListItem = function (popId) {
        var popList = self.getPopWinList();
        if(popList)popList.deleteItem(popId + "_Itm")

    }
    //根据ID设置此弹出框页面为可见，并置前
    this.visiblePopWin = function (popId) {
        var pop = self.getPopWin(popId);
       if(!!pop){
          if(pop["obj"]) pop.obj.doVisible(true);
           self.exchangePositionWithFirst(pop);
       }
    }
    //根据ID设置此弹出框页面为可见，并置前
    this.deletePopWinById = function (popId) {
        var pop = self.getPopWin(popId);
        pop.obj.destroy();
    }
    this.show = function (Title, url, width, height, returnFunc, showCloseBox, showScrolling, showPopList,popId) {
        var showMinBox=(!!showPopList);
        var pop = self.getCurrentPop();
        if (!pop)pop = createNewPopWin((!!showPopList),Title,popId);
        pop.show(Title, url, width, height, returnFunc, showCloseBox, showScrolling,showMinBox);
        return pop;
//        self.showPopWinList();
    }

    this.showNew = function (Title, url, width, height, returnFunc, showCloseBox, showScrolling, showPopList,popId) {
        var showMinBox=(!!showPopList);
        var pop = createNewPopWin((!!showPopList),Title,popId);

        pop.show(Title, url, width, height, returnFunc, showCloseBox, showScrolling,showMinBox);
//        if (!!showPopList) {

        var xZom = 15, yZom = 20;
        var popArr = self.getPopWinStackArr();
        var arrSize = popArr.length;
        pop.adjustPopWin(arrSize * xZom, arrSize * yZom);
        return pop;
    }

}
var showPopWin=window.showPopWin||function(){
            PopWin.showNew.apply(this,arguments);
        }
var $$popWinList = function (p) {
    this.popWinFactory = p;
    this.popWinListBt;
    this.itemCache = {};
    this.parentBody = document.getElementsByTagName('BODY')[0];
    this.parentElement= document.getElementsByTagName('BODY')[0];
    if(this.parentElement.clientHeight<10){
        this.parentElement=document.documentElement;
    }
    this.eventListeners = {};
    this.popWinListVisible = true;
    this.id = "";
}
$$popWinList.prototype.init = function (id) {
    this.id = id;
    this.popWinListBt = $("myPopwin");
//        $$("div", id + "_BT", "x-pop-win-list-bt");
    this.popWinListBt.style.display = "block";
    this.popWinListBt.style.visibility = "visible";
//    this.popWinListBt.style.position = "absolute";
//    this.popWinListBt.style.zIndex = 999999;
//    this.popWinListBt.style.top = "600px";
//    this.popWinListBt.style.left = "900px";
    this.popWinListBt.title = "点击获取您的弹出窗口";
//    header_right_div_img

    this.popWinListBtNum =$("myPopwinNum")
//        $$("div", id + "_BT_NUM", "x-pop-win-list-bt-num");
//    this.popWinListBt.appendChild(this.popWinListBtNum);

    this.popWinListWin = $$("div", id + "_WIN", "x-pop-win-list-win");
    this.popWinListWin.style.display = "block";//"none";
    this.popWinListWin.style.visibility = "visible";
    this.popWinListWin.style.position = "absolute";
    this.popWinListWin.style.zIndex = 999999;
    this.popWinListWin.style.top = "-200px";
    this.popWinListWin.style.left = "900px";
//    this.parentBody.appendChild(this.popWinListBt);
    this.parentBody.appendChild(this.popWinListWin);
    this.popWinListTopLeft = $$("div", "", "x-pop-win-list-win-top-left");
    this.popWinListTopRight = $$("div", "", "x-pop-win-list-win-top-right");
    this.popWinListTopline = $$("div", "", "x-pop-win-list-win-top-line");
    this.popWinListTopLeft.appendChild(this.popWinListTopRight);
    this.popWinListTopRight.appendChild(this.popWinListTopline);

    this.popWinListBottomLeft = $$("div", "", "x-pop-win-list-win-bottom-left");
    this.popWinListBottomRight = $$("div", "", "x-pop-win-list-win-bottom-right");
    this.popWinListBottomline = $$("div", "", "x-pop-win-list-win-bottom-line");
    this.popWinListBottomLeft.appendChild(this.popWinListBottomRight);
    this.popWinListBottomRight.appendChild(this.popWinListBottomline);


    this.popWinListConLeft = $$("div", "", "x-pop-win-list-win-con-left");
    this.popWinListConRight = $$("div", "", "x-pop-win-list-win-con-right");
    this.popWinListCon = $$("div", "", "x-pop-win-list-win-con");
    this.popWinListConLeft.appendChild(this.popWinListConRight);
    this.popWinListConRight.appendChild(this.popWinListCon);

    this.popWinListWin.appendChild(this.popWinListTopLeft);
    this.popWinListWin.appendChild(this.popWinListConLeft);
    this.popWinListWin.appendChild(this.popWinListBottomLeft);
    this.popWinListWin.style.display = "none";
    domBase.dom.setOpacity(this.popWinListWin, 90);
    this.doAdjustListBt();
    this.initEvent();
}
$$popWinList.prototype.initEvent = function () {
    this.addEvent("mousedown", this.popWinListBt, "showPopList");
//    this.addEvent("mouseout", this.popWinListBt, "hiddenPopList");
//    this.addEvent("mouseout", this.popWinListWin, "hiddenPopList");
}
$$popWinList.prototype.restoreItemNum = function () {
    var arr=this.getPopWinStackArr();
    this.popWinListBtNum.innerHTML=arr.length;
}
$$popWinList.prototype.addEvent = function (ev, tag, fun) {
    var Handle = dmUtils.bind(this, function () {
        this[fun || ev]();
    });
    this.eventListeners[fun || ev] = Handle;
    addEvent(tag || this.popWinListBt, ev, Handle);
}
$$popWinList.prototype.showPopList = function (e) {
//    alert(1)
    this.popWinListWin.style.display = this.popWinListVisible ? "none" : "block";
    this.popWinListVisible = !this.popWinListVisible;
}
$$popWinList.prototype.hiddenPopList = function (e) {
//    var ev = domBase.util.Event.getEvent(e);
//    var tag = domBase.util.Event.getTarget(ev);
//    var realTag = null;
//    while (tag.tagName.toUpperCase() != "BODY") {
//        if (tag.id.indexOf(this.id) >= 0) {
//            realTag = tag;
//            break;
//        }
//        tag = tag.parentNode;
//    }
//    this.popWinListWin.style.display = (realTag != null) ? "block" : "none";
    this.popWinListWin.style.display =  "none";
}
$$popWinList.prototype.getViewportHeight = function () {
    if (window.innerHeight != window.undefined) return window.innerHeight;
    if (document.compatMode == 'CSS1Compat') return document.documentElement.clientHeight;
    if (this.parentElement) return this.parentElement.clientHeight;
    return window.undefined;
}
$$popWinList.prototype.getViewportWidth = function () {

    if (window.innerWidth != window.undefined) return window.innerWidth;
    if (document.compatMode == 'CSS1Compat') return document.documentElement.clientWidth;
    if (this.parentElement) return this.parentElement.clientWidth;
    return window.undefined;
}
$$popWinList.prototype.doAdjustListBt = function () {
//    width: 32px;
//    height: 28px;
    var height = 28, width = 60, popWinListWinWidth = 290;
    //var theBody = document.documentElement;
    this.parentBody.style.overflowY = "hidden";

//    var scTop = parseInt(this.parentBody.scrollTop);
//    var scLeft = parseInt(this.parentBody.scrollLeft);
//    var fullHeight = this.getViewportHeight();
    var fullWidth = this.getViewportWidth();

//    this.popWinListBt.style.top = (fullHeight - height) + "px";//(scTop + ((fullHeight - (height + 20)) / 2)) + "px";
//    this.popWinListBt.style.left = (fullWidth - width) + "px";//(scLeft + ((fullWidth - width) / 2)) + "px";
    this.popWinListWin.style.left = (fullWidth - width - popWinListWinWidth) + "px";

    this.parentBody.style.overflowY = "auto";
}
$$popWinList.prototype.doAdjustListWin = function () {
    var itemArr = this.getPopWinStackArr();
    var yZom = 33, paddingFlow = 22;
//    this.popWinListBt.style.top.replace("px", "")
    var top = parseInt(40);
   var xy= domBase.dom.getElePositionP(this.popWinListBt);
//    alert(xy.y)
    this.popWinListWin.style.top = (top+xy.y)+ "px";
//        (top - yZom * itemArr.length - paddingFlow) + "px";
//    var arr = this.getPopWinStackArr();
//    this.popWinListWin.style.display =
    this.visableListBt(itemArr.length > 0);
    this.restoreItemNum();
}
$$popWinList.prototype.visableListBt = function (f) {
    this.popWinListBt.style.display = (!!!f) ? "none" : "block";
}
$$popWinList.prototype.getPopWinStackArr = function () {
    var arr = [];
    for (var index in this.itemCache) {
        arr.push(this.itemCache[index])
    }
    return arr;
}

$$popWinList.prototype.doChangePopWinIndex = function (id) {
    var popWinId = id.replace("_Itm", "")
    this.popWinFactory.visiblePopWin(popWinId);
}
//此方法没有和doChangePopWinIndex进行合并，目的是防止上行调用和下行调用时产生方法回路
$$popWinList.prototype.doUnCheckedItemNotIn = function (itemId) {
    for (var index in this.itemCache) {
        var item = (this.itemCache[index]);
        if (item.id != itemId) {
            item.doUnChecked();
        }
    }
}
$$popWinList.prototype.changeItemTitle = function (itemId, title) {
    var item = this.itemCache[itemId];
    item.changeTitle(title);
}
//给popWinFactory调用的方法,选中某一个列表项
/*
 * 去除其他列表项的选中状态
 * 获取列表项并选中
 * */
$$popWinList.prototype.checkItem = function (itemId) {

    this.doUnCheckedItemNotIn(itemId);
    var item = (this.itemCache[itemId]);
    if (item) item.doChecked();//通过列表对象查询
}
$$popWinList.prototype.deleteItem = function (itemId) {
    var item = this.itemCache[itemId];
    if (item)item.destroy();
}
$$popWinList.prototype.removeChild = function (itemId) {
    this.itemCache[itemId] = null;
    delete this.itemCache[itemId];
    this.doAdjustListWin();
    var itemArr = this.getPopWinStackArr();
    if(itemArr.length == 0)this.hiddenPopList();
}
$$popWinList.prototype.deletePopWinById = function (itemId) {

    var popWinId = itemId.replace("_Itm", "")
    this.popWinFactory.deletePopWinById(popWinId);

}

$$popWinList.prototype.createItem = function (data) {
    var item = new $$popWinItem(this, data);
    this.itemCache[item.id] = item;
    this.popWinListCon.appendChild(item.element);
    this.doAdjustListWin();
    return item;
}
var $$popWinItem = function (p, data) {
    this.data = data;
    this.id = data["id"];
    this.checked = false;
    this.constants = {
        "ON_NORMAL": 0,
        "ON_MOUSE_OVER": 5,
        "ON_MOUSE_OUT": 10,
        "ON_CHECKED": 15
    }

    //console.log("id:"+this.id);
    this.name = data["name"];
    this.parent = p;
    this.element = $$("div", this.id + "item", "x-pop-win-list-item");
    this.leftDiv = $$("div", this.id + "left", "x-pop-win-list-item-left");
    this.rightDiv = $$("div", this.id + "right", "x-pop-win-list-item-right");

    this.winDiv = $$("div", this.id + "win", "x-pop-win-list-item-win");
    this.titleDiv = $$("div", this.id + "title", "x-pop-win-list-item-win-title");
    this.closeDiv = $$("div", this.id + "close", "x-pop-win-list-item-close-none");
    this.element.appendChild(this.leftDiv);
    this.element.appendChild(this.winDiv);
    this.winDiv.appendChild(this.titleDiv);
    this.winDiv.appendChild(this.closeDiv);
    this.element.appendChild(this.rightDiv);
    this.eventListeners = {};
    this.selected = 0;
    this.addEvent("click");
    this.addEvent("click", this.closeDiv, "deleteItem");
    this.addEvent("mouseover");
    this.addEvent("mouseout");
    domBase.dom.setOpacity(this.element, 80);
    domBase.dom.unSelectable(this.titleDiv);
    this.changeTitle(this.name);
}
$$popWinItem.prototype.changeTitle = function (title) {

    var titleArr = [], dot = "", fix = 0, reg = /[\u4E00-\u9FA5]/gi;
    for (var k = 0; k < title.length; k++) {
        var s = title.charAt(k);
        titleArr.push(s);
        if (reg.test(s)) {
            fix = fix + 2
        } else {
            fix = fix + 1
        }
        if (fix >= 26) {
            dot = "...";
            break;
        }
    }
    this.titleDiv.title = title;
    this.titleDiv.innerHTML = titleArr.join("") + dot;
}
$$popWinItem.prototype.addEvent = function (ev, tag, fun) {
    var Handle = dmUtils.bind(this, function () {
        this[fun || ev]();
    });
    this.eventListeners[fun || ev] = Handle;
    addEvent(tag || this.element, ev, Handle);
}
$$popWinItem.prototype.clearEvent = function () {
    for (var ev in this.eventListeners) {
        removeEvent(this.element, ev, this.eventListeners[ev]);
    }
}
$$popWinItem.prototype.deleteItem = function (e) {
    this.parent.deletePopWinById(this.id);//通过popWinFactory删除弹出窗口
    this.destroy();
    domBase.util.Event.stopEvent(e);

}
//销毁本对象的实例
$$popWinItem.prototype.destroy = function () {

    this.clearEvent();//清除事件绑定
    this.parent.removeChild(this.id);//清除父对象对本对象的引用
    this.parent = null;//消除本对象对父对象的引用
    domBase.dom.removeNode(this.element);//清除页面对象
    this.data = null;//清除引用数据
}
$$popWinItem.prototype.click = function (e) {
    /*
     * 通过父类 设置其他对象为未选中，并通过父类调用popWinFactory将此对象对应的窗口显示并置前
     * */
    this.parent.doUnCheckedItemNotIn(this.id);
    this.parent.doChangePopWinIndex(this.id);
    this.parent.showPopList();
    this.doChecked();
    domBase.util.Event.stopEvent(e);
}

$$popWinItem.prototype.doChecked = function () {
    this.checked = true;
    this.doChangeStyle(this.constants.ON_CHECKED);
};
$$popWinItem.prototype.doUnChecked = function () {
    this.checked = false;
    this.doChangeStyle(this.constants.ON_NORMAL);
}
$$popWinItem.prototype.doChangeStyle = function (KEY) {
    var mapDic = {};
    mapDic[this.constants.ON_CHECKED] = {eleOpacity: 100, eleClassName: "x-pop-win-list-item-checked", closeClassName: "x-pop-win-list-item-close-on"};
    mapDic[this.constants.ON_MOUSE_OVER] = {eleOpacity: 100, eleClassName: "x-pop-win-list-item-on", closeClassName: "x-pop-win-list-item-close-on"};
    mapDic[this.constants.ON_MOUSE_OUT] = {eleOpacity: 80, eleClassName: "x-pop-win-list-item", closeClassName: "x-pop-win-list-item-close-none"}
    mapDic[this.constants.ON_NORMAL] = {eleOpacity: 80, eleClassName: "x-pop-win-list-item", closeClassName: "x-pop-win-list-item-close-none"}
    var styleMap = mapDic[KEY];
    domBase.dom.setOpacity(this.element, styleMap.eleOpacity);
    this.element.className = styleMap.eleClassName;
    this.closeDiv.className = styleMap.closeClassName;
}
$$popWinItem.prototype.mouseover = function (e) {
    this.doChangeStyle(this.constants.ON_MOUSE_OVER);
    domBase.util.Event.stopEvent(e);
}
$$popWinItem.prototype.mouseout = function (e) {
    if (!this.checked) {
        this.doChangeStyle(this.constants.ON_NORMAL);
    } else {
        this.doChangeStyle(this.constants.ON_CHECKED);
    }
    domBase.util.Event.stopEvent(e);
}