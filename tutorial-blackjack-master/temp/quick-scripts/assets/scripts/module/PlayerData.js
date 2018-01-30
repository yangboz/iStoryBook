(function() {"use strict";var __module = CC_EDITOR ? module : {exports:{}};var __filename = 'preview-scripts/assets/scripts/module/PlayerData.js';var __require = CC_EDITOR ? function (request) {return cc.require(request, require);} : function (request) {return cc.require(request, __filename);};function __define (exports, require, module) {"use strict";
cc._RF.push(module, '4f9c5eXxqhHAKLxZeRmgHDB', 'PlayerData', __filename);
// scripts/module/PlayerData.js

'use strict';

var players = [{
	name: '燃烧吧，蛋蛋儿军',
	gold: 3000,
	photoIdx: 0
}, {
	name: '地方政府',
	gold: 2000,
	photoIdx: 1
}, {
	name: '手机超人',
	gold: 1500,
	photoIdx: 2
}, {
	name: '天灵灵，地灵灵',
	gold: 500,
	photoIdx: 3
}, {
	name: '哟哟，切克闹',
	gold: 9000,
	photoIdx: 4
}, {
	name: '学姐不要死',
	gold: 5000,
	photoIdx: 5
}, {
	name: '提百万',
	gold: 10000,
	photoIdx: 6
}];

module.exports = {
	players: players
};

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
        //# sourceMappingURL=PlayerData.js.map
        