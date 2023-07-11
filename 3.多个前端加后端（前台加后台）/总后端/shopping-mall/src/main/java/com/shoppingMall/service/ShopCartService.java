package com.shoppingMall.service;

import com.shoppingMall.entity.bo.GoodsBo;
import com.shoppingMall.entity.bo.SingleAllBo;

import java.util.List;

/**
 * 操作购物车
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/2/28
 */
public interface ShopCartService {
    /**
     * 添加到购物车
     *
     * @param token
     * @param goodsId
     * @return
     */
    SingleAllBo<Boolean> addShopCartData(String token, Long goodsId);

    /**
     * 获取购物车数据
     *
     * @param token
     * @return
     */
    List<GoodsBo> getShopCartData(String token);

    /**
     * 删除购物车数据
     *
     * @param token
     * @param goodsId
     * @return
     */
    SingleAllBo<Boolean> deleteShopCartData(String token,Long goodsId);
}
