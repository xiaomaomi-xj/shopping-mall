import {
    defineStore
} from 'pinia';
import {
    useOrderGoods
} from '../orderPageStores/useOrderGoodsStore';
import {
    useAllGoods
} from '../UtilsStores/useAllGoodsStore';
import {
    useUsers
} from '../loginAndRegistPageStores/useUserStore';
export const useShopCart = defineStore("shopCart", {
    state: () => {
        return {
            goodsDatasOnShopingCart: []
        };
    },
    actions: {
        //修改初始信息，就是初始化
        initGoodsDatas() {
            
        },
        //获取当前用户购物车的数据
        getCurrentUserShopCart() {
            const userEmail = useUsers().getUserDataOnToken()['userEmail'];
            return this.goodsDatasOnShopingCart.filter(v => v['userEmail'] === userEmail);
        },
        //添加商品到购物车
        addGoodsToShopCart(goodsData) {
            let isRepeat = false;
            const userEmail = useUsers().getUserDataOnToken()['userEmail'];
            goodsData['userEmail'] = userEmail;
            for (const data of this.goodsDatasOnShopingCart) {
                if (data['goodsData']['goodsId'] === goodsData['goodsData']['goodsId'] && data['userEmail'] === goodsData['userEmail']) {
                    isRepeat = true;
                }
            }
            if (isRepeat) {
                return {
                    type: 'warning',
                    message: '添加失败！购物车已有此商品！'
                };
            } else {
                this.goodsDatasOnShopingCart.push(goodsData);
                return {
                    type: 'success',
                    message: '添加成功！'
                };
            }
        },
        //从购物车里面删除某个商品
        deleteGoodsData(goodsId) {
            let index = -1;
            const userEmail = useUsers().getUserDataOnToken()['userEmail'];
            for (const data of this.goodsDatasOnShopingCart) {
                index++;
                if (data['goodsData']['goodsId'] === goodsId && data['userEmail'] === userEmail) {
                    break;
                }
            }
            this.goodsDatasOnShopingCart.splice(index, 1);
        },
        //购买商品，可能购买一个或多个
        buyGoodsDatasOnShopCart(goodsDatas) {
            for (const goodsData of goodsDatas) {
                const data = Object.assign(goodsData, {
                    shipStatus: 1,
                    isShow: true,
                });
                //订单数据增加
                useOrderGoods().addOrderGoodsData(data);
                //总数据库存减少
                useAllGoods().modifyAllGoodsDatas(data['goodsData']['goodsId'], data['goodsNum']);
                //去除掉购物测里面买完之后的商品
                this.deleteGoodsData(data['goodsData']['goodsId']);
            }

        }
    }
});