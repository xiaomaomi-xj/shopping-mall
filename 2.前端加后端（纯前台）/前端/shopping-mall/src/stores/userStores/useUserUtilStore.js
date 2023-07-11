import {
    defineStore
} from "pinia";
import {
    getCheckId,
    getCheckImg,
    getEmailId,
    sendEmail
} from '@/api/userUtil';
import {
    globals
} from "@/main";
export const useUserUtil = defineStore('userUtil', {
    state: () => {
        return {
            //验证码id和邮箱id
            checkId: 0,
            emailId: 0,
            //错误图片任务id
            errorChackImgTaskId: 0,
            //图片验证码
            checkImg: window.loadingCheckImg
        }
    },
    actions: {
        //获取验证码图片
        getCheckImgUrl() {
            getCheckId().then(res => {
                this.checkId = res.data['value'];
                this.checkImg = getCheckImg(this.checkId);
                //倒计时改变坏的图片
                this.getErrorCheckImgUrl();
            });
        },
        //一但过期就获取错误的图片,私有
        getErrorCheckImgUrl() {
            //刷新就要先清除
            this.clearErrorCheckImgId();
            this.errorChackImgTaskId = setTimeout(() => {
                //十分钟执行
                this.checkImg = getCheckImg('error');
            }, 1000 * 600);
        },
        //清除掉
        clearErrorCheckImgId() {
            clearTimeout(this.errorChackImgTaskId);
        },
        //发送邮箱验证码
        sendEmail(emailAccount, captchaCode) {
            getEmailId({
                emailAccount
            }).then(res => {
                this.emailId = res.data['value'];
                sendEmail({
                    captchaId: this.checkId,
                    captchaCode,
                    emailKey: this.emailId,
                    emailAccount
                }).then(() => {
                    globals.$selfMessage.openMessage({
                        type: 'success',
                        message: '邮箱验证码发送成功，请注意查收!'
                    });
                });
                //发完重新获取验证码图片，防止多次提交
                this.getCheckImgUrl();
            });
        }
    }
});