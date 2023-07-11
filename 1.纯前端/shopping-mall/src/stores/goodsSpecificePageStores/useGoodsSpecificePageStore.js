import {
    defineStore
} from 'pinia';
import {
    useAllGoods
} from '../UtilsStores/useAllGoodsStore';
import {
    useOrderGoods
} from '../orderPageStores/useOrderGoodsStore';
import {
    useShopCart
} from '../shopCartPageStores/useShopCartStore';
export const useGoodsSpecifics = defineStore('goodsSpecifics', {
    state: () => {
        return {
            goodsData: {}
        };
    },
    actions: {
        modifyGoodsData(goodId) {
            //根据单个id查询商品
            this.goodsData = useAllGoods().getDataById(goodId);
        },
        //购买
        buyGoodsData(goodsData) {
            //订单还有额外的参数
            goodsData = Object.assign(goodsData, {
                shipStatus: 1,
                isShow: true,
            });
            //调用订单函数进行增加
            useOrderGoods().addOrderGoodsData(goodsData);
            //调用全部的数据进行数量修改
            useAllGoods().modifyAllGoodsDatas(goodsData['goodsData']['goodsId'], goodsData['goodsNum']);
        },
        //加入购物车
        addGoodsToShopCart(goodsData) {
            //购物车数据还有其他数据
            goodsData = Object.assign({goodsData}, {
                //对于其他数据，我们直接用初始化的
                selectSwitch: false,
                goodsNum: 1,
                totalPrice: goodsData['goodsPrice']
            });
            return useShopCart().addGoodsToShopCart(goodsData);
        },
    }
});
