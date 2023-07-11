import {
    defineStore
} from 'pinia';
import {
    userConfigJson
} from '@/handleCustomConfig/userConfig';
import {
    ls
} from '@/utils/ls';
import {
    token
} from '@/utils/token';
export const useUsers = defineStore("users", {
    state: () => {
        return {
            userConfigDatas: userConfigJson,
            currentIndex: -1,
            currentEmailCode: ''
        };
    },
    actions: {
        //注册
        registerUserData(userConfigData) {
            //测试邮箱验证码
            if (!this.checkEmailCode(userConfigData['userEmailCode'])) {
                return 'email_code_error';
            }
            //邮箱是否重复
            if (this.checkRepeatUserEmail(userConfigData['userEmail'])) {
                return 'email_repeat_error';
            }
            this.userConfigDatas.push(userConfigData);
            //写入本地存储
            this.writeTokenTolocalStorage(userConfigData['userEmail'] + userConfigData['password']);
            return 'ok';
        },
        //获取图片验证码
        getCheckCodeImgData() {
            //纯前端的方式
            this.currentIndex++;
            const data = window.checkCodeDatas[this.currentIndex];
            if (this.currentIndex >= 4) {
                this.currentIndex = -1;
            }
            return data['img'];
        },
        //验证图片验证码
        checkCheckCode(code) {
            if (code === window.checkCodeDatas[this.currentIndex]['code']) {
                return true;
            }
            return false;
        },
        //发送邮箱验证码
        postEmailCode() {
            //纯前端使用
            this.currentEmailCode = Math.floor(Math.random() * (9999 - 1000) + 1000).toString();
            return this.currentEmailCode;
        },
        //验证邮箱验证码是否正确
        checkEmailCode(code) {
            if (this.currentEmailCode === code) {
                return true;
            }
            return false;
        },
        //验证注册的账号是否重复
        checkRepeatUserEmail(userEmail) {
            for (const v of this.userConfigDatas) {
                if (v['userEmail'] === userEmail) {
                    return true;
                }
            }
            return false;
        },
        //注册成功和登录成功，都要写入token
        writeTokenTolocalStorage(content) {
            ls.set('token', token.create(content));
        },
        //验证token
        getTokenTolocalStorageOnCheck() {
            const data = ls.get('token');
            for (const userData of this.userConfigDatas) {
                //两种方式，一种账号密码，一种是账号和验证码
                if (token.create(userData['userEmail'] + userData['password']) === data) {
                    return true;
                }
            }
            return false;
        },
        //根据token获得相应的用户
        getUserDataOnToken() {
            const data = ls.get('token');
            for (const userData of this.userConfigDatas) {
                if (token.create(userData['userEmail'] + userData['password']) === data) {
                    return userData;
                }
            }
            return null;
        },

        //下面就是登陆的函数
        //账号密码登录
        userPasswordOnLogin(user) {
            for (const userData of this.userConfigDatas) {
                if (userData['userEmail'] === user['userEmail']) {
                    if (userData['password'] === user['password']) {
                        this.writeTokenTolocalStorage(userData['userEmail'] + userData['password']);
                        return true;
                    }
                }
            }
            return false;
        },
        //账号验证码登录
        userEmailCodeOnLogin(user) {
            //验证码登录也要确保是注册过的号码
            for (const userData of this.userConfigDatas) {
                if (userData['userEmail'] === user['userEmail']) {
                    if (userData['userEmailCode'] === user['userEmailCode']) {
                        this.writeTokenTolocalStorage(userData['userEmail'] + userData['password']);
                        return true;
                    }
                }
            }
            return false;
        },
        //退出登录
        logOutUser() {
            ls.del("token");
        },

        //下面为操作用户
        //修改用户信息
        modifyUserInfo(userData) {
            this.userConfigDatas.map(v => {
                if (v['userEmail'] === userData['userEmail']) {
                    v['password'] = userData['password']
                    v['userEmailCode'] = userData['userEmailCode']
                    v['userName'] = userData['userName']
                    v['userSex'] = userData['userSex']
                }
            });
        },
        //修改密码，检验当前密码是否准确
        checkUserPassword(userData) {
            for (const data of this.userConfigDatas) {
                if (data['userEmail'] === userData['userEmail']) {
                    if (data['password'] === userData['password']) {
                        return true;
                    }
                }
            }
            return false;
        }
    }
});