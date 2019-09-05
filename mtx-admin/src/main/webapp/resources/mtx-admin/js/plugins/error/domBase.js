// JavaScript Document
/*
 为控件添加监听方法
 exp addEvent(window,'load',function(){alert(1)}) or addEvent(window,'load',functionname})
 */
var addEvent = function (/*documentElement*/ el, /*eventType*/ type, /*method*/ fn) {/*zowell20090209*/
    var _addEvent= function (/*documentElement*/ el, /*eventType*/ type, /*method*/ fn) {
        if (window.addEventListener) {
            el.addEventListener(type + "", fn, false);
        }
        else if (window.attachEvent) {
            el.attachEvent('on' + type, fn);
        }
    }
    var _loopLoad=function(/*documentElement*/ el, /*eventType*/ type, /*method*/ fn){
        var canLoad=true;
        for(var url in EHM.Cache["EHM_SCRIPT"]){
            if(EHM.Cache["EHM_SCRIPT"][url]["load"]==false){
                //console.log(url);
                canLoad=false;break;
            }
        }
        if(canLoad==false){
            window.setTimeout(function(){_loopLoad(el,type,fn);},200);
        }else{
            _addEvent(/*documentElement*/ el, /*eventType*/ type, /*method*/ fn);
        }
    };
    if (!!!el) {
        throw new Error(addEvent.caller + "被监听对象不存在"+el)
    }else{
        if(el==window&&type=="load"){
            //_loopLoad(el,  type,  fn);
            _addEvent( el,  type,  fn);
        }else{
            _addEvent( el,  type,  fn);
        }
    }

}
/*
 为控件删除监听方法
 exp  removeEvent(window,'load',functionname})
 */
var removeEvent = function (el, type, fn) {/*zowell20090209*/
    try {
        if (window.removeEventListener) {
            el.removeEventListener(type, fn);
        }
        else if (window.detachEvent) {
            el.detachEvent('on' + type, fn);
        }
    } catch (e) {
    }

}
function $() {/*getElementById(..);ID  return element or element[]*/
    var elements = [];
    for (var i = 0, len = arguments.length; i < len; ++i) {
        var element = arguments[i];
        var is = !!0;
        if (typeof element == 'string' || typeof element == 'number') {
            var ele = document.getElementsByName("" + element);
            if (ele.length < 1) {
                ele = document.getElementById("" + element);
                is = !!1;
            }
            else if (ele.length == 1) {
                ele = ele[0]
            }
            element = ele;
        }
        if (element == null) {//
            arguments[i] = "$(" + arguments[i] + ")";
            if (typeof arguments[i] == 'string') {
                element = document.getElementById(arguments[i]);
                if (element == null && !domBase.IsMoz())//
                    element = document.all[arguments[i]];

            }
        }
        if (arguments.length == 1) {
            return element;
        }
        (is) ? (elements.push(element)) : (elements.concat(element));
    }
    return (!!elements.length) ? (elements) : (null);
}
/*
 js库：向命名空间divClass注册getElementByClassName()事件，
 获得页面中所有className相同的元素
 */

$CLS = window.document['getElementsByClassName'] = function (className) {
    var el = [];
    var _el = document.getElementsByTagName("*");
    for (var i = 0, l = _el.length; i < l; i++) {
        if (_el[i].className == className) {
            el.push(_el[i]);
        }
    }
    return el;
}
/*
 - ocumentzowell20090210
 - M:$$(ag1,ag2,ag3,ag4);
 - ag1:tagName
 - ag2:tagId
 - ag3:tagClassName
 - ag4:tagInnerHTML
 return element
 */

