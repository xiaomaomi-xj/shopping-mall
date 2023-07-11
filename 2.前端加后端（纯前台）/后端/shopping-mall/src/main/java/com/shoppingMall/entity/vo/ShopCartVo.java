package com.shoppingMall.entity.vo;

/**
 * 用于接受购物车参数
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/21
 */
public class ShopCartVo {
    /**
     * 用户令牌
     */
    private String token;

    /**
     * 商品id
     */
    private Long goodsId;

    public ShopCartVo() {
    }

    public ShopCartVo(String token, Long goodsId) {
        this.token = token;
        this.goodsId = goodsId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    @Override
    public String toString() {
        return "ShopCartVo{" +
                "token='" + token + '\'' +
                ", goodsId=" + goodsId +
                '}';
    }
}
