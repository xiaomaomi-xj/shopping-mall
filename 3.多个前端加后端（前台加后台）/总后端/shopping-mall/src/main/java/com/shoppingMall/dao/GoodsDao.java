package com.shoppingMall.dao;

import com.shoppingMall.entity.po.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 商品操作接口
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/2/28
 */
public interface GoodsDao extends JpaRepository<Goods, Long>, JpaSpecificationExecutor<Goods> {
    /**
     * 根据条件查询商品
     *
     * @param goodsName
     * @return
     */
    List<Goods> findAllByGoodsNameLike(String goodsName);

    /**
     * 根据toggleView查询出所有商品
     *
     * @param toggleViewId
     * @return
     */
    List<Goods> findAllByToggleViewGoods(Long toggleViewId);

    /**
     * 获取轮播图的商品
     *
     * @param check
     * @return
     */
    List<Goods> findAllByRotationChartGoods(String check);

    /**
     * 获取广告的商品
     *
     * @param check
     * @return
     */
    List<Goods> findAllByAdGoods(String check);

    /**
     * 获取主要界面左边的商品
     *
     * @param containerId
     * @return
     */
    Goods findByContainerSpecialGoods(Long containerId);

    /**
     * 获取主要界面右边上排商品
     *
     * @param containerId
     * @return
     */
    List<Goods> findAllByContainerTopGoods(Long containerId);

    /**
     * 获取主要界面右边下排商品
     *
     * @param containerId
     * @return
     */
    List<Goods> findAllByContainerBottomGoods(Long containerId);

    /**
     * 减少库存
     *
     * @param maxBuyNum
     * @param goodsId
     */
    @Modifying
    @Transactional(rollbackFor = RuntimeException.class)
    @Query(value = "UPDATE goods set max_buy_num = max_buy_num - :maxBuyNum where goods_id = :goodsId", nativeQuery = true)
    void declineMaxBuyNum(@Param("maxBuyNum") Integer maxBuyNum, @Param("goodsId") Long goodsId);

    /**
     * 增加库存
     *
     * @param maxBuyNum
     * @param goodsId
     */
    @Modifying
    @Transactional(rollbackFor = RuntimeException.class)
    @Query(value = "UPDATE goods set max_buy_num = max_buy_num + :maxBuyNum where goods_id = :goodsId", nativeQuery = true)
    void increaseMaxBuyNum(@Param("maxBuyNum") Integer maxBuyNum, @Param("goodsId") Long goodsId);
}
