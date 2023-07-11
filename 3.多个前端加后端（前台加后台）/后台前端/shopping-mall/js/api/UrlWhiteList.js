import { AdminUrls, ChatBoosUrls, UserUrls } from "./ApiConstant.js";
var loadingWhiteList = [
    UserUrls.allUserDataUrl,
    UserUrls.allWechatDataUrl,
    ChatBoosUrls.allDataUrl,
    ChatBoosUrls.readChatBoosUrl,
    AdminUrls.checkLoginUrl
];
export { loadingWhiteList };
