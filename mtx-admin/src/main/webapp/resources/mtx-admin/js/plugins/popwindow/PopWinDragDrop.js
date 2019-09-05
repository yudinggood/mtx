var ___PopWinDragDrop = new function () {
    var self = this;
    this.ParentFrame = {};//框架
    this.FrameHeight = 0;//框架高度
    this.dragHelper = {w: 0, h: 0, element: null};//盛放被拖拽对象的div容器
    this.Positions = {x: 0, y: 0};
    this.RectifyPositions = {x: 0, y: 0};
    this.iMouseDown = false;
    this.DataBase = {};
    this.HandMod = {};
    this.timeHandle =null;
    this.ActionMod = null;
    this.currentEventMod = null;
    this.Register = function () {
        if (arguments.length <= 0) {
            throw new Error("参数不正确，注册拖拽模块失败。");
        } else if (arguments.length >= 2) {
            var actionMod = arguments[1];
            self.DataBase[arguments[0].id] = {handMo: arguments[0], actionMo: arguments[1]};
            this.HandMod = self.DataBase[arguments[0].id].handMo;
        } else {
            self.DataBase[arguments[0].id] = {handMo: arguments[0], actionMo: arguments[0]};
            this.HandMod = self.DataBase[arguments[0].id].handMo;
        }
        self.DataBase[arguments[0].id].handMo.style.cursor = "move";

    }

    this.unRegister = function () {
        if (arguments.length <= 0) {
            throw new Error("参数不正确，注销拖拽模块失败。");
        } else {
            if (self.DataBase[arguments[0].id]) delete self.DataBase[arguments[0].id];
        }
    }
    this.mouseDown = function (ev) {
        ev = ev || window.event;
        var target = ev.target || ev.srcElement;
        var acMod = self.currentEventMod = self.getMod(target);

        if (acMod == null)return;

        domBase.util.Event.setCapture(acMod.handMo);
        var docBody = document.documentElement;
        self.Positions.MaxX = (docBody.clientWidth - acMod.actionMo.clientWidth);
        self.Positions.MaxY = (docBody.clientHeight - acMod.actionMo.clientHeight);
        if (!ev.pageX)ev.pageX = ev.clientX;
        if (!ev.pageY)ev.pageY = ev.clientY;
        self.Positions.x = ev.pageX - acMod.actionMo.offsetLeft;
        self.Positions.y = ev.pageY - acMod.actionMo.offsetTop;
        self.RectifyPositions.x = parseInt(acMod.actionMo.offsetLeft);
        self.RectifyPositions.y = parseInt(acMod.actionMo.offsetTop);
        self.iMouseDown = true;

        domBase.util.Event.stopEvent(ev);
    }
    this.mouseMove = function (ev) {
        if (self.iMouseDown) {
            ev = ev || window.event;
            var currentEventMod = self.currentEventMod;
            if (currentEventMod == null)return;

            if (!ev.pageX)ev.pageX = ev.clientX;
            if (!ev.pageY)ev.pageY = ev.clientY;
            var diff_x = ev.pageX - self.Positions.x;
            var diff_y = ev.pageY - self.Positions.y;
            //console.log(ev.pageX+">>"+ev.pageY+"################"+diff_x + ">>" + diff_y);
            if (diff_x < 0)diff_x = 0;
            if (diff_y < 0)diff_y = 0;
            //if (diff_y > self.Positions.MaxY - 2) {
            //    diff_y = self.Positions.MaxY - 2
            //}
            //if (diff_x > self.Positions.MaxX - 2) {
            //    diff_x = self.Positions.MaxX - 2
            //}
         //if(!!this.timeHandle)   window.clearTimeout(this.timeHandle);
         //   this.timeHandle=   setTimeout(function(){
                currentEventMod.actionMo.style.left = diff_x + 'px';
                currentEventMod.actionMo.style.top = diff_y + 'px';
            //},5);

            domBase.util.Event.stopEvent(ev);
        }


    }
    this.mouseUp = function (ev) {
        if (self.iMouseDown) {
            self.iMouseDown = false;
            if (self.currentEventMod == null)return;
            domBase.util.Event.releaseCapture(self.currentEventMod.handMo);
            self.currentEventMod = null;
            domBase.util.Event.stopEvent(ev);
        }
    }
    this.getMod = function (target) {
        while ((!domBase.IsMoz()) ? target.parentElement : target.parentNode) {
            if (target.tagName == "BODY" || target.tagName == "INPUT") {
                return null;
            }
            var tempId = target.id
            if (tempId == "") {
                return null;
            }
            if (self.DataBase[tempId]) {
                return self.DataBase[tempId];
            }
            target = ((!domBase.IsMoz()) ? target.parentElement : target.parentNode);
        }
        return null;
    }

    this.init = (function () {
        addEvent(window, 'load', function () {
            var docBody = document.body;
            docBody = document.documentElement;
            addEvent(docBody, "mousedown", self.mouseDown);
            addEvent(docBody, "mousemove", self.mouseMove);
            addEvent(docBody, "mouseup", self.mouseUp);
        });
    })();
}