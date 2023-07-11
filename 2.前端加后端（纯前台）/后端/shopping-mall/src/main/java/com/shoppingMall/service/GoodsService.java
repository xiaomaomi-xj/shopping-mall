package com.shoppingMall.service;

import com.shoppingMall.entity.bo.GoodsBo;

import java.util.List;

/**
 * 操作商品
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/2/28
 */
public interface GoodsService {

    /**
     * 根据条件查询出来商品
     *
     * @param goodsName
     * @return
     */
    List<GoodsBo> getSearchGoods(String goodsName);

    /**
     * 根据商品id查询商品
     *
     * @param goodsId
     * @return
     */
    GoodsBo getGoodsById(Long goodsId);
}
