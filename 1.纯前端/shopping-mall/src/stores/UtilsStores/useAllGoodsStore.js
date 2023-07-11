//装着全部的数据
import {
    defineStore
} from 'pinia';
import {
    allGoodsDataJson
} from '@/handleCustomConfig/allGoodsDatas';
export const useAllGoods = defineStore("allGoods", {
    state: () => {
        return {
            allGoodsDatas: allGoodsDataJson
        };
    },
    actions: {
        //根据单个id返回具体的数值,还是拿id当下标，方便
        getDataById(id) {
            return this.allGoodsDatas[id-1];
        },
        //一旦购买，需要改变这个商品的库存数量
        modifyAllGoodsDatas(goodsId,buyNum){
            this.allGoodsDatas[goodsId-1]['maxBuyNum']=this.allGoodsDatas[goodsId-1]['maxBuyNum']-buyNum;
        },
        //根据关键字返回对应的结果
        getGoodsDatasOnSearch(content){
            return this.allGoodsDatas.filter(v=>{
                return v['goodsName'].includes(content);
            });
        }
    }
});