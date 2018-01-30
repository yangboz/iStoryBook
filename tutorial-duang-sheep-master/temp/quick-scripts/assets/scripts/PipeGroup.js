(function() {"use strict";var __module = CC_EDITOR ? module : {exports:{}};var __filename = 'preview-scripts/assets/scripts/PipeGroup.js';var __require = CC_EDITOR ? function (request) {return cc.require(request, require);} : function (request) {return cc.require(request, __filename);};function __define (exports, require, module) {"use strict";
cc._RF.push(module, '5026dczn8lKZpq2Z+LGadpZ', 'PipeGroup', __filename);
// scripts/PipeGroup.js

"use strict";

cc.Class({
    extends: cc.Component,
    properties: {
        speed: 0,
        botYRange: cc.p(0, 0),
        spacingRange: cc.p(0, 0),
        topPipe: cc.Node,
        botPipe: cc.Node
    },
    onEnable: function onEnable() {
        var botYPos = this.botYRange.x + Math.random() * (this.botYRange.y - this.botYRange.x);
        var space = this.spacingRange.x + Math.random() * (this.spacingRange.y - this.spacingRange.x);
        var topYPos = botYPos + space;
        this.topPipe.y = topYPos;
        this.botPipe.y = botYPos;
    },
    update: function update(dt) {
        if (D.game.state !== D.GameManager.State.Run) {
            return;
        }

        this.node.x += this.speed * dt;

        var disappear = this.node.getBoundingBoxToWorld().xMax < 0;
        if (disappear) {
            D.pipeManager.despawnPipe(this);
        }
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
        //# sourceMappingURL=PipeGroup.js.map
        