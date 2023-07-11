package com.shoppingMall.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.shoppingMall.dao.GoodsDao;
import com.shoppingMall.dao.ImgDao;
import com.shoppingMall.dao.ShopCartDao;
import com.shoppingMall.entity.bo.GoodsBo;
import com.shoppingMall.entity.bo.SingleAllBo;
import com.shoppingMall.entity.po.Goods;
import com.shoppingMall.entity.po.Img;
import com.shoppingMall.entity.po.ShopCart;
import com.shoppingMall.exception.AppException;
import com.shoppingMall.exception.BizException;
import com.shoppingMall.manager.ManagerTokenService;
import com.shoppingMall.service.ShopCartService;
import com.zhuyahui.annotation.ZyhDataSourceRead;
import com.zhuyahui.annotation.ZyhService;
import com.zhuyahui.util.MyAloneHandlerReadWrite;

import java.util.ArrayList;
import java.util.List;

/**
 * 操作购物车
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/2/28
 */
@ZyhService
@ZyhDataSourceRead
public class ShopCartServiceImpl implements ShopCartService {
    private final GoodsDao goodsDao;
    private final ShopCartDao shopCartDao;
    private final ImgDao imgDao;
    private final ManagerTokenService managerTokenService;

    public ShopCartServiceImpl(GoodsDao goodsDao, ShopCartDao shopCartDao, ImgDao imgDao, ManagerTokenService managerTokenService) {
        this.goodsDao = goodsDao;
        this.shopCartDao = shopCartDao;
        this.imgDao = imgDao;
        this.managerTokenService = managerTokenService;
    }

    /**
     * 添加到购物车
     *
     * @param token
     * @param goodsId
     * @return
     */
    @Override
    public SingleAllBo<Boolean> addShopCartData(String token, Long goodsId) {
        if (!goodsDao.existsById(goodsId)) {
            throw new BizException("没有此商品！");
        }
        Long userId = managerTokenService.getUserId(token);
        if (shopCartDao.existsByGoodsIdAndUserId(goodsId, userId)) {
            throw new BizException("购物车里面已有此商品！");
        }
        MyAloneHandlerReadWrite.write(() -> shopCartDao.save(new ShopCart(
                IdUtil.getSnowflakeNextId(),
                userId,
                goodsId
        )));
        return new SingleAllBo<>(true);
    }

    /**
     * 获取购物车商品数据
     *
     * @param token
     * @return
     */
    @Override
    public List<GoodsBo> getShopCartData(String token) {
        List<GoodsBo> goodsBoList = new ArrayList<>();
        Long id = managerTokenService.getUserId(token);
        List<ShopCart> shopCarts = shopCartDao.findAllByUserId(id);
        for (ShopCart shopCart : shopCarts) {
            Long goodsId = shopCart.getGoodsId();
            Goods goods = goodsDao.findById(goodsId).orElseGet(() -> null);
            if (ObjectUtil.isNull(goods)) {
                throw new AppException("商品数据丢失，请联系管理员！");
            }
            Img img = imgDao.findByCoverGoods(goodsId).orElseGet(() -> null);
            if (ObjectUtil.isNull(img)) {
                throw new AppException("图片数据丢失，请联系管理员！");
            }
            goodsBoList.add(new GoodsBo(
                    String.valueOf(goodsId),
                    img.getImgName(),
                    goods.getGoodsName(),
                    goods.getGoodsDescribe(),
                    goods.getGoodsPrice(),
                    goods.getMaxBuyNum(),
                    //这个数据不需要，节省掉
                    null
            ));
        }
        return goodsBoList;
    }

    /**
     * 删除购物车数据
     *
     * @param token
     * @param goodsId
     * @return
     */
    @Override
    public SingleAllBo<Boolean> deleteShopCartData(String token, Long goodsId) {
        if (!goodsDao.existsById(goodsId)) {
            throw new BizException("没有此商品！");
        }
        Long id = managerTokenService.getUserId(token);
        ShopCart shopCart = shopCartDao.findByUserIdAndGoodsId(id, goodsId);
        if (ObjectUtil.isNull(shopCart)) {
            throw new BizException("购物车里面没有你要删除的商品！");
        }
        MyAloneHandlerReadWrite.write(() -> {
            shopCartDao.delete(shopCart);
            return null;
        });
        return new SingleAllBo<>(true);
    }
}
