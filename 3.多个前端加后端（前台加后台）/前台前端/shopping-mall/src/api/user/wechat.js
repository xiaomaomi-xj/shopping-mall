import {
    req
} from "@/utils/req";
const PREFIX_URL = "/wechat/";
//获取登录id
export function getWechatLoginId() {
    return req({
        url: PREFIX_URL + 'get_wechat_login_id'
    });
}
//获取登录二维码，get请求，直接显示，不用异步请求
export function getWechatLoginImg(data) {
    return window.localBaseUrl + PREFIX_URL + 'get_wechat_login_img?login_img_id=' + data;
}
//检验是否登录成功
export function checkLogin(data) {
    return req({
        url: PREFIX_URL + 'check_login',
        data
    });
}
//为西京用户绑定邮箱
export function bindAccountApi(data) {
    return req({
        url: PREFIX_URL + 'bind_account',
        data
    });
}