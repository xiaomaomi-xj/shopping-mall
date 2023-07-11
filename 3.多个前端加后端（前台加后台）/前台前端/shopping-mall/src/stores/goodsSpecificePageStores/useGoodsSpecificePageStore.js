import {
    defineStore
} from 'pinia';
import {
    getGoodsById
} from '@/api/goods';
import {
    globals
} from '@/main';
export const useGoodsSpecifics = defineStore('goodsSpecifics', {
    state: () => {
        return {
            goodsData: {}
        };
    },
    actions: {
        //获取商品详细信息
        getGoodsDataById(goodsId) {
            //根据单个id查询商品
            getGoodsById({
                goodsId
            }).then(res => {
                if (res.data == "") {
                    //没拿到值，直接跳转到404界面
                    globals.$router.push({
                        name: 'notFund'
                    });
                }
                this.goodsData = res.data;
            }, () => {
                ////出错了，直接跳转到404界面
                globals.$router.push({
                    name: 'notFund'
                });
            });
        }
    }
});