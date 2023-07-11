package com.shoppingMall.admin.entity.bo;

import java.util.List;

/**
 * 用于后台获取轮播图数据和广告数据
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/5/9
 */
public class AdminPageCheckBo {
    /**
     * 包含的商品id
     */
    private List<String> goodsIds;

    public AdminPageCheckBo() {

    }

    public AdminPageCheckBo(List<String> goodsIds) {
        this.goodsIds = goodsIds;
    }

    public List<String> getGoodsIds() {
        return goodsIds;
    }

    public void setGoodsIds(List<String> goodsIds) {
        this.goodsIds = goodsIds;
    }

    @Override
    public String toString() {
        return "AdminPageCheckBo{" +
                "goodsIds=" + goodsIds +
                '}';
    }
}
