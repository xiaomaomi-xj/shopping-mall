var _a;
var _this = this;
var ImgComponentBoxControl = (function () {
    function ImgComponentBoxControl() {
    }
    ImgComponentBoxControl.mount = function (el) {
        el.appendChild(this.imgComponentBoxEl);
    };
    ImgComponentBoxControl.unMount = function (el) {
        el.removeChild(this.imgComponentBoxEl);
    };
    ImgComponentBoxControl.createNode = function (componentEl) {
        var mainEl = document.createElement('div');
        mainEl.className = 'img-component-box-main';
        var headEl = document.createElement('div');
        headEl.className = 'img-component-box-main-head';
        var closeEl = document.createElement('div');
        closeEl.className = 'img-component-box-main-head-close';
        closeEl.addEventListener('click', this.close.bind(this));
        var leftEl = document.createElement('div');
        leftEl.className = 'img-component-box-main-head-close-left';
        var rightEl = document.createElement('div');
        rightEl.className = 'img-component-box-main-head-close-right';
        var bodyEl = document.createElement('div');
        bodyEl.className = 'img-component-box-main-body';
        bodyEl.appendChild(componentEl);
        closeEl.appendChild(leftEl);
        closeEl.appendChild(rightEl);
        headEl.appendChild(closeEl);
        mainEl.appendChild(headEl);
        mainEl.appendChild(bodyEl);
        return mainEl;
    };
    ImgComponentBoxControl.createImgNode = function (imgSrc) {
        var imgBoxEl = document.createElement('div');
        imgBoxEl.style.width = '80vh';
        imgBoxEl.style.height = '50vh';
        imgBoxEl.style.display = 'flex';
        imgBoxEl.style.justifyContent = 'center';
        imgBoxEl.style.alignItems = 'center';
        var imgEl = document.createElement('img');
        imgEl.alt = '图片资源丢失...';
        imgEl.style.maxWidth = '100%';
        imgEl.style.height = '100%';
        imgEl.style.borderRadius = '5px';
        imgEl.src = imgSrc;
        imgBoxEl.appendChild(imgEl);
        return imgBoxEl;
    };
    ImgComponentBoxControl.open = function (src) {
        var _this = this;
        this.imgComponentBoxEl.innerHTML = '';
        this.imgComponentBoxEl.appendChild(this.createNode(this.createImgNode(src)));
        this.imgComponentBoxEl.style.display = 'flex';
        setTimeout(function () {
            _this.imgComponentBoxEl.style.opacity = '1';
        });
    };
    ImgComponentBoxControl.close = function () {
        var _this = this;
        this.imgComponentBoxEl.style.opacity = '0';
        setTimeout(function () {
            _this.imgComponentBoxEl.style.display = 'none';
            _this.imgComponentBoxEl.innerHTML = '';
        }, 600);
    };
    return ImgComponentBoxControl;
}());
export { ImgComponentBoxControl };
_a = ImgComponentBoxControl;
(function () {
    _a.imgComponentBoxEl = document.createElement('div');
    _a.imgComponentBoxEl.className = 'img-component-box';
})();
