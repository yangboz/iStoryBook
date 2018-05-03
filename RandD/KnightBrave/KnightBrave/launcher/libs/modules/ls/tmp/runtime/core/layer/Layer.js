var ls;
(function (ls) {
    var Layer = (function (_super) {
        __extends(Layer, _super);
        function Layer() {
            _super.call(this);
            this.updateCamera = true;
        }
        var d = __define,c=Layer,p=c.prototype;
        p.lookAtPoint = function (pos) {
            if (this.oldX != pos.x) {
                this.oldX = pos.x;
                this.updateCamera = true;
                this.scrollX = pos.x;
            }
            if (this.oldY != pos.y) {
                this.oldY = pos.y;
                this.updateCamera = true;
                this.scrollY = pos.y;
            }
        };
        p.lookAt = function (x, y) {
            if (this.oldX != x) {
                this.oldX = x;
                this.updateCamera = true;
                this.scrollX = x;
            }
            if (this.oldY != y) {
                this.oldY = y;
                this.updateCamera = true;
                this.scrollY = y;
            }
        };
        p.lookAtX = function (x) {
            if (this.oldX != x) {
                this.oldX = x;
                this.scrollX = x;
                this.updateCamera = true;
            }
        };
        p.lookAtY = function (y) {
            if (this.oldY != y) {
                this.oldY = y;
                this.scrollY = y;
                this.updateCamera = true;
            }
        };
        p.add = function (addValueX, addValueY) {
            if (!this.isSet) {
                this.x -= addValueX;
                this.y -= addValueY;
                this.isSet = true;
            }
        };
        return Layer;
    }(egret.Sprite));
    ls.Layer = Layer;
    egret.registerClass(Layer,'ls.Layer');
})(ls || (ls = {}));
//# sourceMappingURL=Layer.js.map