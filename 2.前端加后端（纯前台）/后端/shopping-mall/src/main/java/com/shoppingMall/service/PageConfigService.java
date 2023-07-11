package com.shoppingMall.service;

import com.shoppingMall.entity.bo.pageconfig.ContainerModuleBo;
import com.shoppingMall.entity.bo.pageconfig.GoodsAdBo;
import com.shoppingMall.entity.bo.pageconfig.RotationChartBo;
import com.shoppingMall.entity.bo.pageconfig.ToggleViewBo;

import java.util.List;

/**
 * 页面整体操作
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/2/28
 */
public interface PageConfigService {
    /**
     * 获取页面配置
     *
     * @return
     */
    List<ToggleViewBo> getToggleView();

    /**
     * 获取轮播图数据
     *
     * @return
     */
    RotationChartBo getRotationChart();

    /**
     * 获取广告数据
     *
     * @return
     */
    GoodsAdBo getGoodsAd();

    /**
     * 获取主要界面数据
     *
     * @return
     */
    List<ContainerModuleBo> getContainerModule();
}
