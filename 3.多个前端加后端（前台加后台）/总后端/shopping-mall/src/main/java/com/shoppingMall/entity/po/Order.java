package com.shoppingMall.entity.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

/**
 * 订单实体类,如果脚order的话会报错，因为order是关键字
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/2/28
 */
@Entity(name = "order_data")
public class Order implements Serializable {
    private static final long serialVersionUID = -445726753956575660L;

    /**
     * 订单id
     */
    @Id
    @Column(name = "order_id")
    private Long orderId;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 商品id
     */
    @Column(name = "goods_id")
    private Long goodsId;

    /**
     * 商户id
     */
    @Column(name = "merchant_id")
    private Long merchantId;

    /**
     * 商品数量
     */
    @Column(name = "goods_num")
    private int goodsNum;

    /**
     * 运送状态
     */
    @Column(name = "ship_status")
    private int shipStatus;

    /**
     * 总金额
     */
    @Column(name = "total_price")
    private float totalPrice;

    /**
     * 订单状态
     */
    @Column(name = "order_state")
    private String orderState;

    /**
     * 组合式id
     */
    @Column(name = "compose_order_id")
    private Long composeOrderId;

    public Order(){

    }

    public Order(Long orderId, Long userId, Long goodsId, Long merchantId, int goodsNum, int shipStatus, float totalPrice, String orderState, Long composeOrderId) {
        this.orderId = orderId;
        this.userId = userId;
        this.goodsId = goodsId;
        this.merchantId = merchantId;
        this.goodsNum = goodsNum;
        this.shipStatus = shipStatus;
        this.totalPrice = totalPrice;
        this.orderState = orderState;
        this.composeOrderId = composeOrderId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
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

    public Long getComposeOrderId() {
        return composeOrderId;
    }

    public void setComposeOrderId(Long composeOrderId) {
        this.composeOrderId = composeOrderId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return goodsNum == order.goodsNum &&
                shipStatus == order.shipStatus &&
                Float.compare(order.totalPrice, totalPrice) == 0 &&
                Objects.equals(orderId, order.orderId) &&
                Objects.equals(userId, order.userId) &&
                Objects.equals(goodsId, order.goodsId) &&
                Objects.equals(merchantId, order.merchantId) &&
                Objects.equals(orderState, order.orderState) &&
                Objects.equals(composeOrderId, order.composeOrderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, userId, goodsId, merchantId, goodsNum, shipStatus, totalPrice, orderState, composeOrderId);
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", userId=" + userId +
                ", goodsId=" + goodsId +
                ", merchantId=" + merchantId +
                ", goodsNum=" + goodsNum +
                ", shipStatus=" + shipStatus +
                ", totalPrice=" + totalPrice +
                ", orderState='" + orderState + '\'' +
                ", composeOrderId=" + composeOrderId +
                '}';
    }
}
