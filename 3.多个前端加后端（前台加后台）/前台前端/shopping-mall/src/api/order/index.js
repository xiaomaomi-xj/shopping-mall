import {
    req
} from "@/utils/req";
const PREFIX_URL = "/order/"
//获取订单数据
export function getOrderData(data) {
    return req({
        url: PREFIX_URL + 'get_order_data',
        data
    });
}
//删除订单数据
export function delOrderData(data){
    return req({
        url: PREFIX_URL + 'del_order_data',
        data
    });
}
//创建单个订单
export function createSingleOrderData(data){
    return req({
        url: PREFIX_URL + 'create_single_order_data',
        data
    });
}
//创建多个订单
export function createMultipleOrderData(data){
    return req({
        url: PREFIX_URL + 'create_multiple_order_data',
        data
    });
}