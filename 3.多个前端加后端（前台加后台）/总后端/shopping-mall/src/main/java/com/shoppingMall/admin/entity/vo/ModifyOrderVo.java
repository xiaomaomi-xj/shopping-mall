package com.shoppingMall.admin.entity.vo;

/**
 * 修改订单vo实体类
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/4/28
 */
public class ModifyOrderVo {
    /**
     * 订单id
     */
    private Long orderId;

    /**
     * 运送状态
     */
    private int shipStatus;

    public ModifyOrderVo() {
    }

    public ModifyOrderVo(Long orderId, int shipStatus) {
        this.orderId = orderId;
        this.shipStatus = shipStatus;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public int getShipStatus() {
        return shipStatus;
    }

    public void setShipStatus(int shipStatus) {
        this.shipStatus = shipStatus;
    }

    @Override
    public String toString() {
        return "ModifyOrderVo{" +
                "orderId=" + orderId +
                ", shipStatus=" + shipStatus +
                '}';
    }
}