function $$() {
    var agL = arguments.length;
    if (agL.length <= 0) {
        var fs = ($$.caller == "null") ? "" : "" + $$.caller + "У";
        throw new Error("在方法" + fs + "中调用的$$方法没有参数传入！");
        return null;
    }
    else if (agL == 1) {
        return document.createElement(arguments[0]);
    }
    else {
        var ele = document.createElement(arguments[0]);
        var att = ["id", "className", "innerHTML" , "innerText|textContent"];
        for (var _i = 0; _i < agL - 1; _i++) {
            if (arguments[_i + 1] != null && arguments[_i + 1] != "")
                ele[(att[_i].indexOf("|") >= 0) ? ((domBase.IsMoz()) ? (att[_i].split("|")[1]) : (att[_i].split("|")[0])) : (att[_i])] = arguments[_i + 1];
        }
        return ele;
    }
}
function $$F(id) {
    var ifr = $$("iframe");
    ifr.setAttribute("scrolling", "no");
    ifr.style.position = "absolute";
    ifr.style.top = "0px";
    ifr.style.left = "0px";
    ifr.src = "";
    ifr.id = id;
    ifr.border = ifr.frameBorder = 0;
    ifr.style.display = "none";
    return ifr;
}
function $$OF(obj, x, f) {
    var _x = x || 0;
    var ifr = $$F();
    obj.appendChild(ifr);
    ifr.style.position = "absolute";
    ifr.style.width = (obj.clientWidth + _x);
    ifr.style.height = (obj.clientHeight + _x);
    ifr.style.top = (parseInt(obj.style.top.replace("px", "")) - _x) + "px";
    ifr.style.left = (parseInt(obj.style.left.replace("px", "")) - _x) + "px";
    obj.style.display = ifr.style.display = (f) ? "block" : "none";
    ifr.style.zIndex = -1; //obj.style.zIndex -1
    domBase.dom.setOpacity(ifr, 20);
    ifr.id = (obj.id || "temp") + "_of"
    return {ele: obj, ifr: ifr};
}
var domBase = window.domBase || {
    UserInfo: {done: false},
    config: {
        Opacity: 70,
        useCookie: 1,
        useManager: 1,
        canP: 0
    },
    Struts: null,
    Data: {},
    components: {},
    componentsF: {},
    LockScreen: {p: null, c: null},
    IsMoz: function () {
        var vers = navigator.appVersion;
//        alert(vers)
        return !((vers.match(/MSIE (.)/)) && (vers.match(/MSIE (.)/)[1] >= 6 || (vers.match(/MSIE (.)/)[1] >= 5 && vers.match(/MSIE .\.(.)/)[1] >= 5 )));
    },
    IsIE: function () {
        var vers = navigator.appVersion;

        return !!((vers.match(/MSIE (.)/)) && (vers.match(/MSIE (.)/)[1] >= 6 || (vers.match(/MSIE (.)/)[1] >= 5 && vers.match(/MSIE .\.(.)/)[1] >= 5 )));
    },
    IEV: (function () {
        var v = navigator.appVersion;
        var r = /MSIE.*?;/gi;
        var Vs = r.exec(v);
        Vs = (Vs && Vs != "" && Vs != null) ? Vs.input.substr(Vs.index, Vs.lastIndex - Vs.index).replace(/MSIE|;| /gi, "") : "0";
        return Vs;
    })()
};

