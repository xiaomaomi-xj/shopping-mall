import {
    defineStore
} from "pinia";
import {
    register
} from '@/api/user';
import {
    ls
} from '@/utils/ls';
import {
    globals
} from "@/main";
export const useUserRegister = defineStore("userRegister", {
    state: () => {
        return {
            //任务id
            emailCodeTaskId: -1,
        }
    },
    actions: {
        //注册
        register(userName, userEmail, userSex, password, emailKey, emailCode) {
            register({
                userName,
                userEmail,
                userSex,
                password,
                emailKey,
                emailCode
            }).then(res => {
                if (res.data['status']) {
                    globals.$selfMessage.openMessage({
                        type: 'success',
                        message: '注册成功！'
                    });
                    ls.set("token", res.data['token']);
                    ls.set("refreshToken", res.data['refreshToken']);
                    //跳转主页面
                    globals.$router.push({
                        name: 'home'
                    });
                }
            });
        },
        //任务id
        setTaskId(emailCodeTaskId) {
            this.emailCodeTaskId = emailCodeTaskId;
        },
        //清除掉任务id
        clearTaskId() {
            clearInterval(this.emailCodeTaskId);
        }
    }
});