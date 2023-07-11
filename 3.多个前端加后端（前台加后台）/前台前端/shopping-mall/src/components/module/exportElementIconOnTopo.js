import {
    View,
    Hide,
    Close,
    CloseBold,
    Refresh
} from "@element-plus/icons-vue";
export default {
    //vue.use就会调用install方法
    install(Vue) {
        Vue.component('ViewPassword', View);
        Vue.component('HidePassword', Hide);
        Vue.component('CloseBold', Close);
        Vue.component('CloseBoldMessage', CloseBold);
        Vue.component('RefreshIcon', Refresh);
    }
};