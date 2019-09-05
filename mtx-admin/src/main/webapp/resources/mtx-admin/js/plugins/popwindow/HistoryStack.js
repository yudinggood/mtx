// JavaScript Document
//ehm鐨勫巻鍙茬邯褰曞爢鏍�
//鍦ㄨ繖閲� 骞舵病鏈夊姭鎸佹祻瑙堝櫒鐨勮繑鍥炵瓑鎸夋壄锛岃嫢鏈夐渶瑕佽嚜琛屾坊鍔犮��
//zowell@20090105
function HistoryStack()
{
    this.stack = new Array();
    this.current = -1;
    //鎸囬拡
    this.stack_limit = 8;//鏍堟繁
}
//鍏ユ爤浠庡綋鍓嶆寚閽堝紑濮�
HistoryStack.prototype.push = function(resource)
{
    if (this.stack.length > 0) {
        this.stack = this.stack.slice(0, this.current + 1);
    }
    this.stack.push(resource);
    while (this.stack.length > this.stack_limit) {
        this.stack.shift();
    }
    this.current = this.stack.length - 1;
};
HistoryStack.prototype.getCurrent = function () {
    return this.stack[this.current];
};
HistoryStack.prototype.Prev = function () {
    if (this.hasPrev()) {
        this.current--;
    }
};
HistoryStack.prototype.hasPrev = function() {
    return (this.current > 0);
};
HistoryStack.prototype.Next = function () {
    if (this.hasNext()) {
        this.current++;
    }
};
HistoryStack.prototype.hasNext = function() {
    return (this.current < this.stack.length - 1 && this.current > -1);
};
HistoryStack.prototype.show = function() {//娴嬭瘯鐢�
    var divs = document.getElementById("showhis");
    divs.innerHTML = "";
    for (var i = 0,len = this.stack.length; i < len; i++) {
        var d = document.createElement("DIV");
        d.innerHTML = this.stack[i];
        divs.appendChild(d);
    }
};