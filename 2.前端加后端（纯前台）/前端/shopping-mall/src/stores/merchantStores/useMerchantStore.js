import {
    defineStore
} from "pinia";
import {
    getAllMerchant
} from "@/api/merchant";
export const useMerchant = defineStore('merchant', {
    state: () => {
        return {
            merchantInfos: [{}]
        }
    },
    actions: {
        //获取全部商户
        getAllMerchantFun() {
            getAllMerchant().then(res => {
                this.merchantInfos = res.data;
            });
        }
    }
});