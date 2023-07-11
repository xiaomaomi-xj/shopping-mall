var SrcToFile = (function () {
    function SrcToFile() {
    }
    SrcToFile.check = function (src) {
        if (src.indexOf('/src/bak-img.jpg') != -1) {
            return false;
        }
        return true;
    };
    SrcToFile.moreSrcToFile = function (srcs, files, successCallBackFun, failCallBackFun) {
        var _this = this;
        if (srcs.length > 0) {
            var src = srcs.shift();
            this.srcToFile(src, function (file) {
                files.push(file);
                _this.moreSrcToFile(srcs, files, successCallBackFun, failCallBackFun);
            }, function () {
                failCallBackFun();
            });
        }
        else {
            successCallBackFun();
        }
    };
    SrcToFile.srcToFile = function (src, successCallBackFun, failCallBackFun) {
        var _this = this;
        var xhr = new XMLHttpRequest();
        xhr.open('GET', src, true);
        xhr.responseType = 'blob';
        xhr.onload = function () {
            if (xhr.status == 200) {
                var blob = xhr.response;
                if (blob.type.indexOf('image/') == 0) {
                    var fileName = _this.createFileName(blob.type);
                    successCallBackFun(new File([blob], fileName, { type: blob.type }));
                }
                else {
                    failCallBackFun();
                }
            }
            else {
                failCallBackFun();
            }
        };
        xhr.send();
    };
    SrcToFile.createFileName = function (type) {
        var filePrefix = new Date().getTime() + "" + Math.floor(Math.random() * 100000);
        var fileSuffix = '.'.concat(type.split("/")[1]);
        return filePrefix + fileSuffix;
    };
    return SrcToFile;
}());
export { SrcToFile };
