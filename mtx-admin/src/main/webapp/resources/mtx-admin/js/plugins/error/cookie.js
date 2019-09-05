/*
 - cookie 操作相关类库
 - zowell@20090313 17:04
*/ 

var cookie=EHM.Cookie = new function(){
	var self=this;
	this.get=function(cN) {
		var c_V = {};
		var ck = document.cookie;
		var ret=null;
		var Ar=ck.split(";");
		var Str="";
		for(var i=0;i<Ar.length;i++){
			var _Arr=Ar[i].split("=");
			c_V[_Arr[0].trim()]=(_Arr.length>1)?_Arr[1].trim():"";
			}
		return c_V[cN];
	};
	this.set=function(cN, c_V, expires, path, domain, secure){
		var date=new Date();
		expires=new Date();
		expires.setMonth(expires.getMonth()+10)
		var c=escape(cN) + '=' + escape(c_V)+ (expires ? '; expires=' + expires.toGMTString() : '')+ ('; path=\/')+ (domain ? '; domain=' + domain : '')+ (secure ? '; secure' : '');
		document.cookie =c;
		};
	this.clear=function(){document.cookie ="";};
	this.reset=function(){};
	};
