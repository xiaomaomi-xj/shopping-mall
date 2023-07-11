package com.shoppingMall.dao;

import com.shoppingMall.entity.po.Img;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

/**
 * 图片数据操作接口
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/2/28
 */
public interface ImgDao extends JpaRepository<Img,Long>, JpaSpecificationExecutor<Img> {
    /**
     * 获取商品封面
     *
     * @param goodsId
     * @return
     */
    Optional<Img> findByCoverGoods(Long goodsId);

    /**
     * 获取商品的详情图片
     *
     * @param goodsId
     * @return
     */
    List<Img> findAllByRotationGoods(Long goodsId);
}
