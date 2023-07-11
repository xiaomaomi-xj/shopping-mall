package com.shoppingMall.admin.entity.bo;

import java.util.List;

/**
 * 用于后台获取主要界面数据
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/5/9
 */
public class AdminContainerModuleBo {
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
    private String specialGoodsInfo;

    /**
     * 上排商品id
     */
    private List<String>  topGoodsInfo;

    /**
     * 下排商品
     */
    private List<String> bottomGoodsInfo;

    public AdminContainerModuleBo() {
    }

    public AdminContainerModuleBo(String containerModuleId, String titleName, String specialGoodsInfo, List<String> topGoodsInfo, List<String> bottomGoodsInfo) {
        this.containerModuleId = containerModuleId;
        this.titleName = titleName;
        this.specialGoodsInfo = specialGoodsInfo;
        this.topGoodsInfo = topGoodsInfo;
        this.bottomGoodsInfo = bottomGoodsInfo;
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

    public String getSpecialGoodsInfo() {
        return specialGoodsInfo;
    }

    public void setSpecialGoodsInfo(String specialGoodsInfo) {
        this.specialGoodsInfo = specialGoodsInfo;
    }

    public List<String> getTopGoodsInfo() {
        return topGoodsInfo;
    }

    public void setTopGoodsInfo(List<String> topGoodsInfo) {
        this.topGoodsInfo = topGoodsInfo;
    }

    public List<String> getBottomGoodsInfo() {
        return bottomGoodsInfo;
    }

    public void setBottomGoodsInfo(List<String> bottomGoodsInfo) {
        this.bottomGoodsInfo = bottomGoodsInfo;
    }

    @Override
    public String toString() {
        return "AdminContainerModuleBo{" +
                "containerModuleId='" + containerModuleId + '\'' +
                ", titleName='" + titleName + '\'' +
                ", specialGoodsInfo='" + specialGoodsInfo + '\'' +
                ", topGoodsInfo=" + topGoodsInfo +
                ", bottomGoodsInfo=" + bottomGoodsInfo +
                '}';
    }
}
