/**
 * ztree插件的封装 （有checkbox）
 */

(function(){

    var $ZTree = function(id, url,settings) {
        this.id = id;
        this.url = url;
        this.onClick = null;
        this.settings = settings;
        this.ondblclick=null;
    };


    $ZTree.prototype = {
        /**
         * 初始化ztree的设置
         */
        initSetting : function() {
            var settings = {
                view : {
                    dblClickExpand : true,
                    selectedMulti : false
                },
                check : {},
                data : {simpleData : {enable : true}},
                callback : {
                    onClick : zTreeOnClick,
                    onDblClick:this.ondblclick,
                    onCheck: zTreeOnCheck,
                    onExpand: zTreeOnExpand,
                }
            };
            return settings;
        },

        /**
         * 手动设置ztree的设置
         */
        setSettings : function(val) {
            this.settings = val;
        },

        setSettingOfCheck: function(){
            if(this.settings == null){
                this.settings = this.initSetting();
            }
            var  check = {
                enable: true,
                chkStyle: "checkbox",
                chkboxType: { "Y": "ps", "N": "ps" }
            };

            this.settings.check = check;
        },
        setSettingOfasync:function(){
            if(this.settings == null){
                this.settings = this.initSetting();
            }
            var async={
                enable:true,
                contentType: "json",
                url: this.url,
                type:"post",
                // autoParam: ["id", "name"],
            }
            this.settings.async = async;
        },

        /**
         * 初始化ztree
         */
        init : function() {
            var zNodeSeting = null;
            if(this.settings != null){
                zNodeSeting = this.settings;
            }else{
                zNodeSeting = this.initSetting();
            }
            var zNodes = this.loadNodes();
            $.fn.zTree.init($("#" + this.id), zNodeSeting, zNodes);
            var treeObj = $.fn.zTree.getZTreeObj(this.id);
            treeObj.expandAll(true);
        },

        /**
         * 绑定onclick事件
         */
        bindOnClick : function(func) {
            this.onClick = func;
        },
        /**
         * 绑定双击事件
         */
        bindOnDblClick : function(func) {
            this.ondblclick=func;
        },
        /**
         * 加载节点
         */
        loadNodes : function() {
            var zNodes = null;
            var ajax = new $ax( this.url, function(data) {
                zNodes = data;
            }, function(data) {
                //Feng.error("加载ztree信息失败!");
            });
            ajax.start();
            zNodes = JSON.parse(zNodes.result);
            return zNodes;
        },

        /**
         * 获取选中的值
         */
        getSelectedVal : function(){
            var zTree = $.fn.zTree.getZTreeObj(this.id);
            var nodes = zTree.getSelectedNodes();
            return nodes[0].name;
        }
    };
    function zTreeOnExpand(event, treeId, treeNode) {
        var pidval=treeNode.pId;
        var level=treeNode.level;
        $("#pId").val(pidval);
        $("#level").val(level);
    };
    var firstAsyncSuccessFlag = 0;
    function zTreeOnAsyncSuccess(event, treeId, msg) {
        if (firstAsyncSuccessFlag == 0) {
            try {
                //调用默认展开第一个结点
                var selectedNode = zTree.getSelectedNodes();
                var nodes = zTree.getNodes();
                zTree.expandNode(nodes[0], true);

                var childNodes = zTree.transformToArray(nodes[0]);
                zTree.expandNode(childNodes[1], true);
                zTree.selectNode(childNodes[1]);
                var childNodes1 = zTree.transformToArray(childNodes[1]);
                zTree.checkNode(childNodes1[1], true, true);
                firstAsyncSuccessFlag = 1;
            } catch (err) {

            }
        }
    }
    function zTreeOnClick(event, treeId, treeNode) {

    }
    function zTreeOnCheck(event, treeId, treeNode) {
        var villageTree = $.fn.zTree.getZTreeObj("villageTree");
        var changedNodes = villageTree.getCheckedNodes();
        var vids=[],minArr=[],flag=true;
        for(var i=0;i<changedNodes.length;i++){
            var treeNode = changedNodes[i].id;
            if(changedNodes[i].isParent){
                vids.push(treeNode)
            }else{
                if(isNaN(treeNode)){
                    flag=false
                    minArr.push(treeNode.substring(5));
                }else{
                    vids.push(treeNode)
                }
            }
        }
        if(!flag){
            $("#vids").val(minArr);
        }else{
            $("#vids").val(vids);
        }
    };
    window.$ZTree = $ZTree;
})()