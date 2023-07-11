package com.shoppingMall.entity.bo;

import com.shoppingMall.utils.Assert;

import java.io.Serializable;
import java.util.List;

/**
 * 返回商品数据要用
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/1
 */
public class GoodsBo implements Serializable {

    private static final long serialVersionUID = 4872804771353923313L;
    /**
     * 商品id,更改类型，防止精度丢失
     */
    private String goodsId;

    /**
     * 商品封面
     */
    private String imgUrl;

    /**
     * 商品名字
     */
    private String goodsName;

    /**
     * 商品描述
     */
    private String goodsDescribe;

    /**
     * 商品价格
     */
    private Float goodsPrice;

    /**
     * 库存
     */
    private Integer maxBuyNum;

    /**
     * 商品详情所需要的图片
     */
    private List<String> rotationGoodsImgs;

    public GoodsBo() {

    }

    public GoodsBo(String goodsId, String imgUrl, String goodsName, String goodsDescribe, Float goodsPrice, Integer maxBuyNum, List<String> rotationGoodsImgs) {
        this.goodsId = goodsId;
        this.imgUrl = imgUrl;
        this.goodsName = goodsName;
        this.goodsDescribe = goodsDescribe;
        this.goodsPrice = goodsPrice;
        this.maxBuyNum = maxBuyNum;
        this.rotationGoodsImgs = rotationGoodsImgs;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsDescribe() {
        return goodsDescribe;
    }

    public void setGoodsDescribe(String goodsDescribe) {
        this.goodsDescribe = goodsDescribe;
    }

    public Float getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(Float goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public Integer getMaxBuyNum() {
        return maxBuyNum;
    }

    public void setMaxBuyNum(Integer maxBuyNum) {
        this.maxBuyNum = maxBuyNum;
    }

    public List<String> getRotationGoodsImgs() {
        return rotationGoodsImgs;
    }

    public void setRotationGoodsImgs(List<String> rotationGoodsImgs) {
        this.rotationGoodsImgs = rotationGoodsImgs;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void check() {
        Assert.isNotBlank(goodsName, "商品名字");
        Assert.isNotBlank(goodsDescribe, "商品描述");
        Assert.isNotNull(goodsPrice, "商品价格");
        Assert.isNotNull(maxBuyNum, "库存");
    }

    @Override
    public String toString() {
        return "GoodsBo{" +
                "goodsId=" + goodsId +
                ", imgUrl='" + imgUrl + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", goodsDescribe='" + goodsDescribe + '\'' +
                ", goodsPrice=" + goodsPrice +
                ", maxBuyNum=" + maxBuyNum +
                ", rotationGoodsImgs=" + rotationGoodsImgs +
                '}';
    }
}
