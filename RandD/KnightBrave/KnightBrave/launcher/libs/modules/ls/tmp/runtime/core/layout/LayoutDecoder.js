var ls;
(function (ls) {
    var Layout = (function () {
        function Layout() {
        }
        var d = __define,c=Layout,p=c.prototype;
        return Layout;
    }());
    ls.Layout = Layout;
    egret.registerClass(Layout,'ls.Layout');
    var LayoutDecoder = (function () {
        function LayoutDecoder() {
        }
        var d = __define,c=LayoutDecoder,p=c.prototype;
        LayoutDecoder.getTexture = function (name) {
            for (var resName in this.spriteSheets) {
                var spriteSheet = this.spriteSheets[resName];
                var texture = spriteSheet.getTexture(name);
                if (texture != undefined) {
                    return texture;
                }
            }
            return null;
        };
        LayoutDecoder.saveLayout = function (layoutName) {
            var layout = ls.Config.sceneInfo.layoutData;
            var layoutVo = new Layout();
            layoutVo.layoutName = layoutName;
            layoutVo.nextLayoutName = layout.attributes.next;
            layoutVo.prevLayoutName = layout.attributes.previous;
            layoutVo.eventSheetName = layout.attributes.eventSheet;
            var version = layout.attributes.version;
            layoutVo.version = version ? version : "1.1.1";
            this.layouts[layoutName] = layoutVo;
            this.layoutVo = layoutVo;
        };
        /**解析表达式 */
        LayoutDecoder.decodeExpressions = function () {
            //先进行解码
            if (!this.layoutVo.eventSheetName)
                return;
            var layoutHandler = ls[this.layoutVo.layoutName];
            if (layoutHandler === undefined)
                return;
            var exps = layoutHandler();
            ls.EventSheetDecoder.expressionObject = {};
            for (var expkey in exps) {
                var curkey = decodeURIComponent(expkey);
                curkey = curkey.replace(/^['\"]{1}(.*)['\"]{1}$/, "$1");
                ls.EventSheetDecoder.expressionObject[curkey] = exps[expkey];
            }
        };
        LayoutDecoder.start = function (layoutName) {
            ls.assert(layoutName == null || layoutName == "", "layout canot null!");
            var layout = ls.Config.sceneInfo.layoutData;
            ls.assert(layout == null, "can not find" + layoutName);
            var sceneSize = layout.attributes.sceneSize.split(",");
            this.sceneWidth = +(sceneSize[0]);
            this.sceneHeight = +(sceneSize[1]);
            this.currentLayoutName = layoutName;
            this.saveLayout(layoutName);
            this.decodeExpressions();
            var layoutDataList = layout.children;
            if (layoutDataList) {
                //初始化场景实例
                var sorts = [];
                for (var i = 0, itemlen = layoutDataList.length; i < itemlen; i++) {
                    var data = layoutDataList[i];
                    if (data.localName != "layer") {
                        var instance = this.decodeInstance(data);
                        if (instance && instance["index"] !== undefined) {
                            var sortIndex = data.attributes.index ? (+data.attributes.index) : i;
                            instance.index = sortIndex;
                            sorts.push(instance);
                        }
                    }
                    else {
                        if (this.layers[+data.attributes.index] == null) {
                            var layerVo = this.decodeLayers(data);
                            this.layers[layerVo.index] = layerVo;
                        }
                    }
                }
                sorts = sorts.concat(LayoutDecoder.globalInstances);
                sorts.sort(function (a, b) {
                    if (a.index > b.index)
                        return 1;
                    else if (a.index < b.index)
                        return -1;
                    else
                        return 0;
                });
                for (var i = 0; i < sorts.length; i++) {
                    var instance = sorts[i];
                    ls.World.getInstance().addChild(instance);
                }
            }
            //为了在事件表中可以直接引用，必须先实体化非场景实例
            if (ls.internalData) {
                for (var i = 0; i < ls.internalData.length; i++) {
                    var _name = ls.internalData[i].name;
                    var nosceneInst = ls.getInstanceByPluginClassName(_name, true);
                    if (nosceneInst) {
                        if (nosceneInst.initialize)
                            nosceneInst.initialize();
                        this.noSceneInstances[_name] = nosceneInst;
                    }
                }
            }
            //注册模板实例，这样，就可以根据实例名来引用实例了
            for (var uid in this.curSceneInstances) {
                ls.registerObject(this.curSceneInstances[uid].name, this.curSceneInstances[uid]);
            }
        };
        LayoutDecoder.decodeInstances = function (datas) {
            var instances = [];
            if (datas) {
                var children = datas.children;
                if (children) {
                    for (var i = 0; i < children.length; i++) {
                        var item = children[i];
                        if (item.localName == "layer") {
                            if (this.layers[+item.attributes.index] == null) {
                                var layerVo = this.decodeLayers(item);
                                this.layers[layerVo.index] = layerVo;
                            }
                        }
                        else {
                            var instance = this.decodeInstance(item);
                            if (instance && instance["index"] !== undefined)
                                instance.index = +item.attributes.index;
                            if (instance)
                                instances.push(instance);
                        }
                    }
                }
            }
            return instances;
        };
        //解析图层数据        
        LayoutDecoder.decodeLayers = function (data) {
            var layerVo = new ls.LayerVo();
            layerVo.index = +data.attributes.index;
            layerVo.parallaxX = +data.attributes.parallaxX;
            layerVo.parallaxY = +data.attributes.parallaxY;
            layerVo.layerAlpha = +data.attributes.layerAlpha;
            layerVo.layerVisible = data.attributes.index != "false";
            layerVo.layerScaleX = +data.attributes.layerScaleX;
            layerVo.layerScaleY = +data.attributes.layerScaleY;
            layerVo.cacheAsBitmap = data.attributes.cacheAsBitmap == "true";
            return layerVo;
        };
        //解析实例列表
        LayoutDecoder.decodeInstance = function (data) {
            if (data.localName == "spritesheets")
                return;
            var UID = +data.attributes.UID;
            var plugin = data.attributes.plugin;
            var instanceName = data.attributes.name;
            //为了兼容性，这里过滤掉单例
            if (ls.isInternal(instanceName))
                return null;
            //判断当前是否存在这个实例
            var instance = this.curSceneInstances[UID];
            if (instance == undefined) {
                instance = ls.getInstanceByPluginClassName(plugin);
            }
            else {
                return instance;
            }
            instance.isModel = true;
            instance.name = instanceName;
            instance.id = UID;
            instance.parallaxX = +data.attributes.parallaxX;
            instance.parallaxY = +data.attributes.parallaxY;
            if (data.children == null)
                return;
            if (this.instanceNames[instanceName] == null)
                this.instanceNames[instanceName] = instanceName;
            var isHasLayer = false;
            //临时存储属性列表
            var properties = {};
            //解析属性
            var behaviorPropertyItem;
            for (var j = 0, propertylen = data.children.length; j < propertylen; j++) {
                var propertyItem = data.children[j];
                var propertyName = propertyItem.attributes.name;
                var propertyValue = propertyItem.attributes.value;
                var propertyValueType = propertyItem.attributes.valueDataType;
                switch (propertyName) {
                    case "layer":
                        isHasLayer = true;
                        instance[propertyName] = +propertyValue;
                        break;
                    case "actions":
                        instance["setData"](propertyItem.children);
                        break;
                    case "behaviors":
                        behaviorPropertyItem = propertyItem;
                        break;
                    default:
                        switch (propertyValueType) {
                            case "number":
                                properties[propertyName] = +propertyValue;
                                break;
                            case "string":
                                // if(propertyName!="name")
                                //     properties[propertyName] = eval_e(decodeURIComponent(propertyValue));
                                // else
                                properties[propertyName] = decodeURIComponent(propertyValue);
                                //解析碰撞数据
                                if (propertyName == "collisionData") {
                                    instance.collisionVectorData = this.decodeCollision(instance, properties[propertyName]);
                                    instance["collisionSourceData"] = decodeURIComponent(propertyItem.attributes.sourceValue);
                                    instance.collisionSourceVectorData = this.decodeCollision(instance, instance["collisionSourceData"]);
                                }
                                break;
                            case "boolean":
                                properties[propertyName] = ls.eval_e(propertyValue);
                                break;
                            case "any":
                                properties[propertyName] = ls.eval_e(decodeURIComponent(propertyValue));
                                break;
                        }
                        break;
                }
            }
            if (properties.hasOwnProperty("width"))
                instance["width"] = properties["width"];
            if (properties.hasOwnProperty("height"))
                instance["height"] = properties["height"];
            for (var prop in properties) {
                instance[prop] = properties[prop];
            }
            if (behaviorPropertyItem) {
                this.decodeGlobalBehaviors(instance, behaviorPropertyItem);
            }
            //这里假定不设置layer属性，那么，这里默认设置其图层为1
            if (!isHasLayer)
                instance["layer"] = 0;
            //所有的扩展组件进行初始化
            if (instance)
                instance.initialize();
            if (this.curSceneInstances[UID] == null)
                this.curSceneInstances[UID] = instance;
            return instance;
        };
        //解析碰撞数据
        LayoutDecoder.decodeCollision = function (inst, data) {
            var bindData;
            if (inst && data) {
                if (data == "")
                    inst.collisionType - 1;
                else {
                    var spData = data.split('/n');
                    if (spData.length == 2) {
                        inst.collisionType = +spData[0];
                        switch (inst.collisionType) {
                            case 0:
                                //先分隔下划线
                                //检测是否存在下划线
                                bindData = [];
                                var isExistUnderline = spData[1].indexOf("_");
                                if (isExistUnderline != -1) {
                                    var underlineSplit = spData[1].split('_');
                                    for (var i = 0; i < underlineSplit.length; i++) {
                                        var undrelineItem = underlineSplit[i];
                                        var vdSplit = undrelineItem.split(',');
                                        var polyData = [];
                                        for (var j = 0; j < vdSplit.length; j++) {
                                            var pointSplit = vdSplit[j].split('|');
                                            var v = new ls.Vector2D(+pointSplit[0], +pointSplit[1]);
                                            polyData[j] = v;
                                        }
                                        bindData[i] = polyData;
                                    }
                                }
                                else {
                                    var vdSplit = spData[1].split(',');
                                    var polyData = [];
                                    for (var i = 0; i < vdSplit.length; i++) {
                                        var pointSplit = vdSplit[i].split('|');
                                        var v = new ls.Vector2D(+pointSplit[0], +pointSplit[1]);
                                        polyData[i] = v;
                                    }
                                    bindData[0] = polyData;
                                }
                                break;
                            case 1:
                                var circleData = spData[1].split("|"); //x,y,r;
                                var circle = new ls.Circle();
                                circle.center = new ls.Vector2D(+circleData[0], +circleData[1]);
                                circle.radius = +circleData[2];
                                bindData = circle;
                                break;
                            case 2:
                                var dotData = spData[1].split("|"); //x，y;
                                bindData = new ls.Vector2D(+dotData[0], +dotData[1]);
                                break;
                        }
                    }
                }
            }
            return bindData;
        };
        //解析全局实例行为列表
        LayoutDecoder.decodeGlobalBehaviors = function (instance, datas) {
            if (instance && datas) {
                if (instance.global) {
                    var children = datas.children;
                    if (children) {
                        for (var i = 0; i < children.length; i++) {
                            var data = children[i];
                            var behavior = ls.EventSheetDecoder.decodeBehavior(data);
                            if (instance instanceof ls.AIDisplayObject)
                                instance.addBehavior(behavior);
                            else
                                ls.assert(true, instance + "must instance of AIDisplayObject for have Behaviors");
                        }
                    }
                }
            }
        };
        /**
         * 销毁实例
         */
        LayoutDecoder.destory = function () {
            this.instanceNames = {};
            this.layers = {};
        };
        LayoutDecoder.curSceneInstances = {};
        LayoutDecoder.noSceneInstances = {};
        LayoutDecoder.instanceNames = {}; //根据名字存储对象
        LayoutDecoder.layouts = {};
        LayoutDecoder.spriteSheets = {};
        LayoutDecoder.spriteSheetDatas = {};
        LayoutDecoder.globalInstances = []; //全局实例
        LayoutDecoder.layers = {}; //图层数据
        return LayoutDecoder;
    }());
    ls.LayoutDecoder = LayoutDecoder;
    egret.registerClass(LayoutDecoder,'ls.LayoutDecoder');
})(ls || (ls = {}));
//# sourceMappingURL=LayoutDecoder.js.map