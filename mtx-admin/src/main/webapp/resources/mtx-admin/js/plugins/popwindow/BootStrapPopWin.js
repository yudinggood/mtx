//窗体对象
var _BootStrapPopWin = function (id) {
    this.parentBody = $('BODY');
    this.id = id;

};

_BootStrapPopWin.prototype.init = function (id, index) {
    this.initWindow(id, index);
};

_BootStrapPopWin.prototype.initWindow = function (id, index) {
    var self = this;

    this.popMain = $("<div class='modal modal-default fade' data-spy='scroll' style='overflow-y: hidden;'></div>");//解决弹出框有y滚动条
    this.popMain.attr("id", id);
    this.popMain.attr("name", "modal");

    this.popMain.attr("tabindex", "-1");
    this.popMain.attr("role", "dialog");
    this.popMain.attr("aria-labelledby", "exampleModalLabel");

    this.popMainInner = $("<div class='' style='margin:30px auto 30px auto;'></div>");
    this.popMainInner.attr("role", "document");

    this.popContent = $("<div class='modal-content'></div>");

    this.popModalHeader = $("<div class='modal-header' style='padding: 10px;'></div>");

    var idd="popupFrame" + this.id;
    this.popModalHeaderCloseBtn = $("<div>" +
        "<button type='button' class='close' style='font-size: 21px;padding: 0' data-dismiss='modal' aria-label='Close'>×</button>" +
        "<button type='button' id='id_pop_fullscreen' class='close' style='font-size: 16px;padding: 4px'  onclick='self.fullScreen(\""+idd+"\")'><i class='fa fa-tv'></i></button>" +
        "</div>");
    this.popModalHeaderTitle = $("<div class='modal-title' style='font-weight: bold;font-size:15px;color: #3c8dbc;'></div>");

    this.popModalBody = $("<div class='modal-body' style='padding: 0;'></div>");

    this.popModalHeader.append(this.popModalHeaderCloseBtn);
    this.popModalHeader.append(this.popModalHeaderTitle);

    this.popContent.append(this.popModalHeader);
    this.popContent.append(this.popModalBody);

    this.popMainInner.append(this.popContent);


    // this.toTopDiv = $("<a class='btn btn-app'><i class='fa fa-arrow-up' style='font-size: 30px;'></i></a>");
    // this.toTopDiv.css("position", "absolute");
    // this.toTopDiv.css("top", document.body.clientHeight - 100);
    // this.toTopDiv.css("right", 30);
    //
    // this.popMain.on("scroll", function () {
    //     self.toTopDiv.css("top", document.body.clientHeight - 100 + self.popMain.scrollTop());
    // })
    //
    // this.toTopDiv.on("click", function () {
    //     self.popMain.scrollTop(0);
    // })

    this.popMain.append(this.popMainInner);
    // this.popMain.append(this.toTopDiv);

    this.parentBody.append(this.popMain);

};

