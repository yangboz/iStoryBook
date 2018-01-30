"use strict";
cc._RF.push(module, '3aae7lZKyhPqqsLD3wMKl6X', 'SideSwitcher');
// scripts/SideSwitcher.js

"use strict";

cc.Class({
    extends: cc.Component,

    properties: {
        retainSideNodes: {
            default: [],
            type: cc.Node
        }
    },

    // use this for initialization
    switchSide: function switchSide() {
        this.node.scaleX = -this.node.scaleX;
        for (var i = 0; i < this.retainSideNodes.length; ++i) {
            var curNode = this.retainSideNodes[i];
            curNode.scaleX = -curNode.scaleX;
        }
    }
});

cc._RF.pop();