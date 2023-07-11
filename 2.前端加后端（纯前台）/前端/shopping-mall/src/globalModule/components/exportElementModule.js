import {
    ElRow,
    ElCol,
    ElAvatar,
    ElBadge,
    ElCarouselItem,
    ElCarousel,
    ElIcon,
    ElContainer,
    ElHeader,
    ElMain,
    ElFooter,
    ElAside
} from "element-plus";
export default {
    install(Vue) {
        //Layout 布局
        Vue.component('ElRow', ElRow);
        Vue.component('ElCol', ElCol);
        //头像
        Vue.component('ElAvatar', ElAvatar);
        //消息数
        Vue.component('ElBadge', ElBadge);
        //轮播图
        Vue.component('ElCarouselItem', ElCarouselItem);
        Vue.component('ElCarousel', ElCarousel);
        //存图标的
        Vue.component('ElIcon', ElIcon);
        //主页
        Vue.component('ElContainer', ElContainer);
        Vue.component('ElHeader', ElHeader);
        Vue.component('ElMain', ElMain);
        Vue.component('ElFooter', ElFooter);
        Vue.component('ElAside', ElAside);
    }
}