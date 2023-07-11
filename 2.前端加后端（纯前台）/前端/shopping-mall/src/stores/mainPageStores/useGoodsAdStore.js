import {
    defineStore
} from 'pinia';
import {
    getGoodsAd
} from '@/api/pageConfig';
export const useGoodsAd = defineStore("goodsAd", {
    state: () => {
        return {
            goodsDatas: []
        };
    },
    actions: {
        initGoodsDatas() {
            //留给后端初始化
            getGoodsAd().then(res => {
                this.goodsDatas = res.data['goodsDatas'];
            });
        }
    }
});