"use strict";
cc._RF.push(module, '226a2AvzRpHL7SJGTMy5PDX', 'Player');
// scripts/Player.js

'use strict';

var Actor = require('Actor');

cc.Class({
    extends: Actor,

    init: function init() {
        this._super();
        this.labelStake = this.renderer.labelStakeOnTable;
        this.stakeNum = 0;
    },

    reset: function reset() {
        this._super();
        this.resetStake();
    },

    addCard: function addCard(card) {
        this._super(card);

        // var Game = require('Game');
        // Game.instance.canReport = this.canReport;
    },

    addStake: function addStake(delta) {
        this.stakeNum += delta;
        this.updateStake(this.stakeNum);
    },

    resetStake: function resetStake(delta) {
        this.stakeNum = 0;
        this.updateStake(this.stakeNum);
    },

    updateStake: function updateStake(number) {
        this.labelStake.string = number;
    }

});

cc._RF.pop();