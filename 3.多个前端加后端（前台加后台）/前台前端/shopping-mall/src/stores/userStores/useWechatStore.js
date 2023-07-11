import {
    defineStore
} from "pinia";
import {
    getWechatLoginId,
    getWechatLoginImg,
    checkLogin
} from "@/api/user/wechat";
import {
    ls
} from "@/utils/ls";
import {
    globals
} from "@/main";
export const useWechat = defineStore('wechat', {
    state: () => {
        return {
            //登录二维码id
            loginImgId: 0,
            //错误图片任务id
            errorWechatImgTaskId: 0,
            //二维码图片
            qrCodeImg: window.qrCodeImgUrl
        }
    },
    actions: {
        //获取二维码
        getWechatLoginImgUrl() {
            getWechatLoginId().then(res => {
                this.loginImgId = res.data['value'];
                this.qrCodeImg = getWechatLoginImg(this.loginImgId);
                //过期获取错误图片
                this.getErrorWechatLoginImgUrl();
            });
        },
        //过期获取错误的图片，私有
        getErrorWechatLoginImgUrl() {
            //刷新就要先清除
            this.clearErrorWechatLoginImgId();
            this.errorWechatImgTaskId = setTimeout(() => {
                //十分钟执行
                this.qrCodeImg = getWechatLoginImg('error');
            }, 1000 * 600);
        },
        //清除任务id
        clearErrorWechatLoginImgId() {
            clearTimeout(this.errorWechatImgTaskId);
        },
        //验证是否已经对扫码授权
        pollingCheckLogin() {
            checkLogin({
                id: this.loginImgId
            }).then(res => {
                if (res.data['status']) {
                    globals.$selfMessage.openMessage({
                        type: 'success',
                        message: '登陆成功！'
                    });
                    ls.set("token", res.data['token']);
                    ls.set("refreshToken", res.data['refreshToken']);
                    //跳转主页面
                    globals.$router.push({
                        name: 'home'
                    });
                }
            });
        }
    }
});