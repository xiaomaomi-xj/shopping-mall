import {
    defineStore
} from "pinia";
import {
    ls
} from "@/utils/ls";
import {
    globals
} from "@/main";
import {
    bindAccountApi
} from "@/api/user/wechat";
import {
    modifyPassword,
    modifyUserInfo
} from "@/api/user";
export const useUserModify = defineStore('userModify', {
    state: () => {
        return {

        }
    },
    actions: {
        //微信用户绑定邮箱
        bindAccount(userEmail, password, callBackFun) {
            let token = ls.get('token');
            bindAccountApi({
                userEmail,
                password,
                token
            }).then(res => {
                //刷新token
                if (res.data['status']) {
                    ls.set("token", res.data['token']);
                    ls.set("refreshToken", res.data['refreshToken']);
                    globals.$selfMessage.openMessage({
                        type: 'success',
                        message: '邮箱绑定成功！'
                    });
                    globals.$router.go(0);
                }
            }).finally(() => {
                //无论如何先关闭抽屉弹窗
                callBackFun();
            });
        },
        //修改当前用户密码
        modifyPasswordFun(oldPassword, newPassword) {
            let token = ls.get('token');
            modifyPassword({
                token,
                oldPassword,
                newPassword
            }).then(res => {
                ls.del("token", res.data['token']);
                ls.del("refreshToken", res.data['refreshToken']);
                globals.$selfMessage.openMessage({
                    type: 'success',
                    message: '密码修改成功，请重新登陆！'
                });
                //跳转登录页
                globals.$router.push({
                    name: 'userLogin'
                });
            });
        },
        //修改当前用户信息
        modifyUserInfoFun(imgData, userEmail, userName, userSex, callBackFun) {
            let token = ls.get('token');
            modifyUserInfo({
                token,
                imgData,
                userEmail,
                userName,
                userSex
            }).then(res => {
                callBackFun();
                if (res.data['status']) {
                    ls.set("token", res.data['token']);
                    ls.set("refreshToken", res.data['refreshToken']);
                    globals.$selfMessage.openMessage({
                        type: 'success',
                        message: '个人用户信息修改成功！'
                    });
                    globals.$router.go(0);
                }
            }).catch(() => {
                //无论如何先关闭抽屉弹窗
                callBackFun();
            });
        }
    }
});