package com.shoppingMall.service;

import com.shoppingMall.entity.bo.GoodsBo;
import com.shoppingMall.entity.bo.SingleAllBo;
import org.springframework.web.multipart.MultipartFile;

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

    /**
     * 获取所有商品数据
     *
     * @return
     */
    List<GoodsBo> getAllGoods();

    /**
     * 删除商品
     *
     * @param id
     * @return
     */
    SingleAllBo<Boolean> delGoods(String id);

    /**
     * 修改商品
     *
     * @param goodsId
     * @param goodsName
     * @param goodsDescribe
     * @param goodsPrice
     * @param maxBuyNum
     * @param files
     * @return
     */
    SingleAllBo<Boolean> modifyGoods(String goodsId, String goodsName, String goodsDescribe, Float goodsPrice, Integer maxBuyNum, List<MultipartFile> files);

    /**
     * 添加商品
     *
     * @param goodsName
     * @param goodsDescribe
     * @param goodsPrice
     * @param maxBuyNum
     * @param files
     * @return
     */
    SingleAllBo<Boolean> addGoods(String goodsName, String goodsDescribe, Float goodsPrice, Integer maxBuyNum, List<MultipartFile> files);
}
