import Router from "../utils/Router.js";
import Ajax from "../utils/Ajax.js";
import { AdminUrls } from "../api/ApiConstant.js";
import LocalStorageToken from "../utils/LocalStorageToken.js";
import { CheckLoginType } from "../interface/Types";
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

const TITLE_LIST = [
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
let htmlTemplate = `
<div class="custom-component modify-password-custom-component">
    <div class="modify-password-custom-component-title">
        修改密码
    </div>
    <div class="input-text-box">
        <div class="text">请输入旧密码：</div>
        <div class="input-box">
            <div class="password">
                <div class="header">
                    <div class="header-before"></div>
                    <div class="header-after"></div>
                </div>
                <div class="body"></div>
            </div>
            <input type="password" name="oldAdminPassword">
        </div>
    </div>
    <div class="input-text-box">
        <div class="text">请输入新密码：</div>
        <div class="input-box">
            <div class="password">
                <div class="header">
                    <div class="header-before"></div>
                    <div class="header-after"></div>
                </div>
                <div class="body"></div>
            </div>
            <input type="password" name="newAdminPassword">
        </div>
    </div>
    <button class="modify-password-custom-component-button">修改</button>
</div>
<div class="main-public-main">
    <div class="main-public-header">
        <div class="main-public-header-left">后台管理系统</div>
        <div class="main-public-header-right">
            <div class="main-public-header-right-info">
                <div class="main-public-header-right-info-icon">
                    <div class="main-public-header-right-info-icon-head"></div>
                    <div class="main-public-header-right-info-icon-body"></div>
                </div>
                <span>超级管理员(修改密码)</span>
            </div>
            <div class="main-public-header-right-exit">
                <div class="main-public-header-right-exit-icon">
                    <div class="main-public-header-right-exit-icon-outside">
                        <div class="main-public-header-right-exit-icon-inner"></div>
                    </div>
                </div>
                <span>退出</span>
            </div>
        </div>
    </div>
    <div class="main-public-body">
        <div class="main-public-left-body">
            <div class="main-public-left-body-title">
            #{titleStatus}
            </div>
        </div>
        <div class="main-public-right-body">
            <div class="main-public-right-body-box">
                <div class="main-public-right-body-box-title">
                    <div class="main-public-right-body-box-title-mark"></div>
                    #{titleContent}
                </div>
                <div class="main-public-right-body-box-body">
                    #{sonHtml}
                </div>
            </div>
        </div>
    </div>
</div>
`;

//此接口用于规范所有主页功能
interface PublicMain {
    //获取你是第几个页面
    getIndex: () => number,
    //指定你的主元素
    getMainEl: () => HTMLElement,
    //你要执行的事件
    getEvent: () => Function
}

//此类处理公共页面
class PublicMainView {
    //因为要放到body上，所以直接写死了
    private static bodyEl = document.body;
    private static createHtml(index = 0): string {
        let titleHtml = '';
        TITLE_LIST.map((v, k) => {
            if (k == index) {
                titleHtml += `<div class="main-public-left-body-title-span check">${v.title}</div>`;
                htmlTemplate = htmlTemplate.replace("#{titleContent}", v.title);
            } else {
                titleHtml += `<div class="main-public-left-body-title-span">${v.title}</div>`;
            }
        });
        return htmlTemplate.replace("#{titleStatus}", titleHtml);
    }
    private static merge(sonEl: HTMLElement, index = 0): string {
        const sonHtml = sonEl.outerHTML;
        let html = this.createHtml(index);
        return html.replace("#{sonHtml}", sonHtml);
    }
    public static run(publicMain: PublicMain) {
        const html = this.merge(publicMain.getMainEl(), publicMain.getIndex());
        this.bodyEl.innerHTML = html;
        this.startEvent(publicMain.getEvent());
    }
    //事件执行，要注意事件顺序，防止拿不到元素
    private static startEvent(callbackFun: Function) {
        window.onload = () => {
            //自己本身的公共事件
            this.publicEvenet();
            //传过来的事件
            callbackFun();
        }
    }
    //自己本身带的事件，通用公共事件
    private static publicEvenet() {
        //修改密码组件,组件和清空方法(这里先执行是为了标签消失，能存到这个变量中)
        const modifyPasswordEvents = this.modifyPasswordEvent();
        //个人信息
        const infoEl = document.querySelector('.main-public-header-right-info') as HTMLElement;
        infoEl.addEventListener('click', () => {
            CustomComponentBoxControl.open(modifyPasswordEvents[0] as HTMLElement, modifyPasswordEvents[1] as Function);
        });
        //退出
        const exitEl = document.querySelector('.main-public-header-right-exit') as HTMLElement;
        exitEl.addEventListener("click", () => {
            MessageBoxControl.open("确定要退出吗？", this.failHandel);
        });
        //自检
        this.autoCheckEvent();
        //页面跳转
        const toUrlBthEls = document.querySelectorAll('.main-public-left-body-title-span');
        toUrlBthEls.forEach((toUrlBthEl, index) => {
            toUrlBthEl.addEventListener('click', () => {
                Router.go(TITLE_LIST[index].path);
            });
        });
        //因为相当于外层在这，所以先把需要的组件进行挂载
        const mainPublicEl = document.querySelector('.main-public-main') as HTMLElement;
        MessageControl.mount(mainPublicEl);
        MessageBoxControl.mount(mainPublicEl);
        LoadingControl.mount(mainPublicEl);
        CustomComponentBoxControl.mount(mainPublicEl);
        DelTipsBoxControl.mount(mainPublicEl);
        ImgComponentBoxControl.mount(mainPublicEl);
    }
    //公共自检方法,检测是否可以显示主页
    private static autoCheckEvent() {
        if (LocalStorageToken.getToken() == null || LocalStorageToken.getToken() == undefined) {
            //不存在，登录
            this.failHandel();
        }
        new Ajax(AdminUrls.checkLoginUrl).config().sendEmpty().result(this.successHandel, this.failHandel, this.failHandel);
    }
    //验证登录成功
    private static successHandel(data: CheckLoginType) {
        if (!data.value) {
            //失败处理，成功不处理
            this.failHandel();
        }
    }
    //验证登录错误
    private static failHandel() {
        LocalStorageToken.delToken();
        LocalStorageToken.delRefreshToken();
        Router.go('/login.html');
    }
    //处理密码组件事件
    private static modifyPasswordEvent(): Array<HTMLElement | Function> {
        //修改密码自定义组件
        const modifyPasswordCustomComponentEl = document.querySelector('.modify-password-custom-component') as HTMLElement;
        const oldPasswordEl = document.querySelector('input[name="oldAdminPassword"]') as HTMLInputElement;
        const newPasswordEl = document.querySelector('input[name="newAdminPassword"]') as HTMLInputElement;
        const submitEl = document.querySelector('.modify-password-custom-component-button') as HTMLElement;
        TipsControl.mount(modifyPasswordCustomComponentEl, submitEl);
        submitEl.addEventListener('click', () => {
            if (oldPasswordEl.value.trim().length <= 0 || newPasswordEl.value.trim().length <= 0) {
                TipsControl.show('旧密码或新密码不得为空！');
            } else {
                new Ajax(AdminUrls.modifyPasswordUrl).config().sendJson({
                    oldPassword: oldPasswordEl.value,
                    newPassword: newPasswordEl.value
                }).result(
                    //修改成功也要重新登录
                    ()=>{
                        CustomComponentBoxControl.close();
                        this.failHandel();
                    },
                    () => { },
                    this.failHandel
                );
            }
            //不论怎么样先清空
            oldPasswordEl.value = '';
            newPasswordEl.value = '';
        });
        //对密码框开启显示和隐藏控制
        PasswordKeyControl.run();
        //对下拉框控制
        SelectComponentControl.run();
        //对按钮进行控制
        UploadImgComponentControl.run();
        return [modifyPasswordCustomComponentEl, () => {
            oldPasswordEl.value = '';
            newPasswordEl.value = '';
        }];
    }
}
export {
    PublicMain,
    PublicMainView
};