package com.shoppingMall.admin.entity.bo;

import java.util.List;

/**
 * 用于后台获取标题数据
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/5/9
 */
public class AdminToggleViewBo {
    /**
     * 标题id
     */
    private String toggleViewId;

    /**
     * 标题
     */
    private String titleName;

    /**
     * 包含的上排id
     */
    private List<String> goodsIds;

    public AdminToggleViewBo() {
    }

    public AdminToggleViewBo(String toggleViewId, String titleName, List<String> goodsIds) {
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
        return "AdminToggleViewBo{" +
                "toggleViewId='" + toggleViewId + '\'' +
                ", titleName='" + titleName + '\'' +
                ", goodsIds=" + goodsIds +
                '}';
    }
}
