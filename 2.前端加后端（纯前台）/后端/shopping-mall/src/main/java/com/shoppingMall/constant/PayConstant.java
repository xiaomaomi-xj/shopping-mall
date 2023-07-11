package com.shoppingMall.constant;

/**
 * 支付放行的名单
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/25
 */
public class PayConstant {
    /**
     * VIP前往支付界面链接
     */
    public static final String VIP_TO_PAY_URL = "/shopping-mall/vip_pay/to_pay_html";

    /**
     * VIP支付界面链接
     */
    public static final String VIP_PAY_URL = "/shopping-mall/vip_pay/pay";

    /**
     * VIP支付结果回调链接
     */
    public static final String VIP_CALLBACK_URL = "/shopping-mall/vip_pay/callback";

    /**
     * ALIPAY支付结果回调链接
     */
    public static final String ALIPAY_CALLBACK_URL = "/shopping-mall/alipay/callback";
}