_BootStrapPopWin.prototype.show = function (Title, url, width, height, returnFunc, showCloseBox, showScrolling, submitType) {

    var self = this;

    var scrolling = (showScrolling != null && showScrolling) ? "auto" : "no";
    var iframeInner = $('<iframe src="" frameborder="0" style="background-color:transparent;" allowtransparency="true" id="popupFrame' + this.id + '" name="popupFrame" width="100%" height="100%"></iframe>');
    iframeInner.attr("scrolling", scrolling);
    this.popModalBody.append(iframeInner);

    this.gPopFrame = $("#popupFrame" + this.id);
    //top.EHM.Cache["mainFrame"] = this.gPopFrame;

    this.popModalHeaderTitle.html(Title);

    if (height == -1) {
        this.popMainInner.css("margin", '0px auto 0px auto');
        this.popMainInner.css("position", 'absolute');
        this.popMainInner.css("left", '');
        this.popMainInner.css("right", '0px');
        // this.popModalHeader.css("height",'45px');
    }
    this.gPopFrame.css("height", height == -1 ? document.body.clientHeight - 48 : height);
    $('#id_pop_fullscreen').css('display',height!=-1?'none':'block');


    this.popMainInner.removeClass();
    var widthType = "modal-dialog";
    if (width != null && width != undefined) {
        if (width == "lg") {
            widthType = "modal-lg";

        } else if (width == "md") {
            widthType = "modal-dialog";

        } else if (width == "sm") {
            widthType = "modal-sm";
        } else {
            widthType = "modal-dialog";
        }

    }

    if(!isNaN(width)){
        this.popMainInner.css("width",width+"px");
    }else{
        this.popMainInner.addClass(widthType);
    }

    if (!!submitType && submitType == "post") {
        var form = $("<form></form>");
        form.attr("id", "postData_form");
        form.attr("method", "post");
        form.attr("target", "popupFrame");
        var urlArr = url.split("?");
        var postUrl = urlArr[0];
        var postData = urlArr[1];
        form.attr("action", postUrl);
        var postDataArr = postData.split("&");
        for (var i = 0; i < postDataArr.length; i++) {
            var postDataObj = postDataArr[i];
            var postDataObjKey = postDataObj.split("=")[0];
            var postDataObjValue = postDataObj.split("=")[1];
            var inputObj = $('<input type="hidden" name="' + postDataObjKey + '"/>');//??????IE
            inputObj.val(encodeURI(postDataObjValue));
            inputObj.attr("name", postDataObjKey);
            form.append(inputObj);
        }
        this.gPopFrame[0].contentWindow.document.write("<body>" + form.outerHTML + "</body>");
        this.gPopFrame[0].contentWindow.document.getElementById("postData_form").submit();
    } else {
        this.gPopFrame.attr("src", url);
    }

    $("#" + this.id).modal("toggle");

    $("#" + this.id).on("hidden.bs.modal", function () {
        if ($("[name='modal']") && ($("[name='modal']").length - 1 != 0)) {
            $(".modal").css("overflow", "auto");
            $("body").css("overflow", "hidden");
        }
        self.close();
        self.popMain.remove();

        if (!!!$("[name='modal']")[0]) {
            $("body").css("overflow", "hidden");
        }
    });

    $("body").css("overflow", "hidden");

    /*if (height != -1)有报错暂时 废弃
        this.popContent.draggable({
            handle: ".modal-header",
            cursor: 'move',
            iframeFix: true,
            opacity: 0.7,
            helper: "cloneEmpty",
            stop: function (event, ui) {
                self.popMainInner.css({'position': "absolute"});
                self.popMainInner.css({'top': ui.offset.top - 30 + self.popMain.scrollTop(), 'left': ui.offset.left});
            }
        });*/
}

_BootStrapPopWin.prototype.close = function () {
    //top.EHM.Cache["mainFrame"] = $('#main');
    this.gPopFrame.attr("src", "javascript:false");
    $("#" + this.id).modal("hide");
    BootStrapPopWin.removePopwin(this);
}

var BootStrapPopWin = EHM.BootStrapPopWinFactory = new function () {
    var popWinStack = {}, self = this, index = 0;
    var createNewPopWin = function () {
        var genId = new Date().valueOf() + "";
        var pop = new _BootStrapPopWin(genId);
        index = index + 1;
        popWinStack[genId] = {index: index, obj: pop};
        pop.init(genId, index);
        return pop;
    }

    this.removePopwin = function (pop) {
        popWinStack[pop.id] = null;
        delete popWinStack[pop.id];
        pop = null;
        delete pop;
    }

    this.close = function () {
        var pop = self.getCurrentPop();
        if (!!pop) {
            pop.close();
        }
    }

    this.getCurrentPop = function () {
        var max = null;
        for (var ob in popWinStack) {
            if (max == null) max = popWinStack[ob];
            if (popWinStack[ob].index > max.index) max = popWinStack[ob];
        }
        return max == null ? null : max.obj;
    }

    this.show = function (Title, url, width, height, returnFunc, showCloseBox, showScrolling, submitType) {
        var pop = self.getCurrentPop();
        if (!pop) pop = createNewPopWin();
        pop.show(Title, url, width, height, returnFunc, showCloseBox, showScrolling, submitType);
    }

    this.showNew = function (Title, url, width, height, returnFunc, showCloseBox, showScrolling, submitType) {
        var pop = createNewPopWin();
        pop.show(Title, url, width, -1, returnFunc, showCloseBox, showScrolling, submitType);
    }

}

window.BootStrapPopWin = top.BootStrapPopWin;