var ls;
(function (ls) {
    var LayerManager = (function () {
        function LayerManager() {
        }
        var d = __define,c=LayerManager,p=c.prototype;
        LayerManager.getLayerByIndex = function (target, index) {
            if (this.layers[index] === undefined) {
                var layer = new ls.Layer();
                layer.layerIndex = index;
                layer.scrollX = ls.GameUILayer.stage.stageWidth / 2;
                layer.scrollY = ls.GameUILayer.stage.stageHeight / 2;
                this.layers[index] = layer;
                var container = ls.GameUILayer.renderContainer;
                var insertLayerIndex = 0;
                var nums = container.numChildren;
                for (var i = 0; i < nums; i++) {
                    var object = container.getChildAt(i);
                    var layerIndex = object.layerIndex;
                    if (index > layerIndex) {
                        insertLayerIndex++;
                        continue;
                    }
                    break;
                }
                var layerVo = ls.LayoutDecoder.layers[index];
                if (layerVo === undefined) {
                    //全局实例可能为空，比如场景2中在图层2中创建了全局实例，在场景1中只有图层1，那么，会在图场景1中创建图层2
                    layerVo = new ls.LayerVo();
                    layerVo.index = index;
                    layerVo.parallaxX = target.parallaxX;
                    layerVo.parallaxY = target.parallaxY;
                    layerVo.layerAlpha = 1;
                    layerVo.layerVisible = true;
                    layerVo.layerScaleX = 100;
                    layerVo.layerScaleY = 100;
                    layerVo.cacheAsBitmap = false;
                    ls.LayoutDecoder.layers[index] = layerVo;
                }
                layer.scaleX = layerVo.layerScaleX / 100;
                layer.scaleY = layerVo.layerScaleY / 100;
                layer.alpha = layerVo.layerAlpha;
                layer.visible = layerVo.layerVisible;
                layer.cacheAsBitmap = layerVo.cacheAsBitmap;
                ls.GameUILayer.renderContainer.addChildAt(layer, insertLayerIndex);
            }
            return this.layers[index];
        };
        LayerManager.getIndexByLayer = function ($layer) {
            if ($layer)
                return $layer.layerIndex;
            return -1;
        };
        LayerManager.getLayer = function ($index) {
            return this.layers[$index];
        };
        LayerManager.layers = {};
        return LayerManager;
    }());
    ls.LayerManager = LayerManager;
    egret.registerClass(LayerManager,'ls.LayerManager');
})(ls || (ls = {}));
//# sourceMappingURL=LayerManager.js.map