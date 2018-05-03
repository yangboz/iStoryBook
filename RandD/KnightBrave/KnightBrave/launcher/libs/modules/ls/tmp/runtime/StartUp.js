var ls;
(function (ls) {
    var StartUp = (function () {
        function StartUp() {
        }
        var d = __define,c=StartUp,p=c.prototype;
        StartUp.execute = function (document) {
            this.stage = document.stage;
            //初始化图层
            this.onLayerInit(document);
            this.onPreResourceLoad();
        };
        StartUp.onLayerInit = function (document) {
            ls.GameUILayer.init(document.stage);
            ls.GameUILayer.document = document;
        };
        StartUp.onPreResourceLoad = function () {
            var projectsData = ls.ResCache.configResouces["projects"];
            if (projectsData)
                this.publish = true;
            if (this.publish) {
                StartUp.loadConfig(projectsData.children);
            }
            else {
                RES.getResByUrl("resource/assets/projects.xml", function (data) {
                    StartUp.loadConfig(data.children);
                }, this, RES.ResourceItem.TYPE_XML);
            }
        };
        StartUp.loadConfig = function (projectsData) {
            var children = projectsData;
            //先解析
            if (children) {
                for (var i = 0; i < children.length; i++) {
                    var item = children[i];
                    var lname = item.localName;
                    var attributes = item.attributes;
                    if (lname === "internal") {
                        var internalStr = item.children[0].text;
                        var internalObject = JSON.parse(decodeURIComponent(internalStr));
                        ls.internalData = internalObject.internalComponents;
                    }
                }
                for (var i = 0; i < children.length; i++) {
                    var item = children[i];
                    var lname = item.localName;
                    var attributes = item.attributes;
                    switch (lname) {
                        case "sceneSize":
                            ls.Config.sceneWidth = +(attributes.width);
                            ls.Config.sceneHeight = +(attributes.height);
                            ls.Config.openLog = (+(attributes.openLog) == 1);
                            break;
                        case "info":
                            StartUp.baseUrl = attributes.baseUrl;
                            ls.Config.sceneInfo = { layoutName: attributes.layoutName, layoutUrl: attributes.layoutUrl, eventsheetName: attributes.eventsheetName, eventsheetUrl: attributes.eventsheetUrl, baseUrl: StartUp.baseUrl };
                            if (this.publish) {
                                this.globalData = ls.ResCache.configResouces["global"];
                                ls.LayoutDecoder.globalInstances = ls.LayoutDecoder.decodeInstances(this.globalData);
                                //解析布局表
                                ls.Config.sceneInfo.layoutData = ls.ResCache.configResouces[ls.Config.sceneInfo.layoutName];
                                //解析事件表
                                ls.Config.sceneInfo.eventsheetData = ls.ResCache.configResouces[ls.Config.sceneInfo.eventsheetName];
                                //开始布局与事件表
                                ls.LayoutDecoder.start(ls.Config.sceneInfo.layoutName);
                                ls.EventSheetDecoder.start(ls.Config.sceneInfo.eventsheetName);
                            }
                            else {
                                var self = this;
                                //加载全局变量
                                self.onGlobalConfigLoad(ls.Config.sceneInfo.baseUrl + "global.xml", function () {
                                    //加载布局配置
                                    RES.getResByUrl(ls.Config.sceneInfo.baseUrl + ls.Config.sceneInfo.layoutUrl, function (layoutData) {
                                        ls.Config.sceneInfo.layoutData = layoutData;
                                        //加载事件表配置
                                        RES.getResByUrl(ls.Config.sceneInfo.baseUrl + ls.Config.sceneInfo.eventsheetUrl, function (eventsheetData) {
                                            ls.Config.sceneInfo.eventsheetData = eventsheetData;
                                            ls.LayoutDecoder.start(ls.Config.sceneInfo.layoutName);
                                            ls.EventSheetDecoder.start(ls.Config.sceneInfo.eventsheetName);
                                        }, this);
                                    }, ls.Config.sceneInfo);
                                }, [ls.Config.sceneInfo.layoutName]);
                            }
                            break;
                        case "version":
                            ls.Config.version = +(item.text);
                            break;
                    }
                }
            }
        };
        //全局配置加载
        StartUp.onGlobalConfigLoad = function (url, onComplete, onCompleteParams) {
            if (onComplete === void 0) { onComplete = null; }
            if (onCompleteParams === void 0) { onCompleteParams = null; }
            RES.getResByUrl(url, function (data) {
                this.globalData = data;
                //全局实例添加到舞台
                ls.LayoutDecoder.globalInstances = ls.LayoutDecoder.decodeInstances(this.globalData);
                if (onComplete != null)
                    onComplete.apply(null, onCompleteParams);
            }, this, RES.ResourceItem.TYPE_XML);
        };
        /**配置加载 */
        StartUp.onConfigLoad = function (layoutName) {
            ls.Config.sceneInfo.layoutName = layoutName;
            ls.Config.sceneInfo.layoutUrl = layoutName + ".xml";
            if (this.publish) {
                //解析布局表
                ls.Config.sceneInfo.layoutData = ls.ResCache.configResouces[ls.Config.sceneInfo.layoutName];
                ls.Config.sceneInfo.eventsheetName = ls.Config.sceneInfo.layoutData.attributes.eventSheet;
                //解析事件表
                ls.Config.sceneInfo.eventsheetData = ls.ResCache.configResouces[ls.Config.sceneInfo.layoutData.attributes.eventSheet];
                ls.Config.sceneInfo.eventsheetUrl = ls.Config.sceneInfo.layoutData.attributes.eventSheet + ".xml";
                ls.LayoutDecoder.start(ls.Config.sceneInfo.layoutName);
                ls.EventSheetDecoder.start(ls.Config.sceneInfo.eventsheetName);
            }
            else {
                //加载布局配置
                RES.getResByUrl(ls.Config.sceneInfo.baseUrl + ls.Config.sceneInfo.layoutUrl, function (layoutData) {
                    ls.Config.sceneInfo.layoutData = layoutData;
                    ls.Config.sceneInfo.eventsheetName = layoutData.attributes.eventSheet;
                    ls.Config.sceneInfo.eventsheetUrl = ls.Config.sceneInfo.eventsheetName + ".xml";
                    //加载事件表配置
                    RES.getResByUrl(ls.Config.sceneInfo.baseUrl + ls.Config.sceneInfo.eventsheetUrl, function (eventsheetData) {
                        ls.Config.sceneInfo.eventsheetData = eventsheetData;
                        ls.LayoutDecoder.start(ls.Config.sceneInfo.layoutName);
                        ls.EventSheetDecoder.start(ls.Config.sceneInfo.eventsheetName);
                    }, this);
                }, this);
            }
        };
        StartUp.publish = false;
        return StartUp;
    }());
    ls.StartUp = StartUp;
    egret.registerClass(StartUp,'ls.StartUp');
})(ls || (ls = {}));
//# sourceMappingURL=StartUp.js.map