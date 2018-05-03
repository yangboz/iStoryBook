var ls;
(function (ls) {
    var SceneCamera = (function () {
        function SceneCamera(scene) {
            this.oldSceneX = 0;
            this.oldSceneY = 0;
            this.newSceneX = 0;
            this.newSceneY = 0;
            this.updateCamera = false;
            this.fov = 250;
            this.scene = scene;
            this._scrollX = (ls.GameUILayer.stage.stageWidth >> 1);
            this._scrollY = (ls.GameUILayer.stage.stageHeight >> 1);
            this.otherLayers = [];
        }
        var d = __define,c=SceneCamera,p=c.prototype;
        p.resetCamera = function () {
            ls.GameUILayer.renderContainer.x = ls.GameUILayer.renderContainer.y = 0;
            this.oldSceneX = this.oldSceneY = 0;
            this.newSceneX = this.newSceneY = 0;
        };
        p.lookAtPoint = function (pos) {
            if (this.pos != pos) {
                this.pos = pos;
                this._scrollX = this.pos.x;
                this._scrollY = this.pos.y;
                this.updateCamera = true;
            }
        };
        p.lookAtX = function (x) {
            if (this._scrollX != x) {
                this.updateCamera = true;
                this._scrollX = x;
            }
        };
        p.lookAtY = function (y) {
            if (this._scrollY != y) {
                this.updateCamera = true;
                this._scrollY = y;
            }
        };
        p.lookAtChar = function (target) {
            this.lookAtTarget = target;
            if (this._scrollX != this.lookAtTarget.x || this._scrollY != this.lookAtTarget.y) {
                this.updateCamera = true;
                this._scrollX = this.lookAtTarget.x;
                this._scrollY = this.lookAtTarget.y;
            }
        };
        d(p, "cameraLayer"
            ,function () {
                return this._cameraLayer;
            }
            ,function (value) {
                this._cameraLayer = value;
                this.otherLayers = [];
                for (var i = 0, l = ls.GameUILayer.renderContainer.numChildren; i < l; i++) {
                    var layer = ls.GameUILayer.renderContainer.getChildAt(i);
                    if (layer === this._cameraLayer)
                        continue;
                    this.otherLayers.push(layer);
                }
            }
        );
        p.render = function () {
            if (this.cameraLayer) {
                if (!this.cameraLayer.updateCamera)
                    return;
                this._cameraLayer = this.cameraLayer;
                var sceneWidth = ls.LayoutDecoder.sceneWidth;
                var sceneHeight = ls.LayoutDecoder.sceneHeight;
                var stageWidth = ls.GameUILayer.stage.stageWidth;
                var stageHeight = ls.GameUILayer.stage.stageHeight;
                var ox = stageWidth >> 1;
                var oy = stageHeight >> 1;
                var rightBound = sceneWidth - ((stageWidth / this.cameraLayer.scaleX) >> 1);
                var buttomBound = sceneHeight - ((stageHeight / this.cameraLayer.scaleY) >> 1);
                if (this.cameraLayer.scrollX < ox / this.cameraLayer.scaleX)
                    this.cameraLayer.scrollX = ox / this.cameraLayer.scaleX;
                if (this.cameraLayer.scrollY < oy / this.cameraLayer.scaleY)
                    this.cameraLayer.scrollY = oy / this.cameraLayer.scaleY;
                if (this.cameraLayer.scrollX > rightBound)
                    this.cameraLayer.scrollX = rightBound;
                if (this.cameraLayer.scrollY > buttomBound)
                    this.cameraLayer.scrollY = buttomBound;
                this.cameraLayer.tx = (this.cameraLayer.scrollX - ox) * this.cameraLayer.parallaxX / 100;
                this.cameraLayer.ty = (this.cameraLayer.scrollY - oy) * this.cameraLayer.parallaxY / 100;
                this.cameraLayer.dScrollX = (!isNaN(this.cameraLayer.oldScrollX)) ? (this.cameraLayer.scrollX - this.cameraLayer.oldScrollX) : (this.cameraLayer.scrollX - ox);
                this.cameraLayer.dScrollY = (!isNaN(this.cameraLayer.oldScrollY)) ? (this.cameraLayer.scrollY - this.cameraLayer.oldScrollY) : (this.cameraLayer.scrollY - oy);
                this.cameraLayer.oldScrollX = this.cameraLayer.scrollX;
                this.cameraLayer.oldScrollY = this.cameraLayer.scrollY;
                this.cameraLayer.x = -this.cameraLayer.tx * this.cameraLayer.scaleX;
                this.cameraLayer.y = -this.cameraLayer.ty * this.cameraLayer.scaleY;
                this.cameraLayer.x -= ox * (this.cameraLayer.scaleX - 1);
                this.cameraLayer.y -= oy * (this.cameraLayer.scaleY - 1);
                //如果图层缩放了，摄像机驱动的背景移动有问题
                //其它层
                //摄像机层移动多少像素，那么，其它层将移动的像素值为其它层的视差值比上摄像机的视差值
                //以场景中心为注册点来进行层的位移
                //首先将层移动场景中心    
                //层移动多少距离与视差本身没有绝对关系，还与是否居中有关系
                for (var i = 0, l = this.otherLayers.length; i < l; i++) {
                    var layer = this.otherLayers[i];
                    var tx = this.cameraLayer.tx;
                    var ty = this.cameraLayer.ty;
                    var x1 = -this.cameraLayer.tx * layer.parallaxX / this.cameraLayer.parallaxX;
                    var y1 = -this.cameraLayer.ty * layer.parallaxY / this.cameraLayer.parallaxY;
                    var x2 = ox * (layer.scaleX - 1) - this.cameraLayer.tx;
                    var y2 = oy * (layer.scaleY - 1) - this.cameraLayer.ty;
                    layer.update = false;
                    if (layer.tmpTx != tx) {
                        layer.tmpTx = tx;
                        layer.update = true;
                    }
                    if (layer.tmpTy != ty) {
                        layer.tmpTy = ty;
                        layer.update = true;
                    }
                    if (layer.tmpX1 != x1) {
                        layer.tmpX1 = x1;
                        layer.update = true;
                    }
                    if (layer.tmpY1 != y1) {
                        layer.tmpY1 = y1;
                        layer.update = true;
                    }
                    if (layer.tmpX2 != x2) {
                        layer.tmpX2 = x2;
                        layer.update = true;
                    }
                    if (layer.tmpY2 != y2) {
                        layer.tmpY2 = y2;
                        layer.update = true;
                    }
                    if (layer.update) {
                        layer.anchorOffsetX = this.cameraLayer.tx;
                        layer.anchorOffsetY = this.cameraLayer.ty;
                        layer.x = layer.tmpX1 - layer.tmpX2;
                        layer.y = layer.tmpY1 - layer.tmpY2;
                    }
                }
                this.cameraLayer.updateCamera = false;
            }
        };
        return SceneCamera;
    }());
    ls.SceneCamera = SceneCamera;
    egret.registerClass(SceneCamera,'ls.SceneCamera');
})(ls || (ls = {}));
//# sourceMappingURL=SceneCamera.js.map