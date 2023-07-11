import {
    defineStore
} from 'pinia';
import {
    getExtraConfig
} from '@/api/extraConfig';
export const useSpecialConfig = defineStore("specialConfig", {
    state: () => {
        return {
            extraConfig: {
                //先给背景一个默认的，因为是异步的
                registerBgImgUrl: window.registerBgImgUrl,
                loginBgImgUrl: window.loginBgImgUrl
            }
        };
    },
    actions: {
        initExtraConfig() {
            //初始化，注意的是，每个完整页面，请求一次就行了
            getExtraConfig().then(res => {
                this.extraConfig = res.data;
            });
        }
    }
});