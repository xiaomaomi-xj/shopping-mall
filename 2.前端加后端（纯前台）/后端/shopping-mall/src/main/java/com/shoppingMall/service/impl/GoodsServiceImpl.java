package com.shoppingMall.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.shoppingMall.constant.GlobalConstant;
import com.shoppingMall.dao.GoodsDao;
import com.shoppingMall.dao.ImgDao;
import com.shoppingMall.entity.bo.GoodsBo;
import com.shoppingMall.entity.po.Goods;
import com.shoppingMall.service.GoodsService;
import com.shoppingMall.utils.ConvertUtil;
import com.zhuyahui.annotation.ZyhDataSourceRead;
import com.zhuyahui.annotation.ZyhService;

import java.util.List;

/**
 * 操作商品
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/2/28
 */
@ZyhService
@ZyhDataSourceRead
public class GoodsServiceImpl implements GoodsService {

    private final GoodsDao goodsDao;
    private final ImgDao imgDao;

    public GoodsServiceImpl(GoodsDao goodsDao, ImgDao imgDao) {
        this.goodsDao = goodsDao;
        this.imgDao = imgDao;
    }

    /**
     * 根据条件查询商品
     *
     * @return
     */
    @Override
    public List<GoodsBo> getSearchGoods(String goodsName) {
        List<Goods> allByGoodsName = goodsDao.findAllByGoodsNameLike(GlobalConstant.HOLDER + goodsName.trim() + GlobalConstant.HOLDER);
        return ConvertUtil.batchGoodsToGoodsBo(allByGoodsName, imgDao);
    }

    /**
     * 根据id查询商品
     *
     * @param goodsId
     * @return
     */
    @Override
    public GoodsBo getGoodsById(Long goodsId) {
        Goods goods = goodsDao.findById(goodsId).orElse(null);
        if(ObjectUtil.isNull(goods)){
            return null;
        }
        return ConvertUtil.goodsToGoodsBo(goods, imgDao);
    }
}
