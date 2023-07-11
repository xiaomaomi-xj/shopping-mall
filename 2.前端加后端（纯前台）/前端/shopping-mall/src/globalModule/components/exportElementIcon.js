import {
    Search,
    UserFilled,
    User,
    ShoppingCart,
    GoodsFilled,
    ChatDotSquare,
    ArrowRightBold,
    View,
    Hide,
    Close,
    CloseBold,
    Select,
    Minus,
    Plus
} from "@element-plus/icons-vue";
export default {
    install(Vue) {
        Vue.component('SearchIcon', Search);
        Vue.component('UserFilledIcon', UserFilled);
        Vue.component('UserIcon', User);
        Vue.component('ShoppingCart', ShoppingCart);
        Vue.component('GoodsFilled', GoodsFilled);
        Vue.component('ChatDotSquare', ChatDotSquare);
        Vue.component('ArrowRightBold', ArrowRightBold);
        Vue.component('ViewPassword', View);
        Vue.component('HidePassword', Hide);
        Vue.component('CloseBold', Close);
        Vue.component('CloseIcon', CloseBold);
        Vue.component('SelectIcon', Select);
        Vue.component('MinusIcon', Minus);
        Vue.component('PlusIcon', Plus);
    }
};