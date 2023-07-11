var _this = this;
var LoadingControl = (function () {
    function LoadingControl() {
    }
    LoadingControl.mount = function (el) {
        el.append(this.loadingEl);
    };
    LoadingControl.unMount = function (el) {
        el.removeChild(this.loadingEl);
    };
    LoadingControl.open = function () {
        var _this = this;
        this.loadingEl.style.display = "flex";
        setTimeout(function () {
            _this.loadingEl.style.opacity = '1';
        });
    };
    LoadingControl.close = function () {
        var _this = this;
        this.loadingEl.style.opacity = '0';
        setTimeout(function () {
            _this.loadingEl.style.display = "none";
        }, 1000);
    };
    var _a;
    _a = LoadingControl;
    LoadingControl.loadingHtml = "\n        <div class=\"loading-box\">\n            <div class=\"loading-box-item\"></div>\n            <div class=\"loading-box-item\"></div>\n            <div class=\"loading-box-item\"></div>\n            <div class=\"loading-box-item\"></div>\n            <div class=\"loading-box-item\"></div>\n            <div class=\"loading-box-item\"></div>\n            <div class=\"loading-box-item\"></div>\n            <div class=\"loading-box-item\"></div>\n            <div class=\"loading-box-item\"></div>\n            <div class=\"loading-box-item\"></div>\n            <div class=\"loading-box-item\"></div>\n            <div class=\"loading-box-item\"></div>\n            <div class=\"loading-box-item\"></div>\n        </div>\n    ";
    (function () {
        _a.loadingEl = document.createElement('div');
        _a.loadingEl.className = 'loading';
        _a.loadingEl.innerHTML = _a.loadingHtml;
    })();
    return LoadingControl;
}());
export default LoadingControl;
