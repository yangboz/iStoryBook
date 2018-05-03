var ls;
(function (ls) {
    var World = (function () {
        function World() {
            this._objectList = [];
            this._objectHash = {};
            this._updateCamera = true;
            if (World._instance)
                throw new Error(this["constructor"] + "为单例！");
            World._instance = this;
            this._sceneCamera = new ls.SceneCamera(this);
        }
        var d = __define,c=World,p=c.prototype;
        World.getInstance = function () {
            if (this._instance == null)
                this._instance = new World();
            return this._instance;
        };
        d(p, "sceneCamera"
            ,function () {
                return this._sceneCamera;
            }
        );
        d(p, "objectList"
            /**获取世界中的AI对象列*/
            ,function () {
                return this._objectList;
            }
        );
        d(p, "objectHash"
            /**获取世界中的AI Hash结构列表*/
            ,function () {
                return this._objectHash;
            }
        );
        //锁定到目标
        p.scrollToTarget = function (inst) {
            this._sceneCamera.lookAtChar(inst);
        };
        p.scrollToXY = function (x, y) {
            this._sceneCamera.lookAtPoint(new egret.Point(x, y));
        };
        p.scrollToX = function (x) {
            this._sceneCamera.lookAtX(x);
        };
        p.scrollToY = function (y) {
            this._sceneCamera.lookAtY(y);
        };
        d(p, "cameraLayer"
            ,function () {
                return this._sceneCamera.cameraLayer;
            }
        );
        //这里移动的不是整个sceneContainer,而是遍历的对象
        p.render = function () {
        };
        /**根据唯一id查找对象*/
        p.getChildByUID = function ($uid) {
            if ($uid === void 0) { $uid = 0; }
            for (var i = 0, len = this._objectList.length; i < len; i++) {
                var object = this._objectList[i];
                if (object.id == $uid)
                    return object;
            }
            return null;
        };
        /**根据名字查找AiObject对象列表，一般情况下，多个名称列表都是表示关联复制生成的，而不是直接创建生成的*/
        p.getChildByName = function ($name) {
            var findAiObjects = [];
            if ($name == null || $name == "")
                return findAiObjects;
            for (var i = 0, len = this._objectList.length; i < len; i++) {
                var object = this._objectList[i];
                if (object.name == $name)
                    findAiObjects.push(object);
            }
            return findAiObjects;
        };
        p.addChildLayerAt = function ($child, layerIndex, index) {
            var container = $child["container"];
            if (!(container == undefined || container == null)) {
                var layer = ls.LayerManager.getLayer(layerIndex);
                if (!layer) {
                    layer = ls.LayerManager.getLayerByIndex($child, layerIndex);
                    layer.parallaxX = $child.parallaxX;
                    layer.parallaxY = $child.parallaxY;
                    layer.addChild(container);
                }
                else {
                    layer.addChildAt(container, index);
                }
            }
            this._addChild($child);
        };
        p.addChild = function ($child) {
            var container = $child["container"];
            if (!(container == undefined || container == null)) {
                var layerIndex = $child.layer;
                var layer = ls.LayerManager.getLayerByIndex($child, layerIndex);
                layer.parallaxX = $child.parallaxX;
                layer.parallaxY = $child.parallaxY;
                layer.addChild(container);
            }
            this._addChild($child);
        };
        p._addChild = function ($child) {
            var name = $child.name;
            if (!this._objectHash.hasOwnProperty(name))
                this._objectHash[name] = [];
            var __searchIndex = -1;
            for (var i = 0; i < this._objectHash[name].length; i++) {
                var __inst = this._objectHash[name][i];
                if (__inst.u_id == $child.u_id) {
                    __searchIndex = i;
                    break;
                }
            }
            if (__searchIndex == -1) {
                this._objectHash[name].push($child);
                this._objectList.push($child);
            }
            else
                this._objectHash[name][__searchIndex] = $child;
        };
        //移除对象，全局对象不删除
        p.removeChild = function ($child) {
            if ($child) {
                //删除显示列表
                var _index = this.objectList.indexOf($child);
                if (_index != -1)
                    this._objectList.splice(_index, 1);
                var container = $child["container"];
                if (container && container.parent != null)
                    container.parent.removeChild(container);
                //删除对象列表
                if (this._objectHash.hasOwnProperty($child.name)) {
                    var list = this._objectHash[$child.name];
                    var __searchIndex = -1;
                    for (var i = 0; i < list.length; i++) {
                        var __inst = list[i];
                        if (__inst.u_id == $child.u_id) {
                            __searchIndex = i;
                            break;
                        }
                    }
                    if (__searchIndex != -1)
                        list.splice(__searchIndex, 1);
                }
                //移除布局容器对象
                if ($child.id >= 0)
                    delete ls.LayoutDecoder.curSceneInstances[$child.id];
                ls.Trigger.removeTrigger($child);
            }
        };
        //移除所有的角色(全局实例只保存实例本身，不保存其事件表中的内容)
        p.removeAllChildrens = function () {
            ls.Trigger.removeAllTriggers();
            for (var name in this._objectHash) {
                var data = this._objectHash[name];
                if (data) {
                    for (var i = 0; i < data.length; i++) {
                        var instance = data[i];
                        if (!instance.global) {
                            instance.destoryOnChangeScene();
                            i--;
                        }
                    }
                }
            }
        };
        //destory all
        p.destory = function () {
            this._sceneCamera.lookAtPoint(new egret.Point(0, 0));
            if (World.onWorldDestory != null)
                World.onWorldDestory();
            ls.LayoutDecoder.destory();
            ls.EventSheetDecoder.destory();
            this._sceneCamera.resetCamera();
            this.removeAllChildrens();
        };
        World.renderFirst = true;
        return World;
    }());
    ls.World = World;
    egret.registerClass(World,'ls.World');
})(ls || (ls = {}));
//# sourceMappingURL=World.js.map