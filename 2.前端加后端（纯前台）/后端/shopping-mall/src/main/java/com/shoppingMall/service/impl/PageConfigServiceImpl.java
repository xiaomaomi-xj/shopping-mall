package com.shoppingMall.service.impl;

import com.shoppingMall.dao.ContainerModuleDao;
import com.shoppingMall.dao.GoodsDao;
import com.shoppingMall.dao.ImgDao;
import com.shoppingMall.dao.ToggleViewDao;
import com.shoppingMall.entity.bo.pageconfig.ContainerModuleBo;
import com.shoppingMall.entity.bo.pageconfig.GoodsAdBo;
import com.shoppingMall.entity.bo.pageconfig.RotationChartBo;
import com.shoppingMall.entity.bo.pageconfig.ToggleViewBo;
import com.shoppingMall.entity.po.ContainerModule;
import com.shoppingMall.entity.po.Goods;
import com.shoppingMall.entity.po.ToggleView;
import com.shoppingMall.exception.BizException;
import com.shoppingMall.service.PageConfigService;
import com.shoppingMall.utils.ConvertUtil;
import com.zhuyahui.annotation.ZyhDataSourceRead;
import com.zhuyahui.annotation.ZyhService;

import java.util.ArrayList;
import java.util.List;

/**
 * 页面整体操作
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/2/28
 */
@ZyhService
@ZyhDataSourceRead
public class PageConfigServiceImpl implements PageConfigService {

    private final ContainerModuleDao containerModuleDao;
    private final ToggleViewDao toggleViewDao;
    private final GoodsDao goodsDao;
    private final ImgDao imgDao;

    public PageConfigServiceImpl(ContainerModuleDao containerModuleDao, ToggleViewDao toggleViewDao, GoodsDao goodsDao, ImgDao imgDao) {
        this.containerModuleDao = containerModuleDao;
        this.toggleViewDao = toggleViewDao;
        this.goodsDao = goodsDao;
        this.imgDao = imgDao;
    }

    /**
     * 获取页面标题数据
     * 对于缓存是永久存在，如果后续更新，也会更新缓存 @CachePut用它去进行更新缓存
     * 缓存只用于有后台的时候，否者做调试的时候，修改数据，可能界面没有变化，让人疑惑
     *
     * @return
     * @Cacheable(value = "pageConfig", key = "'toggleView'", sync = true)
     */
    @Override
    public List<ToggleViewBo> getToggleView() {
        List<ToggleView> allToggleView = toggleViewDao.findAll();
        if (allToggleView.isEmpty()) {
            throw new BizException("未查询到页面标题数据！");
        }
        List<ToggleViewBo> allToggleViewBo = new ArrayList<>();
        for (ToggleView toggleView : allToggleView) {
            Long toggleViewId = toggleView.getToggleViewId();
            List<Goods> allByToggleViewGoods = goodsDao.findAllByToggleViewGoods(toggleViewId);
            allToggleViewBo.add(new ToggleViewBo(
                    toggleView.getTitleName(),
                    ConvertUtil.batchGoodsToGoodsBo(allByToggleViewGoods, imgDao)
            ));
        }
        return allToggleViewBo;
    }

    /**
     * 获取轮播图数据
     * 对于缓存是永久存在，如果后续更新，也会更新缓存 @CachePut用它去进行更新缓存
     * 缓存只用于有后台的时候，否者做调试的时候，修改数据，可能界面没有变化，让人疑惑
     *
     * @return
     * @Cacheable(value = "pageConfig", key = "'rotationChart'", sync = true)
     */
    @Override
    public RotationChartBo getRotationChart() {
        List<Goods> checkGoods = goodsDao.findAllByRotationChartGoods("check");
        if (checkGoods.isEmpty()) {
            throw new BizException("未查询到轮播图数据！");
        }
        return new RotationChartBo(
                ConvertUtil.batchGoodsToGoodsBo(checkGoods, imgDao)
        );
    }

    /**
     * 获取广告数据
     * 对于缓存是永久存在，如果后续更新，也会更新缓存 @CachePut用它去进行更新缓存
     * 缓存只用于有后台的时候，否者做调试的时候，修改数据，可能界面没有变化，让人疑惑
     *
     * @return
     * @Cacheable(value = "pageConfig", key = "'goodsAd'", sync = true)
     */
    @Override
    public GoodsAdBo getGoodsAd() {
        List<Goods> checkGoods = goodsDao.findAllByAdGoods("check");
        if (checkGoods.isEmpty()) {
            throw new BizException("未查询到广告数据！");
        }
        return new GoodsAdBo(
                ConvertUtil.batchGoodsToGoodsBo(checkGoods, imgDao)
        );
    }

    /**
     * 获取主要界面数据
     * 对于缓存是永久存在，如果后续更新，也会更新缓存 @CachePut用它去进行更新缓存
     * 缓存只用于有后台的时候，否者做调试的时候，修改数据，可能界面没有变化，让人疑惑
     *
     * @return
     * @Cacheable(value = "pageConfig", key = "'containerModule'", sync = true)
     */
    @Override
    public List<ContainerModuleBo> getContainerModule() {
        List<ContainerModule> allContainerModule = containerModuleDao.findAll();
        if (allContainerModule.isEmpty()) {
            throw new BizException("未查询到界面数据！");
        }
        List<ContainerModuleBo> allContainerModuleBo = new ArrayList<>();
        for (ContainerModule containerModule : allContainerModule) {
            String titleName = containerModule.getTitleName();
            Long containerModuleId = containerModule.getContainerModuleId();
            Goods byContainerSpecialGoods = goodsDao.findByContainerSpecialGoods(containerModuleId);
            List<Goods> allByContainerTopGoods = goodsDao.findAllByContainerTopGoods(containerModuleId);
            List<Goods> allByContainerBottomGoods = goodsDao.findAllByContainerBottomGoods(containerModuleId);
            allContainerModuleBo.add(new ContainerModuleBo(
                    titleName,
                    ConvertUtil.goodsToGoodsBo(byContainerSpecialGoods, imgDao),
                    ConvertUtil.batchGoodsToGoodsBo(allByContainerTopGoods, imgDao),
                    ConvertUtil.batchGoodsToGoodsBo(allByContainerBottomGoods, imgDao)
            ));
        }
        return allContainerModuleBo;
    }
}
