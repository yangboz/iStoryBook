"use strict";
cc._RF.push(module, '6cd7aG5qipG8Z9+eOwNmn3m', 'CollisionProxy');
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