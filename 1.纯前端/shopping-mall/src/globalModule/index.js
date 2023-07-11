import exportElementModule from "./components/exportElementModule"
import exportElementIcon from "./components/exportElementIcon"
import selfModule from "./components/selfModule";
import wechatTitle from "@/components/wechat-title";
export default {
    install(Vue) {
        Vue.use(exportElementModule);
        Vue.use(exportElementIcon);
        Vue.use(selfModule);
        Vue.use(wechatTitle);
    }
}