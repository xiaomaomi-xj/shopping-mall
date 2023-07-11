import { req } from "@/utils/req";
//获取额外配置
export function getExtraConfig(){
    return req({
        url:'/extra_config/get_config'
    });
}