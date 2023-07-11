package com.shoppingMall.pay.vippay.entity.vo;

/**
 * vip支付vo类
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/25
 */
public class VipPayVo {
    /**
     * 订单号
     */
    private String orderId;

    /**
     * 金额
     */
    private String amount;

    /**
     * 商品名字
     */
    private String goodsName;

    /**
     * 取消支付地址
     */
    private String requestFromUrl;

    /**
     * 支付回调地址
     */
    private String callbackUrl;

    /**
     * 订单token
     */
    private String token;

    public VipPayVo() {
    }

    public VipPayVo(String orderId, String amount, String goodsName, String requestFromUrl, String callbackUrl, String token) {
        this.orderId = orderId;
        this.amount = amount;
        this.goodsName = goodsName;
        this.requestFromUrl = requestFromUrl;
        this.callbackUrl = callbackUrl;
        this.token = token;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getRequestFromUrl() {
        return requestFromUrl;
    }

    public void setRequestFromUrl(String requestFromUrl) {
        this.requestFromUrl = requestFromUrl;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "VipPayVo{" +
                "orderId='" + orderId + '\'' +
                ", amount='" + amount + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", requestFromUrl='" + requestFromUrl + '\'' +
                ", callbackUrl='" + callbackUrl + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
