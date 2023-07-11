package com.shoppingMall.entity.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

/**
 * 购物车实体类
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/2/28
 */
@Entity(name = "shop_cart")
public class ShopCart implements Serializable {
    private static final long serialVersionUID = 1593576959092567407L;

    /**
     * 购物车id
     */
    @Id
    @Column(name = "shop_cart_id")
    private Long shopCartId;

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

    public ShopCart() {

    }

    public ShopCart(Long shopCartId, Long userId, Long goodsId) {
        this.shopCartId = shopCartId;
        this.userId = userId;
        this.goodsId = goodsId;
    }

    public Long getShopCartId() {
        return shopCartId;
    }

    public void setShopCartId(Long shopCartId) {
        this.shopCartId = shopCartId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShopCart shopCart = (ShopCart) o;
        return Objects.equals(shopCartId, shopCart.shopCartId) &&
                Objects.equals(userId, shopCart.userId) &&
                Objects.equals(goodsId, shopCart.goodsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shopCartId, userId, goodsId);
    }

    @Override
    public String toString() {
        return "ShopCart{" +
                "shopCartId=" + shopCartId +
                ", userId=" + userId +
                ", goodsId=" + goodsId +
                '}';
    }
}
