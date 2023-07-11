package com.shoppingMall.pay.vippay.service;

/**
 * 处理支付界面
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/25
 */
public interface VipPayHtmlService {
    /**
     * 获取前往支付页面
     *
     * @param orderId
     * @param amount
     * @param goodsName
     * @param requestFromUrl
     * @param callbackUrl
     * @return
     */
    String toPayHtml(String orderId, String amount, String goodsName, String requestFromUrl, String callbackUrl);

    /**
     * 获取支付页面
     *
     * @param token
     * @return
     */
    String getPayHtml(String token);

    /**
     * 删除订单
     *
     * @param token
     */
    void deleteToken(String token);

    /**
     * 是否订单还存在
     *
     * @param token
     * @return
     */
    Boolean hasToken(String token);
}
