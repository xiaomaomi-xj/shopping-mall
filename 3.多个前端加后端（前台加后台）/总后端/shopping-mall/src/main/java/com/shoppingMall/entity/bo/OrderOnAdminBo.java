package com.shoppingMall.entity.bo;

/**
 * 用于管理员的订单bo类
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/4/12
 */
public class OrderOnAdminBo {
    /**
     * 订单id
     */
    private String orderId;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 商品名字
     */
    private String goodsName;

    /**
     * 商户名字
     */
    private String merchantName;

    /**
     * 商品数量
     */
    private int goodsNum;

    /**
     * 运送状态
     */
    private int shipStatus;

    /**
     * 总金额
     */
    private float totalPrice;

    /**
     * 订单状态
     */
    private String orderState;

    public OrderOnAdminBo() {
    }

    public OrderOnAdminBo(String orderId, String userId, String goodsName, String merchantName, int goodsNum, int shipStatus, float totalPrice, String orderState) {
        this.orderId = orderId;
        this.userId = userId;
        this.goodsName = goodsName;
        this.merchantName = merchantName;
        this.goodsNum = goodsNum;
        this.shipStatus = shipStatus;
        this.totalPrice = totalPrice;
        this.orderState = orderState;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public int getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(int goodsNum) {
        this.goodsNum = goodsNum;
    }

    public int getShipStatus() {
        return shipStatus;
    }

    public void setShipStatus(int shipStatus) {
        this.shipStatus = shipStatus;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }
}
