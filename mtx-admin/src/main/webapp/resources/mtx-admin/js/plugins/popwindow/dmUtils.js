function dmPoint(x, y) {
    this.x = (x != null) ? x : 0;
    this.y = (y != null) ? y : 0;
};
dmPoint.prototype.x = null;
dmPoint.prototype.y = null;
dmPoint.prototype.equals = function(obj) {
    return obj.x == this.x && obj.y == this.y;
};
var dmUtils = {
    fit:function(node) {
        var left = parseInt(node.offsetLeft);
        var width = parseInt(node.offsetWidth);
        var b = document.body;
        var d = document.documentElement;
        var right = (b.scrollLeft || d.scrollLeft) + (b.clientWidth || d.clientWidth);
        if (left + width > right) {
            node.style.left = Math.max((b.scrollLeft || d.scrollLeft), right - width) + 'px';
        }
        var top = parseInt(node.offsetTop);
        var height = parseInt(node.offsetHeight);
        var bottom = (b.scrollTop || d.scrollTop) + Math.max(b.clientHeight || 0, d.clientHeight);
        if (top + height > bottom) {
            node.style.top = Math.max((b.scrollTop || d.scrollTop), bottom - height) + 'px';
        }
    },
    bind:function(scope, funct) {
        return function() {
            return funct.apply(scope, arguments);
        };
    },getScrollOrigin:function(node) {
        var b = document.body;
        var d = document.documentElement;
        var sl = (b.scrollLeft || d.scrollLeft);
        var st = (b.scrollTop || d.scrollTop);
        var result = new dmPoint(sl, st);
        while (node != null && node != b && node != d) {
            result.x += node.scrollLeft;
            result.y += node.scrollTop;
            node = node.parentNode;
        }
        return result;
    }
}