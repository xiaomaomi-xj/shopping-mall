var SelectComponentControl = (function () {
    function SelectComponentControl() {
    }
    SelectComponentControl.run = function () {
        var _this = this;
        var selectCheckEls = document.querySelectorAll('.select-box > .select-check-value');
        var selectItemBoxEls = document.querySelectorAll('.select-box > .select-item-box');
        selectCheckEls.forEach(function (selectCheckEl, index) {
            var _a;
            for (var i = 0; i < selectItemBoxEls[index].childElementCount; i++) {
                (_a = selectItemBoxEls[index].children.item(i)) === null || _a === void 0 ? void 0 : _a.addEventListener('click', function (e) {
                    _this.hide(selectItemBoxEls[index]);
                    selectCheckEl.innerText = e.target.innerText;
                });
            }
            selectCheckEl.addEventListener('click', function () {
                if (selectItemBoxEls[index].style.opacity == '1') {
                    _this.hide(selectItemBoxEls[index]);
                }
                else {
                    _this.show(selectItemBoxEls[index]);
                }
            });
        });
    };
    SelectComponentControl.hide = function (selectItemBoxEl) {
        selectItemBoxEl.style.opacity = '0';
        setTimeout(function () {
            selectItemBoxEl.style.display = 'none';
        }, 500);
    };
    SelectComponentControl.show = function (selectItemBoxEl) {
        selectItemBoxEl.style.display = 'flex';
        setTimeout(function () {
            selectItemBoxEl.style.opacity = '1';
        });
    };
    return SelectComponentControl;
}());
export default SelectComponentControl;
