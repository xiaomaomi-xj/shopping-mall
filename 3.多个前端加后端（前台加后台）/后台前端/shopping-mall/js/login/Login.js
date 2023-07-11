var __spreadArray = (this && this.__spreadArray) || function (to, from, pack) {
    if (pack || arguments.length === 2) for (var i = 0, l = from.length, ar; i < l; i++) {
        if (ar || !(i in from)) {
            if (!ar) ar = Array.prototype.slice.call(from, 0, i);
            ar[i] = from[i];
        }
    }
    return to.concat(ar || Array.prototype.slice.call(from));
};
import Ajax from "../utils/Ajax.js";
import LoadingControl from "../components/LoadingControl.js";
import LocalStorageToken from "../utils/LocalStorageToken.js";
import TipsControl from "../components/TipsControl.js";
import { AdminUrls } from "../api/ApiConstant.js";
import Router from "../utils/Router.js";
import LoginColor from "./LoginColor.js";
import PasswordKeyControl from "../components/PasswordKeyControl.js";
var Login = (function () {
    function Login(formEl, button, adminUserEl, adminPasswordEl, mainEl) {
        this.adminUserText = '';
        this.adminPasswordText = '';
        this.formEl = formEl;
        this.submitEl = button;
        this.adminUserEl = adminUserEl;
        this.adminPasswordEl = adminPasswordEl;
        this.mainEl = mainEl;
    }
    Login.prototype.run = function () {
        TipsControl.mount(this.formEl, this.submitEl);
        LoadingControl.mount(this.mainEl);
        PasswordKeyControl.run();
        this.clickListen();
        this.start();
    };
    Login.prototype.start = function () {
        if (LocalStorageToken.getToken() != null || LocalStorageToken.getToken() != undefined) {
            new Ajax(AdminUrls.checkLoginUrl, false, false).config().sendEmpty().result(this.startSuccessHandel, this.startFailHandel, this.startFailHandel);
        }
    };
    Login.prototype.startSuccessHandel = function (content) {
        if (content.value) {
            Router.go('/');
        }
        else {
            LocalStorageToken.delToken();
            LocalStorageToken.delRefreshToken();
        }
    };
    Login.prototype.startFailHandel = function () {
        LocalStorageToken.delToken();
        LocalStorageToken.delRefreshToken();
    };
    Login.prototype.clickListen = function (eventName) {
        if (eventName === void 0) { eventName = 'click'; }
        this.submitEl.addEventListener(eventName, this.handelClick.bind(this));
    };
    Login.prototype.handelClick = function () {
        this.adminContentHandel();
        if (this.adminUserText.trim().length <= 0 || this.adminPasswordText.trim().length <= 0) {
            TipsControl.show();
        }
        else {
            new Ajax(AdminUrls.loginUrl, false, false).config().sendJson({
                adminUser: this.adminUserText,
                adminPassword: this.adminPasswordText
            }).result(this.successHandel, this.failHandel, this.failHandel);
        }
        this.clearAdminContent();
        loginColor.colorLevel();
    };
    Login.prototype.adminContentHandel = function () {
        this.adminUserText = this.adminUserEl.value;
        this.adminPasswordText = this.adminPasswordEl.value;
    };
    Login.prototype.successHandel = function (content) {
        LocalStorageToken.setToken(content.token);
        LocalStorageToken.setRefreshToken(content.refreshToken);
        Router.go('/');
    };
    Login.prototype.failHandel = function (message) {
        TipsControl.show(message);
    };
    Login.prototype.clearAdminContent = function () {
        this.adminUserEl.value = '';
        this.adminPasswordEl.value = '';
    };
    return Login;
}());
var formEl = document.querySelector('.form');
var submitEl = document.querySelector('button');
var adminUserEl = document.querySelector('input[name="adminUser"]');
var adminPasswordEl = document.querySelector('input[name="adminPassword"]');
var mainEl = document.querySelector('main');
var colorEls = [];
var bgColorEls = [];
var borderColorEls = [];
var h1El = document.querySelector('h1');
var inputEls = document.querySelectorAll('.input-box');
var userIconHeaderEl = document.querySelector('.input-box .user .body');
var userIconBodyEl = document.querySelector('.input-box .user .header .header-before');
var passwordIconHeaderEl = document.querySelector('.input-box .password .header .header-after');
var passwordIconBodyEl = document.querySelector('.input-box .password .body');
colorEls.push(h1El, adminUserEl, adminPasswordEl, submitEl);
bgColorEls.push(userIconBodyEl, userIconHeaderEl, passwordIconBodyEl, passwordIconHeaderEl);
borderColorEls.push.apply(borderColorEls, __spreadArray(__spreadArray([], inputEls, false), [submitEl], false));
var loginColor = new LoginColor(adminUserEl, adminPasswordEl, colorEls, bgColorEls, borderColorEls);
loginColor.run();
new Login(formEl, submitEl, adminUserEl, adminPasswordEl, mainEl).run();
