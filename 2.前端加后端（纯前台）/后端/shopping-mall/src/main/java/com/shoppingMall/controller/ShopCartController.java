package com.shoppingMall.controller;

import com.shoppingMall.entity.bo.GoodsBo;
import com.shoppingMall.entity.bo.SingleAllBo;
import com.shoppingMall.entity.vo.ShopCartVo;
import com.shoppingMall.service.ShopCartService;
import com.shoppingMall.utils.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 购物车数据控制类
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/21
 */
@RestController
@RequestMapping("/shop_cart")
public class ShopCartController {
    private final ShopCartService shopCartService;

    public ShopCartController(ShopCartService shopCartService) {
        this.shopCartService = shopCartService;
    }

    /**
     * 加入购物车
     */
    @PostMapping("/add_shop_cart_data")
    public SingleAllBo<Boolean> addShopCartData(@RequestBody ShopCartVo shopCartVo) {
        Long goodsId = shopCartVo.getGoodsId();
        String token = shopCartVo.getToken();
        Assert.isNotBlank(token, "用户令牌");
        Assert.isNotNull(goodsId, "商品id");
        return shopCartService.addShopCartData(token, goodsId);
    }

    /**
     * 获取购物车数据
     */
    @PostMapping("/get_shop_cart_data")
    public List<GoodsBo> getShopCartData(@RequestBody ShopCartVo shopCartVo) {
        String token = shopCartVo.getToken();
        Assert.isNotBlank(token, "用户令牌");
        return shopCartService.getShopCartData(token);
    }

    /**
     * 删除购物车的数据
     */
    @PostMapping("/del_shop_cart_data")
    public SingleAllBo<Boolean> delShopCartData(@RequestBody ShopCartVo shopCartVo) {
        String token = shopCartVo.getToken();
        Long goodsId = shopCartVo.getGoodsId();
        Assert.isNotBlank(token, "用户令牌");
        Assert.isNotNull(goodsId, "商品id");
        return shopCartService.deleteShopCartData(token, goodsId);
    }
}
