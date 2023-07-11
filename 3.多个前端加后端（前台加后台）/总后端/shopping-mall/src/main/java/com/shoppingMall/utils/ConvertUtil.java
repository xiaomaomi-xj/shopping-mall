package com.shoppingMall.utils;

import com.shoppingMall.dao.ImgDao;
import com.shoppingMall.entity.bo.GoodsBo;
import com.shoppingMall.entity.po.Goods;
import com.shoppingMall.entity.po.Img;
import com.shoppingMall.exception.AppException;

import java.util.ArrayList;
import java.util.List;

/**
 * 代码复用，用于一些类型的切换
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/1
 */
public class ConvertUtil {
    /**
     * 把goods转换成goodsBo
     *
     * @param goods
     * @param imgDao
     * @return
     */
    public static GoodsBo goodsToGoodsBo(Goods goods, ImgDao imgDao){
        Img img1 = imgDao.findByCoverGoods(goods.getGoodsId()).orElse(null);
        if(img1==null){
            throw new AppException("系统数据错误，请联系管理员！");
        }
        List<String> imgNames = new ArrayList<>();
        imgDao.findAllByRotationGoods(goods.getGoodsId()).forEach(img -> imgNames.add(img.getImgName()));
        return new GoodsBo(
                goods.getGoodsId().toString(),
                img1.getImgName(),
                goods.getGoodsName(),
                goods.getGoodsDescribe(),
                goods.getGoodsPrice(),
                goods.getMaxBuyNum(),
                imgNames
        );
    }

    /**
     * 批量把goods转换成goodsBo
     *
     * @param goodsList
     * @param imgDao
     * @return
     */
    public static List<GoodsBo> batchGoodsToGoodsBo(List<Goods> goodsList,ImgDao imgDao){
        List<GoodsBo> goodsBoList=new ArrayList<>();
        for (Goods goods:goodsList) {
            GoodsBo goodsBo = goodsToGoodsBo(goods, imgDao);
            goodsBoList.add(goodsBo);
        }
        return goodsBoList;
    }
}
