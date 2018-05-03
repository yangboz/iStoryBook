var ls;
(function (ls) {
    //条件块，条件块中包含1个逻辑列表的条件
    var ConditionBlock = (function () {
        function ConditionBlock() {
            //条件块当前的条件状态
            this.status = false;
            //当前条件所保存的数据，在执行动作的时候可以获取
            //一般情况下这个数据为空，像Array的forEachElement里就有
            this.loopDatas = [];
            //循环层数
            this.loopLayer = 0;
            //持续性条件
            this.persistent = true;
            this._checked = false;
            this._checkall = false;
            this.execActionIndex = 0;
            this.searchInstances = {};
        }
        var d = __define,c=ConditionBlock,p=c.prototype;
        d(p, "loop"
            //获取这个条件块是否是循环模式
            ,function () {
                if (this.conditions == null)
                    return false;
                for (var i = 0, len = this.conditions.length; i < len; i++) {
                    var condition = this.conditions[i];
                    if (condition.loop)
                        return true;
                }
                return false;
            }
        );
        d(p, "checkall"
            //是否需要检测所有状态都正确后才会执行结果，这里在某些情况大可以大大的提升条件判断性能
            ,function () {
                if (!this._checked) {
                    for (var i = 0, len = this.conditions.length; i < len; i++) {
                        var condition = this.conditions[i];
                        if (condition.operatorType == 1) {
                            this._checkall = true;
                            break;
                        }
                    }
                    this._checked = true;
                }
                return this._checkall;
            }
        );
        d(p, "isloop"
            ,function () {
                if (this.loopDatas && this.loopDatas.length)
                    return this.loopDatas[0] instanceof ls.ForEvent;
                return false;
            }
        );
        d(p, "isArray"
            ,function () {
                if (this.loopDatas && this.loopDatas.length)
                    return this.loopDatas[0] instanceof ls.OnForEachArrayElementEvent;
                return false;
            }
        );
        d(p, "action"
            ,function () {
                return this.actions[this.execActionIndex];
            }
        );
        d(p, "world"
            ,function () {
                return ls.World.getInstance();
            }
        );
        /**
         * 执行动作
         * @param filterinstances 过滤的实例
         */
        p.execActions = function (filterinstances) {
            if (!this.actions)
                return;
            this.lastActionResults = [];
            this.execActionIndex = 0;
            this.filterinstances = filterinstances;
            if (this.isloop)
                this.execloop();
            else if (this.isArray)
                this.execArray();
            else
                this.execNextAction();
        };
        p.execloop = function () {
            var counts = [];
            counts[0] = [];
            counts[1] = [];
            counts[2] = [];
            for (var i = 0; i < this.loopLayer; i++) {
                var loopdata = this.loopDatas[i];
                var startindex = Math.min(loopdata.startIndex, loopdata.endIndex);
                var endindex = Math.max(loopdata.startIndex, loopdata.endIndex);
                counts[0][i] = startindex;
                counts[1][i] = endindex;
                counts[2][i] = loopdata;
            }
            this.execloopLayer(counts, 0);
        };
        //这里需要优化一下
        p.execloopLayer = function (counts, index) {
            for (var i = counts[0][index], l = counts[1][index]; i < l; i++) {
                var loopdata = counts[2][index];
                ls.loopIndex[loopdata.name] = i;
                if (index == (this.loopLayer - 1)) {
                    this.execActionIndex = 0;
                    this.lastActionResults = [];
                    this.execNextAction();
                }
                else {
                    this.execloopLayer(counts, index + 1);
                }
            }
        };
        //执行数组循环
        p.execArray = function () {
            var arrayelementEvent = this.loopDatas[0];
            var dims = ls.eval_e(arrayelementEvent.xyzDimention);
            var array = arrayelementEvent.array;
            switch (dims) {
                case 1:
                    for (var i = 0, il = array.width; i < il; i++) {
                        array.curX = i;
                        array.curValue = array.at(i, 0, 0);
                        this.execActionIndex = 0;
                        this.lastActionResults = [];
                        this.execNextAction();
                    }
                    break;
                case 2:
                    for (var i = 0, il = array.width; i < il; i++) {
                        array.curX = i;
                        for (var j = 0, jl = array.height; j < jl; j++) {
                            array.curY = j;
                            array.curValue = array.at(i, j, 0);
                            this.execActionIndex = 0;
                            this.lastActionResults = [];
                            this.execNextAction();
                        }
                    }
                    break;
                case 3:
                    for (var i = 0, il = array.width; i < il; i++) {
                        array.curX = i;
                        for (var j = 0, jl = array.height; j < jl; j++) {
                            array.curY = j;
                            for (var k = 0, kl = array.depth; k < kl; k++) {
                                array.curZ = k;
                                array.curValue = array.at(i, j, k);
                                this.execActionIndex = 0;
                                this.lastActionResults = [];
                                this.execNextAction();
                            }
                        }
                    }
                    break;
                default:
                    ls.assert(true, "无法解析当前数组维数：" + dims);
                    break;
            }
        };
        //当前输出的结果如果在上一次的结果列表中找到了名字，那么，删除上一次结果，将这次的结果放入进来
        p.execNextAction = function () {
            if (this.execActionIndex >= this.actions.length)
                return;
            var action = this.action;
            var world = this.world;
            var callHanlderName = action.callHanlderName;
            var actioninstances = [];
            //////////////////////wait///////////////////
            if (callHanlderName === "wait") {
                var callparams = ls.EventSheetDecoder.decodeActionParams(action).params;
                var waittime = ls.eval_e(callparams[0]);
                if (typeof waittime !== "number")
                    waittime = 0;
                egret.setTimeout(function () {
                    this.execActionIndex++;
                    this.execNextAction();
                }, this, waittime * 1000);
                return;
            }
            //动作参数列表中可能有需要选择的实例,有可能是单独的实例，有可能是组,组可能暂时没有解析TODO
            //先不进行过滤操作，后面一起作过滤操作
            var paramsinstances = [];
            // for (var i: number = 0, l: number = callparams.length; i < l; i++){
            //     var param: any = callparams[i];
            //     if (param instanceof AIObject)
            //         paramsinstances.push(param);
            // }
            //目标有可能是组,如果是组，取组目标
            if (action.isFamily) {
                //需要将组中的所有目标提出来
                var familyinsts = [];
                for (var i = 0, fl = action.callTargets.length; i < fl; i++) {
                    var finst = action.callTargets[i];
                    var fname = finst.name;
                    var finsts = world.objectHash[fname];
                    for (var j = 0, fsl = finsts.length; j < fsl; j++) {
                        familyinsts.push(finsts[j]);
                    }
                }
                actioninstances = paramsinstances.concat(familyinsts);
            }
            else {
                actioninstances = paramsinstances.concat(action.callTargets);
            }
            //计算条件过滤的目标与动作里需要计算的目标
            //过滤机制为，如果在过滤目标里找到了需要计算的目标，那么，取过滤的目标，否则取所有的目标
            this.searchInstances = {};
            for (var i = 0, l = actioninstances.length; i < l; i++) {
                var ainst = actioninstances[i];
                var searchIndex = -1;
                for (var j = 0, jl = this.filterinstances.length; j < jl; j++) {
                    var finst = this.filterinstances[j];
                    if (ainst.name === finst.name) {
                        this.searchInstances[finst.u_id] = finst;
                        searchIndex = j;
                    }
                }
                if (searchIndex == -1) {
                    var nosearchname = ainst.name;
                    var nosearchs;
                    if (ls.isSingleInst(nosearchname))
                        nosearchs = [ls.getInstanceByInstanceName(nosearchname)];
                    else
                        nosearchs = world.objectHash[nosearchname];
                    if (nosearchs) {
                        for (var j = 0, nl = nosearchs.length; j < nl; j++) {
                            var noinst = nosearchs[j];
                            this.searchInstances[noinst.u_id] = noinst;
                        }
                    }
                }
            }
            //上一次的过滤掉
            if (this.lastActionResults) {
                for (var uid in this.searchInstances) {
                    var searchinst = this.searchInstances[uid];
                    for (var j = 0, jl = this.lastActionResults.length; j < jl; j++) {
                        var linst = this.lastActionResults[j];
                        //如果有的，但id不一致的过滤掉
                        if (searchinst.name === linst.name) {
                            delete this.searchInstances[searchinst.u_id];
                        }
                    }
                }
                //添加
                for (var i = 0, l = this.lastActionResults.length; i < l; i++) {
                    var linst = this.lastActionResults[i];
                    this.searchInstances[linst.u_id] = linst;
                }
            }
            if (action.isBehaviorExecAction) {
                var caller = action.callTargetBehavior[callHanlderName];
                for (var uid in this.searchInstances) {
                    var sinst = this.searchInstances[uid];
                    if (action.isFamily) {
                        ls.lakeshoreInst()[sinst.name] = sinst;
                        //查找实例的行为列表
                        var behavior = sinst.getBehavior(action.callTargetBehavior["constructor"]);
                        if (caller && behavior) {
                            var callparams = ls.EventSheetDecoder.decodeActionParams(action).params;
                            this.execLastActionResult(caller.apply(behavior, callparams));
                        }
                    }
                    else {
                        //过滤出的目标等于目标名
                        if (sinst.name === action.callTargets.name) {
                            //查找实例的行为列表
                            ls.lakeshoreInst()[sinst.name] = sinst;
                            var behavior = sinst.getBehavior(action.callTargetBehavior["constructor"]);
                            if (caller && behavior) {
                                var callparams = ls.EventSheetDecoder.decodeActionParams(action).params;
                                this.execLastActionResult(caller.apply(behavior, callparams));
                            }
                        }
                    }
                }
            }
            else {
                for (var uid in this.searchInstances) {
                    var sinst = this.searchInstances[uid];
                    if (action.isFamily || sinst.name == action.callTargets.name) {
                        ls.lakeshoreInst()[sinst.name] = sinst;
                        var caller = sinst[callHanlderName];
                        if (caller) {
                            var callparams = ls.EventSheetDecoder.decodeActionParams(action).params;
                            this.execLastActionResult(caller.apply(sinst, callparams));
                        }
                    }
                }
            }
            //所有的查找目标查找完成，执行动作
            this.execActionIndex++;
            //执行完成一个动作之后，执行下一个动作
            this.execNextAction();
        };
        p.execLastActionResult = function (curresults) {
            if (curresults) {
                var ml = this.lastActionResults.length;
                if (ml) {
                    //如果有上一次结果，那么，需要将新的结果替换为旧的结果
                    for (var m = 0; m < ml; m++) {
                        var linst = this.lastActionResults[m];
                        for (var n = 0, nl = curresults.length; n < nl; n++) {
                            var cinst = curresults[n];
                            if (linst.name == cinst.name)
                                this.lastActionResults[m] = cinst;
                            else
                                this.lastActionResults = this.lastActionResults.concat(cinst);
                        }
                    }
                }
                else {
                    //否则，直接将旧的新的结果放于上一次结果列表中
                    this.lastActionResults = this.lastActionResults.concat(curresults);
                }
            }
        };
        return ConditionBlock;
    }());
    ls.ConditionBlock = ConditionBlock;
    egret.registerClass(ConditionBlock,'ls.ConditionBlock');
})(ls || (ls = {}));
//# sourceMappingURL=ConditionBlock.js.map