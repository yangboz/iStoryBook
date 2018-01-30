"use strict";
cc._RF.push(module, '1657ewfijBOXLq5zGqr6PvE', 'RankItem');
// scripts/UI/RankItem.js

"use strict";

cc.Class({
    extends: cc.Component,

    properties: {
        spRankBG: cc.Sprite,
        labelRank: cc.Label,
        labelPlayerName: cc.Label,
        labelGold: cc.Label,
        spPlayerPhoto: cc.Sprite,
        texRankBG: cc.SpriteFrame,
        texPlayerPhoto: cc.SpriteFrame
        // ...
    },

    // use this for initialization
    init: function init(rank, playerInfo) {
        if (rank < 3) {
            // should display trophy
            this.labelRank.node.active = false;
            this.spRankBG.spriteFrame = this.texRankBG[rank];
        } else {
            this.labelRank.node.active = true;
            this.labelRank.string = (rank + 1).toString();
        }

        this.labelPlayerName.string = playerInfo.name;
        this.labelGold.string = playerInfo.gold.toString();
        this.spPlayerPhoto.spriteFrame = this.texPlayerPhoto[playerInfo.photoIdx];
    },

    // called every frame
    update: function update(dt) {}
});

cc._RF.pop();