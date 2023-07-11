var _a;
var _this = this;
var CustomComponentBoxControl = (function () {
    function CustomComponentBoxControl() {
    }
    CustomComponentBoxControl.mount = function (el) {
        el.appendChild(this.customComponentBoxEl);
    };
    CustomComponentBoxControl.unMount = function (el) {
        el.removeChild(this.customComponentBoxEl);
    };
    CustomComponentBoxControl.createNode = function (componentEl) {
        var mainEl = document.createElement('div');
        mainEl.className = 'custom-component-box-main';
        var headEl = document.createElement('div');
        headEl.className = 'custom-component-box-main-head';
        var closeEl = document.createElement('div');
        closeEl.className = 'custom-component-box-main-head-close';
        closeEl.addEventListener('click', this.close.bind(this));
        var leftEl = document.createElement('div');
        leftEl.className = 'custom-component-box-main-head-close-left';
        var rightEl = document.createElement('div');
        rightEl.className = 'custom-component-box-main-head-close-right';
        var bodyEl = document.createElement('div');
        bodyEl.className = 'custom-component-box-main-body';
        bodyEl.appendChild(componentEl);
        closeEl.appendChild(leftEl);
        closeEl.appendChild(rightEl);
        headEl.appendChild(closeEl);
        mainEl.appendChild(headEl);
        mainEl.appendChild(bodyEl);
        return mainEl;
    };
    CustomComponentBoxControl.open = function (componentEl, callbackFun) {
        var _this = this;
        if (callbackFun === void 0) { callbackFun = function () { }; }
        this.callbackFun = callbackFun;
        this.customComponentBoxEl.innerHTML = '';
        componentEl.className = componentEl.className.replace('custom-component ', '');
        this.customComponentBoxEl.appendChild(this.createNode(componentEl));
        this.customComponentBoxEl.style.display = 'flex';
        setTimeout(function () {
            _this.customComponentBoxEl.style.opacity = '1';
        });
    };
    CustomComponentBoxControl.close = function () {
        var _this = this;
        setTimeout(function () {
            _this.callbackFun();
        });
        this.customComponentBoxEl.style.opacity = '0';
        setTimeout(function () {
            _this.customComponentBoxEl.style.display = 'none';
            _this.customComponentBoxEl.innerHTML = '';
        }, 600);
    };
    return CustomComponentBoxControl;
}());
_a = CustomComponentBoxControl;
(function () {
    _a.customComponentBoxEl = document.createElement('div');
    _a.customComponentBoxEl.className = 'custom-component-box';
})();
export default CustomComponentBoxControl;
