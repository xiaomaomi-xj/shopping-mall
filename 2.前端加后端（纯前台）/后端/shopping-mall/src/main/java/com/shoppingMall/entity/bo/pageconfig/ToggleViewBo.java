package com.shoppingMall.entity.bo.pageconfig;

import com.shoppingMall.entity.bo.GoodsBo;

import java.io.Serializable;
import java.util.List;

/**
 * 用于实际的标题实际的返回给前端的数据
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/1
 */
public class ToggleViewBo implements Serializable {
    private static final long serialVersionUID = 4760461477543792135L;
    /**
     * 标题名字
     */
    private String titleName;

    /**
     * 用到的商品
     */
    private List<GoodsBo> goodsDatas;

    public ToggleViewBo(){

    }

    public ToggleViewBo(String titleName, List<GoodsBo> goodsDatas) {
        this.titleName = titleName;
        this.goodsDatas = goodsDatas;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public List<GoodsBo> getGoodsDatas() {
        return goodsDatas;
    }

    public void setGoodsDatas(List<GoodsBo> goodsDatas) {
        this.goodsDatas = goodsDatas;
    }

    @Override
    public String toString() {
        return "ToggleViewBo{" +
                "titleName='" + titleName + '\'' +
                ", goodsDatas=" + goodsDatas +
                '}';
    }
}
