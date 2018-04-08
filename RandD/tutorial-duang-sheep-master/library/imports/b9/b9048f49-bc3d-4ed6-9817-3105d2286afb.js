"use strict";
cc._RF.push(module, 'b90489JvD1O1pgXMQXSKGr7', 'Scroller');
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