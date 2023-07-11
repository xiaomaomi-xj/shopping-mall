package com.shoppingMall.entity.bo.pageconfig;

import com.shoppingMall.entity.bo.GoodsBo;

import java.io.Serializable;
import java.util.List;

/**
 * 轮播图实际的返回给前端的数据
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/1
 */
public class RotationChartBo implements Serializable {
    private static final long serialVersionUID = 6944966015865778485L;
    /**
     * 用到的商品数据
     */
    private List<GoodsBo> goodsDatas;

    public RotationChartBo(){

    }

    public RotationChartBo(List<GoodsBo> goodsDatas) {
        this.goodsDatas = goodsDatas;
    }

    public List<GoodsBo> getGoodsDatas() {
        return goodsDatas;
    }

    public void setGoodsDatas(List<GoodsBo> goodsDatas) {
        this.goodsDatas = goodsDatas;
    }

    @Override
    public String toString() {
        return "RotationChartBo{" +
                "goodsDatas=" + goodsDatas +
                '}';
    }
}
