import Router from "../utils/Router.js";
import Ajax from "../utils/Ajax.js";
import { AdminUrls } from "../api/ApiConstant.js";
import LocalStorageToken from "../utils/LocalStorageToken.js";
import { MessageBoxControl } from "./MessageBoxControl.js";
import { MessageControl } from "./MessageControl.js";
import LoadingControl from "./LoadingControl.js";
import CustomComponentBoxControl from "./CustomComponentBoxControl.js";
import TipsControl from "./TipsControl.js";
import PasswordKeyControl from "./PasswordKeyControl.js";
import SelectComponentControl from "./SelectComponentControl.js";
import { DelTipsBoxControl } from "./DelTipsBoxControl.js";
import { UploadImgComponentControl } from "./UploadImgComponentControl.js";
import { ImgComponentBoxControl } from "./ImgComponentBoxControl.js";
var TITLE_LIST = [
    { path: "/", title: "首页" },
    { path: "/manager/productManage.html", title: "商品管理" },
    { path: "/manager/chatManage.html", title: "聊天管理" },
    { path: "/manager/pageManage.html", title: "界面管理" },
    { path: "/manager/userManage.html", title: "用户管理" },
    { path: "/manager/orderManage.html", title: "订单管理" },
    { path: "/manager/merchantManage.html", title: "商户管理" },
    { path: "/manager/vipCardManage.html", title: "购物卡管理" },
    { path: "/manager/extraConfigManage.html", title: "特殊配置管理" }
];
var htmlTemplate = "\n<div class=\"custom-component modify-password-custom-component\">\n    <div class=\"modify-password-custom-component-title\">\n        \u4FEE\u6539\u5BC6\u7801\n    </div>\n    <div class=\"input-text-box\">\n        <div class=\"text\">\u8BF7\u8F93\u5165\u65E7\u5BC6\u7801\uFF1A</div>\n        <div class=\"input-box\">\n            <div class=\"password\">\n                <div class=\"header\">\n                    <div class=\"header-before\"></div>\n                    <div class=\"header-after\"></div>\n                </div>\n                <div class=\"body\"></div>\n            </div>\n            <input type=\"password\" name=\"oldAdminPassword\">\n        </div>\n    </div>\n    <div class=\"input-text-box\">\n        <div class=\"text\">\u8BF7\u8F93\u5165\u65B0\u5BC6\u7801\uFF1A</div>\n        <div class=\"input-box\">\n            <div class=\"password\">\n                <div class=\"header\">\n                    <div class=\"header-before\"></div>\n                    <div class=\"header-after\"></div>\n                </div>\n                <div class=\"body\"></div>\n            </div>\n            <input type=\"password\" name=\"newAdminPassword\">\n        </div>\n    </div>\n    <button class=\"modify-password-custom-component-button\">\u4FEE\u6539</button>\n</div>\n<div class=\"main-public-main\">\n    <div class=\"main-public-header\">\n        <div class=\"main-public-header-left\">\u540E\u53F0\u7BA1\u7406\u7CFB\u7EDF</div>\n        <div class=\"main-public-header-right\">\n            <div class=\"main-public-header-right-info\">\n                <div class=\"main-public-header-right-info-icon\">\n                    <div class=\"main-public-header-right-info-icon-head\"></div>\n                    <div class=\"main-public-header-right-info-icon-body\"></div>\n                </div>\n                <span>\u8D85\u7EA7\u7BA1\u7406\u5458(\u4FEE\u6539\u5BC6\u7801)</span>\n            </div>\n            <div class=\"main-public-header-right-exit\">\n                <div class=\"main-public-header-right-exit-icon\">\n                    <div class=\"main-public-header-right-exit-icon-outside\">\n                        <div class=\"main-public-header-right-exit-icon-inner\"></div>\n                    </div>\n                </div>\n                <span>\u9000\u51FA</span>\n            </div>\n        </div>\n    </div>\n    <div class=\"main-public-body\">\n        <div class=\"main-public-left-body\">\n            <div class=\"main-public-left-body-title\">\n            #{titleStatus}\n            </div>\n        </div>\n        <div class=\"main-public-right-body\">\n            <div class=\"main-public-right-body-box\">\n                <div class=\"main-public-right-body-box-title\">\n                    <div class=\"main-public-right-body-box-title-mark\"></div>\n                    #{titleContent}\n                </div>\n                <div class=\"main-public-right-body-box-body\">\n                    #{sonHtml}\n                </div>\n            </div>\n        </div>\n    </div>\n</div>\n";
var PublicMainView = (function () {
    function PublicMainView() {
    }
    PublicMainView.createHtml = function (index) {
        if (index === void 0) { index = 0; }
        var titleHtml = '';
        TITLE_LIST.map(function (v, k) {
            if (k == index) {
                titleHtml += "<div class=\"main-public-left-body-title-span check\">".concat(v.title, "</div>");
                htmlTemplate = htmlTemplate.replace("#{titleContent}", v.title);
            }
            else {
                titleHtml += "<div class=\"main-public-left-body-title-span\">".concat(v.title, "</div>");
            }
        });
        return htmlTemplate.replace("#{titleStatus}", titleHtml);
    };
    PublicMainView.merge = function (sonEl, index) {
        if (index === void 0) { index = 0; }
        var sonHtml = sonEl.outerHTML;
        var html = this.createHtml(index);
        return html.replace("#{sonHtml}", sonHtml);
    };
    PublicMainView.run = function (publicMain) {
        var html = this.merge(publicMain.getMainEl(), publicMain.getIndex());
        this.bodyEl.innerHTML = html;
        this.startEvent(publicMain.getEvent());
    };
    PublicMainView.startEvent = function (callbackFun) {
        var _this = this;
        window.onload = function () {
            _this.publicEvenet();
            callbackFun();
        };
    };
    PublicMainView.publicEvenet = function () {
        var _this = this;
        var modifyPasswordEvents = this.modifyPasswordEvent();
        var infoEl = document.querySelector('.main-public-header-right-info');
        infoEl.addEventListener('click', function () {
            CustomComponentBoxControl.open(modifyPasswordEvents[0], modifyPasswordEvents[1]);
        });
        var exitEl = document.querySelector('.main-public-header-right-exit');
        exitEl.addEventListener("click", function () {
            MessageBoxControl.open("确定要退出吗？", _this.failHandel);
        });
        this.autoCheckEvent();
        var toUrlBthEls = document.querySelectorAll('.main-public-left-body-title-span');
        toUrlBthEls.forEach(function (toUrlBthEl, index) {
            toUrlBthEl.addEventListener('click', function () {
                Router.go(TITLE_LIST[index].path);
            });
        });
        var mainPublicEl = document.querySelector('.main-public-main');
        MessageControl.mount(mainPublicEl);
        MessageBoxControl.mount(mainPublicEl);
        LoadingControl.mount(mainPublicEl);
        CustomComponentBoxControl.mount(mainPublicEl);
        DelTipsBoxControl.mount(mainPublicEl);
        ImgComponentBoxControl.mount(mainPublicEl);
    };
    PublicMainView.autoCheckEvent = function () {
        if (LocalStorageToken.getToken() == null || LocalStorageToken.getToken() == undefined) {
            this.failHandel();
        }
        new Ajax(AdminUrls.checkLoginUrl).config().sendEmpty().result(this.successHandel, this.failHandel, this.failHandel);
    };
    PublicMainView.successHandel = function (data) {
        if (!data.value) {
            this.failHandel();
        }
    };
    PublicMainView.failHandel = function () {
        LocalStorageToken.delToken();
        LocalStorageToken.delRefreshToken();
        Router.go('/login.html');
    };
    PublicMainView.modifyPasswordEvent = function () {
        var _this = this;
        var modifyPasswordCustomComponentEl = document.querySelector('.modify-password-custom-component');
        var oldPasswordEl = document.querySelector('input[name="oldAdminPassword"]');
        var newPasswordEl = document.querySelector('input[name="newAdminPassword"]');
        var submitEl = document.querySelector('.modify-password-custom-component-button');
        TipsControl.mount(modifyPasswordCustomComponentEl, submitEl);
        submitEl.addEventListener('click', function () {
            if (oldPasswordEl.value.trim().length <= 0 || newPasswordEl.value.trim().length <= 0) {
                TipsControl.show('旧密码或新密码不得为空！');
            }
            else {
                new Ajax(AdminUrls.modifyPasswordUrl).config().sendJson({
                    oldPassword: oldPasswordEl.value,
                    newPassword: newPasswordEl.value
                }).result(function () {
                    CustomComponentBoxControl.close();
                    _this.failHandel();
                }, function () { }, _this.failHandel);
            }
            oldPasswordEl.value = '';
            newPasswordEl.value = '';
        });
        PasswordKeyControl.run();
        SelectComponentControl.run();
        UploadImgComponentControl.run();
        return [modifyPasswordCustomComponentEl, function () {
                oldPasswordEl.value = '';
                newPasswordEl.value = '';
            }];
    };
    PublicMainView.bodyEl = document.body;
    return PublicMainView;
}());
export { PublicMainView };
