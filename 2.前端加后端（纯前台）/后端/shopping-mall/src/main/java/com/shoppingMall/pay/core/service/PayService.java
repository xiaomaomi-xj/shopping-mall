package com.shoppingMall.pay.core.service;

import com.shoppingMall.entity.po.Merchant;

import java.util.Map;

/**
 * 支付接口类(只要一个支付链接就行，毕竟我们也不做全套的支付)，所有类型支付都要实现这个接口
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/23
 */
public interface PayService {
    /**
     * 获取商户类型
     *
     * @return
     */
    String getMerchantType();

    /**
     * 获取支付链接
     *
     * @param merchant   商户信息
     * @param orderId    订单号
     * @param goodsName  商品名字
     * @param totalPrice 总价格
     * @return
     */
    String getPayUrl(Merchant merchant, Long orderId, String goodsName, Float totalPrice);

    /**
     * 支付结果回调
     *
     * @param data 回调的数据
     * @return
     */
    String callBack(Map<String, String> data);
}
