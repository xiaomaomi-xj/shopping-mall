package com.shoppingMall.pay.core.constant;

/**
 * 商户类型
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/23
 */
public enum MerchantTypeEnum {
    /**
     * 此为自定义的一种支付方式
     */
    VIP_PAY("VIP_PAY","VIP支付(购物卡的方式)"),
    /**
     * 阿里支付
     */
    ALIPAY("ALIPAY","阿里支付宝");

    private final String code;
    private final String name;

    MerchantTypeEnum(String code,String name){
        this.code=code;
        this.name=name;
    }

    public String getCode() {
        return code;
    }
}
