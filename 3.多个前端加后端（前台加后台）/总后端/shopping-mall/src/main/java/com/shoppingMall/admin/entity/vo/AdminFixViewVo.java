package com.shoppingMall.admin.entity.vo;

import java.util.List;

/**
 * 修改标题界面vo类（轮播图和广告都可以用这个）
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/5/10
 */
public class AdminFixViewVo {
    /**
     * 标题界面id
     */
    private String toggleViewId;

    /**
     * 标题界面标题
     */
    private String titleName;

    /**
     * 标题界面和轮播图和广告所选择的商品id
     */
    private List<String> goodsIds;

    public AdminFixViewVo() {
    }

    public AdminFixViewVo(String toggleViewId, String titleName, List<String> goodsIds) {
        this.toggleViewId = toggleViewId;
        this.titleName = titleName;
        this.goodsIds = goodsIds;
    }

    public String getToggleViewId() {
        return toggleViewId;
    }

    public void setToggleViewId(String toggleViewId) {
        this.toggleViewId = toggleViewId;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public List<String> getGoodsIds() {
        return goodsIds;
    }

    public void setGoodsIds(List<String> goodsIds) {
        this.goodsIds = goodsIds;
    }

    @Override
    public String toString() {
        return "AdminFixViewVo{" +
                "toggleViewId='" + toggleViewId + '\'' +
                ", titleName='" + titleName + '\'' +
                ", goodsIds=" + goodsIds +
                '}';
    }
}
