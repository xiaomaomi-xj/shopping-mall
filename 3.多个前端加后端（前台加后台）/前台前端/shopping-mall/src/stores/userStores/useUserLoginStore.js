import {
    defineStore
} from "pinia";
import {
    loginOnEmailCode,
    loginOnEmailAndPassword
} from "@/api/user";
import {
    ls
} from "@/utils/ls";
import {
    globals
} from "@/main";
export const useUserLogin = defineStore("userLogin", {
    state: () => {
        return {
            //邮箱任务id和二维码任务id
            emailCodeTaskId: -1,
            scanCodeTaskId: -1
        };
    },
    actions: {
        //普通登录
        loginOnEmailAndPasswordFun(userEmail, password) {
            loginOnEmailAndPassword({
                userEmail,
                password
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
                } else {
                    globals.$selfMessage.openMessage({
                        type: 'error',
                        message: '账号或密码错误！'
                    });
                }
            });
        },
        //验证码登录
        loginOnEmailCodeFun(userEmail, emailKey, emailCode) {
            loginOnEmailCode({
                userEmail,
                emailKey,
                emailCode
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
                } else {
                    globals.$selfMessage.openMessage({
                        type: 'error',
                        message: '账号或密码错误！'
                    });
                }
            });
        },
        //设置邮箱任务id
        setEmailCodeTaskId(emailCodeTaskId) {
            this.emailCodeTaskId = emailCodeTaskId;
        },
        //设置二维码任务id
        setScanCodeTaskId(scanCodeTaskId) {
            this.scanCodeTaskId = scanCodeTaskId;
        },
        //清除任务id
        clearTaskId() {
            clearInterval(this.emailCodeTaskId);
            clearInterval(this.scanCodeTaskId);
        }
    }
});