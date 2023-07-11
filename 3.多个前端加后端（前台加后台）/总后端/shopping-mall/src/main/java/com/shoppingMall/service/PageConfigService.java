package com.shoppingMall.service;

import com.shoppingMall.admin.entity.bo.AdminContainerModuleBo;
import com.shoppingMall.admin.entity.bo.AdminPageCheckBo;
import com.shoppingMall.admin.entity.bo.AdminToggleViewBo;
import com.shoppingMall.entity.bo.SingleAllBo;
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

    /**
     * 后台获取标题数据
     *
     * @return
     */
    List<AdminToggleViewBo> getAdminToggleView();

    /**
     * 后台获取轮播图数据
     *
     * @return
     */
    AdminPageCheckBo getAdminRotationChart();

    /**
     * 后台获取广告数据
     *
     * @return
     */
    AdminPageCheckBo getAdminGoodsAd();


    /**
     * 后台获取主要界面数据
     *
     * @return
     */
    List<AdminContainerModuleBo> getAdminContainerModule();

    /**
     * 添加标题界面数据
     *
     * @param titleName
     * @param goodsIds
     * @return
     */
    SingleAllBo<Boolean> addToggleView(String titleName, List<String> goodsIds);

    /**
     * 添加主要界面数据
     *
     * @param titleName
     * @param goodsIds
     * @return
     */
    SingleAllBo<Boolean> addContainerModule(String titleName, List<String> goodsIds);

    /**
     * 删除标题界面
     *
     * @param id
     * @return
     */
    SingleAllBo<Boolean> delToggleView(String id);

    /**
     * 删除主要界面
     *
     * @param id
     * @return
     */
    SingleAllBo<Boolean> delContainerModule(String id);

    /**
     * 修改标题界面
     *
     * @param toggleViewId
     * @param titleName
     * @param goodsIds
     * @return
     */
    SingleAllBo<Boolean> fixToggleView(String toggleViewId, String titleName, List<String> goodsIds);

    /**
     * 修改轮播图
     *
     * @param goodsIds
     * @return
     */
    SingleAllBo<Boolean> fixRotationChart(List<String> goodsIds);

    /**
     * 修改广告界面
     *
     * @param goodsIds
     * @return
     */
    SingleAllBo<Boolean> fixGoodsAd(List<String> goodsIds);

    /**
     * 修改主要界面
     *
     * @param containerModuleId
     * @param titleName
     * @param containerSpecialGoodsId
     * @param containerTopGoodsIds
     * @param containerBottomGoodsIds
     * @return
     */
    SingleAllBo<Boolean> fixContainerModule(String containerModuleId, String titleName, List<String> containerSpecialGoodsId, List<String> containerTopGoodsIds, List<String> containerBottomGoodsIds);
}
