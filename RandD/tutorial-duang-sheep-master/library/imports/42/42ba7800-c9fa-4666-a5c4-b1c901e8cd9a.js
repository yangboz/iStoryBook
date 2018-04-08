"use strict";
cc._RF.push(module, '42ba7gAyfpGZqXEsckB6M2a', 'GameOverMenu');
// scripts/GameOverMenu.js

'use strict';

var GameOverMenu = cc.Class({
    //-- 继承
    extends: cc.Component,
    //-- 属性
    properties: {
        btn_play: cc.Button,
        score: cc.Label
    },
    // 加载Game场景(重新开始游戏)
    restart: function restart() {
        cc.director.loadScene('Game');
    }
});

cc._RF.pop();