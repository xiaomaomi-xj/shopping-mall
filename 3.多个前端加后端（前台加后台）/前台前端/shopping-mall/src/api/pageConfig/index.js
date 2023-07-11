import {
    req
} from "@/utils/req";
const PREFIX_URL = "/page_config/"
//获取标题
export function getToggleView() {
    return req({
        url: PREFIX_URL + 'get_toggle_view'
    });
}
//获取轮播图
export function getRotationChart() {
    return req({
        url: PREFIX_URL + 'get_rotation_chart'
    });
}
//获取广告
export function getGoodsAd(){
    return req({
        url: PREFIX_URL + 'get_goods_ad'
    });
}
//获取主要界面数据
export function getContainerModule(){
    return req({
        url: PREFIX_URL + 'get_container_module'
    });
}