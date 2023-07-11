import axios from 'axios';
import {
    globals
} from '@/main';
import {
    ls
} from './ls';
//这些是不会产生messageBox的白名单(对于获取支付链接跳转已经加了，这里就不要加messageBox，也不要关闭messageBox)
const WHITE_LIST = [
    '/wechat/check_login',
    '/chat_boos/get_chat_boos_data',
    '/user/modify_password',
    '/token/check_login',
    '/token/refresh_token',
    '/token/get_user_on_token',
    '/order/create_single_order_data',
    '/order/create_multiple_order_data'
];
const service = axios.create({
    baseURL: getBaseUrl(),
    timeout: 300000,
    headers: {
        "Content-Type": "application/json"
    },
    //全是post请求
    method: 'post'
});
//获取配置的本地IP地址
function getBaseUrl() {
    return window.localBaseUrl;
}
//请求拦截器，在请求或响应被 then 或 catch 处理前拦截它们。
service.interceptors.request.use(
    config => {
        //处理请求，在每次请求开始，都加一层messageBox
        if (WHITE_LIST.indexOf(config.url) == -1) {
            globals.$selfMessageBox.openMessageBox({
                type: 'loading'
            });
        }
        return config;
    },
    error => {
        //处理请求错误
        return Promise.reject(error);
    }
);
//响应拦截器
service.interceptors.response.use(
    response => {
        const res = response.data;
        //响应了关闭messageBox
        if (WHITE_LIST.indexOf(response.config.url) == -1) {
            globals.$selfMessageBox.closeMesssageBox();
        }
        //对于有的错误后端已处理，需要弹窗来提示用户
        if (res['errStatus'] !== undefined) {
            //对于一些biz错误做一些提示
            if (res['errStatus'] === 400) {
                globals.$selfMessage.openMessage({
                    type: 'error',
                    message: res['errMessage']
                });
                return Promise.reject(res['errMessage']);
            }
            //对于一些重要错误直接退出登录
            if (res['errStatus'] === 403) {
                globals.$selfMessageBox.openMessageBox({
                    type: 'tips',
                    tipsMessage: res['errMessage'],
                    callBackFun: () => {
                        //跳转到登录页
                        globals.$router.push({
                            name: 'userLogin'
                        });
                    }
                });
                //清除token,如果点取消，也会没有token
                ls.del("token");
                ls.del("refreshToken");
                return Promise.reject(res['errMessage']);
            }
        }
        return response;
    },
    error => {
        //也要先关闭
        globals.$selfMessageBox.closeMesssageBox();
        //处理错误响应,就是服务端报错了，就会来这里
        globals.$selfMessageBox.openMessageBox({
            type: 'tips',
            tipsMessage: '系统错误，请联系管理员！',
            callBackFun: () => {
                //跳转到登录页
                globals.$router.push({
                    name: 'userLogin'
                });
            }
        });
        //清除token,如果点取消，也会没有token
        ls.del("token");
        ls.del("refreshToken");
        return Promise.reject(error)
    }
)
export {
    service as req
};