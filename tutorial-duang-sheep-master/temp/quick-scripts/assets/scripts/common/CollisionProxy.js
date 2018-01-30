(function() {"use strict";var __module = CC_EDITOR ? module : {exports:{}};var __filename = 'preview-scripts/assets/scripts/common/CollisionProxy.js';var __require = CC_EDITOR ? function (request) {return cc.require(request, require);} : function (request) {return cc.require(request, __filename);};function __define (exports, require, module) {"use strict";
cc._RF.push(module, '6cd7aG5qipG8Z9+eOwNmn3m', 'CollisionProxy', __filename);
// scripts/common/CollisionProxy.js

"use strict";

function onCollisionEnter(other) {
    this.realListener.onCollisionEnter(other);
}
function onCollisionStay(other) {
    this.realListener.onCollisionStay(other);
}
function onCollisionExit(other) {
    this.realListener.onCollisionExit(other);
}

cc.Class({
    extends: cc.Component,

    properties: {
        realListener: cc.Component
    },

    onLoad: function onLoad() {
        this.onCollisionEnter = null;
        this.onCollisionStay = null;
        this.onCollisionExit = null;
        if (this.realListener) {
            if (this.realListener.onCollisionEnter) {
                this.onCollisionEnter = onCollisionEnter;
            }
            if (this.realListener.onCollisionStay) {
                this.onCollisionStay = onCollisionStay;
            }
            if (this.realListener.onCollisionExit) {
                this.onCollisionExit = onCollisionExit;
            }
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
        //# sourceMappingURL=CollisionProxy.js.map
        