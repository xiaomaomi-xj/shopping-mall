package com.shoppingMall.entity.bo;

/**
 * 用于返回订单数据
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/22
 */
public class OrderBo {
    /**
     * 订单id
     */
    private String orderId;

    /**
     * 商品id,更改类型，防止精度丢失
     */
    private String goodsId;

    /**
     * 商品封面
     */
    private String imgUrl;

    /**
     * 商品名字
     */
    private String goodsName;

    /**
     * 商品价格
     */
    private Float goodsPrice;

    /**
     * 商品数量
     */
    private Integer goodsNum;

    /**
     * 商品运送状态
     */
    private Integer shipStatus;

    /**
     * 付款金额
     */
    private Float totalPrice;

    public OrderBo() {
    }

    public OrderBo(String orderId, String goodsId, String imgUrl, String goodsName, Float goodsPrice, Integer goodsNum, Integer shipStatus, Float totalPrice) {
        this.orderId = orderId;
        this.goodsId = goodsId;
        this.imgUrl = imgUrl;
        this.goodsName = goodsName;
        this.goodsPrice = goodsPrice;
        this.goodsNum = goodsNum;
        this.shipStatus = shipStatus;
        this.totalPrice = totalPrice;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Float getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(Float goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public Integer getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(Integer goodsNum) {
        this.goodsNum = goodsNum;
    }

    public Integer getShipStatus() {
        return shipStatus;
    }

    public void setShipStatus(Integer shipStatus) {
        this.shipStatus = shipStatus;
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "OrderBo{" +
                "orderId='" + orderId + '\'' +
                ", goodsId='" + goodsId + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", goodsPrice=" + goodsPrice +
                ", goodsNum=" + goodsNum +
                ", shipStatus=" + shipStatus +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
