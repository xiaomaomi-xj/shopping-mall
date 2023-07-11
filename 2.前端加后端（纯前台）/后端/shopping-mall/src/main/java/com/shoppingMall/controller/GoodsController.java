package com.shoppingMall.controller;

import com.shoppingMall.entity.bo.GoodsBo;
import com.shoppingMall.entity.vo.GoodsVo;
import com.shoppingMall.exception.BizException;
import com.shoppingMall.service.GoodsService;
import com.shoppingMall.utils.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 商品操作接口
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/1
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {
    private final GoodsService goodsService;

    public GoodsController(GoodsService goodsService) {
        this.goodsService = goodsService;
    }

    /**
     * 根据搜索条件获取商品
     *
     * @param goodsVo
     * @return
     */
    @PostMapping("/get_search_goods")
    public List<GoodsBo> getSearchGoods(@RequestBody GoodsVo goodsVo) {
        String goodsName = goodsVo.getGoodsName();
        Assert.isNotBlank(goodsName, "搜索内容");
        return goodsService.getSearchGoods(goodsName);
    }

    /**
     * 根据id查询商品
     *
     * @param goodsVo
     * @return
     */
    @PostMapping("/get_goods_by_id")
    public GoodsBo getGoodsById(@RequestBody GoodsVo goodsVo) {
        String goodsIdStr = goodsVo.getGoodsId();
        Assert.isNotNull(goodsIdStr, "商品id");
        long goodsId;
        try {
            goodsId = Long.parseLong(goodsIdStr);
        } catch (NumberFormatException e) {
            throw new BizException("未找到此商品！");
        }
        return goodsService.getGoodsById(goodsId);
    }
}
