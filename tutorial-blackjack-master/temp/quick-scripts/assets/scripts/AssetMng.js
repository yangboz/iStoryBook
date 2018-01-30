(function() {"use strict";var __module = CC_EDITOR ? module : {exports:{}};var __filename = 'preview-scripts/assets/scripts/AssetMng.js';var __require = CC_EDITOR ? function (request) {return cc.require(request, require);} : function (request) {return cc.require(request, __filename);};function __define (exports, require, module) {"use strict";
cc._RF.push(module, '54522LcoVpPHbrqYgwp/1Qm', 'AssetMng', __filename);
// scripts/AssetMng.js

"use strict";

var AssetMng = cc.Class({
    extends: cc.Component,

    properties: {
        texBust: cc.SpriteFrame,
        texCardInfo: cc.SpriteFrame,
        texCountdown: cc.SpriteFrame,
        texBetCountdown: cc.SpriteFrame,
        playerPhotos: [cc.SpriteFrame]
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
        //# sourceMappingURL=AssetMng.js.map
        