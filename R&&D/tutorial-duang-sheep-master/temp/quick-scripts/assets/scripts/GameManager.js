(function() {"use strict";var __module = CC_EDITOR ? module : {exports:{}};var __filename = 'preview-scripts/assets/scripts/GameManager.js';var __require = CC_EDITOR ? function (request) {return cc.require(request, require);} : function (request) {return cc.require(request, __filename);};function __define (exports, require, module) {"use strict";
cc._RF.push(module, '327f03mWlNHJqsi+VEpOBXC', 'GameManager', __filename);
// scripts/GameManager.js

'use strict';

var Sheep = require('Sheep');
var Scroller = require('Scroller');

var State = cc.Enum({
    Menu: -1,
    Run: -1,
    Over: -1
});

var GameManager = cc.Class({
    extends: cc.Component,
    //-- 属性
    properties: {
        //-- 获取绵羊
        sheep: Sheep,
        //-- 获取gameOverMenu对象
        gameOverMenu: cc.Node,
        //-- 获取分数对象
        scoreText: cc.Label,
        //-- 获取背景音效
        gameBgAudio: {
            default: null,
            url: cc.AudioClip
        },
        //-- 获取死亡音效
        dieAudio: {
            default: null,
            url: cc.AudioClip
        },
        //-- 获取失败音效
        gameOverAudio: {
            default: null,
            url: cc.AudioClip
        },
        //-- 获取得分音效
        scoreAudio: {
            default: null,
            url: cc.AudioClip
        }
    },

    statics: {
        State: State
    },

    onLoad: function onLoad() {
        D.GameManager = GameManager;
        D.game = this;

        // activate colliders
        cc.director.getCollisionManager().enabled = true;

        //-- 游戏状态
        this.state = State.Menu;
        //-- 分数
        this.score = 0;
        this.scoreText.string = this.score;
        this.gameOverMenu.active = false;
        this.sheep.init();
    },

    //-- 开始
    start: function start() {
        this.state = State.Run;
        this.score = 0;
        D.pipeManager.startSpawn();
        this.sheep.startRun();
    },
    gameOver: function gameOver() {
        //-- 背景音效停止，死亡音效播放
        cc.audioEngine.stopMusic(this.gameBgAudio);
        cc.audioEngine.playEffect(this.dieAudio);
        cc.audioEngine.playEffect(this.gameOverAudio);
        D.pipeManager.reset();
        this.state = State.Over;
        this.gameOverMenu.active = true;
        this.gameOverMenu.getComponent('GameOverMenu').score.string = this.score;
    },

    //-- 更新分数
    gainScore: function gainScore() {
        //-- 分数+1
        this.score++;
        this.scoreText.string = this.score;
        //-- 分数增加音效
        cc.audioEngine.playEffect(this.scoreAudio);
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
        //# sourceMappingURL=GameManager.js.map
        