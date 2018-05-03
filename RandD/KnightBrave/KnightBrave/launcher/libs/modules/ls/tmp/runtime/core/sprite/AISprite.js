var ls;
(function (ls) {
    var AISprite = (function (_super) {
        __extends(AISprite, _super);
        function AISprite() {
            _super.call(this);
            this._sourceWidth = 0;
            this._sourceHeight = 0;
            this._isResourceLoaded = false;
            this.name = "Sprite";
            this._bitmap = new egret.Bitmap();
        }
        var d = __define,c=AISprite,p=c.prototype;
        d(p, "bitmapURL"
            ,function () {
                return this._bitmapURL;
            }
        );
        p.initialize = function () {
            this.createBitmap(this["url"]);
        };
        /**创建位图*/
        p.createBitmap = function ($url) {
            if ($url && $url != "") {
                this._bitmapURL = $url;
                var self = this;
                var textureDatas = ls.getTexture($url);
                if (textureDatas != null)
                    var texture = textureDatas[0];
                //先从spriteSheet中找
                if (texture != null) {
                    this._bitmap.texture = texture;
                    this._sourceWidth = texture.textureWidth;
                    this._sourceHeight = texture.textureHeight;
                    this._bitmap.width = this.width;
                    this._bitmap.height = this.height;
                    this._bitmap.smoothing = true;
                    if (textureDatas) {
                        this._bitmap.x = textureDatas[1];
                        this._bitmap.y = textureDatas[2];
                    }
                    this.container.addChild(this._bitmap);
                    this.dispatchEvent(new ls.TriggerEvent(ls.TriggerEvent.TRIGGER, self.onResourceLoaded));
                    this.dispatchEvent(new egret.Event(egret.Event.COMPLETE));
                }
                else {
                    var onRESComplete = function (texture) {
                        if (texture) {
                            self._bitmap.texture = texture;
                            self._sourceWidth = texture.textureWidth;
                            self._sourceHeight = texture.textureHeight;
                            self._bitmap.width = self.width;
                            self._bitmap.height = self.height;
                            self.container.addChild(self._bitmap);
                            self.dispatchEvent(new ls.TriggerEvent(ls.TriggerEvent.TRIGGER, self.onResourceLoaded));
                            self.dispatchEvent(new egret.Event(egret.Event.COMPLETE));
                        }
                    };
                    RES.getResByUrl($url, onRESComplete, this, RES.ResourceItem.TYPE_IMAGE);
                }
            }
        };
        //加载图片 动作
        p.loadImage = function ($url) {
            var curUrl = ls.eval_e($url);
            this.createBitmap(curUrl);
        };
        d(p, "width"
            ,function () {
                return this._width;
            }
            ,function (value) {
                if (this._width != value) {
                    this._width = value;
                    this.update = true;
                }
                if (this._bitmap) {
                    if (this._bitmap.width != value)
                        this._bitmap.width = value;
                }
            }
        );
        d(p, "height"
            ,function () {
                return this._height;
            }
            ,function (value) {
                if (this._height != value) {
                    this._height = value;
                    this.update = true;
                }
                if (this._bitmap) {
                    if (this._bitmap.height != value)
                        this._bitmap.height = value;
                }
            }
        );
        ////////////////////////////////////组制API实现///////////////////////////////////   
        p.beginFill = function (color, alpha) {
            color = ls.eval_e(color);
            alpha = ls.eval_e(alpha);
            this.container.graphics.beginFill(color, alpha);
        };
        p.beginGadientFill = function (type, colors, alphas, radios, matrixs) {
            type = ls.eval_e(type);
            var _colors = colors.split(",");
            _colors.forEach(function (value, index, array) {
                value = parseFloat(value);
            });
            var _alphas = alphas.split(",");
            _alphas.forEach(function (value, index, array) {
                value = parseFloat(value);
            });
            var _radios = radios.split(",");
            _radios.forEach(function (value, index, array) {
                value = parseFloat(value);
            });
            var _matrixs = matrixs.split(",");
            _matrixs.forEach(function (value, index, array) {
                value = parseFloat(value);
            });
            if (_matrixs.length > 5)
                ls.assert(true, "渐变填充矩阵数据长度不能大于5");
            var _matrix = new egret.Matrix();
            _matrix.createGradientBox(_matrixs[0], _matrixs[1], _matrixs[2], _matrixs[3], _matrixs[4]);
            this.container.graphics.beginGradientFill(type, _colors, _alphas, _radios, _matrix);
        };
        p.endFill = function () {
            this.container.graphics.endFill();
        };
        p.clear = function () {
            this.container.graphics.clear();
        };
        p.lineStyle = function (thickness, color, alpha, pixelHinting, scaleMode, caps, miterLimit) {
            thickness = ls.eval_e(thickness);
            color = ls.eval_e(color);
            alpha = ls.eval_e(alpha);
            pixelHinting = ls.eval_e(pixelHinting);
            scaleMode = ls.eval_e(scaleMode);
            caps = ls.eval_e(caps);
            miterLimit = ls.eval_e(miterLimit);
            this.container.graphics.lineStyle(thickness, color, alpha, pixelHinting, scaleMode, caps, miterLimit);
        };
        p.moveTo = function (x, y) {
            x = ls.eval_e(x);
            y = ls.eval_e(y);
            this.container.graphics.moveTo(x - this.x + this.anchorOffsetX, y - this.y + this.anchorOffsetY);
        };
        p.lineTo = function (x, y) {
            x = ls.eval_e(x);
            y = ls.eval_e(y);
            this.container.graphics.lineTo(x - this.x + this.anchorOffsetX, y - this.y + this.anchorOffsetY);
        };
        p.drawRect = function (x, y, width, height) {
            x = ls.eval_e(x);
            y = ls.eval_e(y);
            width = ls.eval_e(width);
            height = ls.eval_e(height);
            this.container.graphics.drawRect(x - this.x + this.anchorOffsetX, y - this.y + this.anchorOffsetY, width, height);
        };
        p.drawCircle = function (x, y, radius) {
            x = ls.eval_e(x);
            y = ls.eval_e(y);
            radius = ls.eval_e(radius);
            this.container.graphics.drawCircle(x - this.x + this.anchorOffsetX, y - this.y + this.anchorOffsetY, radius);
        };
        p.drawEllipse = function (x, y, width, height) {
            x = ls.eval_e(x);
            y = ls.eval_e(y);
            width = ls.eval_e(width);
            height = ls.eval_e(height);
            this.container.graphics.drawEllipse(x - this.x + this.anchorOffsetX, y - this.y + this.anchorOffsetY, width, height);
        };
        p.drawRoundRect = function (x, y, width, height, ellipseWidth, ellipseHeight) {
            x = ls.eval_e(x);
            y = ls.eval_e(y);
            width = ls.eval_e(width);
            height = ls.eval_e(height);
            ellipseWidth = ls.eval_e(ellipseWidth);
            ellipseHeight = ls.eval_e(ellipseHeight);
            this.container.graphics.drawRoundRect(x - this.x + this.anchorOffsetX, y - this.y + this.anchorOffsetY, width, height, ellipseWidth, ellipseHeight);
        };
        //绘制一段圆弧路径
        p.drawArc = function (x, y, radius, startAngle, endAngle, anticlockwise) {
            x = ls.eval_e(x);
            y = ls.eval_e(y);
            radius = ls.eval_e(radius);
            startAngle = ls.eval_e(startAngle);
            endAngle = ls.eval_e(endAngle);
            anticlockwise = ls.eval_e(anticlockwise);
            this.container.graphics.drawArc(x - this.x + this.anchorOffsetX, y - this.y + this.anchorOffsetY, radius, startAngle, endAngle, anticlockwise);
        };
        p.curveTo = function (moveToX, moveToY, cx1, cy1, anchorX, anchorY) {
            moveToX = ls.eval_e(moveToX);
            moveToY = ls.eval_e(moveToY);
            cx1 = ls.eval_e(cx1);
            cy1 = ls.eval_e(cy1);
            anchorX = ls.eval_e(anchorX);
            anchorY = ls.eval_e(anchorY);
            this.container.graphics.moveTo(moveToX - this.x + this.anchorOffsetX, moveToY - this.y + this.anchorOffsetY);
            this.container.graphics.curveTo(cx1 - this.x + this.anchorOffsetX, cy1 - this.y + this.anchorOffsetY, anchorX - this.x + this.anchorOffsetX, anchorY - this.y + this.anchorOffsetY);
        };
        p.cubicCurveTo = function (moveToX, moveToY, cx1, cy1, cx2, cy2, anchorX, anchorY) {
            moveToX = ls.eval_e(moveToX);
            moveToY = ls.eval_e(moveToY);
            cx1 = ls.eval_e(cx1);
            cy1 = ls.eval_e(cy1);
            cx2 = ls.eval_e(cx2);
            cy2 = ls.eval_e(cy2);
            anchorX = ls.eval_e(anchorX);
            anchorY = ls.eval_e(anchorY);
            this.container.graphics.moveTo(moveToX - this.x + this.anchorOffsetX, moveToY - this.y + this.anchorOffsetY);
            this.container.graphics.cubicCurveTo(cx1 - this.x + this.anchorOffsetX, cy1 - this.y + this.anchorOffsetY, cx2 - this.x + this.anchorOffsetX, cy2 - this.y + this.anchorOffsetY, anchorX - this.x + this.anchorOffsetX, anchorY - this.y + this.anchorOffsetY);
        };
        ////////////////////////////////////conditions///////////////////////////////////
        //当资源加载完毕
        p.onResourceLoaded = function ($onResourceLoaded) {
            return { instances: [this], status: true };
        };
        ////////////////////////////////////action///////////////////////////////////
        p.subtractFrom = function ($instanceVariables, $value) {
            var value = ls.eval_e($value);
            ls.assert(typeof value !== "number", "AIObject subtractFrom parameter type incorrect!!");
            this[$instanceVariables] -= value;
        };
        ////////////////////////////////////behaviors///////////////////////////////////
        p.saveToJSON = function () {
            var o = _super.prototype.saveToJSON.call(this);
            o["url"] = this["url"];
            return o;
        };
        p.loadFromJSON = function (o) {
            if (o) {
                this["url"] = o["url"];
                _super.prototype.loadFromJSON.call(this, o);
            }
        };
        p.clone = function () {
            var cl = _super.prototype.clone.call(this);
            cl["url"] = this["url"];
            return cl;
        };
        return AISprite;
    }(ls.AIDisplayObject));
    ls.AISprite = AISprite;
    egret.registerClass(AISprite,'ls.AISprite');
    var OnResourceLoadedEvent = (function (_super) {
        __extends(OnResourceLoadedEvent, _super);
        function OnResourceLoadedEvent() {
            _super.call(this);
        }
        var d = __define,c=OnResourceLoadedEvent,p=c.prototype;
        return OnResourceLoadedEvent;
    }(ls.BaseEvent));
    ls.OnResourceLoadedEvent = OnResourceLoadedEvent;
    egret.registerClass(OnResourceLoadedEvent,'ls.OnResourceLoadedEvent');
})(ls || (ls = {}));
//# sourceMappingURL=AISprite.js.map