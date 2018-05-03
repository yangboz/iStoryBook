var ls;
(function (ls) {
    var EventSheetVo = (function () {
        function EventSheetVo() {
        }
        var d = __define,c=EventSheetVo,p=c.prototype;
        return EventSheetVo;
    }());
    ls.EventSheetVo = EventSheetVo;
    egret.registerClass(EventSheetVo,'ls.EventSheetVo');
    var FamilyVo = (function () {
        function FamilyVo() {
        }
        var d = __define,c=FamilyVo,p=c.prototype;
        return FamilyVo;
    }());
    ls.FamilyVo = FamilyVo;
    egret.registerClass(FamilyVo,'ls.FamilyVo');
    var VariableVo = (function () {
        function VariableVo() {
        }
        var d = __define,c=VariableVo,p=c.prototype;
        return VariableVo;
    }());
    ls.VariableVo = VariableVo;
    egret.registerClass(VariableVo,'ls.VariableVo');
    var CollisionDataVo = (function () {
        function CollisionDataVo() {
        }
        var d = __define,c=CollisionDataVo,p=c.prototype;
        return CollisionDataVo;
    }());
    ls.CollisionDataVo = CollisionDataVo;
    egret.registerClass(CollisionDataVo,'ls.CollisionDataVo');
    var CollisionSearchVo = (function () {
        function CollisionSearchVo() {
            this.enemyNames = [];
        }
        var d = __define,c=CollisionSearchVo,p=c.prototype;
        return CollisionSearchVo;
    }());
    egret.registerClass(CollisionSearchVo,'CollisionSearchVo');
    var EventSheetDecoder = (function () {
        function EventSheetDecoder() {
        }
        var d = __define,c=EventSheetDecoder,p=c.prototype;
        EventSheetDecoder.saveEventSheetData = function (eventSheetName) {
            var eventSheet = ls.Config.sceneInfo.eventsheetData;
            this.eventsheetVo = new EventSheetVo();
            this.eventsheetVo.eventSheetName = eventSheetName;
            this.eventsheetVo.nextEventSheetName = eventSheet.attributes.next;
            this.eventsheetVo.prevEventSheetName = eventSheet.attributes.previous;
            this.eventsheetVo.layoutName = eventSheet.attributes.layout;
            var version = eventSheet.attributes.version;
            this.eventsheetVo.version = version ? (version) : "1.1.1";
            this.eventSheets[eventSheetName] = this.eventsheetVo;
        };
        EventSheetDecoder.start = function (eventSheetName) {
            ls.assert(eventSheetName == null || eventSheetName == "", eventSheetName + " can not null!");
            this.saveEventSheetData(eventSheetName);
            var eventSheet = ls.Config.sceneInfo.eventsheetData;
            ls.assert(eventSheet == null, "can not find " + eventSheetName);
            this.curSceneInstancesData = [];
            this.curSceneEventsData = [];
            this.curSceneAiObjects = [];
            this.curSceneAiObjectsHash = {};
            this.curSceneEvents = [];
            var objectList = ls.World.getInstance().objectList;
            this.currentEventSheetName = eventSheetName;
            var _eventSheetDataList = eventSheet.children;
            if (_eventSheetDataList === undefined)
                return;
            for (var i = 0, itemlen = _eventSheetDataList.length; i < itemlen; i++) {
                var _data = _eventSheetDataList[i];
                var _type = _data.attributes.type;
                switch (_type) {
                    case "instance":
                        if (ls.LayoutDecoder.curSceneInstances[_data.attributes.UID])
                            this.curSceneInstancesData.push(_data);
                        break;
                    case "family":
                        var familyVo = this.decodeFamily(_data);
                        this.curFamilys[familyVo.name] = familyVo;
                        break;
                    case "event":
                        this.curSceneEventsData.push(_data);
                        break;
                    case "variable":
                        //如果是全局变量，那么全部加入到System对象中
                        var v = this.decodeVaraible(_data);
                        v.initValue = decodeURIComponent(v.initValue);
                        if (ls.AISystem.instance[v.name] == undefined) {
                            switch (v.variableType) {
                                case "number":
                                    ls.AISystem.instance[v.name] = +ls.eval_e(v.initValue);
                                    break;
                                case "string":
                                    ls.AISystem.instance[v.name] = v.initValue + "";
                                    break;
                                case "any":
                                    ls.AISystem.instance[v.name] = v.initValue;
                                    break;
                                case "boolean":
                                    ls.AISystem.instance[v.name] = Boolean(ls.eval_e(v.initValue));
                                    break;
                            }
                            if (ls.AISystem.instance.globalVariables == undefined)
                                ls.AISystem.instance.globalVariables = {};
                            ls.AISystem.instance.globalVariables[v.name] = ls.AISystem.instance[v.name];
                        }
                        break;
                }
            }
            this.decode();
        };
        EventSheetDecoder.decode = function () {
            //解析属性
            this.decodePropertys();
            //解析事件
            this.decodeEvents();
            //实例初始化完毕之后，将能得到所有同类型的目标的行为列表
            this.decodeFamilyBehaviors();
            //行为创建
            this.onBehaviorsCreate();
            //实例初始化完毕
            this.onInstancesCreate();
            //发送场景初始化完成事件
            ls.AISystem.instance.sendSceneInitComplete();
            //开始渲染
            ls.GameUILayer.stage.addEventListener(egret.Event.ENTER_FRAME, this.eventsheetRender, this);
        };
        /**解析组行为，场景中所有同名的对象的行为添加上 */
        EventSheetDecoder.decodeFamilyBehaviors = function () {
            var curFamilys = this.curFamilys;
            var world = ls.World.getInstance();
            for (var familyname in curFamilys) {
                var familyVo = curFamilys[familyname];
                var finsts = familyVo.insts;
                for (var i = 0, l = finsts.length; i < l; i++) {
                    var finst = finsts[i];
                    var finstname = finst.name;
                    var insts = world.objectHash[finstname];
                    for (var j = 0, jl = insts.length; j < jl; j++) {
                        var inst = insts[j];
                        for (var k = 0, kl = familyVo.behaviors.length; k < kl; k++) {
                            var bh = familyVo.behaviors[k];
                            var cbh = bh.clone();
                            inst.addBehavior(cbh);
                            cbh.onCreate();
                            cbh.isCreated = true;
                        }
                    }
                }
            }
        };
        //行为创建
        EventSheetDecoder.onBehaviorsCreate = function () {
            var objects = ls.World.getInstance().objectList;
            for (var i = 0; i < objects.length; i++) {
                var object = objects[i];
                if (object) {
                    var behaviors = object.behaviors;
                    if (behaviors) {
                        for (var j = 0; j < behaviors.length; j++) {
                            var bh = behaviors[j];
                            bh.onCreate();
                            bh.isCreated = true;
                        }
                    }
                }
            }
            //组行为创建
            // for (var key in this.curFamilys) {
            //     var familyVo: FamilyVo = this.curFamilys[key];
            //     for (var m: number = 0, blen: number = familyVo.behaviors.length; m < blen; m++){
            //         var bh: BaseBehavior = familyVo.behaviors[m];
            //         bh.onCreate();
            //         bh.isCreated = true;
            //     }
            // }
        };
        //初始化aiObject
        EventSheetDecoder.decodePropertys = function () {
            //解析实例属性
            for (var i = 0, instancelen = this.curSceneInstancesData.length; i < instancelen; i++) {
                var _instanceData = this.curSceneInstancesData[i];
                var _targetType = _instanceData.attributes.type;
                var UID = +_instanceData.attributes.UID;
                var _instance = ls.LayoutDecoder.curSceneInstances[UID];
                if (_instance == null) {
                    ls.assert(true, "EventSheetDecoder UID:" + UID + " instance is null!");
                    continue;
                }
                this.curSceneAiObjectsHash[_instance.name] = _instance;
                this.curSceneAiObjects.push(_instance);
                //解析属性
                var _instanceItems = _instanceData.children;
                if (_instanceItems) {
                    var _instanceItemlen = _instanceItems.length;
                    for (var j = 0; j < _instanceItemlen; j++) {
                        var _instanceItem = _instanceItems[j];
                        this.decodeInstancePropertie(_instanceItem, _instance);
                    }
                }
            }
        };
        EventSheetDecoder.decodeFamily = function (data) {
            var _uids = data.attributes.UID;
            var uids = _uids == "" ? [] : _uids.split(',');
            var familyVo = new FamilyVo();
            familyVo.name = data.attributes.target;
            familyVo.UIDs = [];
            familyVo.insts = [];
            if (uids && uids.length) {
                for (var i = 0; i < uids.length; i++) {
                    familyVo.UIDs[i] = parseFloat(uids[i]);
                    familyVo.insts[i] = ls.LayoutDecoder.curSceneInstances[familyVo.UIDs[i]];
                }
            }
            //解析行为列表
            familyVo.behaviors = [];
            familyVo.variables = [];
            var familyChildrenData = data.children;
            for (var i = 0; i < familyChildrenData.length; i++) {
                var item = familyChildrenData[i];
                switch (item.attributes.name) {
                    case "behaviors":
                        var behaviorDatas = item.children;
                        for (var i1 = 0; i1 < behaviorDatas.length; i1++) {
                            var behaviorVo = this.decodeBehavior(behaviorDatas[i1]);
                            familyVo.behaviors[i1] = behaviorVo;
                        }
                        break;
                    case "variables":
                        var variableDatas = item.children;
                        for (var i2 = 0; i2 < variableDatas.length; i2++) {
                            var variableVo = this.decodeVaraible(variableDatas[i2]);
                            familyVo.variables[i2] = variableVo;
                        }
                        break;
                }
            }
            //将组的变量绑定到对象实例上
            if (familyVo.insts && familyVo.variables) {
                for (var i = 0; i < familyVo.insts.length; i++) {
                    var inst = familyVo.insts[i];
                    for (var j = 0; j < familyVo.variables.length; j++) {
                        var v = familyVo.variables[j];
                        v.initValue = decodeURIComponent(v.initValue);
                        switch (v.variableType) {
                            case "number":
                                inst[v.name] = +ls.eval_e(v.initValue);
                                break;
                            case "string":
                                inst[v.name] = v.initValue;
                                break;
                            case "any":
                                inst[v.name] = v.initValue;
                                break;
                            case "boolean":
                                inst[v.name] = Boolean(ls.eval_e(v.initValue));
                                break;
                        }
                    }
                }
            }
            return familyVo;
        };
        EventSheetDecoder.decodeVaraible = function (data) {
            var v = new VariableVo();
            v.initValue = decodeURIComponent(data.attributes.initValue);
            v.variableType = data.attributes.variableType;
            v.name = data.attributes.variableName;
            return v;
        };
        //解析事件数据
        EventSheetDecoder.decodeEvents = function () {
            //解析事件属性
            for (var i = 0; i < this.curSceneEventsData.length; i++) {
                var eventData = this.curSceneEventsData[i];
                var event = this.decodeEvent(eventData, i);
                if (event)
                    this.curSceneEvents.push(event);
            }
        };
        //解析事件
        EventSheetDecoder.decodeEvent = function (eventData, index) {
            var event = new ls.AIEvent();
            event.index = index;
            //【兼容1.11以前】,1.11以前版本有$conditionRelationShip,后面的将会取消，以operatorType替代
            var conditionRelationShip = eventData.attributes.conditionRelationShip;
            if (conditionRelationShip)
                event.conditionRelationShip = ls.eval_e(conditionRelationShip);
            //是否是一次性触发事件
            var triggerOnceWhileTrue = eventData.attributes.triggerOnceWhileTrue;
            if (triggerOnceWhileTrue)
                event.triggerOnceWhileTrue = ls.eval_e(triggerOnceWhileTrue);
            //条件列表
            var items = eventData.children;
            if (items == null)
                return;
            var prevConditionBlock;
            var nextConditionBlock;
            var eventlen = items.length;
            var conditionBlockIndex = 0;
            var conditionIndex = 0;
            var actionIndex = 0;
            //检测是否有conditionBlock元素
            var isHasConditionBlock = false;
            for (var i = 0; i < eventlen; i++) {
                if (items[i].attributes.type === "conditionBlock") {
                    isHasConditionBlock = true;
                    break;
                }
            }
            if (isHasConditionBlock) {
                var subEventIndex = 0;
                for (var j = 0; j < eventlen; j++) {
                    var childItem = items[j];
                    var childType = childItem.attributes.type;
                    var conditionBlock;
                    //1.2及以后的版本
                    //解析条件块
                    switch (childType) {
                        case "conditionBlock":
                            conditionBlock = this.decodeConditionBlock(childItem.children, conditionBlockIndex, event);
                            conditionBlock.event = event;
                            if (event.conditionBlocks == null)
                                event.conditionBlocks = [];
                            event.conditionBlocks.push(conditionBlock);
                            if (conditionBlockIndex === 0) {
                                prevConditionBlock = conditionBlock;
                            }
                            else {
                                //上一个
                                conditionBlock.prevConditionBlock = prevConditionBlock;
                                prevConditionBlock = conditionBlock;
                                //下一个
                                conditionBlock.prevConditionBlock.nextConditionBlock = conditionBlock;
                            }
                            conditionBlockIndex++;
                            break;
                        case "event":
                            if (event.children == null)
                                event.children = [];
                            var subEvent = this.decodeEvent(childItem, subEventIndex);
                            subEvent.parent = event;
                            event.children.push(subEvent);
                            subEventIndex++;
                            break;
                    }
                }
            }
            else {
                //1.11及以前的版本
                subEventIndex = 0;
                var version1_1_1Datas = [];
                for (var j = 0; j < eventlen; j++) {
                    var childItem = items[j];
                    var childType = childItem.attributes.type;
                    switch (childType) {
                        case "condition":
                        case "action":
                            version1_1_1Datas.push(childItem);
                            break;
                        case "event":
                            if (event.children == null)
                                event.children = [];
                            var subEvent = this.decodeEvent(childItem, subEventIndex);
                            subEvent.parent = event;
                            event.children.push(subEvent);
                            subEventIndex++;
                            break;
                    }
                }
                conditionBlock = this.decodeConditionBlock(version1_1_1Datas, 0, event);
                conditionBlock.event = event;
                if (event.conditionBlocks == null)
                    event.conditionBlocks = [];
                event.conditionBlocks.push(conditionBlock);
                if (conditionBlockIndex === 0) {
                    prevConditionBlock = conditionBlock;
                }
                else {
                    //上一个
                    conditionBlock.prevConditionBlock = prevConditionBlock;
                    prevConditionBlock = conditionBlock;
                    //下一个
                    conditionBlock.prevConditionBlock.nextConditionBlock = conditionBlock;
                }
                conditionBlockIndex++;
            }
            return event;
        };
        //解析实例属性数据
        EventSheetDecoder.decodeInstancePropertie = function (data, instance) {
            var _itemType = data.attributes.type;
            switch (_itemType) {
                case "behavior":
                    //如果global.xml解析过一次，那么，此时不需要解析了
                    if (instance.global)
                        break;
                    var _behavior = this.decodeBehavior(data);
                    if (instance instanceof ls.AIDisplayObject)
                        instance.addBehavior(_behavior);
                    else
                        ls.assert(true, instance + "must instance of AIDisplayObject for have Behaviors");
                    break;
                case "variable":
                    var variableName = data.attributes.variableName;
                    var variableValueType = data.attributes.variableType;
                    var initValue = decodeURIComponent(data.attributes.initValue);
                    switch (variableValueType) {
                        case "number":
                            instance.addVariable(variableName, +ls.eval_e(initValue));
                            break;
                        case "string":
                            instance.addVariable(variableName, initValue + "");
                            break;
                        case "any":
                            instance.addVariable(variableName, initValue);
                            break;
                        case "boolean":
                            instance.addVariable(variableName, Boolean(ls.eval_e(initValue)));
                            break;
                    }
                    break;
            }
        };
        //解析行为列表
        EventSheetDecoder.decodeBehavior = function (data) {
            //注意完全限定类名中都要加Behavior扩展关键字以便好识别
            var _behaivorType = data.attributes.behaviorType;
            var _behaivor = ls.getInstanceByPluginClassName(_behaivorType);
            _behaivor.name = data.attributes.name;
            //行为数据
            var _behaivorDatas = data.children;
            if (_behaivorDatas) {
                var _behaivorDatalen = _behaivorDatas.length;
                for (var k = 0; k < _behaivorDatalen; k++) {
                    var _propertyItem = _behaivorDatas[k];
                    var _propertyName = _propertyItem.attributes.name;
                    var _propertyValue = decodeURIComponent(_propertyItem.attributes.value);
                    var _propertyValueType = _propertyItem.attributes.valueDataType;
                    switch (_propertyValueType) {
                        case "number":
                            _behaivor[_propertyName] = +ls.eval_e(_propertyValue);
                            break;
                        case "string":
                            _behaivor[_propertyName] = _propertyValue + "";
                            break;
                        case "any":
                            _behaivor[_propertyName] = _propertyValue;
                            break;
                        case "boolean":
                            _behaivor[_propertyName] = Boolean(ls.eval_e(_propertyValue));
                            break;
                    }
                }
            }
            return _behaivor;
        };
        //解析条件块数据
        //为了兼容以前的1.1.1版本，data是个数组
        EventSheetDecoder.decodeConditionBlock = function (data, index, event) {
            var items = data;
            if (items) {
                var conditionBlock = new ls.ConditionBlock();
                conditionBlock.index = index;
                conditionBlock.conditions = [];
                conditionBlock.actions = [];
                var len = items.length;
                var conditionIndex = 0;
                var actionIndex = 0;
                var firstCondition;
                var prevCondition;
                var nextCondition;
                var firstAction;
                var prevAction;
                var nextAction;
                for (var i = 0; i < len; i++) {
                    var childItem = items[i];
                    var childType = childItem.attributes.type;
                    switch (childType) {
                        case "condition":
                            var condition = this.decodeCondition(childItem, conditionIndex, event);
                            condition.conditionBlock = conditionBlock;
                            condition.event = event;
                            if (condition.callCondition == undefined) {
                                if (!condition.isFamily)
                                    ls.assert(true, "目标：" + condition.callTarget + "没有" + condition.callConditionName + "方法！！！");
                                else
                                    ls.assert(true, "目标组没有" + condition.callConditionName + "方法！！！");
                            }
                            conditionBlock.conditions.push(condition);
                            if (conditionIndex === 0)
                                prevCondition = firstCondition = condition;
                            else {
                                condition.prevCondition = prevCondition;
                                prevCondition = condition;
                                condition.prevCondition.nextCondition = condition;
                            }
                            conditionIndex++;
                            break;
                        case "action":
                            var action = this.decodeAction(childItem, actionIndex, event);
                            action.conditionBlock = conditionBlock;
                            action.event = event;
                            conditionBlock.actions.push(action);
                            if (actionIndex === 0)
                                prevAction = firstAction = action;
                            else {
                                action.prevAction = prevAction;
                                prevAction = action;
                                action.prevAction.nextAction = action;
                            }
                            actionIndex++;
                            break;
                    }
                }
                return conditionBlock;
            }
            return null;
        };
        /**
         * 解析条件属性(条件属性要在运行时一直解析，否则，很多时候读取的值可能不是动态的，只是初始化的值)
         * 并且条件属性中可能会有组
         */
        EventSheetDecoder.decodeConditionProperties = function (eventsid, conditionIndex, conditionInstance) {
            var data = conditionInstance.data;
            var conditionPropsInfos = data.children;
            if (conditionPropsInfos) {
                var conditionProplen = conditionPropsInfos.length;
                for (var i = 0; i < conditionProplen; i++) {
                    var conditionPropItem = conditionPropsInfos[i];
                    var attributes = conditionPropItem.attributes;
                    var conditionPropName = attributes.name;
                    var conditionPropValue = attributes.value;
                    var isVariable = attributes.variable ? attributes.variable === "true" : false;
                    var conditionPropType = attributes.valueDataType;
                    var isFamily = attributes.isFamily ? attributes.isFamily === "true" : false;
                    conditionPropValue = decodeURIComponent(conditionPropValue);
                    conditionInstance.isFamily = isFamily;
                    if (isVariable)
                        conditionPropValue = "\"" + conditionPropValue + "\"";
                    conditionInstance[conditionPropName] = ls.eval_e(conditionPropValue);
                }
            }
            return conditionInstance;
        };
        EventSheetDecoder.decodeConditionFamilyProperties = function (conditionInstance, data) {
            var list = [];
            var conditionPropsInfos = data.children;
            if (conditionPropsInfos) {
                var conditionProplen = conditionPropsInfos.length;
                for (var i = 0; i < conditionProplen; i++) {
                    var conditionPropItem = conditionPropsInfos[i];
                    var isFamily = conditionPropItem.attributes.isFamily ? conditionPropItem.attributes.isFamily === "true" : false;
                    if (isFamily) {
                        var enemyName = conditionPropItem.attributes.value;
                        var familyVo = this.curFamilys[enemyName];
                        var familyInstances = familyVo.insts;
                        for (var j = 0; j < familyInstances.length; j++) {
                            if (list.indexOf(familyInstances[j].name) == -1) {
                                list.push(familyInstances[j].name);
                            }
                        }
                    }
                }
            }
            return list;
        };
        //解析条件数据
        EventSheetDecoder.decodeCondition = function (data, index, event) {
            var targetName = data.attributes.target; //实例名
            var behaviorName = data.attributes.behaviorName; //如果此值存在，那么，表示是执行该目标的行为方法，否则执行实例方法
            var conditionInstance = ls.getInstanceByPluginClassName(data.attributes.paramsClass);
            var callName = data.attributes.callName; //【目标，函数名，函数参数】
            var invert = false;
            //invert
            if (data.attributes.invert)
                invert = (data.attributes.invert === "true");
            //loop
            var loop = false;
            if (data.attributes.loop)
                loop = (data.attributes.loop === "true");
            var condition = new ls.Condition();
            condition.index = index;
            condition.firstCondition = (index == 0) ? condition : null;
            condition.targetName = targetName;
            condition.paramClassName = data.attributes.paramsClass;
            condition.isInvert = invert;
            condition.paramsInstance = conditionInstance;
            condition.callConditionName = callName;
            condition.isFamily = (data.attributes.family == "true");
            condition.event = event;
            if (ls.Version.compareVersion(this.eventsheetVo.version, "1.1.1") === 1) {
                var operatorType = 0;
                if (data.attributes.operatorType)
                    operatorType = +data.attributes.operatorType;
                condition.operatorType = operatorType;
            }
            else {
                //【兼容1.1.1以前】为了兼容以前的版本，解析conditionRelationShip
                condition.operatorType = +(!event.conditionRelationShip);
            }
            if (conditionInstance) {
                conditionInstance.data = data;
                condition.isTrigger = Boolean(ls.eval_e(data.attributes.isTrigger));
                //存储带触发条件的条件
                if (condition.isTrigger)
                    this.triggerConditions.push(condition);
            }
            //这里要优化碰撞检测查找目标，这里可能也需要组的操作,这里需要对组进行处理
            if (callName == "onCollisionWithOtherObject")
                this.decodeCollsionFamily(data, condition, targetName);
            if (ls.isSingleInst(targetName)) {
                condition.callTarget = ls.getInstanceByInstanceName(targetName);
                condition.callCondition = condition.callTarget[callName];
            }
            else {
                //查找目标的行为列表
                if (condition.isFamily) {
                    var callThisObject = null;
                    var familyVo = this.curFamilys[targetName];
                    var familyInstances = familyVo.insts;
                    if (familyInstances == undefined)
                        alert("当前场景中没有" + targetName + "的组！！！");
                    var callFamilyTargets = [];
                    for (var f = 0; f < familyInstances.length; f++) {
                        var fInstance = familyInstances[f];
                        var templateInstance = ls.World.getInstance().objectHash[fInstance.name][0];
                        //这里需要查找当前条件中用到的行为
                        if (behaviorName != null && behaviorName != "") {
                            if (templateInstance) {
                                var behaviors = familyVo.behaviors;
                                var _b;
                                for (var i = 0, len = behaviors.length; i < len; i++) {
                                    var behaivor = behaviors[i];
                                    if (behaivor.name == behaviorName) {
                                        _b = behaivor;
                                        break;
                                    }
                                }
                                callThisObject = (_b == null) ? templateInstance : _b;
                            }
                        }
                        else {
                            callThisObject = [];
                            callThisObject.push(templateInstance);
                        }
                    }
                    condition.callTarget = callThisObject;
                    if (callThisObject[0] == undefined)
                        condition.callCondition = callThisObject[callName];
                    else
                        condition.callCondition = callThisObject[0][callName];
                }
                else {
                    //取模板实例
                    var callThisObject = null;
                    if (ls.World.getInstance().objectHash[targetName] == undefined)
                        alert("当前场景中没有" + targetName + "实例对象！！！");
                    var templateInstance = ls.World.getInstance().objectHash[targetName][0];
                    if (behaviorName != null && behaviorName != "") {
                        if (templateInstance) {
                            var behaviors = templateInstance.behaviors;
                            for (var i = 0, len = behaviors.length; i < len; i++) {
                                var behaivor = behaviors[i];
                                if (behaivor.name == behaviorName) {
                                    callThisObject = behaivor;
                                    break;
                                }
                            }
                            callThisObject = (callThisObject == null) ? templateInstance : callThisObject;
                        }
                    }
                    else {
                        callThisObject = templateInstance;
                    }
                    condition.callTarget = callThisObject;
                    condition.callCondition = condition.callTarget[callName];
                }
                if (condition.callCondition === undefined)
                    ls.assert(true, "条件目标:" + condition.targetName + ",没有调用的方法名：" + callName);
            }
            return condition;
        };
        /**
         * 为了优化性能，解析事件表中参与碰撞的组
         */
        EventSheetDecoder.decodeCollsionFamily = function (data, condition, targetName) {
            var conditionPropsInfos = data.children;
            if (conditionPropsInfos) {
                var enemyName = conditionPropsInfos[0].attributes.value;
                var enemyIsFamily = ls.eval_e(conditionPropsInfos[0].attributes.isFamily);
                var enemy = ls.eval_e(ls.getTransformationStr(enemyName));
                var list = this.decodeConditionFamilyProperties(condition.paramsInstance, data);
                var collisionVo = null;
                if (condition.isFamily) {
                    var familyVo = this.curFamilys[targetName];
                    if (familyVo) {
                        //这里暂时存储场景中的组实例碰撞对象列表，后面添加的后面处理
                        for (var i = 0, l = familyVo.insts.length; i < l; i++) {
                            var finst = familyVo.insts[i];
                            var fname = finst.name;
                            if (this.collisionSearchs[fname] === undefined) {
                                collisionVo = new CollisionSearchVo();
                                collisionVo.owerName = fname;
                                this.collisionSearchs[fname] = collisionVo;
                            }
                            else
                                collisionVo = this.collisionSearchs[fname];
                            //判断敌人列表也是组，那么，解析组
                            if (enemyIsFamily) {
                                var familyEnemyVo = this.curFamilys[enemy];
                                if (familyEnemyVo) {
                                    for (var j = 0, jl = familyEnemyVo.insts.length; j < jl; j++) {
                                        var fenemyinst = familyEnemyVo.insts[j];
                                        var fenemyname = fenemyinst.name;
                                        if (collisionVo.enemyNames.indexOf(fenemyname) == -1)
                                            collisionVo.enemyNames.push(fenemyname);
                                    }
                                }
                            }
                            else {
                                if (collisionVo.enemyNames.indexOf(enemyName) == -1)
                                    collisionVo.enemyNames.push(enemyName);
                            }
                        }
                    }
                }
                else {
                    //如果目标不是组
                    if (this.collisionSearchs[targetName] === undefined) {
                        collisionVo = new CollisionSearchVo();
                        collisionVo.owerName = targetName;
                        this.collisionSearchs[targetName] = collisionVo;
                    }
                    else
                        collisionVo = this.collisionSearchs[targetName];
                    //判断敌人列表也是组，那么，解析组
                    if (enemyIsFamily) {
                        var familyEnemyVo = this.curFamilys[enemy];
                        if (familyEnemyVo) {
                            for (var j = 0, jl = familyEnemyVo.insts.length; j < jl; j++) {
                                var fenemyinst = familyEnemyVo.insts[j];
                                var fenemyname = fenemyinst.name;
                                if (collisionVo.enemyNames.indexOf(fenemyname) == -1)
                                    collisionVo.enemyNames.push(fenemyname);
                            }
                        }
                    }
                    else {
                        if (collisionVo.enemyNames.indexOf(enemyName) == -1)
                            collisionVo.enemyNames.push(enemyName);
                    }
                }
            }
        };
        //解析动作数据
        EventSheetDecoder.decodeAction = function (data, index, event) {
            var targetName = data.attributes.target;
            var behaviorName = data.attributes.behaviorName; //如果此值存在，那么，表示是执行该目标的行为方法，否则执行实例方法 
            var callName = data.attributes.callName;
            var persistent = ls.eval_e(data.attributes.persistent);
            var isFamily = ls.eval_e(data.attributes.family);
            var action = new ls.Action();
            action.isBehaviorExecAction = (behaviorName !== undefined);
            action.persistent = persistent;
            //根据行为来区分
            var callTarget = null;
            if (isFamily) {
                //目标名为组名，调用目标可能为组里的多个对象
                if (callTarget == null)
                    callTarget = [];
                var familyVo = this.curFamilys[targetName];
                for (var i = 0, fl = familyVo.insts.length; i < fl; i++) {
                    var finst = familyVo.insts[i];
                    //组一般不可能是单例，因此，不需要判断单例
                    callTarget.push(finst);
                }
                //存储行为目标
                if (action.isBehaviorExecAction) {
                    var behaviors = familyVo.behaviors;
                    if (behaviors) {
                        for (var i = 0, bl = behaviors.length; i < bl; i++) {
                            var behavior = behaviors[i];
                            if (behavior.name == behaviorName) {
                                action.callTargetBehavior = behavior;
                            }
                        }
                    }
                }
            }
            else {
                //目标名为实例名，调用目标单个
                if (ls.isSingleInst(targetName))
                    callTarget = ls.getInstanceByInstanceName(targetName);
                else {
                    var sceneobjects = this.curSceneAiObjects;
                    //查找场景中的对象
                    for (var i = 0, l = sceneobjects.length; i < l; i++) {
                        var inst = sceneobjects[i];
                        if (inst.name == targetName) {
                            callTarget = inst;
                            break;
                        }
                    }
                    if (callTarget === null)
                        callTarget = ls.eval_e(targetName);
                }
                //查找调用目标的行为
                if (action.isBehaviorExecAction && callTarget) {
                    var behaviors = callTarget["behaviors"];
                    if (behaviors) {
                        for (var i = 0, bl = behaviors.length; i < bl; i++) {
                            var behavior = behaviors[i];
                            if (behavior.name == behaviorName) {
                                action.callTargetBehavior = behavior;
                                break;
                            }
                        }
                    }
                }
            }
            action.index = index;
            action.targetName = targetName;
            action.isFamily = isFamily;
            action.callTargets = callTarget;
            action.paramData = data.children;
            action.callHanlderName = callName;
            return action;
        };
        /**解析动作参数 */
        EventSheetDecoder.decodeActionParams = function (action) {
            var actionParams = {};
            var params = [];
            var actionPropsInfos = action.paramData;
            var isFamilys = [];
            var hasFamily = false;
            if (actionPropsInfos && actionPropsInfos.length) {
                for (var i = 0, len = actionPropsInfos.length; i < len; i++) {
                    var _propertyItem = actionPropsInfos[i];
                    var _attributes = _propertyItem.attributes;
                    var _propertyValueType = _attributes.valueDataType;
                    var _propertyName = _attributes.name;
                    var _propertyValue = decodeURIComponent(_attributes.value);
                    var _isVariable = _attributes.variable ? _attributes.variable === "true" : false;
                    var _isFamily = _attributes.isFamily ? _attributes.isFamily === "true" : false;
                    if (_isFamily)
                        hasFamily = true;
                    if (_isVariable)
                        _propertyValue = "\"" + _propertyValue + "\"";
                    var _value = ls.eval_e(_propertyValue);
                    isFamilys[i] = _isFamily;
                    params.push(_value);
                }
            }
            actionParams.params = params;
            actionParams.isFamilys = isFamilys;
            actionParams.hasFamily = hasFamily;
            return actionParams;
        };
        EventSheetDecoder.tick = function () {
            return this._tick;
        };
        EventSheetDecoder.eventsheetRender = function (event) {
            var system = ls.AISystem.instance;
            var currentTime = egret.getTimer();
            if (this.lastTime !== 0) {
                this._tick = currentTime - this.lastTime;
                if (this._tick < 0)
                    this._tick = 0;
                //下一帧与上一帧的时间间隔，单位：毫秒
                this.dt1 = this._tick / 1000;
                //如果最小化了，或者切换了tab标签，那么，停止游戏
                if (this.dt1 > 0.5)
                    this.dt1 = 0;
                else if (this.dt1 > 1 / system.minimumFramerate)
                    this.dt1 = 1 / system.minimumFramerate;
            }
            this.lastTime = currentTime;
            this.dt = this.dt1 * this.timeScale;
            //渲染事件
            this.renderEvents();
            //渲染行为
            this.renderBehaviors();
            //渲染摄像机
            ls.World.getInstance().sceneCamera.render();
            this.renderCamera();
            //渲染更新
            if (this.onEventSheetTick != null)
                this.onEventSheetTick();
            //渲染更新
            this.onRenderUpdate();
            //碰撞检测
            this.checkCollistions();
        };
        EventSheetDecoder.onRenderUpdate = function () {
            var insts = ls.World.getInstance().objectList;
            for (var i = 0; i < insts.length; i++) {
                var inst = insts[i];
                if (inst instanceof ls.AIDisplayObject) {
                    inst.onTick();
                    inst.renderUpdate();
                }
            }
        };
        //渲染摄像机
        EventSheetDecoder.renderCamera = function () {
            ls.World.getInstance().render();
        };
        //触发实例创建时事件
        EventSheetDecoder.onInstancesCreate = function () {
            for (var uid in ls.LayoutDecoder.curSceneInstances) {
                var instance = ls.LayoutDecoder.curSceneInstances[uid];
                instance.onCreate();
            }
            //实例化非场景对象的创建
            for (var name in ls.LayoutDecoder.noSceneInstances) {
                var inst = ls.LayoutDecoder.noSceneInstances[name];
                if (inst && inst.onCreate)
                    inst.onCreate();
            }
        };
        //渲染行为的列表
        EventSheetDecoder.renderBehaviors = function () {
            var objectList = ls.World.getInstance().objectList;
            //检测是否绑定了横轴跑酷行为，如果有，那么，让其行为最后执行
            //检测所有的对象，并渲染其行为 
            var platforms = [];
            for (var i = 0; i < objectList.length; i++) {
                var inst = objectList[i];
                var behaviors = inst.behaviors;
                if (behaviors) {
                    for (var j = 0, blen = behaviors.length; j < blen; j++) {
                        var behavior = behaviors[j];
                        if (behavior.enabled && behavior.inst) {
                            //如果绑定的行为的实例不在场景中，那么，暂停行为
                            var behaviorClassName = egret.getQualifiedClassName(behavior);
                            if (behaviorClassName != "ls.ScrollToBehavior")
                                behavior.tick();
                        }
                    }
                }
            }
            //为了正确计算加了摄像机行为的对象位置，将摄像机行为放到最后来计算，否则，会发生轻微的偏移
            for (var i = 0; i < objectList.length; i++) {
                var inst = objectList[i];
                var behaviors = inst.behaviors;
                if (behaviors) {
                    for (var j = 0, blen = behaviors.length; j < blen; j++) {
                        var behavior = behaviors[j];
                        if (behavior.enabled && behavior.inst) {
                            var behaviorClassName = egret.getQualifiedClassName(behavior);
                            if (behaviorClassName == "ls.ScrollToBehavior")
                                behavior.tick();
                        }
                    }
                }
            }
        };
        EventSheetDecoder.renderEvents = function () {
            var disableDataEvents = ls.AISystem.instance.disableDataEvents;
            for (var i = 0; i < this.curSceneEvents.length; i++) {
                //序号从1开始
                if (disableDataEvents[i + 1] == undefined) {
                    var event = this.curSceneEvents[i];
                    this.execEvent(event);
                }
            }
        };
        //条件块是为if else 条件语句而生的
        EventSheetDecoder.execEvent = function (event, triggerInfo) {
            if (triggerInfo === void 0) { triggerInfo = null; }
            if (event.enabled) {
                //如果有父事件，判断父事件条件块状态，否则，直接判断
                var status = false;
                if (event.parent) {
                    if (event.parent.conditionBlocks[0].status)
                        status = true;
                }
                else
                    status = true;
                if (status) {
                    var cbs = event.conditionBlocks;
                    this.execConditionBlock(cbs[0], triggerInfo);
                }
            }
        };
        EventSheetDecoder.execConditionBlock = function (cb, triggerInfo) {
            if (cb) {
                cb.cs = {};
                cb.loopDatas = [];
                cb.loopLayer = 0;
                this.execNextCondition(cb.conditions[0], cb, triggerInfo, function () {
                    //如果所有的条件都进行完毕，那么，直接进行动作处理，这里将产生所有条件过滤出来的结果
                    var filterInstances = [];
                    //第一步，所有条件进行过滤
                    var cStatus;
                    for (var i = 0; i < cb.conditions.length; i++) {
                        var condition = cb.conditions[i];
                        if (i == 0) {
                            cStatus = condition.currentStatus;
                        }
                        else {
                            if (condition.operatorType == 1)
                                cStatus = cStatus || condition.currentStatus;
                            else
                                cStatus = cStatus && condition.currentStatus;
                        }
                        condition.triggerStatus = false;
                    }
                    cb.status = cStatus;
                    if (cStatus) {
                        //如果是一次性触发条件，那么，将去掉禁止掉该事件的运行
                        if (cb.event.triggerOnceWhileTrue)
                            cb.event.enabled = false;
                        var filterhashs = {};
                        //所有的总的条件计算完毕，并成立后，再次计算所有目标所有条件的状态比较值
                        for (var uid in cb.cs.instanceStatus) {
                            var cs = cb.cs.instanceStatus[uid];
                            var _inst = cs.instance;
                            var _ccs = cs.ccs;
                            var _s;
                            var _computeIndex = 0;
                            for (var cindex in _ccs) {
                                var ccs = _ccs[cindex];
                                var c = ccs.c;
                                //首先总的条件要成立
                                if (_computeIndex == 0) {
                                    _s = ccs.status;
                                }
                                else {
                                    if (c.operatorType == 1)
                                        _s = (_s || ccs.status);
                                    else
                                        _s = (_s && ccs.status);
                                }
                                _computeIndex++;
                            }
                            if (_s) {
                                filterInstances.push(_inst);
                                filterhashs[_inst.u_id] = _inst;
                            }
                        }
                        //只有过滤的目标大于0，才会执行动作与子事件
                        if (filterInstances.length > 0) {
                            //注册过滤出的对象
                            for (var k = 0, kl = filterInstances.length; k < kl; k++) {
                                ls.lakeshoreInst()[filterInstances[k].name] = filterInstances[k];
                            }
                            cb.execActions(filterInstances);
                            //解析子事件
                            var _event = cb.event;
                            if (_event.children) {
                                for (var m = 0; m < _event.children.length; m++) {
                                    var subevent = _event.children[m];
                                    if (subevent) {
                                        subevent.lastFilterTargets = filterhashs;
                                        EventSheetDecoder.execEvent(subevent);
                                    }
                                }
                            }
                        }
                    }
                });
                var nextBlock = cb.nextConditionBlock;
                if (nextBlock)
                    this.execConditionBlock(nextBlock, triggerInfo);
            }
        };
        /**
         * 执行下一个条件
         * c2做了特殊处理，触发条件产生的过滤与普通条件不一致
         */
        EventSheetDecoder.execNextCondition = function (c, cb, triggerInfo, onComplete) {
            if (onComplete === void 0) { onComplete = null; }
            if (c) {
                var condition = c;
                var targetName = condition.targetName; //实例名称
                var isFamily = condition.isFamily; //是否是组
                var callTarget = condition.callTarget; //可能为实例，也可能为行为（模板）
                var callCondition = condition.callCondition;
                var callConditionName = condition.callConditionName;
                var isInvert = condition.isInvert;
                var paramClassName = condition.paramClassName; //参数实例名称（模板）
                var paramsInstance = condition.paramsInstance; //参数实例类（模板）
                var conditionIsTrigger = condition.isTrigger; //当前条件是否是触发
                var objectlist;
                //检测所有的目标
                //重置当前总的条件状态
                condition.currentStatus = false;
                if (!cb.cs.instanceStatus)
                    cb.cs.instanceStatus = {};
                //如果条件本身是触发条件
                if (conditionIsTrigger) {
                    if (condition.triggerStatus) {
                        //计算当前触发条件状态
                        var triggerfilters = condition.triggerfilters;
                        var triggerStatus = false;
                        for (var tf_uid in triggerfilters) {
                            var triggerinstance = triggerfilters[tf_uid];
                            var searchBehavior;
                            //如果调用的目标是行为
                            if (callTarget instanceof ls.BaseBehavior) {
                                var behaviors;
                                behaviors = triggerinstance.behaviors;
                                //如果是行为，查找同名行为，这也意味着1个实例只有存在着同一个种行为，不能添加多种行为
                                for (var j = 0; j < behaviors.length; j++) {
                                    var bh = behaviors[j];
                                    if (bh.name == callTarget.name) {
                                        searchBehavior = bh;
                                        break;
                                    }
                                }
                            }
                            //解析条件属性
                            this.decodeConditionProperties(c.event.sid, c.index, paramsInstance);
                            //键值一般情况下不会变，但也有可能有动态生成的键值
                            //将值传进来
                            if (condition.triggerData) {
                                for (var triggerkey in condition.triggerData) {
                                    paramsInstance.currentKeys[triggerkey] = condition.triggerData[triggerkey];
                                }
                            }
                            //如果有参数发生变化，那么，改变这个变化值
                            if (condition.triggerChangeValue) {
                                for (var changekey in condition.triggerChangeValue) {
                                    paramsInstance[changekey] = condition.triggerChangeValue[changekey];
                                }
                            }
                            var rs = callCondition.apply(searchBehavior ? searchBehavior : triggerinstance, [paramsInstance]);
                            if (rs.status)
                                triggerStatus = true;
                            var triggerlist = rs.instances.concat();
                            if (lastfilterTargets) {
                                var triggersame = {};
                                for (var o = 0; o < triggerlist.length; o++) {
                                    var instance = triggerlist[o];
                                    //如果存在同样的名字
                                    if (lastfilterTargets[instance.u_id])
                                        triggersame[instance.u_id] = instance;
                                }
                                //删除相同名字的对象
                                for (var v = 0; v < triggerlist.length; v++) {
                                    var instance = triggerlist[v];
                                    for (var uid in triggersame) {
                                        var sinstance = triggersame[uid];
                                        if (instance.name == sinstance.name && instance.u_id != sinstance.u_id) {
                                            triggerlist.splice(v, 1);
                                            v--;
                                            break;
                                        }
                                    }
                                }
                                for (var filter_uid in lastfilterTargets) {
                                    //存储上一次过滤的目标
                                    var filterinstance = lastfilterTargets[filter_uid];
                                    if (!cb.cs.instanceStatus[filterinstance.u_id])
                                        cb.cs.instanceStatus[filterinstance.u_id] = { instance: filterinstance, ccs: {} };
                                    cb.cs.instanceStatus[filterinstance.u_id].ccs[condition.index] = { c: condition, status: true };
                                }
                            }
                            for (var o = 0, l = triggerlist.length; o < l; o++) {
                                //将结果存入进去
                                var triggerinstance = triggerlist[o];
                                if (!cb.cs.instanceStatus[triggerinstance.u_id])
                                    cb.cs.instanceStatus[triggerinstance.u_id] = { instance: triggerinstance, ccs: {} };
                                cb.cs.instanceStatus[triggerinstance.u_id].ccs[condition.index] = { c: condition, status: rs.status };
                            }
                            condition.triggerStatus = false;
                            condition.triggerfilters = {};
                            condition.currentStatus = triggerStatus;
                        }
                    }
                }
                else {
                    if (isFamily) {
                        //如果是组，那么，选取组中所有的对象
                        var _finsts = this.curFamilys[targetName].insts;
                        var finsts = [];
                        for (var fi = 0, filen = _finsts.length; fi < filen; fi++) {
                            var fname = _finsts[fi].name;
                            var flist = ls.World.getInstance().objectHash[fname];
                            for (var fj = 0, fjlen = flist.length; fj < fjlen; fj++) {
                                finsts.push(flist[fj]);
                            }
                        }
                        objectlist = finsts.concat();
                    }
                    else {
                        //非组中可能会出现单例
                        if (ls.isSingleInst(targetName))
                            objectlist = [ls.getInstanceByInstanceName(targetName)];
                        else
                            objectlist = ls.World.getInstance().objectHash[targetName];
                    }
                    //如果上一次过滤的目标存在，则计算过滤出的目标
                    var lastfilterTargets = cb.event.lastFilterTargets;
                    if (lastfilterTargets) {
                        var sames = {};
                        objectlist = objectlist.concat();
                        for (var u = 0; u < objectlist.length; u++) {
                            var instance = objectlist[u];
                            //如果存在同样的名字
                            if (lastfilterTargets[instance.u_id])
                                sames[instance.u_id] = instance;
                        }
                        //删除相同名字的对象
                        for (var v = 0; v < objectlist.length; v++) {
                            var instance = objectlist[v];
                            for (var uid in sames) {
                                var sinstance = sames[uid];
                                if (instance.name == sinstance.name && instance.u_id != sinstance.u_id) {
                                    objectlist.splice(v, 1);
                                    v--;
                                    break;
                                }
                            }
                        }
                        for (var filter_uid in lastfilterTargets) {
                            //存储上一次过滤的目标
                            var filterinstance = lastfilterTargets[filter_uid];
                            if (!cb.cs.instanceStatus[filterinstance.u_id])
                                cb.cs.instanceStatus[filterinstance.u_id] = { instance: filterinstance, ccs: {} };
                            cb.cs.instanceStatus[filterinstance.u_id].ccs[condition.index] = { c: condition, status: true };
                        }
                    }
                    for (var i = 0; i < objectlist.length; i++) {
                        var instance = objectlist[i];
                        var searchBehavior;
                        //如果调用的目标是行为
                        if (callTarget instanceof ls.BaseBehavior) {
                            var behaviors;
                            if (isFamily) {
                                if (triggerInfo && triggerInfo.familyVo)
                                    behaviors = triggerInfo.familyVo.behaviors;
                            }
                            else
                                behaviors = instance.behaviors;
                            //如果是行为，查找同名行为，这也意味着1个实例只有存在着同一个种行为，不能添加多种行为
                            for (var j = 0; j < behaviors.length; j++) {
                                var bh = behaviors[j];
                                if (bh.name == callTarget.name) {
                                    searchBehavior = bh;
                                    break;
                                }
                            }
                        }
                        //解析条件属性
                        this.decodeConditionProperties(c.event.sid, c.index, paramsInstance);
                        //求解条件结果，这里的条件有可能是实例的条件，也有可能是行为的条件
                        //求解结果需要将所有的条件中包包含的目标存起来进行比较，否则，逻辑不正确
                        var rs = callCondition.apply(searchBehavior ? searchBehavior : instance, [paramsInstance]);
                        rs.status = (isInvert) ? !rs.status : rs.status;
                        //记录for循环
                        if (rs.data && (rs.data instanceof ls.ForEvent || rs.data instanceof ls.ForEachOrderEvent || rs.data instanceof ls.OnForEachArrayElementEvent))
                            cb.loopDatas[cb.loopLayer++] = rs.data;
                        if (!cb.cs.instanceStatus[instance.u_id])
                            cb.cs.instanceStatus[instance.u_id] = { instance: instance, ccs: {} };
                        cb.cs.instanceStatus[instance.u_id].ccs[condition.index] = { c: condition, status: rs.status };
                        //注册结果中的对象
                        if (rs.instances && rs.status) {
                            for (var k = 0; k < rs.instances.length; k++) {
                                var rsIntance = rs.instances[k];
                                if (!cb.cs.instanceStatus[rsIntance.u_id])
                                    cb.cs.instanceStatus[rsIntance.u_id] = { instance: rsIntance, ccs: {} };
                                cb.cs.instanceStatus[rsIntance.u_id].ccs[condition.index] = { c: condition, status: true };
                            }
                        }
                        if (rs.status)
                            condition.currentStatus = rs.status;
                    }
                }
                //如果需要检测所有的条件，直接检测所有的条件
                if (cb.checkall) {
                    //如果下一个条件为或，那么，继续检测
                    var nc = c.nextCondition;
                    if (nc) {
                        this.execNextCondition(nc, cb, triggerInfo, onComplete);
                    }
                    else {
                        if (onComplete)
                            onComplete();
                    }
                }
                else {
                    //否则，只有当前条件成立才检测下一个条件 TODO,可能还需要将过滤目标提取出来
                    if (condition.currentStatus) {
                        var nc = c.nextCondition;
                        if (nc) {
                            this.execNextCondition(nc, cb, triggerInfo, onComplete);
                        }
                        else {
                            if (onComplete)
                                onComplete();
                        }
                    }
                    else {
                        if (onComplete)
                            onComplete();
                    }
                }
            }
        };
        EventSheetDecoder.checkCollistions = function () {
            var world = ls.World.getInstance();
            var objectList = world.objectList;
            var collisionGroups = {};
            for (var targetName in this.collisionSearchs) {
                var coVo = this.collisionSearchs[targetName];
                var owners = world.objectHash[coVo.owerName];
                for (var ownerKey in owners) {
                    var ownerObject = owners[ownerKey];
                    if (!ownerObject.container.stage)
                        continue;
                    if (ownerObject.isDead)
                        continue;
                    if (!ownerObject.collision)
                        continue;
                    for (var i = 0; i < coVo.enemyNames.length; i++) {
                        var enemyName = coVo.enemyNames[i];
                        var enemys = world.objectHash[enemyName];
                        for (var enemyKey in enemys) {
                            var enemyObject = enemys[enemyKey];
                            if (!enemyObject.container.stage)
                                continue;
                            if (!enemyObject.collision)
                                continue;
                            if (enemyObject.isDead)
                                continue;
                            var colliding = ls.Collision.checkCollision(ownerObject, enemyObject);
                            if (colliding) {
                                if (collisionGroups[ownerObject.u_id] == null) {
                                    collisionGroups[ownerObject.u_id] = [ownerObject, enemyObject];
                                }
                                if (collisionGroups[enemyObject.u_id] == null) {
                                    collisionGroups[enemyObject.u_id] = [enemyObject, ownerObject];
                                }
                            }
                        }
                    }
                }
            }
            var isExecCollision = {};
            for (var i2 = 0; i2 < objectList.length; i2++) {
                if (objectList[i2] instanceof ls.AIDisplayObject) {
                    var inst = objectList[i2];
                    var targets = collisionGroups[inst.u_id];
                    if (targets != null) {
                        var target = targets[1];
                        var insertNums = 0;
                        if (isExecCollision[inst.u_id] == null) {
                            insertNums++;
                            isExecCollision[inst.u_id] = inst;
                        }
                        if (isExecCollision[target.u_id] == null) {
                            insertNums++;
                            isExecCollision[target.u_id] = target;
                        }
                        if (insertNums != 2) {
                            inst.setIsColliding(true, target);
                        }
                    }
                    else {
                        inst.setIsColliding(false, null);
                    }
                }
            }
        };
        EventSheetDecoder.execScenePauseOrPlay = function (type) {
            if (type == 0)
                ls.StartUp.stage.frameRate = 0.001;
            else
                ls.StartUp.stage.frameRate = 60;
        };
        EventSheetDecoder.destory = function () {
            this.curSceneInstancesData = []; //重置当前场景的实例列表
            this.curSceneEventsData = [];
            this.curSceneAiObjects = [];
            this.curSceneAiObjectsHash = {};
            this.curSceneEvents = [];
            this.triggerConditions = [];
            this.curFamilys = {};
            ls.AISystem.instance.disableDataEvents = {};
            //全局变量不要销毁，除非主动销毁
            ls.GameUILayer.stage.removeEventListener(egret.Event.ENTER_FRAME, this.eventsheetRender, this);
        };
        EventSheetDecoder.curSceneInstancesData = []; //当前场景的实例列表
        EventSheetDecoder.curSceneEventsData = []; //当前场景的事件数据
        EventSheetDecoder.curSceneAiObjects = []; //当前场景的对象列表
        EventSheetDecoder.curSceneAiObjectsHash = {}; //当前场景的对象列表(以实例名字存储)
        EventSheetDecoder.curSceneEvents = []; //当前场景的事件列表 
        EventSheetDecoder.curFamilys = {}; //当前场景组列表
        EventSheetDecoder.eventSheets = {};
        EventSheetDecoder.collisionSearchs = {};
        EventSheetDecoder.lastTime = 0;
        EventSheetDecoder._tick = 60;
        EventSheetDecoder.testShape = new egret.Sprite();
        EventSheetDecoder.dt = 0;
        EventSheetDecoder.dt1 = 0;
        EventSheetDecoder.timeScale = 1;
        //存储触发条件，这样就不用每次都要用查找了,提升运行效率
        EventSheetDecoder.triggerConditions = [];
        return EventSheetDecoder;
    }());
    ls.EventSheetDecoder = EventSheetDecoder;
    egret.registerClass(EventSheetDecoder,'ls.EventSheetDecoder');
})(ls || (ls = {}));
//# sourceMappingURL=EventSheetDecoder.js.map