var _this = this;
var TipsControl = (function () {
    function TipsControl() {
    }
    TipsControl.mount = function (el, beforeEl) {
        el.insertBefore(this.tipssEl, beforeEl);
    };
    TipsControl.show = function (message) {
        if (message === void 0) { message = '账号或密码不得为空！'; }
        clearTimeout(this.taskId);
        this.tipssEl.innerText = message;
        this.tipssEl.style.opacity = '1';
        this.hide();
    };
    TipsControl.hide = function () {
        var _this = this;
        this.taskId = setTimeout(function () {
            _this.tipssEl.style.opacity = '0';
        }, 3000);
    };
    var _a;
    _a = TipsControl;
    TipsControl.taskId = -1;
    (function () {
        _a.tipssEl = document.createElement('span');
        _a.tipssEl.className = 'tips-message';
    })();
    return TipsControl;
}());
export default TipsControl;
