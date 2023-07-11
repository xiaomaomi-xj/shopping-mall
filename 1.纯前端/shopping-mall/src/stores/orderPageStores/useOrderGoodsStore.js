import {
    defineStore
} from 'pinia';
import {
    orderDataBak
} from '@/handleCustomConfig/pageConfig';
import {
    useUsers
} from '../loginAndRegistPageStores/useUserStore';
export const useOrderGoods = defineStore("orderGoods", {
    state: () => {
        return {
            orderGoodsDatas: orderDataBak
        };
    },
    actions: {
        initOrderGoodsDatas() {
            // 初始化添加额外的属性
            this.orderGoodsDatas.map(v => {
                v['isShow'] = true;
            });
        },
        //获取对应的商品
        getcurrentUserOrder() {
            const userEmail = useUsers().getUserDataOnToken()['userEmail'];
            return this.orderGoodsDatas.filter(v => v['userEmail'] === userEmail);
        },
        //删除某件商品
        deleteOrderGoodsDataById(goodsId) {
            let index = -1;
            const userEmail = useUsers().getUserDataOnToken()['userEmail'];
            for (const data of this.orderGoodsDatas) {
                index++;
                if (data['goodsData']['goodsId'] === goodsId && data['userEmail'] === userEmail) {
                    break;
                }
            }
            this.orderGoodsDatas.splice(index, 1);
        },
        //增加某件商品
        addOrderGoodsData(orderGoodsData) {
            const userEmail = useUsers().getUserDataOnToken()['userEmail'];
            orderGoodsData['userEmail'] = userEmail;
            this.orderGoodsDatas.push(orderGoodsData);
        }
    }
});