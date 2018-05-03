var ls;
(function (ls) {
    var BaseEvent = (function (_super) {
        __extends(BaseEvent, _super);
        function BaseEvent() {
            _super.apply(this, arguments);
            this.currentKeys = {};
        }
        var d = __define,c=BaseEvent,p=c.prototype;
        p.getStatus = function (key) {
            return ls.eval_e(this[key]) === this.currentKeys[key];
        };
        return BaseEvent;
    }(ls.BaseClass));
    ls.BaseEvent = BaseEvent;
    egret.registerClass(BaseEvent,'ls.BaseEvent');
})(ls || (ls = {}));
//# sourceMappingURL=BaseEvent.js.map