import {
    req
} from "@/utils/req";
const PREFIX_URL = "/token/";
//验证当前用户是否登录
export function checkLogin(data) {
    return req({
        url: PREFIX_URL + 'check_login',
        data
    });
}
//刷新令牌
export function refreshToken(data) {
    return req({
        url: PREFIX_URL + 'refresh_token',
        data
    });
}
//根据token获取用户
export function getUserOnToken(data) {
    return req({
        url: PREFIX_URL + 'get_user_on_token',
        data
    });
}