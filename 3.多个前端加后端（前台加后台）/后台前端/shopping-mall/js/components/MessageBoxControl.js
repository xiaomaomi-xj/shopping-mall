var _a;
var _this = this;
var MessageBoxControl = (function () {
    function MessageBoxControl() {
    }
    MessageBoxControl.mount = function (el) {
        el.appendChild(this.messageBoxEl);
    };
    MessageBoxControl.unMount = function (el) {
        el.removeChild(this.messageBoxEl);
    };
    MessageBoxControl.createNode = function (message, callbackFun) {
        var _this = this;
        var mainEl = document.createElement('div');
        mainEl.className = 'message-box-main';
        var headerEl = document.createElement('div');
        headerEl.className = 'message-box-header';
        var closeEl = document.createElement('div');
        closeEl.className = 'message-box-close';
        closeEl.addEventListener('click', this.close.bind(this));
        var leftEl = document.createElement('div');
        leftEl.className = 'message-box-left';
        var rightEl = document.createElement('div');
        rightEl.className = 'message-box-right';
        closeEl.appendChild(leftEl);
        closeEl.appendChild(rightEl);
        headerEl.appendChild(closeEl);
        var tipsEl = document.createTextNode("温馨提示");
        headerEl.appendChild(tipsEl);
        mainEl.appendChild(headerEl);
        var bodyEl = document.createElement('div');
        bodyEl.className = 'message-box-body';
        bodyEl.innerText = message;
        mainEl.appendChild(bodyEl);
        var footerEl = document.createElement('div');
        footerEl.className = 'message-box-footer';
        var buttonEl = document.createElement('div');
        buttonEl.className = 'message-box-button';
        buttonEl.innerText = '确定';
        buttonEl.addEventListener('click', function () {
            try {
                callbackFun();
            }
            finally {
                _this.close();
            }
        });
        footerEl.appendChild(buttonEl);
        mainEl.appendChild(footerEl);
        return mainEl;
    };
    MessageBoxControl.open = function (message, callbackFun) {
        var _this = this;
        this.messageBoxEl.innerHTML = '';
        this.messageBoxEl.appendChild(this.createNode(message, callbackFun));
        this.messageBoxEl.style.display = 'flex';
        setTimeout(function () {
            _this.messageBoxEl.style.opacity = '1';
        });
    };
    MessageBoxControl.close = function () {
        var _this = this;
        this.messageBoxEl.style.opacity = '0';
        setTimeout(function () {
            _this.messageBoxEl.style.display = 'none';
            _this.messageBoxEl.innerHTML = '';
        }, 600);
    };
    return MessageBoxControl;
}());
export { MessageBoxControl };
_a = MessageBoxControl;
(function () {
    _a.messageBoxEl = document.createElement('div');
    _a.messageBoxEl.className = 'message-box';
})();
