package com.shoppingMall.entity.vo;

/**
 * 用于接受商品参数
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/1
 */
public class GoodsVo {

    /**
     * 商品id
     */
    private String goodsId;

    /**
     * 商品名字
     */
    private String goodsName;

    public GoodsVo(){

    }

    public GoodsVo(String goodsId, String goodsName) {
        this.goodsId = goodsId;
        this.goodsName = goodsName;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    @Override
    public String toString() {
        return "GoodsVo{" +
                "goodsId=" + goodsId +
                ", goodsName='" + goodsName + '\'' +
                '}';
    }
}
