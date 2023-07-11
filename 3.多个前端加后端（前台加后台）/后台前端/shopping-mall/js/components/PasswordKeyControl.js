var PasswordKeyControl = (function () {
    function PasswordKeyControl() {
    }
    PasswordKeyControl.run = function () {
        var _this = this;
        var passwordIconEls = document.querySelectorAll('.input-box .password');
        passwordIconEls.forEach(function (passwordIconEl) {
            var headerEl = passwordIconEl.firstElementChild;
            var inputEl = passwordIconEl.nextElementSibling;
            passwordIconEl.addEventListener('click', function () {
                headerEl.className.indexOf('open') == -1 ? _this.show(headerEl, inputEl) : _this.hide(headerEl, inputEl);
            });
        });
    };
    PasswordKeyControl.show = function (el, inputEl) {
        el.className += ' open';
        inputEl.type = 'text';
    };
    PasswordKeyControl.hide = function (el, inputEl) {
        el.className = el.className.replace(' open', '');
        inputEl.type = 'password';
    };
    return PasswordKeyControl;
}());
export default PasswordKeyControl;
