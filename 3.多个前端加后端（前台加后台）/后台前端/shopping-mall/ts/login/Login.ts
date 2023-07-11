import Ajax from "../utils/Ajax.js";
import LoadingControl from "../components/LoadingControl.js";
import LocalStorageToken from "../utils/LocalStorageToken.js";
import TipsControl from "../components/TipsControl.js";
import { AdminUrls } from "../api/ApiConstant.js";
import Router from "../utils/Router.js";
import LoginColor from "./LoginColor.js";
import { CheckLoginType, LoginType } from "../interface/Types.js";
import PasswordKeyControl from "../components/PasswordKeyControl.js";
class Login {
    formEl: HTMLDivElement;
    submitEl: HTMLButtonElement;
    adminUserEl: HTMLInputElement;
    adminPasswordEl: HTMLInputElement;
    adminUserText: string = '';
    adminPasswordText: string = '';
    mainEl: HTMLElement;
    constructor(formEl: HTMLDivElement, button: HTMLButtonElement, adminUserEl: HTMLInputElement, adminPasswordEl: HTMLInputElement, mainEl: HTMLElement) {
        this.formEl = formEl;
        this.submitEl = button;
        //赋值账号和密码
        this.adminUserEl = adminUserEl;
        this.adminPasswordEl = adminPasswordEl;
        this.mainEl = mainEl;
    }
    //运行
    run() {
        //挂载tips组件
        TipsControl.mount(this.formEl, this.submitEl);
        //挂载loading组件
        LoadingControl.mount(this.mainEl);
        //开启密码框功能
        PasswordKeyControl.run();
        //开启监听
        this.clickListen();
        //验证登录情况
        this.start();
    }
    //开始检测是否需要登录
    private start() {
        if (LocalStorageToken.getToken() != null || LocalStorageToken.getToken() != undefined) {
            new Ajax(AdminUrls.checkLoginUrl, false, false).config().sendEmpty().result(this.startSuccessHandel, this.startFailHandel, this.startFailHandel);
        }
    }
    //验证登录成功处理
    private startSuccessHandel(content: CheckLoginType) {
        if (content.value) {
            Router.go('/');
        } else {
            //验证失败
            LocalStorageToken.delToken();
            LocalStorageToken.delRefreshToken();
        }
    }
    //验证失败就删除token
    private startFailHandel() {
        LocalStorageToken.delToken();
        LocalStorageToken.delRefreshToken();
    }
    //监听事件
    private clickListen(eventName: string = 'click') {
        //因为this变了，需要改变一下this
        this.submitEl.addEventListener(eventName, this.handelClick.bind(this));
    }
    //处理点击事件
    private handelClick() {
        this.adminContentHandel();
        if (this.adminUserText.trim().length <= 0 || this.adminPasswordText.trim().length <= 0) {
            TipsControl.show();
        } else {
            new Ajax(AdminUrls.loginUrl, false, false).config().sendJson({
                adminUser: this.adminUserText,
                adminPassword: this.adminPasswordText
            }).result(this.successHandel, this.failHandel, this.failHandel);
        }
        this.clearAdminContent();
        loginColor.colorLevel();
    }
    //再点击的时候，需要先获取内容
    private adminContentHandel() {
        this.adminUserText = this.adminUserEl.value;
        this.adminPasswordText = this.adminPasswordEl.value;
    }
    //成功响应处理
    private successHandel(content: LoginType) {
        LocalStorageToken.setToken(content.token);
        LocalStorageToken.setRefreshToken(content.refreshToken);
        Router.go('/');
    }
    //失败响应处理
    private failHandel(message: string) {
        TipsControl.show(message);
    }
    //清空内容处理
    private clearAdminContent() {
        this.adminUserEl.value = '';
        this.adminPasswordEl.value = '';
    }
}
const formEl = document.querySelector('.form') as HTMLDivElement;
const submitEl = document.querySelector('button') as HTMLButtonElement;
const adminUserEl = document.querySelector('input[name="adminUser"]') as HTMLInputElement;
const adminPasswordEl = document.querySelector('input[name="adminPassword"]') as HTMLInputElement;
const mainEl = document.querySelector('main') as HTMLElement;
const colorEls = [] as Array<HTMLElement>;
const bgColorEls = [] as Array<HTMLElement>;
const borderColorEls = [] as Array<HTMLElement>;
const h1El = document.querySelector('h1') as HTMLElement;
const inputEls = document.querySelectorAll('.input-box') as unknown as Array<HTMLElement>;
const userIconHeaderEl = document.querySelector('.input-box .user .body') as HTMLElement;
const userIconBodyEl = document.querySelector('.input-box .user .header .header-before') as HTMLElement;
const passwordIconHeaderEl = document.querySelector('.input-box .password .header .header-after') as HTMLElement;
const passwordIconBodyEl = document.querySelector('.input-box .password .body') as HTMLElement;
colorEls.push(h1El, adminUserEl, adminPasswordEl, submitEl);
bgColorEls.push(userIconBodyEl, userIconHeaderEl, passwordIconBodyEl, passwordIconHeaderEl);
borderColorEls.push(...inputEls, submitEl);
const loginColor = new LoginColor(adminUserEl, adminPasswordEl, colorEls, bgColorEls, borderColorEls);
loginColor.run();
new Login(formEl, submitEl, adminUserEl, adminPasswordEl, mainEl).run();