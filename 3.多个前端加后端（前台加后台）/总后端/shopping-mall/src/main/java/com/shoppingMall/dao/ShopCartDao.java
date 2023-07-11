package com.shoppingMall.dao;

import com.shoppingMall.entity.po.ShopCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * 购物车数据操作接口
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/2/28
 */
public interface ShopCartDao extends JpaRepository<ShopCart, Long>, JpaSpecificationExecutor<ShopCart> {
    /**
     * 根据用户查询出所有购物车数据
     *
     * @param userId
     * @return
     */
    List<ShopCart> findAllByUserId(Long userId);

    /**
     * 判断购物车里面是否已经存在此商品
     *
     * @param goodsId
     * @param userId
     * @return
     */
    Boolean existsByGoodsIdAndUserId(Long goodsId, Long userId);

    /**
     * 判断用户合商品是否在购物车中
     *
     * @param userId
     * @param goodsId
     * @return
     */
    ShopCart findByUserIdAndGoodsId(Long userId, Long goodsId);

    /**
     * 根据商品id和用户id删除购物车数据
     *
     * @param goodsId
     * @param userId
     */
    void deleteByGoodsIdAndUserId(Long goodsId,Long userId);

    /**
     * 根据商品id删除所有的购物车数据
     *
     * @param goodsId
     */
    void deleteAllByGoodsId(Long goodsId);
}
