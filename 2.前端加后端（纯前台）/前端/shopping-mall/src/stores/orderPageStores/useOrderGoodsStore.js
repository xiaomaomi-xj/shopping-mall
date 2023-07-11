import {
    defineStore
} from 'pinia';
import {
    getOrderData,
    delOrderData,
    createSingleOrderData,
    createMultipleOrderData
} from '@/api/order';
import {
    ls
} from '@/utils/ls';
import {
    globals
} from '@/main';
export const useOrderGoods = defineStore("orderGoods", {
    state: () => {
        return {
            orderGoodsData: [{}]
        };
    },
    actions: {
        //获取订单数据
        getOrderDataFun() {
            let token = ls.get('token');
            getOrderData({
                token
            }).then(res => {
                this.orderGoodsData = res.data
            });
        },
        //删除订单数据
        delOrderDataFun(orderId) {
            let token = ls.get('token');
            delOrderData({
                token,
                orderId
            }).then(() => {
                globals.$selfMessage.openMessage({
                    type: 'success',
                    message: '删除成功！'
                });
                //删除界面没变换，需要更新数据
                this.getOrderDataFun();
            });
        },
        //创建单个订单
        createSingleOrderDataFun(merchantId, goodsInfo) {
            globals.$selfMessageBox.openMessageBox({
                type: 'loading'
            });
            let token = ls.get('token');
            createSingleOrderData({
                token,
                merchantId,
                goodsInfo
            }).then(res => {
                let url = res.data['value'];
                if (url == null || url == undefined || url == "" || url.trim().length == 0) {
                    globals.$selfMessage.openMessage({
                        type: 'error',
                        message: '创建订单失败，请重试！'
                    });
                } else {
                    window.location.href = url;
                }
            });
        },
        //创建多个订单
        createMultipleOrderDataFun(merchantId, goodsInfo, goodsInfos) {
            globals.$selfMessageBox.openMessageBox({
                type: 'loading'
            });
            let token = ls.get('token');
            createMultipleOrderData({
                token,
                merchantId,
                goodsInfo,
                goodsInfos
            }).then(res => {
                let url = res.data['value'];
                if (url == null || url == undefined || url == "" || url.trim().length == 0) {
                    globals.$selfMessage.openMessage({
                        type: 'error',
                        message: '创建订单失败，请重试！'
                    });
                } else {
                    window.location.href = url;
                }
            });
        }
    }
});