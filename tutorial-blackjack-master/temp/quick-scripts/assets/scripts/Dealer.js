(function() {"use strict";var __module = CC_EDITOR ? module : {exports:{}};var __filename = 'preview-scripts/assets/scripts/Dealer.js';var __require = CC_EDITOR ? function (request) {return cc.require(request, require);} : function (request) {return cc.require(request, __filename);};function __define (exports, require, module) {"use strict";
cc._RF.push(module, 'ce2dfoqEulHCLjS1Z9xPN7t', 'Dealer', __filename);
// scripts/Dealer.js

'use strict';

var Actor = require('Actor');
var Utils = require('Utils');

cc.Class({
    extends: Actor,

    properties: {
        // 手上最接近 21 点的点数（有可能超过 21 点）
        bestPoint: {
            get: function get() {
                var cards = this.holeCard ? [this.holeCard].concat(this.cards) : this.cards;
                var minMax = Utils.getMinMaxPoint(cards);
                return minMax.max;
            },
            override: true
        }
    },

    init: function init() {
        this._super();
        this.renderer.initDealer();
    },

    // 返回是否要牌
    wantHit: function wantHit() {
        var Game = require('Game');
        var Types = require('Types');

        var bestPoint = this.bestPoint;

        // 已经最大点数
        if (bestPoint === 21) {
            return false;
        }

        // 不论抽到什么牌肯定不会爆，那就接着抽
        if (bestPoint <= 21 - 10) {
            return true;
        }

        var player = Game.instance.player;
        var outcome = Game.instance._getPlayerResult(player, this);

        switch (outcome) {
            case Types.Outcome.Win:
                return true;
            case Types.Outcome.Lose:
                return false;
        }

        return this.bestPoint < 17;
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
        //# sourceMappingURL=Dealer.js.map
        