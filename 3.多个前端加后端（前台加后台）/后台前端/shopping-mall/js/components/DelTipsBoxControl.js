var _a;
var _this = this;
var DelTipsBoxControl = (function () {
    function DelTipsBoxControl() {
    }
    DelTipsBoxControl.mount = function (el) {
        el.appendChild(this.delTipsBoxEl);
    };
    DelTipsBoxControl.unMount = function (el) {
        el.removeChild(this.delTipsBoxEl);
    };
    DelTipsBoxControl.createNode = function (message, callbackFun) {
        var _this = this;
        var mainEl = document.createElement('div');
        mainEl.className = 'del-tips-box-main';
        var headerEl = document.createElement('div');
        headerEl.className = 'del-tips-box-header';
        var tipsEl = document.createTextNode("特别提示");
        headerEl.appendChild(tipsEl);
        mainEl.appendChild(headerEl);
        var bodyEl = document.createElement('div');
        bodyEl.className = 'del-tips-box-body';
        bodyEl.innerHTML = message;
        mainEl.appendChild(bodyEl);
        var footerEl = document.createElement('div');
        footerEl.className = 'del-tips-box-footer';
        var cancellBthEl = document.createElement('div');
        cancellBthEl.className = 'del-tips-box-calcel';
        cancellBthEl.innerText = '取消';
        cancellBthEl.addEventListener('click', this.close.bind(this));
        var determineBthEl = document.createElement('div');
        determineBthEl.className = 'del-tips-box-determine';
        determineBthEl.innerText = '确定';
        determineBthEl.addEventListener('click', function () {
            try {
                callbackFun();
            }
            finally {
                _this.close();
            }
        });
        footerEl.appendChild(cancellBthEl);
        footerEl.appendChild(determineBthEl);
        mainEl.appendChild(footerEl);
        return mainEl;
    };
    DelTipsBoxControl.open = function (message, callbackFun) {
        var _this = this;
        this.delTipsBoxEl.innerHTML = '';
        this.delTipsBoxEl.appendChild(this.createNode(message, callbackFun));
        this.delTipsBoxEl.style.display = 'flex';
        setTimeout(function () {
            _this.delTipsBoxEl.style.opacity = '1';
        });
    };
    DelTipsBoxControl.close = function () {
        var _this = this;
        this.delTipsBoxEl.style.opacity = '0';
        setTimeout(function () {
            _this.delTipsBoxEl.style.display = 'none';
            _this.delTipsBoxEl.innerHTML = '';
        }, 600);
    };
    return DelTipsBoxControl;
}());
export { DelTipsBoxControl };
_a = DelTipsBoxControl;
(function () {
    _a.delTipsBoxEl = document.createElement('div');
    _a.delTipsBoxEl.className = 'del-tips-box';
})();
