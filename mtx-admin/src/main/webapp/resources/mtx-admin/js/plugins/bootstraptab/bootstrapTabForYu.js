/**
 * Created by xiaox on 2016/2/23.
 */

var $$$Tab = function (id, content_id, t, active) {
    this.id = id;
    this._t = t;
    this.content;
    this.element = $('<li id="' + id + '" ' + ((!!active) ? 'class="active"' : '') + '><a href="#' + content_id + '" data-toggle="tab">' + t + '</a></li>');
    return this;
}

var $$$TabContainer = function (container, par) {
    this.currentIframe = null;
    this.loaded = false;
    this.container = $("#" + container);
    this.container.css({"overflow": "hidden"});
    this.custom = $('<div class="nav-tabs-custom" style="height: 100%"></div>');
    this.tabsNavCon = $('<ul class="nav nav-tabs" style="height: 45px;"></ul>');
    this.tabsContentCon = $('<div class="tab-content" style="padding: 0;height: 100%;"></div>');
    this.custom.append(this.tabsNavCon);
    this.custom.append(this.tabsContentCon);
    this.container.append(this.custom);
    if (par) {
        this.create(par);
    }
};

$$$TabContainer.prototype.createIframe = function (iframeId) {
    var iframe = $("<iframe frameborder='0' id='" + iframeId + "_tabFrame' style='width:100%;height:100%;overflow: hidden;border: 0;'></iframe>");
    return iframe;
}

$$$TabContainer.prototype.create = function (par) {
    var self = this;
    var parr = par.tabs;
    for (var k = 0; k < parr.length; k++) {
        var tbPar = parr[k];

        var tbId = tbPar["id"] || new Date().valueOf();
        var tbTitle = tbPar["title"] || "---";
        var tbContentID = tbPar["content"] || "";
        var conId = tbId + "_" + tbContentID;

        var tb = new $$$Tab(tbId, conId, tbTitle, tbPar["active"]);
        this.tabsNavCon.append(tb.element);

        if (tbPar["onchecked"]) {
            tb.element.on('shown.bs.tab', function (fun) {
                return function () {
                    fun();
                }
            }(tbPar["onchecked"]))
        }

        var contCon = $("#" + tbContentID).clone();

        var contConMain = $('<div class="tab-pane ' + ((tbPar["active"]) ? 'active' : '') + '" id="' + conId + '" style="height: 100%"></div>');
        $("#" + tbContentID).remove();

        if (!!tbPar["url"]) {
            var iframe = null;
            if (!!tbPar["iframeLoad"] && tbPar["iframeLoad"] == "iframes-on-demand") {
                if ($('#' + tbId).attr("class") && $('#' + tbId).attr("class").indexOf("active") > -1) {
                    iframe = self.createIframe(tbId);
                    iframe.attr("src", tbPar["url"]);

                    contCon = iframe;
                    self.currentIframe = iframe;
                    contConMain.append(contCon);
                    this.tabsContentCon.append(contConMain);
                    if (tbPar["onchecked"]) {
                        tbPar["onchecked"]();
                    }

                }

                tb.element.on('show.bs.tab', function (tbIdA, tbParA, contConA, contConMainA, tabsContentConA) {
                    return function () {

                        var aa = function () {
                            if (!!!$("#" + tbIdA + "_tabFrame")[0]) {
                                self.loaded = true;
                                iframe = self.createIframe(tbIdA);
                                iframe.attr("src", tbParA["url"]);
                                iframe.load(function () {
                                    self.loaded = false;
                                });
                                contConA = iframe;

                                contConMainA.append(contConA);
                                tabsContentConA.append(contConMainA);
                            }
                        }

                        if (!self.loaded) {
                            aa();
                        } else {
                            return false;
                        }

                    }
                }(tbId, tbPar, contCon, contConMain, self.tabsContentCon))

                continue;
            } else {
                var iframe = this.createIframe(tbId);
                iframe.attr("src", tbPar["url"]);
            }
            contCon = iframe;
        }
        contConMain.append(contCon);
        this.tabsContentCon.append(contConMain);
    }
}

var BootStrapTab = function () {
    var _a = function () {
    }
    _a.prototype.createTab = function (container, par) {
        var tb = new $$$TabContainer(container, par);
        return tb;
    }
    return new _a();
}()