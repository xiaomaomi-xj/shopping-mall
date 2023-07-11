import {
    req
} from "@/utils/req";
const PREFIX_URL = "/user_util/";
//获取图片验证码id
export function getCheckId() {
    return req({
        url: PREFIX_URL + 'get_check_id'
    });
}
//获取图片验证码,get请求，直接显示，不用异步请求
export function getCheckImg(checkId) {
    return window.localBaseUrl + PREFIX_URL + "get_check_img" + "?code_id=" + checkId;
}
//获取邮箱验证码id
export function getEmailId(data) {
    return req({
        url: PREFIX_URL + "get_email_key",
        data
    });
}
//发送邮箱验证码
export function sendEmail(data) {
    return req({
        url: PREFIX_URL + "send_email",
        data
    });
}