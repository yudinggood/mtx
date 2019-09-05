//基础js的封装类
var EHM_b = navigator.userAgent.toLowerCase();
var EHM = {
    iscache: false,
    randData: (new Date()).valueOf(),
    versionCode: (function () {
        return window["VERSION_JS"] || (new Date()).valueOf();
    })(),
    lowVersion: (function () {
        var ver = window["VERSION_JS"] || (new Date()).valueOf();
        ver = ver + "";
        return ver.indexOf("LOW") > 0;
    })(),
    scriptPar: (function () {
        var sE = document.getElementsByTagName("script");
        var str = "", ret = [];
        for (var ____j = 0; ____j < sE.length; ____j++) {
            if (sE[____j].src && /\/EHM\/Base\.js/.test(sE[____j].src)) {
                var ar = sE[____j].src.split("?");
                if (ar && ar.length && ar.length > 0) {
                    str = ar[1];
                }
            }
        }
        if (str) {
            ret = str.split("&");
        }
        return ret;
    })(),
    Cache: {},
    rootPath: (function () {
        if (window["ROOT_PATH"]) {
            window["ROOT_PATH"] = "/" + window["ROOT_PATH"].split("\/")[1];
        }
        return window["ROOT_PATH"] || (function () {
                var L = window.location;
                var SR = L.pathname
                if (SR.indexOf("/") > 0) SR = "/" + SR;
                return "/" + SR.split("\/")[1];
            })();
    })(),
    containRootPath: function (url) {
        var rtp = EHM.rootPath;
        var re = new RegExp("(" + rtp + ")([^A-Za-z0-9_\.])", "ig");
        var arr = re.exec(url);
        return (((url.indexOf(rtp) + rtp.length) == url.length) || (arr != null))
    },
    appPath: "/resource/",
    browser: {
        safari: /webkit/.test(EHM_b),
        opera: /opera/.test(EHM_b),
        msie: /msie/.test(EHM_b) && !/opera/.test(EHM_b),
        mozilla: /mozilla/.test(EHM_b) && !/(compatible|webkit)/.test(EHM_b)
    },
    _IE: (function () {
        var v = 3, div = document.createElement('div'), all = div.getElementsByTagName('i');
        while (
            div.innerHTML = '<!--[if gt IE ' + (++v) + ']><i></i><![endif]-->',
                all[0]
            );
        return v > 4 ? v : false;
    }()),
    ajax_error:function(status,path){//zzq2017
        if(status==-1){
            top.show_dialog("错误","用户未登录或者登录超时，请重新登录！"+"<br><br>"+path,function () {//todo 模态
                top.location = EHM.rootPath+"/login.jsp";
            });
        }
        else if(status==0)
            top.show_dialog("错误","无法连接到服务器！"+"<br><br>"+path,function () {
            });
        else if(status==404){
            top.show_dialog("错误","请求的页面未找到！"+"<br><br>"+path,function () {
            });
        }
        else if(status==500){
            top.show_dialog("错误","服务器后台处理出错！"+"<br><br>"+path,function () {
            });
        }
    },
    Import: function (url, extend) {
        EHM.Cache["EHM_SCRIPT"] = EHM.Cache["EHM_SCRIPT"] || {};
        EHM.Cache["EHM_SCRIPT_ID"] = EHM.Cache["EHM_SCRIPT_ID"] || {};
        if (!EHM.containRootPath(url) & url.indexOf("jsp") < 0 && url.indexOf("php") < 0) {
            //因为没有项目名称，所以修改这一行
            //url = EHM.rootPath + ((url.indexOf("EHM") == 0) ? EHM.appPath : "") + url;
            url =  ((url.indexOf("EHM") == 0) ? EHM.appPath : "") + url;
        }
        var idarr = url.split("/");
        var id = idarr[idarr.length - 1];
        //if (!!!EHM.iscache) {
        //    if (url.indexOf("?") < 0) {
        //        url += "?v=" + EHM.versionCode;
        //    }
        //    else {
        //        url += "&v=" + EHM.versionCode;
        //    }
        //}
        //对于重复加载的脚本需要剔除
        if (EHM.Cache["EHM_SCRIPT"][url]) {
            return EHM;
        }
        EHM.Cache["EHM_SCRIPT"][url] = {load: false, imp: true};
        EHM.Cache["EHM_SCRIPT_ID"][id] = {load: false, imp: true};
        try {
            //异步执行的脚本程序，如果异步执行 则表示加载的脚本（当页面继续进行解析时，脚本将被执行）
            var asyncScriptStr = "asy" + "nc='true' on" + "load='EHM._SCRIPT_ONLOAD(event)'";
            //默认为同步执行，意味着 加载进的脚本将一边读取一边执行
            document.writeln("<" + "scr" + "ipt type='text/javascript' id='" + id + "'  src='" + url + "' ><" + "/scr" + "ipt>");
        }
        catch (e) {
            var script = document.createElement("script");
            script.src = url;
            script.type = "text/javascript";
            script.async = true;
            script.onload = EHM._SCRIPT_ONLOAD_SUCESS;
            script.onerror = EHM._SCRIPT_ONLOAD_ERROR;
            document.getElementsByTagName("head")[0].appendChild(script);
        }
        return EHM;
    },
    _SCRIPT_ONLOAD_SUCESS: function (e) {
        var ele = e.target || e.srcElement;
        EHM.Cache["EHM_SCRIPT_ID"][ele.id]["load"] = true;
        ele.setAttribute("loaded", "true");
    },
    _SCRIPT_ONLOAD_ERROR: function (e) {
        var ele = e.target || e.srcElement;
        EHM.Cache["EHM_SCRIPT_ID"][ele.id]["load"] = false;
        ele.setAttribute("loaded", "false");
    },
    addCss: function (str) {
        try {
            document.writeln("<style type='text/css'>" + str + "</style>");
        }
        catch (e) {
            var style = document.createElement("style");
            style.innerHTML = str;
            style.type = "text/css";
            document.getElementsByTagName("head")[0].appendChild(style);
        }
        return EHM;
    },
    ImportCss: function (url) {
        EHM.Cache["EHM_CSS"] = EHM.Cache["EHM_CSS"] || {};
        EHM.Cache["EHM_CSS_ID"] = EHM.Cache["EHM_CSS_ID"] || {};
        if (url.indexOf(EHM.rootPath) < 0) {
            //因为没有项目名称，所以修改这一行
            //url = EHM.rootPath + ((url.indexOf("EHM") == 0) ? EHM.appPath : "") + url;
            url =  ((url.indexOf("EHM") == 0) ? EHM.appPath : "") + url;
        }
        var urlArr = url.split("/");
        var id = urlArr[urlArr.length - 1].replace(/\./gi, "_");

        if (EHM.Cache["EHM_CSS"][url]) {
            return EHM;
        }
        EHM.Cache["EHM_CSS"][url] = {load: false, imp: true};
        EHM.Cache["EHM_CSS_ID"][id] = {load: false, imp: true};
        var olink = document.getElementById("" + id);
        if (olink) {
            olink.href = url
        }
        else {
            try {
                document.writeln("<link href=\"" + url + "\" rel=\"stylesheet\" id=\"" + id + "\" type=\"text/css\" />");
            }
            catch (e) {
                var links = document.createElement("link");
                links.href = url;
                links.rel = "stylesheet";
                links.id = id;
                links.type = "text/css";
                document.getElementsByTagName("head")[0].appendChild(links);
            }
        }

        return EHM;
    },
    ImportWebIcon: function (url) {

        if (url.indexOf(EHM.rootPath) < 0) {
            url = EHM.rootPath + ((url.indexOf("EHM") == 0) ? EHM.appPath : "") + url;
        }

        try {
            document.writeln("<link href=\"" + url + "\" rel=\"Bookmark\"  type=\"image/x-icon\"/>");
            document.writeln("<link href=\"" + url + "\" rel=\"Shortcut Icon\"  type=\"image/x-icon\"/>");
        }
        catch (e) {
            var linksBookmark = document.createElement("link");
            linksBookmark.href = url;
            linksBookmark.rel = "Bookmark";

            var linksShortcut = document.createElement("link");
            linksShortcut.href = url;
            linksShortcut.rel = "Shortcut Icon";

            document.getElementsByTagName("head")[0].appendChild(links);
        }

        return EHM;
    },
    request: new function () {
        var L = window.location;
        var _h = L.hash;
        /*“#”后面的分段。*/
        var _hn = L.hostname;
        /* 主机名称部分。*/
        var _pn = L.pathname;
        /* 对象指定的文件名或路径。*/
        var _p = L.port;
        /*与 URL 关联的端口号码*/
        var _pt = L.protocol;
        /*URL 的协议部分。*/
        var _s = L.search;
        /*href 属性中跟在问号后面的部分*/
        var _PArr = _s.replace(/\?/gi, "").split("&");
        var _Pdic = {};
        for (var i = 0; i < _PArr.length; i++) {
            var K = _PArr[i].split("=");
            _Pdic[K[0]] = K[1];
        }
        this.getParameterByUrl = function (url, key) {
            var __pArr = url.substr(url.lastIndexOf("?") + 1, url.length).split("&");
            var __Pdic = {};
            for (var i = 0; i < __pArr.length; i++) {
                var K = __pArr[i].split("=");
                __Pdic[K[0]] = K[1];
            }
            if (__Pdic[key]) {
                return __Pdic[key];
            }
            else {
                return null;
            }
        }
        this.getParameter = function (Str) {
            if (_Pdic[Str]) {
                return _Pdic[Str];
            }
            else {
                return null;
            }
        }
        this.getHostName = function () {
            return _hn;
        };
        this.getPathName = function () {
            return _pn;
        };
        this.Port = function () {
            return _p;
        };
        this.getProtocol = function () {
            return _pt;
        };
        this.getMap = function () {
            return _h;
        };
    },
    extend: function (subClass, superClass) {/* Extend function. */
        var F = function () {
        };
        F.prototype = superClass.prototype;
        subClass.prototype = new F();
        subClass.prototype.constructor = subClass;
        subClass.superclass = superClass.prototype;
        if (superClass.prototype.constructor == Object.prototype.constructor) {
            superClass.prototype.constructor = superClass;
        }
    },
    using: function (p) {
        p = p.split(/\s*\.\s*/g);
        var m = window, d;
        p.each(function (n) {
            if (d) {
                d += '.' + n;
            }
            else {
                d = n;
            }
            if (!m[n]) m[n] = {$name: d};
            m = m[n];
        });
        return m;
    },
    eval: function (expr) {
        var result = null;
        if (expr.indexOf('function') >= 0 && (false || false || true || false)) {
            try {
                eval('var _jexp=' + expr);
                result = _jexp;
                delete _jexp;
            }
            catch (e) {
                alert(e.message + ' while evaluating ' + expr);
            }
        }
        else {
            result = eval("(" + expr + ")");
        }
        return result;
    },
    JsonToString: function (json) {
        if (typeof json == "undefined")return "";
        var isArr = (json.constructor == Array);
        var re = (isArr) ? "[" : "{";
        var ar = [];
        for (var o in json) {
            var obj = json[o];
            if (obj == null) {
                ar.push(( (o + ":") ) + "\"\"");
            } else {
                var objConstructor = obj.constructor;
                if (objConstructor == String) {
                    ar.push(((isArr) ? "" : (o + ":") ) + "\"" + obj + "\"");
                } else if (objConstructor == Object) {
                    ar.push(((isArr) ? "" : (o + ":") ) + EHM.JsonToString(obj) + "");
                } else if (objConstructor == Array) {
                    ar.push(((isArr) ? "" : (o + ":") ) + "" + EHM.JsonToString(json[o]));
                } else if (objConstructor == Number) {
                    ar.push(((isArr) ? "" : (o + ":") ) + "" + obj + "");
                } else if (objConstructor == Date) {
                    ar.push(((isArr) ? "" : (o + ":") ) + "\"" + obj.pattern() + "\"");
                }
            }
        }
        re = re + ar.join(",");
        re = re + ((json.constructor == Array) ? "]" : "}");
        return re;
    },
    clone: function (obj) {
        var result = null;
        if (typeof obj == "object") {
            result = {};
            for (var i in obj) {
                result[i] = obj[i];
            }
        }
        return result;
    },
    SuperFunction: {
        getFixID: function (/*String Boolean Number Object*/Str) {
            if (isNaN(Str)) {
                return 0;
            }
            else {
                return parseInt(Str);
            }
        },
        escapeAt: function (s) {
            return s.replace(/&/gi, "---");
        },
        unescapeAt: function (s) {
            return s.replace(/---/gi, "&");
        }
    }
};

EHM.Import("/resources/mtx-admin/js/plugins/error/domBase.js");
EHM.Import("/resources/mtx-admin/js/EHM/base.ext.js");
EHM.ImportCss("/resources/mtx-admin/js/EHM/base.css");
EHM.ImportCss("/resources/mtx-admin/iconfont/alifont/iconfont.css");//阿里的字体库    加入时整体替换即可
EHM.ImportCss("/resources/mtx-admin/iconfont/awesome/css/font-awesome.min.css");//LTE的两个字体库     一般不变
EHM.ImportCss("/resources/mtx-admin/iconfont/iconicons/css/ionicons.min.css");
EHM.ImportCss("/resources/mtx-admin/js/mycss/scrollbar.css");//滚动条样式
EHM.Import("/resources/mtx-admin/js/plugins/jquery/jquery.cookie.js");//cookies
EHM.Import("/resources/mtx-admin/js/myjs/common.js");//common.js

