var ls;
(function (ls) {
    var ResCache = (function () {
        function ResCache() {
        }
        var d = __define,c=ResCache,p=c.prototype;
        ResCache.configResouces = {}; //所有场景配置文件数据
        ResCache.componentResources = {}; //所有组件的资源地址，不包括图片
        return ResCache;
    }());
    ls.ResCache = ResCache;
    egret.registerClass(ResCache,'ls.ResCache');
})(ls || (ls = {}));
//# sourceMappingURL=ResCache.js.map