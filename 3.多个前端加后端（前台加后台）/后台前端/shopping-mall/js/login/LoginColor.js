var ColorType;
(function (ColorType) {
    ColorType["ONE_LEVEL"] = "#555";
    ColorType["TWO_LEVEL"] = "#888";
    ColorType["THREE_LEVEL"] = "#bbb";
})(ColorType || (ColorType = {}));
var LoginColor = (function () {
    function LoginColor(userInputEl, passwordInputEl, colorEls, bgColorEls, borderColorsEls) {
        this.userInputEl = userInputEl;
        this.passwordInputEl = passwordInputEl;
        this.colorEls = colorEls;
        this.bgColorEls = bgColorEls;
        this.borderColorsEls = borderColorsEls;
    }
    LoginColor.prototype.run = function () {
        this.handelEvent();
    };
    LoginColor.prototype.handelEvent = function () {
        var _this = this;
        this.userInputEl.addEventListener('input', function () {
            _this.colorLevel();
        });
        this.passwordInputEl.addEventListener('input', function () {
            _this.colorLevel();
        });
    };
    LoginColor.prototype.colorLevel = function () {
        if (this.userInputEl.value.trim().length != 0) {
            if (this.passwordInputEl.value.trim().length != 0) {
                this.colorEls.map(function (v) { return v.style.color = ColorType.THREE_LEVEL; });
                this.bgColorEls.map(function (v) { return v.style.backgroundColor = ColorType.THREE_LEVEL; });
                this.borderColorsEls.map(function (v) { return v.style.borderColor = ColorType.THREE_LEVEL; });
            }
            else {
                this.colorEls.map(function (v) { return v.style.color = ColorType.TWO_LEVEL; });
                this.bgColorEls.map(function (v) { return v.style.backgroundColor = ColorType.TWO_LEVEL; });
                this.borderColorsEls.map(function (v) { return v.style.borderColor = ColorType.TWO_LEVEL; });
            }
        }
        else {
            if (this.passwordInputEl.value.trim().length != 0) {
                this.colorEls.map(function (v) { return v.style.color = ColorType.TWO_LEVEL; });
                this.bgColorEls.map(function (v) { return v.style.backgroundColor = ColorType.TWO_LEVEL; });
                this.borderColorsEls.map(function (v) { return v.style.borderColor = ColorType.TWO_LEVEL; });
            }
            else {
                this.colorEls.map(function (v) { return v.style.color = ColorType.ONE_LEVEL; });
                this.bgColorEls.map(function (v) { return v.style.backgroundColor = ColorType.ONE_LEVEL; });
                this.borderColorsEls.map(function (v) { return v.style.borderColor = ColorType.ONE_LEVEL; });
            }
        }
    };
    return LoginColor;
}());
export default LoginColor;
