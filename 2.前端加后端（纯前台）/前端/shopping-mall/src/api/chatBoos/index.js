import {
    req
} from "@/utils/req";
const PREFIX_URL = "/chat_boos/"
//获取聊天记录
export function getChatBoosData(data) {
    return req({
        url: PREFIX_URL + 'get_chat_boos_data',
        data
    });
}
//发送聊天记录
export function sendChatBoosData(data) {
    return req({
        url: PREFIX_URL + 'send_chat_boos_data',
        data
    });
}
//对聊天消息变为已读处理
export function readChatBoosData(data) {
    return req({
        url: PREFIX_URL + 'read_chat_boos_data',
        data
    });
}