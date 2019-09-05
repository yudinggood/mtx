var theme = "";
var ChangeSkin =EHM["_ChangeSkin"]= new function() {
    this.ImportGlobe = function() {
        //EHM.ImportCss("/resources/js/EHM/error/css/global.css");
        //EHM.ImportCss("/resources/js/EHM/error/css/ext-all.css");
        //EHM.ImportCss("/resources/js/EHM/error/css/ext-all-ne-tree.css");
    }
    this.Import = function(src) {
        theme = cookie.get("EAP_THEME");
        theme = (theme) ? theme : "blue";
        //EHM.ImportCss("/resource/style/xtheme/" + theme + "/css/" + src);
        EHM.ImportCss("/resources/js/plugins/error/css/" + src);
    }
}
var ChangeSkinImportPoxy=function(){
    this.act=function(){};
}
ChangeSkinImportPoxy.prototype.setFunction =function(a){this.act=a;}
ChangeSkinImportPoxy.prototype.getFunction=function(){return function(){this.act();}}
var ChangeSkinAPP = new function() {
    var action = [];
    var self = this;
    var _init = function() {
        ChangeSkin.ImportGlobe();
    };
    var _do = function() {
        for (var i = 0; i < action.length; i++) {
            action[i]();
        }
    };
    var hiddenMenu = function() {

    };
    this.Do = function() {
        _do();
    };
    this.Register = function(O) {
        action.push(O);
        return self;
    };
    this.RegisterAndLoad = function(O) {
        action.push(O);
        _init();
        _do();
        return self;
    };
    this.init = function() {
        EHM.Cache["ChangeSkinAPP"] = EHM.Cache["ChangeSkinAPP"] || {};
        EHM.Cache["ChangeSkinAPP"]["theme"] = EHM.Cache["ChangeSkinAPP"]["theme"] || [];
        for (var __$o = 0; __$o < EHM.Cache["ChangeSkinAPP"]["theme"].length; __$o++) {

            var HH = (EHM.Cache["ChangeSkinAPP"]["theme"][__$o]) + "";
           var fun=new ChangeSkinImportPoxy();
            fun.setFunction(ChangeSkin.Import(HH))
            action.push(fun.getFunction);
        }
        _init();
        _do();
        addEvent(window, "load", function() {
            if (parent.EHM && parent.EHM.isTreeFrame) {
                var TWin = parent.document.frames ? parent.document.frames["treeFrame"] : parent.document.getElementById("treeFrame").contentWindow;
                var MWin = parent.document.frames ? parent.document.frames["manageFrame"] : parent.document.getElementById("manageFrame").contentWindow;
                if (MWin == window) {
                    if (top.zowellLabel) {
                        var lab = top.zowellLabel.getCurrent();
                        lab.TreeFrame = lab.TreeFrame || {};
                        lab.TreeFrame.manageFrame = lab.TreeFrame.manageFrame || {};
                        lab.TreeFrame.manageFrame.src = window.location.href;
                    }
                } else if (TWin == window) {
                    if (top.zowellLabel) {
                        var lab = top.zowellLabel.getCurrent();
                        lab.TreeFrame = lab.TreeFrame || {};
                        lab.TreeFrame.treeFrame = lab.TreeFrame.treeFrame || {};
                        lab.TreeFrame.treeFrame.src = window.location.href;
                    }
                }
            }
            if (!!!(EHM.ToolbarInstance)) {
                addEvent(window.document.body, "click", function(ev) {
                    if (top && top.EHM && top.EHM.ToolbarInstance) {
                        try {
                            top.EHM.ToolbarInstance.hidden();
                        }
                        catch(e) {
                        }
                    }
                    // domBase.util.Event.stopEvent(ev);
                });
            }
            else {

                addEvent(window.document.body, "click", function(ev) {
                    EHM.ToolbarInstance.hidden();
                    domBase.util.Event.stopEvent(ev);
                })
            }
            ;
        })
    };
}
