package com.shoppingMall.entity.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

/**
 * 商品实体类
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/2/28
 */
@Entity(name = "goods")
public class Goods implements Serializable {
    private static final long serialVersionUID = -7954520569758756208L;

    /**
     * 商品id
     */
    @Id
    @Column(name = "goods_id")
    private Long goodsId;

    /**
     * 商品名字
     */
    @Column(name = "goods_name")
    private String goodsName;

    /**
     * 商品描述
     */
    @Column(name = "goods_describe")
    private String goodsDescribe;

    /**
     * 商品价格
     */
    @Column(name = "goods_price")
    private Float goodsPrice;

    /**
     * 库存
     */
    @Column(name = "max_buy_num")
    private Integer maxBuyNum;

    /**
     * 标题所需商品,标题id
     */
    @Column(name = "toggle_view_goods")
    private Long toggleViewGoods;

    /**
     * 轮播图所需商品，check
     */
    @Column(name = "rotation_chart_goods")
    private String rotationChartGoods;

    /**
     * 广告所需商品，check
     */
    @Column(name = "ad_goods")
    private String adGoods;

    /**
     * 主要界面左侧所需商品，界面id
     */
    @Column(name = "container_special_goods")
    private Long containerSpecialGoods;

    /**
     * 主要界面右侧上排所需商品，界面id
     */
    @Column(name = "container_top_goods")
    private Long containerTopGoods;

    /**
     * 主要界面右侧下排所需商品，界面id
     */
    @Column(name = "container_bottom_goods")
    private Long containerBottomGoods;

    public Goods(){

    }

    public Goods(Long goodsId, String goodsName, String goodsDescribe, Float goodsPrice, Integer maxBuyNum, Long toggleViewGoods, String rotationChartGoods, String adGoods, Long containerSpecialGoods, Long containerTopGoods, Long containerBottomGoods) {
        this.goodsId = goodsId;
        this.goodsName = goodsName;
        this.goodsDescribe = goodsDescribe;
        this.goodsPrice = goodsPrice;
        this.maxBuyNum = maxBuyNum;
        this.toggleViewGoods = toggleViewGoods;
        this.rotationChartGoods = rotationChartGoods;
        this.adGoods = adGoods;
        this.containerSpecialGoods = containerSpecialGoods;
        this.containerTopGoods = containerTopGoods;
        this.containerBottomGoods = containerBottomGoods;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
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

    public Long getToggleViewGoods() {
        return toggleViewGoods;
    }

    public void setToggleViewGoods(Long toggleViewGoods) {
        this.toggleViewGoods = toggleViewGoods;
    }

    public String getRotationChartGoods() {
        return rotationChartGoods;
    }

    public void setRotationChartGoods(String rotationChartGoods) {
        this.rotationChartGoods = rotationChartGoods;
    }

    public String getAdGoods() {
        return adGoods;
    }

    public void setAdGoods(String adGoods) {
        this.adGoods = adGoods;
    }

    public Long getContainerSpecialGoods() {
        return containerSpecialGoods;
    }

    public void setContainerSpecialGoods(Long containerSpecialGoods) {
        this.containerSpecialGoods = containerSpecialGoods;
    }

    public Long getContainerTopGoods() {
        return containerTopGoods;
    }

    public void setContainerTopGoods(Long containerTopGoods) {
        this.containerTopGoods = containerTopGoods;
    }

    public Long getContainerBottomGoods() {
        return containerBottomGoods;
    }

    public void setContainerBottomGoods(Long containerBottomGoods) {
        this.containerBottomGoods = containerBottomGoods;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Goods goods = (Goods) o;
        return Objects.equals(goodsId, goods.goodsId) &&
                Objects.equals(goodsName, goods.goodsName) &&
                Objects.equals(goodsDescribe, goods.goodsDescribe) &&
                Objects.equals(goodsPrice, goods.goodsPrice) &&
                Objects.equals(maxBuyNum, goods.maxBuyNum) &&
                Objects.equals(toggleViewGoods, goods.toggleViewGoods) &&
                Objects.equals(rotationChartGoods, goods.rotationChartGoods) &&
                Objects.equals(adGoods, goods.adGoods) &&
                Objects.equals(containerSpecialGoods, goods.containerSpecialGoods) &&
                Objects.equals(containerTopGoods, goods.containerTopGoods) &&
                Objects.equals(containerBottomGoods, goods.containerBottomGoods);
    }

    @Override
    public int hashCode() {
        return Objects.hash(goodsId, goodsName, goodsDescribe, goodsPrice, maxBuyNum, toggleViewGoods, rotationChartGoods, adGoods, containerSpecialGoods, containerTopGoods, containerBottomGoods);
    }

    @Override
    public String toString() {
        return "Goods{" +
                "goodsId=" + goodsId +
                ", goodsName='" + goodsName + '\'' +
                ", goodsDescribe='" + goodsDescribe + '\'' +
                ", goodsPrice=" + goodsPrice +
                ", maxBuyNum=" + maxBuyNum +
                ", toggleViewGoods=" + toggleViewGoods +
                ", rotationChartGoods='" + rotationChartGoods + '\'' +
                ", adGoods='" + adGoods + '\'' +
                ", containerSpecialGoods=" + containerSpecialGoods +
                ", containerTopGoods=" + containerTopGoods +
                ", containerBottomGoods=" + containerBottomGoods +
                '}';
    }
}
