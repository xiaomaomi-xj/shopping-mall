import {
    req
} from "@/utils/req";
const PREFIX_URL = "/shop_cart/"
//添加商品到购物车
export function addShopCartData(data) {
    return req({
        url: PREFIX_URL + 'add_shop_cart_data',
        data
    });
}
//发送聊天记录
export function getShopCartData(data) {
    return req({
        url: PREFIX_URL + 'get_shop_cart_data',
        data
    });
}
//对聊天消息变为已读处理
export function delShopCartData(data) {
    return req({
        url: PREFIX_URL + 'del_shop_cart_data',
        data
    });
}