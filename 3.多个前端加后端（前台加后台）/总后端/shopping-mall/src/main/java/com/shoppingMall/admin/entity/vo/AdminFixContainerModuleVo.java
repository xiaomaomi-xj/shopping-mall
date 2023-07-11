package com.shoppingMall.admin.entity.vo;

import java.util.List;

/**
 * 修改主要界面vo类(这个界面属性多，就单独开一个类)
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/5/10
 */
public class AdminFixContainerModuleVo {
    /**
     * 主要界面id
     */
    private String containerModuleId;

    /**
     * 主要界面标题
     */
    private String titleName;

    /**
     * 左侧高的商品id
     */
    private List<String> containerSpecialGoodsId;

    /**
     * 上排商品id
     */
    private List<String> containerTopGoodsIds;

    /**
     * 下排商品id
     */
    private List<String> containerBottomGoodsIds;

    public AdminFixContainerModuleVo() {
    }

    public AdminFixContainerModuleVo(String containerModuleId, String titleName, List<String> containerSpecialGoodsId, List<String> containerTopGoodsIds, List<String> containerBottomGoodsIds) {
        this.containerModuleId = containerModuleId;
        this.titleName = titleName;
        this.containerSpecialGoodsId = containerSpecialGoodsId;
        this.containerTopGoodsIds = containerTopGoodsIds;
        this.containerBottomGoodsIds = containerBottomGoodsIds;
    }

    public String getContainerModuleId() {
        return containerModuleId;
    }

    public void setContainerModuleId(String containerModuleId) {
        this.containerModuleId = containerModuleId;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public List<String> getContainerSpecialGoodsId() {
        return containerSpecialGoodsId;
    }

    public void setContainerSpecialGoodsId(List<String> containerSpecialGoodsId) {
        this.containerSpecialGoodsId = containerSpecialGoodsId;
    }

    public List<String> getContainerTopGoodsIds() {
        return containerTopGoodsIds;
    }

    public void setContainerTopGoodsIds(List<String> containerTopGoodsIds) {
        this.containerTopGoodsIds = containerTopGoodsIds;
    }

    public List<String> getContainerBottomGoodsIds() {
        return containerBottomGoodsIds;
    }

    public void setContainerBottomGoodsIds(List<String> containerBottomGoodsIds) {
        this.containerBottomGoodsIds = containerBottomGoodsIds;
    }

    @Override
    public String toString() {
        return "AdminFixContainerModuleVo{" +
                "containerModuleId='" + containerModuleId + '\'' +
                ", titleName='" + titleName + '\'' +
                ", containerSpecialGoodsId=" + containerSpecialGoodsId +
                ", containerTopGoodsIds=" + containerTopGoodsIds +
                ", containerBottomGoodsIds=" + containerBottomGoodsIds +
                '}';
    }
}
