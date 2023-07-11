import { AdminUrls, ChatBoosUrls, UserUrls } from "./ApiConstant.js";
//白名单（那些链接不需要开启Loading，那些链接不需要开启message-box）
const loadingWhiteList = [
    UserUrls.allUserDataUrl,
    UserUrls.allWechatDataUrl,
    ChatBoosUrls.allDataUrl,
    ChatBoosUrls.readChatBoosUrl,
    AdminUrls.checkLoginUrl
] as Array<string>;
export {
    loadingWhiteList
};