domBase.dom = domBase.dom || {
    getElePositionP: function (obj, pobj) {/*获取对象的绝对定位 obj:需要定位的对象 pobj 需要定位到的顶级对象*/
        function getOJ(e) /**/ {
            var t = {x: e.offsetLeft, y: e.offsetTop};
            if (e = e.offsetParent) {
                var r = getOJ(e);
                t.x += r.x;
                t.y += r.y;
            }
            return t;
        }

        return getOJ(obj);
    },
    getEleCenterPosition: function (obj) {
        var p = domBase.dom.getElePositionP(obj, document.body);
        p.x = p.x + parseInt(obj.clientWidth / 2)
        p.y = p.y + parseInt(obj.clientHeight / 2)
        return p;
    },
    $$If: function (w) {/*iframe return frame*/
        var d = w.document;
        var nf = d.createElement("iframe");
        with (nf.style) {
            width = "0px";
            height = "0px";
        }
        d.body.appendChild(nf);
        nf.contentWindow.document.write("<ht" + "ml><bo" + "dy></bo" + "dy></ht" + "ml>");
        nf.contentWindow.document.close();
        return nf;
    },
    InSIf: function (f, proc) {/*在iframe 中写入脚本*/
        var d = f.contentWindow.document;
        var s = "<scr" + "ipt>\n(" + proc.toString() + ")();\n</scr" + "ipt>";
        d.write(s);
        d.close();
    },
    /*	设置对象的透明度
     - obj : documentElement
     - val : Opacity value (0-100)
     */
    setOpacity: function (obj, val) {
        if (domBase.IsIE()) {
            obj.style.filter = (val <= 0) ? "" : "alpha(Opacity=" + val + ")";
        }
        else {
            obj.style.opacity = (val <= 0) ? "" : (val / 100);

            obj.style.MozOpacity = (val <= 0) ? "" : (val / 100);
        }
    },
    /*将对象设置为不可选择
     - obj : documentElement
     */
    unSelectable: function (obj) {
        if (domBase.IsMoz()) {
            obj.style.MozUserSelect = "none";
        }
        else {
            obj.unselectable = "on";
        }
    },
    /*移除对象
     - node : documentElement
     */
    removeNode: function (node) {
        if (node.parentNode) {
            var PPNOde = node.parentNode;
            PPNOde.removeChild(node);
        }
        node = null;
        return;
    },
    removeNodes: function (node) {
        if (node.parentNode) {
            var PPNOde = node.parentNode;
            PPNOde.removeChild(node);
        }
    },
    removeAllChildNodes: function (node) {
        if (node.childNodes) {
            var childList = [];

            for(var k=0;k<node.childNodes.length;k++){
                var child=node.childNodes[k];
                childList.push(child);
            }
            for(var key=0;key<childList.length;key++){
                node.removeChild(childList[key]);
            }
            childList = null;
        }
    },
    /*

     */
    confirm: function (_title, _el, _flag) {
        var b = {title: _title, el: _el, flag: _flag}
        domBase.dom.createFirm(b);
    },
    createFirm: function (O, cE) {/*代替confirm 没作完*/
        var til = O.title;
        var el = O.el;
        var sc = domBase.dom.createLockScreen(40);
        var sm = domBase.LockScreen.c = $$("DIV");
        document.body.appendChild(sm);
        var widths = 600;
        var lefts = sc.style.width;
        lefts = parseInt(lefts.replace("px", ""));
        lefts = parseInt(lefts / 2) - parseInt(widths / 2)
        sm.style.cssText = "position:absolute;display:block;top:60px;left:" + lefts + "px;width:" + widths + "px";
        sm.style.zIndex = 90000;
        sm.style.backgroundColor = "#ffffff";
        sm.id = "showmoddiv";

        var top = $$("DIV", "showmoddiv_top", "confirm_top");
        var win = $$("DIV", "showmoddiv_win", "confirm_win");
        var top_title = $$("DIV", "showmoddiv_top_title", "confirm_top_title", til);
        var top_tool = $$("DIV", "showmoddiv_top_tool", "confirm_top_tool x-form-trigger-click");
        top.appendChild(top_title);
        top.appendChild(top_tool);
        addEvent(top_tool, "click", function () {
            if (typeof cE == "function")cE();
            domBase.dom.removeLockScreen();
        });
        if (el) {
            win.appendChild(el);
        }
        sm.appendChild(top);
        sm.appendChild(win);
        domBase.LockScreen.OF = $$OF(sm).ifr;
        domBase.LockScreen.OF.style.display = sm.style.display = "block";
    },
    removeLockScreen: function (e) {
        domBase.dom.removeNode(domBase.LockScreen.c);//deleteDiv("showmoddiv")
        domBase.dom.removeNode(domBase.LockScreen.p);//("coverContener")
        domBase.dom.removeNode(domBase.LockScreen.OF);//("coverContener")
        domBase.util.Event.stopPropagation(e);
        domBase.util.GC();
    },
    /****************************************
     function lockScreen
     div
     ****************************************/
    createLockScreen: function (rate) {
        var B = document.body;
        var B_clientWidth = B.clientWidth;
        var B_clientHeight = B.clientHeight;
        var oDiv = domBase.LockScreen.p = $$("DIV");
        oDiv.style.position = "absolute";
        oDiv.style.top = 0 + "px";
        oDiv.style.left = 0 + "px";
        oDiv.style.width = B_clientWidth + "px";
        oDiv.style.height = B_clientHeight + "px";
        var oIDiv = $$("DIV");
        oDiv.appendChild(oIDiv);
        // addEvent(oIDiv,"click", function() {domBase.dom.removeLockScreen();});
        oIDiv.style.width = B_clientWidth + "px";
        oIDiv.style.height = B_clientHeight + "px";

        if (rate) {
            oIDiv.style.backgroundColor = "#faf2ff";
            domBase.dom.setOpacity(oIDiv, rate);
        }
        B.appendChild(oDiv);

        return oDiv;
    }
};
domBase.util = domBase.util || {};
/*内存回收
 - for IE
 */
