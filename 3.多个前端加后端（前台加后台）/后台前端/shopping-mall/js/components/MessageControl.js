var _a;
var _this = this;
export var MessageType;
(function (MessageType) {
    MessageType["INFO"] = "#2f2f30";
    MessageType["ERROR"] = "#432d2d";
    MessageType["SUCCESS"] = "#18351c";
    MessageType["WARNING"] = "#4e3c26";
})(MessageType || (MessageType = {}));
var MessageControl = (function () {
    function MessageControl() {
    }
    MessageControl.mount = function (el) {
        el.appendChild(this.messageEl);
    };
    MessageControl.unMount = function (el) {
        el.removeChild(this.messageEl);
    };
    MessageControl.createChild = function (message, type) {
        var resultEl = document.createElement('div');
        resultEl.className = 'message-item';
        resultEl.style.backgroundColor = type;
        resultEl.innerText = message;
        var closeEl = document.createElement('div');
        closeEl.className = 'message-close';
        var closeLeftChild = document.createElement('div');
        closeLeftChild.className = 'message-left';
        var closeRightChild = document.createElement('div');
        closeRightChild.className = 'message-right';
        closeEl.appendChild(closeLeftChild);
        closeEl.appendChild(closeRightChild);
        resultEl.appendChild(closeEl);
        this.bindEvent(closeEl, resultEl);
        return resultEl;
    };
    MessageControl.bindEvent = function (closeEl, childEl) {
        var _this = this;
        var handelFun = function () {
            _this.close(childEl);
        };
        closeEl.addEventListener('click', handelFun);
        setTimeout(function () {
            closeEl.removeEventListener('click', handelFun);
        }, 2400);
    };
    MessageControl.autoClose = function (childEl) {
        var _this = this;
        setTimeout(function () {
            childEl.style.opacity = '0';
        }, 2200);
        setTimeout(function () {
            try {
                _this.messageEl.removeChild(childEl);
            }
            catch (error) {
            }
        }, 2500);
    };
    MessageControl.open = function (message, type) {
        var childEl = this.createChild(message, type);
        this.messageEl.appendChild(childEl);
        this.autoClose(childEl);
    };
    MessageControl.close = function (el) {
        this.messageEl.removeChild(el);
    };
    return MessageControl;
}());
export { MessageControl };
_a = MessageControl;
(function () {
    _a.messageEl = document.createElement('div');
    _a.messageEl.className = 'message';
})();
