import {
    defineStore
} from 'pinia';
import {
    goodsAdData
} from '@/handleCustomConfig/pageConfig';
export const useGoodsAd = defineStore("goodsAd", {
    state: () => {
        return {
            goodsDatas:goodsAdData['goodsDatas']
        };
    },
    actions: {
        initGoodsDatas() {
           
        }
    }
});