domBase.util.GC = function () {
    if (!domBase.IsMoz()) {
        CollectGarbage();
    }
}

function turnLoading(x) {
    x = encodeURIComponent(x);
    $("frame2").contentWindow.location.href = "../neintegrated/test.jsp?path=" + x;

}
domBase.util.downLoad = function (src) {
//    src = encodeURIComponent(src);
    var iframe=$("itimh_downLoad_iframe");
    if(!!!iframe){
        iframe=   domBase.dom.$$If(top);
        iframe.id="itimh_downLoad_iframe";
    }


    iframe.contentWindow.location.href = src;

}
domBase.util.loadPage = function (type, containerObj, src) {
    src = encodeURIComponent(src);
    if (type == "iframe") {
        containerObj.contentWindow.location.href = "../loadingPage.jsp?path=" + src;
    } else if (type == "window") {
        containerObj.location.href = "../loadingPage.jsp?path=" + src;
    }
}

/*事件方法集*/
domBase.util.Event = {
    /*兼容获取事件
     - e: event
     */
    getEvent: function (e) {
        return e || window.event;
    },
    /*兼容获取触发事件对象
     - e: event
     */
    getTarget: function (e) {
        if (arguments.length == 0 || typeof e == 'undefined')return;
        return e.target || e.srcElement;
    },
    setCapture: function (obj) {

        if (obj.setCapture) {
            obj.setCapture();
        } else if (window.captureEvents) {
            window.captureEvents(Event.MOUSEMOVE | Event.MOUSEUP);
        }

    },
    releaseCapture: function (obj) {
        if(obj.releaseCapture){
            obj.releaseCapture();
        }else if(window.captureEvents){
            window.captureEvents(Event.MOUSEMOVE|Event.MOUSEUP);
        }
    },
    /*兼容停止事件冒泡
     - e: event
     */
    stopPropagation: function (e) {
        if (arguments.length == 0 || typeof e == 'undefined')return;
        e = domBase.util.Event.getEvent(e);
        if (e.stopPropagation) {
            e.stopPropagation();
        }
        else {
            e.cancelBubble = true;
        }
    },
    /*兼容卸载原有句并事件
     - e: event
     */
    preventDefault: function (e) {
        if (arguments.length == 0 || typeof e == 'undefined')return;
        e = domBase.util.Event.getEvent(e);
        if (e.preventDefault) {
            e.preventDefault();
        }
        else {
            e.returnValue = false;
        }
    },
    /*兼容停止事件
     - e: event
     */
    stopEvent: function (e) {
        domBase.util.Event.stopPropagation(e);
        domBase.util.Event.preventDefault(e);
    }
};

/*
 - 静态
 - 测试控制台 zowell@20090225
 - console.[print,[log,[erro,[write]]]]
 - input : string or obj
 */

