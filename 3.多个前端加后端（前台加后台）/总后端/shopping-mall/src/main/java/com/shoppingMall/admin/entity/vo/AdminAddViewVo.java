package com.shoppingMall.admin.entity.vo;

import java.util.List;

/**
 * 接受标题界面和主要界面的vo类
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/5/10
 */
public class AdminAddViewVo {
    /**
     * 标题名字
     */
    private String titleName;

    /**
     * 选择的商品
     */
    private List<String> goodsIds;

    public AdminAddViewVo() {
    }

    public AdminAddViewVo(String titleName, List<String> goodsIds) {
        this.titleName = titleName;
        this.goodsIds = goodsIds;
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
        return "AdminAddViewVo{" +
                "titleName='" + titleName + '\'' +
                ", goodsIds=" + goodsIds +
                '}';
    }
}
