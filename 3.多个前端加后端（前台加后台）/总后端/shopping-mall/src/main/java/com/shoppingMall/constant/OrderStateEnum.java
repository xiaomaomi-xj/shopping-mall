package com.shoppingMall.constant;

/**
 * 订单状态
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/24
 */
public enum OrderStateEnum {
    /**
     * 未支付
     */
    PENDING("PENDING","未支付"),
    /**
     * 已支付
     */
    COMPLET("COMPLET","已支付");

    private final String state;
    OrderStateEnum(String state,String desc){
        this.state=state;
    }

    public String getState() {
        return state;
    }
}