if (!window["console"]) {
    console = new function () {
        var self = this;
        var OPEN = true;
        var data = new Date();
        var callers = {};
        var d;
        var w = window;

        function init() {
            try {

                w.__win = w.open("", null,
                    "height=500,width=800,status=0,toolbar=0,menubar=0,location=0,titlebar=0");
                w.__win.document.write('<html xmlns="http://www.w3.org/1999/xhtml"><head><meta http-equiv="Content-Type" content="text/html; charset=utf-8" /><title>console</title><body></body></html>');
                w.__win.document.close();
                d = w.__win.document.createElement("div");
                d.style.cssText = "margin: 0px;margin-top:5px;padding: 0px;height: 400px;width: 100%;border: 1px dashed #CCCCCC;float:left;	overflow-y:scroll;overflow-x:auto;font-family: Arial, Helvetica, sans-serif;	font-size: 12px;";
                w.__win.document.body.appendChild(d);
            } catch (e) {
                erro = true;
            }
        }

        function getFname(Str) {
            var s = escape(Str.replace(/ |\n|\t|\s/gi, ""));
            var ss = s.substr(s.length - 8, 8)

            return ss;
        };
        var bck = EHM.rootPath + "EHM/css/infoIcon.png";
        this.log = function (s, n) {
            bck = EHM.rootPath + "EHM/css/console/img/infoIcon.png";
            if (n) {
                var fN = getFname(self.log.caller.toString())
                if (!callers[fN]) {
                    callers[fN] = n + 1;
                }
                if (callers[fN] > 1) {
                    self.write(s, null, null, true);
                    callers[fN] = callers[fN] - 1;
                }
            } else {
                self.write(s, null, null, true);
            }

        };
        this.warn = function (s, n) {
            bck = EHM.rootPath + "EHM/css/console/img/warningIcon.png";
            self.write(s, "#FFFF00");
        };
        this.erro = function (s, n) {
            bck = EHM.rootPath + "EHM/css/console/img/errorIcon.png";
            self.write(s, "#FF0000");
        };
        this.print = function (s, n) {
            bck = EHM.rootPath + "EHM/css/console/img/infoIcon.png";
            self.write(s);
        };
        this.output = function (s) {
            if (!OPEN)return;
            try {
                var nwin = w.__win;
                var doc = w.__win.document;
            } catch (e) {
                init();
                var nwin = w.__win;
                var doc = nwin.document;
            }

            var divs = doc.createElement("div");
            divs.innerHTML = "<textarea style='width: 100%'>" + s + "</textarea>";
            d.appendChild(divs);
            d.scrollTop = d.scrollHeight;
        }
        this.write = function (s, col, bgcolor, isInnerHTML) {
            if (!OPEN)return;
            try {
                var nwin = w.__win;
                var doc = w.__win.document;
            } catch (e) {
                init();
                var nwin = w.__win;
                var doc = nwin.document;
            }

            var divs = doc.createElement("div");
            with (divs.style) {
                background = "url(" + bck + ") no-repeat 3px 50%"
                backgroundColor = (bgcolor) ? bgcolor : "#99FFFF";
                paddingLeft = "18px";
                paddingTop = "3px";
                marginTop = "1px";
                color = (col) ? col : "#000";
            }

            d.appendChild(divs);
            var dat = new Date();
            var ss = data.getHours() + ":" + data.getMinutes() + ":" + data.getSeconds() + ":" + data.getMilliseconds() + "(" + (dat.getTime() - data.getTime()) + ")" + ">>" + ((typeof s == "string" || typeof s == "number" || typeof s == "boolean" ) ? s : (typeof s));
            data = dat;

            if (!!isInnerHTML) {
                var t = w.__win.document.createElement("div");
                t.innerHTML = ss;
            } else {
                var t = w.__win.document.createTextNode(ss);
            }
            var _t = w.__win.document.createElement("br");
            divs.appendChild(t);
            divs.appendChild(_t);
            d.scrollTop = d.scrollHeight;
        };
    };
}

//EHM.ImportCss("EHM/css/main/confrim.css");