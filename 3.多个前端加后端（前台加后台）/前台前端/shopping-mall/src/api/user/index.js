import {
    req
} from "@/utils/req";
const PREFIX_URL = "/user/";
//用户注册
export function register(data) {
    return req({
        url: PREFIX_URL + 'register',
        data
    });
}
//账号密码登录
export function loginOnEmailAndPassword(data) {
    return req({
        url: PREFIX_URL + 'login_email_password',
        data
    });
}
//邮箱验证码登录
export function loginOnEmailCode(data) {
    return req({
        url: PREFIX_URL + 'login_email_code',
        data
    });
}
//修改密码
export function modifyPassword(data) {
    return req({
        url: PREFIX_URL + 'modify_password',
        data
    });
}
//修改当前用户信息
export function modifyUserInfo(data) {
    return req({
        url: PREFIX_URL + 'modify_user_info',
        data
    });
}