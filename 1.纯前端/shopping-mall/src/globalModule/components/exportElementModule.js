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
        Vue.component('ElRow', ElRow);
        Vue.component('ElCol', ElCol);
        Vue.component('ElAvatar', ElAvatar);
        Vue.component('ElBadge', ElBadge);
        Vue.component('ElCarouselItem', ElCarouselItem);
        Vue.component('ElCarousel', ElCarousel);
        Vue.component('ElIcon', ElIcon);
        Vue.component('ElContainer', ElContainer);
        Vue.component('ElHeader', ElHeader);
        Vue.component('ElMain', ElMain);
        Vue.component('ElFooter', ElFooter);
        Vue.component('ElAside', ElAside);
    }
}