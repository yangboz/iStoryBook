var ls;
(function (ls) {
    var AIObject = (function (_super) {
        __extends(AIObject, _super);
        function AIObject() {
            _super.call(this);
            this.id = 0;
            this.name = "Object";
            this.isModel = false;
            this.timeScale = -1;
            this.parallaxX = 100;
            this.parallaxY = 100;
            this.isDead = false;
            this.global = false;
            this.variables = {};
            this.index = 0;
            //每个目标都有特定的条件
            //conditions: any = {};
            this.paramInstances = {};
            this.actionSaves = {};
            this.currentStatus = true;
            this.selfStatus = true; //自身状态
            this._uid = 0;
            AIObject._uniqueID++;
            AIObject.U_ID++;
            this._uid = AIObject.U_ID;
            this.name = "Object";
            this.plugin = egret.getQualifiedClassName(this);
            ls.Trigger.register(this);
        }
        var d = __define,c=AIObject,p=c.prototype;
        d(p, "u_id"
            ,function () {
                return this._uid;
            }
        );
        d(p, "dt"
            ,function () {
                return 1 / 60;
                //如果没有设置实例的时间缩放，那么，取系统时间
                // if (this.timeScale === -1)
                //     return EventSheetDecoder.dt;
                // //否则，取实例时间
                // return this.timeScale * EventSheetDecoder.dt1;
            }
        );
        p.getClass = function () {
            return this["constructor"];
        };
        //初始化，所有属性分配完毕，进行初始化
        p.initialize = function () {
        };
        //实例创建时
        p.onCreate = function () {
            this.dispatchEvent(new ls.TriggerEvent(ls.TriggerEvent.TRIGGER, this.onCreated));
        };
        /**
         * 每帧频执行一次
         */
        p.onTick = function () {
        };
        p.addVariable = function (variableName, value) {
            this[variableName] = ls.eval_e(value);
            this.variables[variableName] = ls.eval_e(value);
        };
        p.getFirstPicked = function () {
            var objects = ls.World.getInstance().objectHash[this.name];
            if (objects)
                return objects[0];
        };
        p.onCreated = function ($event) {
            return { instances: [this], status: true };
        };
        p.compareInstanceVariable = function ($event) {
            return { instances: [this], status: ls.compare(this[$event.instanceVariable], $event.operationType, $event.value) };
        };
        /**比较两个值*/
        p.compareTwoValue = function ($compareTwoValues) {
            return { instances: [this], status: ls.compare($compareTwoValues.value1, $compareTwoValues.operationType, $compareTwoValues.value2) };
        };
        /**判断值是否在两个值内*/
        p.isBetweenValues = function ($isBetweenValues) {
            var value = ls.eval_e($isBetweenValues.value);
            var lowerValue = ls.eval_e($isBetweenValues.lowerValue);
            var highValue = ls.eval_e($isBetweenValues.highValue);
            if (lowerValue < highValue)
                return { instances: [this], status: (value > lowerValue && value < highValue) };
            return { instances: [this], status: (value < lowerValue && value > highValue) };
        };
        /**是否是数字*/
        p.isNumberNaN = function ($isNumberNaN) {
            return { instances: [this], status: (typeof ls.eval_e($isNumberNaN.value) === "number") };
        };
        ////////////////////////////////////actions///////////////////////////////////
        p.addTo = function ($instanceVariables, $value) {
            var value = ls.eval_e($value);
            ls.assert(typeof value !== "number", "AIObject addTo parameter type incorrect!!");
            this[$instanceVariables] += value;
            this.variables[$instanceVariables] = this[$instanceVariables];
        };
        p.setBoolean = function ($instanceVariables, $value) {
            var value = ls.eval_e($value);
            ls.assert(typeof value !== "number", "AIObject setBoolean parameter type incorrect!!");
            this[$instanceVariables] = (value == 1);
            this.variables[$instanceVariables] = this[$instanceVariables];
        };
        p.setValue = function ($instanceVariables, $value) {
            var value = ls.eval_e($value);
            this[$instanceVariables] = value;
            this.variables[$instanceVariables] = this[$instanceVariables];
        };
        p.subtractFrom = function ($instanceVariables, $value) {
            var value = ls.eval_e($value);
            ls.assert(typeof value !== "number", "AIObject subtractFrom parameter type incorrect!!");
            this[$instanceVariables] -= value;
            this.variables[$instanceVariables] = this[$instanceVariables];
        };
        p.toogleBoolean = function ($instanceVariables) {
            this[$instanceVariables] = !this[$instanceVariables];
            this.variables[$instanceVariables] = this[$instanceVariables];
        };
        /**
         * 切换场景时销毁
         *
         */
        p.destoryOnChangeScene = function () {
            this.destory();
        };
        p.destory = function () {
        };
        p.saveToJSON = function () {
            return {
                "plugin": egret.getQualifiedClassName(this),
                "id": this.id,
                "uid": this.u_id,
                "name": this.name,
                "isModel": this.isModel,
                "isDead": this.isDead,
                "paramInstances": this.paramInstances,
                "timeScale": this.timeScale,
                "global": this.global,
                "variables": JSON.stringify(this.variables)
            };
        };
        p.loadFromJSON = function (o) {
            if (o) {
                this.plugin = o["plugin"];
                this.id = o["id"];
                this._uid = o["uid"];
                this.name = o["name"];
                this.isModel = o["isModel"];
                this.isDead = o["isDead"];
                this.paramInstances = o["paramInstances"];
                this.timeScale = o["timeScale"];
                this.global = o["global"];
                this.variables = JSON.parse(o["variables"]);
                for (var key in this.variables)
                    this.addVariable(key, this.variables[key]);
            }
        };
        p.clone = function () {
            var cl = this.getClass();
            var cloneInstance = new cl();
            cloneInstance.id = -Number.MAX_VALUE; //拷贝的对象的id都为最小值，这样在取场景对象的时候不会影响
            cloneInstance.name = this.name;
            cloneInstance.isModel = false;
            cloneInstance.timeScale = this.timeScale;
            cloneInstance.global = this.global;
            //clone variables
            for (var key in this.variables)
                cloneInstance.addVariable(key, this.variables[key]);
            return cloneInstance;
        };
        AIObject._uniqueID = 0;
        AIObject.U_ID = 0;
        return AIObject;
    }(egret.EventDispatcher));
    ls.AIObject = AIObject;
    egret.registerClass(AIObject,'ls.AIObject');
    var CompareTwoValuesEvent = (function (_super) {
        __extends(CompareTwoValuesEvent, _super);
        function CompareTwoValuesEvent() {
            _super.apply(this, arguments);
        }
        var d = __define,c=CompareTwoValuesEvent,p=c.prototype;
        return CompareTwoValuesEvent;
    }(ls.BaseEvent));
    ls.CompareTwoValuesEvent = CompareTwoValuesEvent;
    egret.registerClass(CompareTwoValuesEvent,'ls.CompareTwoValuesEvent');
    var IsBetweenValuesEvent = (function (_super) {
        __extends(IsBetweenValuesEvent, _super);
        function IsBetweenValuesEvent() {
            _super.apply(this, arguments);
        }
        var d = __define,c=IsBetweenValuesEvent,p=c.prototype;
        return IsBetweenValuesEvent;
    }(ls.BaseEvent));
    ls.IsBetweenValuesEvent = IsBetweenValuesEvent;
    egret.registerClass(IsBetweenValuesEvent,'ls.IsBetweenValuesEvent');
    var IsNumberNaNEvent = (function (_super) {
        __extends(IsNumberNaNEvent, _super);
        function IsNumberNaNEvent() {
            _super.apply(this, arguments);
        }
        var d = __define,c=IsNumberNaNEvent,p=c.prototype;
        return IsNumberNaNEvent;
    }(ls.BaseEvent));
    ls.IsNumberNaNEvent = IsNumberNaNEvent;
    egret.registerClass(IsNumberNaNEvent,'ls.IsNumberNaNEvent');
    var OnCreatedEvent = (function (_super) {
        __extends(OnCreatedEvent, _super);
        function OnCreatedEvent() {
            _super.apply(this, arguments);
        }
        var d = __define,c=OnCreatedEvent,p=c.prototype;
        return OnCreatedEvent;
    }(ls.BaseEvent));
    ls.OnCreatedEvent = OnCreatedEvent;
    egret.registerClass(OnCreatedEvent,'ls.OnCreatedEvent');
    var CompareInstanceVariableEvent = (function (_super) {
        __extends(CompareInstanceVariableEvent, _super);
        function CompareInstanceVariableEvent() {
            _super.apply(this, arguments);
            this.value = 0;
        }
        var d = __define,c=CompareInstanceVariableEvent,p=c.prototype;
        return CompareInstanceVariableEvent;
    }(ls.BaseEvent));
    ls.CompareInstanceVariableEvent = CompareInstanceVariableEvent;
    egret.registerClass(CompareInstanceVariableEvent,'ls.CompareInstanceVariableEvent');
})(ls || (ls = {}));
//# sourceMappingURL=AIObject.js.map