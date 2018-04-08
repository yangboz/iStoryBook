(function() {"use strict";var __module = CC_EDITOR ? module : {exports:{}};var __filename = 'preview-scripts/assets/scripts/Dust.js';var __require = CC_EDITOR ? function (request) {return cc.require(request, require);} : function (request) {return cc.require(request, __filename);};function __define (exports, require, module) {"use strict";
cc._RF.push(module, '97ca5Hx+7VEy425oaf76L9U', 'Dust', __filename);
// scripts/Dust.js

"use strict";

cc.Class({
    extends: cc.Component,

    properties: {
        anim: cc.Animation
    },

    // use this for initialization
    playAnim: function playAnim(animName) {
        this.anim.play(animName);
    },
    finish: function finish() {
        this.node.removeFromParent();
        cc.pool.putInPool(this);
    }

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },

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
        //# sourceMappingURL=Dust.js.map
        