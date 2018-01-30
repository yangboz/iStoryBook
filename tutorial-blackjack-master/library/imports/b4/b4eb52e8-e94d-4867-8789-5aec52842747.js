"use strict";
cc._RF.push(module, 'b4eb5Lo6U1IZ4eJWuxShCdH', 'TossChip');
// scripts/TossChip.js

'use strict';

cc.Class({
    extends: cc.Component,

    properties: {
        anim: cc.Animation
    },

    // use this for initialization
    play: function play() {
        this.anim.play('chip_toss');
    }
});

cc._RF.pop();