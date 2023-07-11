package com.shoppingMall.entity.bo.pageconfig;

import com.shoppingMall.entity.bo.GoodsBo;

import java.io.Serializable;
import java.util.List;

/**
 * 主要界面实际的返回给前端的数据
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/1
 */
public class ContainerModuleBo implements Serializable {

    private static final long serialVersionUID = -1576382202657369397L;
    /**
     * 标题名字
     */
    private String titleName;

    /**
     * 左侧商品数据
     */
    private GoodsBo specialGoodsInfo;

    /**
     * 右侧上排商品数据
     */
    private List<GoodsBo> topGoodsInfo;

    /**
     * 右侧下排商品数据
     */
    private List<GoodsBo> bottomGoodsInfo;

    public ContainerModuleBo(){

    }

    public ContainerModuleBo(String titleName, GoodsBo specialGoodsInfo, List<GoodsBo> topGoodsInfo, List<GoodsBo> bottomGoodsInfo) {
        this.titleName = titleName;
        this.specialGoodsInfo = specialGoodsInfo;
        this.topGoodsInfo = topGoodsInfo;
        this.bottomGoodsInfo = bottomGoodsInfo;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public GoodsBo getSpecialGoodsInfo() {
        return specialGoodsInfo;
    }

    public void setSpecialGoodsInfo(GoodsBo specialGoodsInfo) {
        this.specialGoodsInfo = specialGoodsInfo;
    }

    public List<GoodsBo> getTopGoodsInfo() {
        return topGoodsInfo;
    }

    public void setTopGoodsInfo(List<GoodsBo> topGoodsInfo) {
        this.topGoodsInfo = topGoodsInfo;
    }

    public List<GoodsBo> getBottomGoodsInfo() {
        return bottomGoodsInfo;
    }

    public void setBottomGoodsInfo(List<GoodsBo> bottomGoodsInfo) {
        this.bottomGoodsInfo = bottomGoodsInfo;
    }

    @Override
    public String toString() {
        return "ContainerModuleBo{" +
                "titleName='" + titleName + '\'' +
                ", specialGoodsInfo=" + specialGoodsInfo +
                ", topGoodsInfo=" + topGoodsInfo +
                ", bottomGoodsInfo=" + bottomGoodsInfo +
                '}';
    }
}
