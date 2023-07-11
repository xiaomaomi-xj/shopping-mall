package com.shoppingMall.pay.core.service;

import com.shoppingMall.entity.bo.SingleAllBo;

/**
 * 多个支付商户控制
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/23
 */
public interface MorePayService {
    /**
     * 获取支付链接
     *
     * @param merchantId 商户id
     * @param orderId    订单id
     * @return
     */
    SingleAllBo<String> getPayUrl(Long merchantId, Long orderId);

    /**
     * 获取支付链接
     *
     * @param merchantId     商户id
     * @param composeOrderId 组合式id
     * @param amount         金额
     * @return
     */
    SingleAllBo<String> getPayUrl(Long merchantId, Long composeOrderId, Float amount);
}
