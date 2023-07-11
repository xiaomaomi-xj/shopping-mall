import {
    req
} from "@/utils/req";
const PREFIX_URL = "/merchant/"
//获取所有商户信息
export function getAllMerchant() {
    return req({
        url: PREFIX_URL + 'get_all_merchant'
    });
}