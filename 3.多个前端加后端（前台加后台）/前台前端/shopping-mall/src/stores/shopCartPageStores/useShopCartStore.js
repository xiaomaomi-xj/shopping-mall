import {
    defineStore
} from 'pinia';
import {
    ls
} from '@/utils/ls';
import {
    globals
} from '@/main';
import {
    getShopCartData,
    addShopCartData,
    delShopCartData
} from '@/api/shopCart';
export const useShopCart = defineStore("shopCart", {
    state: () => {
        return {
            shopCartGoodsData: [{}]
        };
    },
    actions: {
        //获取购物车商品
        getShopCartDataFun() {
            let token = ls.get('token');
            getShopCartData({
                token
            }).then(res => {
                this.shopCartGoodsData = res.data;
            });
        },
        //添加商品到购物车
        addShopCartDataFun(goodsId) {
            let token = ls.get('token');
            addShopCartData({
                token,
                goodsId
            }).then(res => {
                if (res.data['value']) {
                    globals.$selfMessage.openMessage({
                        type: 'success',
                        message: '商品添加到购物车成功！'
                    });
                    //添加无需更新
                }
            });
        },
        //删除购物车的数据
        delShopCartDataFun(goodsId) {
            let token = ls.get('token');
            delShopCartData({
                token,
                goodsId
            }).then(res => {
                if (res.data['value']) {
                    globals.$selfMessage.openMessage({
                        type: 'success',
                        message: '删除成功！'
                    });
                    //删除界面没变换，需要更新数据
                    this.getShopCartDataFun();
                }
            });
        }
    }
});