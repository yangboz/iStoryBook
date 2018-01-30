(function() {"use strict";var __module = CC_EDITOR ? module : {exports:{}};var __filename = 'preview-scripts/assets/scripts/Actor.js';var __require = CC_EDITOR ? function (request) {return cc.require(request, require);} : function (request) {return cc.require(request, __filename);};function __define (exports, require, module) {"use strict";
cc._RF.push(module, '7d008dTf6xB2Z0wCAdzh1Rx', 'Actor', __filename);
// scripts/Actor.js

'use strict';

var Types = require('Types');
var Utils = require('Utils');
var ActorPlayingState = Types.ActorPlayingState;

cc.Class({
    extends: cc.Component,

    properties: {
        // 所有明牌
        cards: {
            default: [],
            serializable: false,
            visible: false
        },
        // 暗牌，demo 暂存
        holeCard: {
            default: null,
            serializable: false,
            visible: false
        },

        // 手上最接近 21 点的点数（有可能超过 21 点）
        bestPoint: {
            get: function get() {
                var minMax = Utils.getMinMaxPoint(this.cards);
                return minMax.max;
            }
        },

        // 牌型，不考虑是否爆牌
        hand: {
            get: function get() {
                var count = this.cards.length;
                if (this.holeCard) {
                    ++count;
                }
                if (count >= 5) {
                    return Types.Hand.FiveCard;
                }
                if (count === 2 && this.bestPoint === 21) {
                    return Types.Hand.BlackJack;
                }
                return Types.Hand.Normal;
            }
        },

        canReport: {
            get: function get() {
                return this.hand !== Types.Hand.Normal;
            },
            visible: false
        },

        renderer: {
            default: null,
            type: cc.Node
        },
        state: {
            default: ActorPlayingState.Normal,
            notify: function notify(oldState) {
                if (this.state !== oldState) {
                    this.renderer.updateState();
                }
            },
            type: ActorPlayingState,
            serializable: false
        }
    },

    init: function init() {
        this.ready = true;
        this.renderer = this.getComponent('ActorRenderer');
    },

    addCard: function addCard(card) {
        this.cards.push(card);
        this.renderer.onDeal(card, true);

        var cards = this.holeCard ? [this.holeCard].concat(this.cards) : this.cards;
        if (Utils.isBust(cards)) {
            this.state = ActorPlayingState.Bust;
        }
    },

    addHoleCard: function addHoleCard(card) {
        this.holeCard = card;
        this.renderer.onDeal(card, false);
    },

    stand: function stand() {
        this.state = ActorPlayingState.Stand;
    },

    revealHoldCard: function revealHoldCard() {
        if (this.holeCard) {
            this.cards.unshift(this.holeCard);
            this.holeCard = null;
            this.renderer.onRevealHoldCard();
        }
    },

    // revealNormalCard: function() {
    //     this.onRevealNormalCard();
    // },

    report: function report() {
        this.state = ActorPlayingState.Report;
    },

    reset: function reset() {
        this.cards = [];
        this.holeCard = null;
        this.reported = false;
        this.state = ActorPlayingState.Normal;
        this.renderer.onReset();
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
        //# sourceMappingURL=Actor.js.map
        