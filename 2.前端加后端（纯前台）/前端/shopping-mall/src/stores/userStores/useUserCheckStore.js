import {
    defineStore
} from "pinia";
import {
    checkLogin,
    refreshToken,
    getUserOnToken
} from "@/api/user/token";
import {
    ls
} from "@/utils/ls";
import {
    globals
} from "@/main";
export const useUserCheck = defineStore('userCheck', {
    state: () => {
        return {
            loginStatus: false,
            loginType: '',
            currentUserData: {}
        }
    },
    actions: {
        //验证token是否存在
        checkToken(token, reToken) {
            if (token == null || reToken == null) {
                ls.del('token');
                ls.del('refreshToken');
                this.loginStatus = false;
                this.loginType = '';
                this.currentUserData = [];
                return false;
            }
            return true;
        },
        //拿取用户
        getUserDataOnToken(token) {
            getUserOnToken({
                token
            }).then(res => {
                if (res.data['status']) {
                    this.loginType = res.data['type'];
                    this.currentUserData = res.data['data'];
                }
            });
        },
        //刷新token
        refreshTokenFun(token, reToken) {
            refreshToken({
                token,
                refreshToken: reToken
            }).then(res => {
                if (res.data['status']) {
                    //刷新成功
                    ls.set('token', res.data['token']);
                    ls.set('refreshToken', res.data['refreshToken']);
                    //重新流程验证
                    this.checkLoginUser();
                } else {
                    //刷新失败
                    ls.del('token');
                    ls.del('refreshToken');
                    //刷新状态
                    this.checkToken(null, null);
                    globals.$selfMessageBox.openMessageBox({
                        type: 'tips',
                        tipsMessage: '登录过期，请重新登录！',
                        callBackFun: () => {
                            //跳转到登录页
                            globals.$router.push({
                                name: 'userLogin'
                            });
                        }
                    });
                }
            });
        },
        //验证登录，公共，需导出
        checkLoginUser() {
            let token = ls.get("token");
            let reToken = ls.get("refreshToken");
            //如果不存在，就不用动，为未登录用户
            if (this.checkToken(token, reToken)) {
                //验证
                checkLogin({
                    token
                }).then(res => {
                    if (res.data['value']) {
                        this.loginStatus = true;
                        //拿取用户
                        this.getUserDataOnToken(token);
                    } else {
                        //刷新token
                        this.refreshTokenFun(token, reToken);
                    }
                });
            }
        },
        //刷新token回调
        refreshTokenFunCallBack(token, reToken, successCallBack, failCallBack) {
            refreshToken({
                token,
                refreshToken: reToken
            }).then(res => {
                if (res.data['status']) {
                    //刷新成功
                    ls.set('token', res.data['token']);
                    ls.set('refreshToken', res.data['refreshToken']);
                    //重新流程验证
                    this.checkLoginUserCallBack(successCallBack, failCallBack);
                } else {
                    //刷新失败
                    ls.del('token');
                    ls.del('refreshToken');
                    this.checkLoginUserCallBack(successCallBack, failCallBack);
                }
            });
        },
        //验证登录回调，公共，需导出
        checkLoginUserCallBack(successCallBack, failCallBack) {
            let token = ls.get("token");
            let reToken = ls.get("refreshToken");
            //如果不存在，就不用动，为未登录用户
            if (this.checkToken(token, reToken)) {
                //验证
                checkLogin({
                    token
                }).then(res => {
                    if (res.data['value']) {
                        this.loginStatus = true;
                        //拿取用户
                        this.getUserDataOnToken(token);
                        successCallBack();
                    } else {
                        //刷新token
                        this.refreshTokenFunCallBack(token, reToken, successCallBack, failCallBack);
                    }
                });
            } else {
                failCallBack();
            }
        },
        //退出登录，公共，需导出
        logOutUser() {
            ls.del("token");
            ls.del("refreshToken");
            //清掉状态
            this.checkToken(null, null);
            //跳转登录页
            globals.$router.push({
                name: 'userLogin'
            });
        }
    }
});