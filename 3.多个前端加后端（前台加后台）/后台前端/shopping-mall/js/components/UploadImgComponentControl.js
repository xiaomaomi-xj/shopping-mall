import { ImgComponentBoxControl } from "./ImgComponentBoxControl.js";
import { MessageControl, MessageType } from "./MessageControl.js";
export var UploadImgComponentControl = (function () {
    function UploadImgComponentControl() {
    }
    UploadImgComponentControl.run = function () {
        var _this = this;
        this.fileUpdateEls = document.querySelectorAll('.file-update-box');
        this.fileUpdateEls.forEach(function (fileUpdateEl) {
            _this.bindEvent(fileUpdateEl);
        });
    };
    UploadImgComponentControl.add = function (fileUpdateEl) {
        var _this = this;
        setTimeout(function () {
            _this.bindEvent(fileUpdateEl);
        });
    };
    UploadImgComponentControl.bindEvent = function (fileUpdateEl) {
        var _this = this;
        var inputEl = fileUpdateEl.firstElementChild;
        inputEl.accept = this.acceptFileType.join(',');
        var imgEl = inputEl.nextElementSibling;
        var extendEl = imgEl.nextElementSibling;
        var selectEl = extendEl.nextElementSibling;
        extendEl.addEventListener('click', function () {
            _this.extendImg(imgEl.src);
        });
        selectEl.addEventListener('click', function () {
            _this.selectInput(inputEl);
        });
        inputEl.addEventListener('change', function () {
            _this.clickSelectImg(inputEl, imgEl);
        });
        fileUpdateEl.addEventListener('drop', function (e) {
            _this.dropSelectImg(e, imgEl);
        });
        fileUpdateEl.addEventListener('dragover', function (e) {
            _this.dragoverEvent(e);
        });
    };
    UploadImgComponentControl.extendImg = function (imgSrc) {
        ImgComponentBoxControl.open(imgSrc);
    };
    UploadImgComponentControl.selectInput = function (inputEl) {
        inputEl.click();
    };
    UploadImgComponentControl.clickSelectImg = function (inputEl, imgEl) {
        var file = inputEl.files[0];
        this.showImg(file, imgEl);
    };
    UploadImgComponentControl.dropSelectImg = function (e, imgEl) {
        e.preventDefault();
        var file = e.dataTransfer.files[0];
        this.showImg(file, imgEl);
    };
    UploadImgComponentControl.dragoverEvent = function (e) {
        e.preventDefault();
    };
    UploadImgComponentControl.showImg = function (file, imgEl) {
        var fileName = file.name.toLowerCase();
        if (this.acceptFileType.indexOf(fileName.substring(fileName.lastIndexOf("."))) == -1) {
            MessageControl.open("只支持【" + this.acceptFileType.join(",") + "】类型的文件", MessageType.ERROR);
        }
        else if (file.size > this.acceptFileSize) {
            MessageControl.open("文件大小不得超过【" + this.acceptFileSize / 1024 / 1024 + "MB】", MessageType.ERROR);
        }
        else {
            imgEl.src = URL.createObjectURL(file);
        }
    };
    UploadImgComponentControl.acceptFileType = ['.bmp', '.jpg', '.png', '.gif', '.jpeg'];
    UploadImgComponentControl.acceptFileSize = 2097152;
    return UploadImgComponentControl;
}());
