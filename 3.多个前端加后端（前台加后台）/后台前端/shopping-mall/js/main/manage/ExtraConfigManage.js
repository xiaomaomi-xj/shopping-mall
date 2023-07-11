var __extends = (this && this.__extends) || (function () {
    var extendStatics = function (d, b) {
        extendStatics = Object.setPrototypeOf ||
            ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
            function (d, b) { for (var p in b) if (Object.prototype.hasOwnProperty.call(b, p)) d[p] = b[p]; };
        return extendStatics(d, b);
    };
    return function (d, b) {
        if (typeof b !== "function" && b !== null)
            throw new TypeError("Class extends value " + String(b) + " is not a constructor or null");
        extendStatics(d, b);
        function __() { this.constructor = d; }
        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
    };
})();
import { ExtraConfigUrls } from "../../api/ApiConstant.js";
import { DelTipsBoxControl } from "../../components/DelTipsBoxControl.js";
import { MessageControl, MessageType } from "../../components/MessageControl.js";
import { PublicMainView } from "../../components/PublicMainView.js";
import Ajax from "../../utils/Ajax.js";
import { ChatCheck } from "../../utils/ChatCheck.js";
import { SrcToFile } from "../../utils/SrcToFile.js";
import ParentMain from "../ParentMain.js";
var ExtraConfigManage = (function (_super) {
    __extends(ExtraConfigManage, _super);
    function ExtraConfigManage() {
        return _super !== null && _super.apply(this, arguments) || this;
    }
    ExtraConfigManage.prototype.getEvent = function () {
        var _this = this;
        return function () {
            var extraConfigIdEl = document.querySelector('.extra-config-manage-box-content-text-box-value');
            var extraConfigInputEls = document.querySelectorAll('.extra-config-manage-box-content-input-box-input > input');
            var extraConfigSelectEl = document.querySelector('.extra-config-manage-box-content-select-box-select .select-check-value');
            var extraConfigImgEls = document.querySelectorAll('.extra-config-manage-box-content-bg-box-bg .file-update-box-img');
            var extraConfigBtnEl = document.querySelector('.extra-config-manage-box-button-box > button');
            new Ajax(ExtraConfigUrls.getConfigUrl).config().sendEmpty().result(function (data) {
                extraConfigIdEl.innerText = data.extraId;
                extraConfigSelectEl.innerText = data.storeNameHaveSpecial ? '含有特殊字' : '不含有特殊字';
                extraConfigInputEls[0].value = data.passwordTemplateText;
                extraConfigInputEls[1].value = data.belowPageText;
                extraConfigInputEls[2].value = data.specialText;
                extraConfigInputEls[3].value = data.storeName;
                if (ChatCheck.isEmpty(data.registerBgImgUrl)) {
                    extraConfigImgEls[0].src = '../src/bak-img.jpg';
                }
                else {
                    extraConfigImgEls[0].src = data.registerBgImgUrl;
                }
                if (ChatCheck.isEmpty(data.loginBgImgUrl)) {
                    extraConfigImgEls[1].src = '../src/bak-img.jpg';
                }
                else {
                    extraConfigImgEls[1].src = data.loginBgImgUrl;
                }
            }, function () { }, _this.parentFailHandel);
            extraConfigBtnEl.addEventListener('click', function () {
                DelTipsBoxControl.open("确定要进行修改吗？", function () {
                    var extraId = extraConfigIdEl.innerText;
                    var storeNameHaveSpecial = extraConfigSelectEl.innerText == '含有特殊字';
                    var passwordTemplateText = extraConfigInputEls[0].value;
                    if (ChatCheck.check(passwordTemplateText) && passwordTemplateText.length > 2) {
                        MessageControl.open("密码模板只允许一个字符！", MessageType.WARNING);
                        return;
                    }
                    else if (!ChatCheck.check(passwordTemplateText) && passwordTemplateText.length > 1) {
                        MessageControl.open("密码模板只允许一个字符！", MessageType.WARNING);
                        return;
                    }
                    var belowPageText = extraConfigInputEls[1].value;
                    var specialText = extraConfigInputEls[2].value;
                    if (specialText.length > 1) {
                        MessageControl.open("特殊字最多只允许一个字符！", MessageType.WARNING);
                        return;
                    }
                    var storeName = extraConfigInputEls[3].value;
                    var registerBgImgUrl = extraConfigImgEls[0].src;
                    var loginBgImgUrl = extraConfigImgEls[1].src;
                    if (ChatCheck.isEmpty(extraId) ||
                        ChatCheck.isEmpty(passwordTemplateText) ||
                        ChatCheck.isEmpty(belowPageText) ||
                        ChatCheck.isEmpty(specialText) ||
                        ChatCheck.isEmpty(storeName)) {
                        MessageControl.open("必要信息【特殊配置id、密码模板、页脚内容、特殊字，商城名字】不得为空！", MessageType.WARNING);
                        return;
                    }
                    var formData = new FormData();
                    formData.append("extraId", extraId);
                    formData.append("storeNameHaveSpecial", storeNameHaveSpecial + "");
                    formData.append("passwordTemplateText", passwordTemplateText);
                    formData.append("belowPageText", belowPageText);
                    formData.append("specialText", specialText);
                    formData.append("storeName", storeName);
                    if (!SrcToFile.check(registerBgImgUrl) || !SrcToFile.check(loginBgImgUrl)) {
                        MessageControl.open("上传的图片不合格！", MessageType.WARNING);
                        return;
                    }
                    var files = [];
                    SrcToFile.moreSrcToFile([registerBgImgUrl, loginBgImgUrl], files, function () {
                        files.forEach(function (file) {
                            formData.append("files", file);
                        });
                        _this.modifyExtraConfig(formData);
                    }, function () {
                        MessageControl.open("文件上传错误，请检查文件类型！", MessageType.WARNING);
                    });
                });
            });
        };
    };
    ExtraConfigManage.prototype.modifyExtraConfig = function (formData) {
        var _this = this;
        new Ajax(ExtraConfigUrls.fixExtraConfig).config(false).sendFormData(formData).result(function () { _this.responseSuccessRefresh('修改特殊配置成功！', false); }, function () { }, this.parentFailHandel);
    };
    return ExtraConfigManage;
}(ParentMain));
PublicMainView.run(new ExtraConfigManage(8));
