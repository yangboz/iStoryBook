(function() {"use strict";var __module = CC_EDITOR ? module : {exports:{}};var __filename = 'preview-scripts/assets/scripts/Scroller.js';var __require = CC_EDITOR ? function (request) {return cc.require(request, require);} : function (request) {return cc.require(request, __filename);};function __define (exports, require, module) {"use strict";
cc._RF.push(module, 'b90489JvD1O1pgXMQXSKGr7', 'Scroller', __filename);
// scripts/Scroller.js

"use strict";

cc.Class({
    extends: cc.Component,

    properties: {
        //-- 滚动的速度
        speed: 0,
        //-- X轴边缘
        resetX: 0
    },

    update: function update(dt) {
        if (D.game.state !== D.GameManager.State.Run) {
            return;
        }
        var x = this.node.x;
        x += this.speed * dt;
        if (x <= this.resetX) {
            x -= this.resetX;
        }
        this.node.x = x;
    }
});

cc._RF.pop();
        }
        if (CC_EDITOR) {
            __define(__module.exports, __require, __module);
        }
        else {
            cc.registerModuleFunc(__filename, function () {
                __define(__module.exports, __require, __module);
            });
        }
        })();
        //# sourceMappingURL=Scroller.js.map
        