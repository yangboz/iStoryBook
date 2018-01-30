(function() {"use strict";var __module = CC_EDITOR ? module : {exports:{}};var __filename = 'preview-scripts/assets/scripts/Bet.js';var __require = CC_EDITOR ? function (request) {return cc.require(request, require);} : function (request) {return cc.require(request, __filename);};function __define (exports, require, module) {"use strict";
cc._RF.push(module, '28f38yToT1Pw7NgyeCvRxDC', 'Bet', __filename);
// scripts/Bet.js

'use strict';

var Game = require('Game');

cc.Class({
    extends: cc.Component,

    properties: {
        chipPrefab: cc.Prefab,
        btnChips: {
            default: [],
            type: cc.Node
        },
        chipValues: {
            default: [],
            type: 'Integer'
        },
        anchorChipToss: cc.Node
    },

    // use this for initialization
    init: function init() {
        this._registerBtns();
    },

    _registerBtns: function _registerBtns() {
        var self = this;
        var registerBtn = function registerBtn(index) {
            self.btnChips[i].on('touchstart', function (event) {
                if (Game.instance.addStake(self.chipValues[index])) {
                    self.playAddChip();
                }
            }, this);
        };
        for (var i = 0; i < self.btnChips.length; ++i) {
            registerBtn(i);
        }
    },

    playAddChip: function playAddChip() {
        var startPos = cc.p(cc.randomMinus1To1() * 50, cc.randomMinus1To1() * 50);
        var chip = cc.instantiate(this.chipPrefab);
        this.anchorChipToss.addChild(chip);
        chip.setPosition(startPos);
        chip.getComponent('TossChip').play();
    },

    resetChips: function resetChips() {
        Game.instance.resetStake();
        Game.instance.info.enabled = false;
        this.resetTossedChips();
    },

    resetTossedChips: function resetTossedChips() {
        this.anchorChipToss.removeAllChildren();
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
        //# sourceMappingURL=Bet.js.map
        