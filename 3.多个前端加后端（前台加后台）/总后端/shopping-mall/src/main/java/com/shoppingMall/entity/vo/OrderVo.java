package com.shoppingMall.entity.vo;

import com.shoppingMall.entity.bo.OrderBo;

import java.util.List;

/**
 * 订单接受参数实体类
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/22
 */
public class OrderVo {
    /**
     * 用户令牌
     */
    private String token;

    /**
     * 商户id
     */
    private Long merchantId;

    /**
     * 订单id
     */
    private Long orderId;

    /**
     * 商品id
     */
    private OrderBo goodsInfo;

    /**
     * 多个商品id
     */
    private List<OrderBo> goodsInfos;

    public OrderVo() {
    }

    public OrderVo(String token, Long merchantId, Long orderId, OrderBo goodsInfo, List<OrderBo> goodsInfos) {
        this.token = token;
        this.merchantId = merchantId;
        this.orderId = orderId;
        this.goodsInfo = goodsInfo;
        this.goodsInfos = goodsInfos;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public OrderBo getGoodsInfo() {
        return goodsInfo;
    }

    public void setGoodsInfo(OrderBo goodsInfo) {
        this.goodsInfo = goodsInfo;
    }

    public List<OrderBo> getGoodsInfos() {
        return goodsInfos;
    }

    public void setGoodsInfos(List<OrderBo> goodsInfos) {
        this.goodsInfos = goodsInfos;
    }

    @Override
    public String toString() {
        return "OrderVo{" +
                "token='" + token + '\'' +
                ", merchantId=" + merchantId +
                ", orderId=" + orderId +
                ", goodsInfo=" + goodsInfo +
                ", goodsInfos=" + goodsInfos +
                '}';
    }
}
