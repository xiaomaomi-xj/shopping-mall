import {
    req
} from "@/utils/req";
const PREFIX_URL = "/goods/"
//根据搜索条件得到商品数据
export function getSearchGoods(data) {
    return req({
        url: PREFIX_URL + 'get_search_goods',
        data
    });
}
//根据id获取商品
export function getGoodsById(data) {
    return req({
        url: PREFIX_URL + 'get_goods_by_id',
        data
    });
}