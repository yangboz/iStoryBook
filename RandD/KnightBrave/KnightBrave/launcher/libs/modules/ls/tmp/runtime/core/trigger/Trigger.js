var ls;
(function (ls) {
    var Trigger = (function () {
        function Trigger() {
        }
        var d = __define,c=Trigger,p=c.prototype;
        Trigger.register = function (target) {
            target.addEventListener(ls.TriggerEvent.TRIGGER, this.onTrigger, this);
        };
        Trigger.removeTrigger = function (target) {
            target.removeEventListener(ls.TriggerEvent.TRIGGER, this.onTrigger, this);
        };
        Trigger.removeAllTriggers = function () {
            for (var i = 0, len = ls.World.getInstance().objectList.length; i < len; i++)
                this.removeTrigger(ls.World.getInstance().objectList[i]);
        };
        Trigger.setConditionStatus = function (condition, inst, triggerData, changeValue) {
            if (triggerData === void 0) { triggerData = null; }
            if (changeValue === void 0) { changeValue = null; }
            if (condition.triggerfilters == undefined)
                condition.triggerfilters = {};
            condition.triggerfilters[inst.u_id] = inst;
            condition.triggerStatus = true;
            condition.triggerData = triggerData;
            condition.triggerChangeValue = changeValue;
        };
        Trigger.onTrigger = function (event) {
            var disableDataEvents = ls.AISystem.instance.disableDataEvents;
            var triggerConditions = ls.EventSheetDecoder.triggerConditions;
            var target = event.currentTarget;
            var isbehavior = target instanceof ls.BaseBehavior;
            var triggerData = event.triggerData;
            for (var i = 0, len = triggerConditions.length; i < len; i++) {
                var condition = triggerConditions[i];
                if (condition.event.enabled) {
                    //判断触发条件
                    if (condition.callCondition != event.triggerCondition)
                        continue;
                    //如果条件为组
                    if (condition.isFamily) {
                        if (isbehavior) {
                            var targets = ls.EventSheetDecoder.curFamilys[condition.targetName].insts;
                            if (targets) {
                                for (var j = 0, l = targets.length; j < l; j++) {
                                    var fi = targets[j];
                                    var fname = fi.name;
                                    var finsts = ls.World.getInstance().objectHash[fname];
                                    for (var k = 0, klen = finsts.length; k < klen; k++) {
                                        var finst = finsts[k];
                                        if (this.getCollsionStatus(condition, triggerData)) {
                                            if (condition.callConditionName === "onCollisionWithOtherObject")
                                                this.setConditionStatus(condition, triggerData.object, triggerData, event.changeValue);
                                            this.setConditionStatus(condition, finst, triggerData, event.changeValue);
                                        }
                                    }
                                }
                            }
                        }
                        else {
                            var searchinst = null;
                            var targets = ls.EventSheetDecoder.curFamilys[condition.targetName].insts;
                            if (targets) {
                                for (var j = 0, l = targets.length; j < l; j++) {
                                    var fi = targets[j];
                                    var fname = fi.name;
                                    var finsts = ls.World.getInstance().objectHash[fname];
                                    for (var k = 0, klen = finsts.length; k < klen; k++) {
                                        var finst = finsts[k];
                                        if (finst.u_id == target.u_id) {
                                            searchinst = finst;
                                            break;
                                        }
                                    }
                                }
                                if (this.getCollsionStatus(condition, triggerData)) {
                                    if (condition.callConditionName === "onCollisionWithOtherObject")
                                        this.setConditionStatus(condition, triggerData.object, triggerData, event.changeValue);
                                    if (searchinst)
                                        this.setConditionStatus(condition, searchinst, triggerData, event.changeValue);
                                }
                            }
                            else {
                                ls.assert(true, "组触发条件目标查找失败,组名：" + condition.targetName);
                            }
                        }
                    }
                    else {
                        if (isbehavior) {
                            //先比较行为名一致与行为调用的目标一致
                            if (condition.callTarget.name == target.name && condition.targetName == target.inst.name) {
                                //碰撞这块需要特殊处理
                                if (this.getCollsionStatus(condition, triggerData)) {
                                    if (condition.callConditionName === "onCollisionWithOtherObject")
                                        this.setConditionStatus(condition, triggerData.object, triggerData, event.changeValue);
                                    //直接将触发的内容传过去
                                    this.setConditionStatus(condition, target.inst, triggerData, event.changeValue);
                                }
                            }
                        }
                        else {
                            //检查是否包含触发数据
                            var searchinst = null;
                            //条件目标与事件发送方名字一样
                            if (condition.targetName == target.name)
                                searchinst = target;
                            //碰撞这块需要特殊处理
                            if (searchinst) {
                                if (this.getCollsionStatus(condition, triggerData)) {
                                    //应该注册目标与碰撞物
                                    if (condition.callConditionName === "onCollisionWithOtherObject")
                                        this.setConditionStatus(condition, triggerData.object, triggerData, event.changeValue);
                                    this.setConditionStatus(condition, searchinst, triggerData, event.changeValue);
                                }
                            }
                        }
                    }
                }
            }
        };
        Trigger.getCollsionStatus = function (condition, triggerData) {
            if (condition.callConditionName === "onCollisionWithOtherObject" && triggerData) {
                var paramsInstance = condition.paramsInstance;
                var data = paramsInstance.data;
                var childrens = data.children;
                var isFind = false;
                if (childrens && childrens.length > 0) {
                    for (var j = 0; j < childrens.length; j++) {
                        var o = childrens[j];
                        if (triggerData.object.name === ls.eval_e(decodeURIComponent(o.attributes.value)).name) {
                            isFind = true;
                            break;
                        }
                    }
                }
                if (!isFind)
                    return false;
            }
            return true;
        };
        return Trigger;
    }());
    ls.Trigger = Trigger;
    egret.registerClass(Trigger,'ls.Trigger');
})(ls || (ls = {}));
//# sourceMappingURL=Trigger.js.map