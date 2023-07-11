import LocalStorageToken from "../utils/LocalStorageToken.js";
import { MessageControl, MessageType } from "../components/MessageControl.js";
import CustomComponentBoxControl from "../components/CustomComponentBoxControl.js";
import Router from "../utils/Router.js";
var ParentMain = (function () {
    function ParentMain(index) {
        if (index === void 0) { index = 0; }
        var mainEl = document.querySelector('main');
        this.mainEl = mainEl;
        this.index = index;
        this.inputIndex = -1;
        this.inputValue = '';
        var iconLinkEl = document.createElement('link');
        iconLinkEl.setAttribute('rel', 'icon');
        iconLinkEl.setAttribute('href', '../src/favicon.ico');
        document.head.appendChild(iconLinkEl);
    }
    ParentMain.prototype.setInputIndex = function (index) {
        this.inputIndex = index;
    };
    ParentMain.prototype.getInputIndex = function () {
        return this.inputIndex;
    };
    ParentMain.prototype.setInputValue = function (value) {
        this.inputValue = value;
    };
    ParentMain.prototype.getInputValue = function () {
        return this.inputValue;
    };
    ParentMain.prototype.getMainEl = function () {
        return this.mainEl;
    };
    ParentMain.prototype.getIndex = function () {
        return this.index;
    };
    ParentMain.prototype.parentFailHandel = function () {
        LocalStorageToken.delToken();
        LocalStorageToken.delRefreshToken();
        Router.go('/login.html');
    };
    ParentMain.prototype.responseSuccessRefresh = function (message, isClose) {
        if (isClose === void 0) { isClose = true; }
        MessageControl.open(message, MessageType.SUCCESS);
        if (isClose) {
            CustomComponentBoxControl.close();
        }
        setTimeout(function () {
            Router.refresh();
        }, 500);
    };
    return ParentMain;
}());
export default ParentMain;
