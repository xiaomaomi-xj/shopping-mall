package com.shoppingMall.pay.vippay.utils;

import com.shoppingMall.entity.po.Merchant;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * vip支付工具类
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/24
 */
public class VipPayUtil {
    /**
     * 获取支付链接
     *
     * @param merchant
     * @param orderId
     * @param amount
     * @param goodsName
     * @param requestFromUrl
     * @return
     */
    public static String getPayUrl(Merchant merchant, Long orderId, Float amount, String goodsName, String requestFromUrl) {
        String sb = merchant.getServerUrl() + "?" +
                "callbackUrl=%s" + "&" +
                "requestFromUrl=%s" + "&" +
                "goodsName=%s" + "&" +
                "amount=%s" + "&" +
                "orderId=%s";
        return String.format(sb, URLEncoder.encode(merchant.getCallbackUrl(), StandardCharsets.UTF_8), URLEncoder.encode(requestFromUrl, StandardCharsets.UTF_8), URLEncoder.encode(goodsName, StandardCharsets.UTF_8), amount, orderId);
    }
}
