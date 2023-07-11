package com.shoppingMall.pay.core.constant;

/**
 * 购买商品结果枚举类
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/26
 */
public enum GoodsStateEnum {
    /**
     * 成功支付
     */
    SUCCESS("SUCCESS"),

    /**
     * 支付的时候，商品被别人抢走了
     */
    LOOT("LOOT"),

    /**
     * 商品数据丢失
     */
    LOSE("LOSE");

    private String code;
    GoodsStateEnum(String code) {
        this.code=code;
    }

    public String getCode() {
        return code;
    }
}
