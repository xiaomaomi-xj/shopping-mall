package com.shoppingMall.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.shoppingMall.admin.entity.bo.AdminContainerModuleBo;
import com.shoppingMall.admin.entity.bo.AdminPageCheckBo;
import com.shoppingMall.admin.entity.bo.AdminToggleViewBo;
import com.shoppingMall.dao.ContainerModuleDao;
import com.shoppingMall.dao.GoodsDao;
import com.shoppingMall.dao.ImgDao;
import com.shoppingMall.dao.ToggleViewDao;
import com.shoppingMall.entity.bo.SingleAllBo;
import com.shoppingMall.entity.bo.pageconfig.ContainerModuleBo;
import com.shoppingMall.entity.bo.pageconfig.GoodsAdBo;
import com.shoppingMall.entity.bo.pageconfig.RotationChartBo;
import com.shoppingMall.entity.bo.pageconfig.ToggleViewBo;
import com.shoppingMall.entity.po.ContainerModule;
import com.shoppingMall.entity.po.Goods;
import com.shoppingMall.entity.po.ToggleView;
import com.shoppingMall.exception.AppException;
import com.shoppingMall.exception.BizException;
import com.shoppingMall.service.PageConfigService;
import com.shoppingMall.utils.ConvertUtil;
import com.zhuyahui.annotation.ZyhDataSourceRead;
import com.zhuyahui.annotation.ZyhService;
import com.zhuyahui.util.MyAloneHandlerReadWrite;

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

    /**
     * 后台获取标题数据
     */
    @Override
    public List<AdminToggleViewBo> getAdminToggleView() {
        List<AdminToggleViewBo> adminToggleViewBoList = new ArrayList<>();
        List<ToggleView> toggleViewList = toggleViewDao.findAll();
        toggleViewList.forEach(toggleView -> {
            List<Goods> all = goodsDao.findAllByToggleViewGoods(toggleView.getToggleViewId());
            List<String> goodsIds = new ArrayList<>();
            all.forEach(goods -> {
                goodsIds.add(String.valueOf(goods.getGoodsId()));
            });
            adminToggleViewBoList.add(new AdminToggleViewBo(
                    String.valueOf(toggleView.getToggleViewId()),
                    toggleView.getTitleName(),
                    goodsIds
            ));
        });
        return adminToggleViewBoList;
    }

    /**
     * 后台获取轮播图数据
     */
    @Override
    public AdminPageCheckBo getAdminRotationChart() {
        List<String> goodsIds = new ArrayList<>();
        List<Goods> goodsList = goodsDao.findAllByRotationChartGoods("check");
        goodsList.forEach(goods -> goodsIds.add(String.valueOf(goods.getGoodsId())));
        return new AdminPageCheckBo(
                goodsIds
        );
    }

    /**
     * 后台获取广告数据
     */
    @Override
    public AdminPageCheckBo getAdminGoodsAd() {
        List<String> goodsIds = new ArrayList<>();
        List<Goods> goodsList = goodsDao.findAllByAdGoods("check");
        goodsList.forEach(goods -> goodsIds.add(String.valueOf(goods.getGoodsId())));
        return new AdminPageCheckBo(
                goodsIds
        );
    }

    /**
     * 后台获取主要界面数据
     */
    @Override
    public List<AdminContainerModuleBo> getAdminContainerModule() {
        List<AdminContainerModuleBo> adminContainerModuleBoList = new ArrayList<>();
        List<ContainerModule> all = containerModuleDao.findAll();
        all.forEach(containerModule -> {
            List<String> topGoodsInfo = new ArrayList<>();
            List<String> bottomGoodsInfo = new ArrayList<>();
            Long containerModuleId = containerModule.getContainerModuleId();
            Goods byContainerSpecialGoods = goodsDao.findByContainerSpecialGoods(containerModuleId);
            List<Goods> allByContainerTopGoods = goodsDao.findAllByContainerTopGoods(containerModuleId);
            List<Goods> allByContainerBottomGoods = goodsDao.findAllByContainerBottomGoods(containerModuleId);
            allByContainerTopGoods.forEach(goods -> topGoodsInfo.add(String.valueOf(goods.getGoodsId())));
            allByContainerBottomGoods.forEach(goods -> bottomGoodsInfo.add(String.valueOf(goods.getGoodsId())));
            adminContainerModuleBoList.add(new AdminContainerModuleBo(
                    String.valueOf(containerModuleId),
                    containerModule.getTitleName(),
                    String.valueOf(byContainerSpecialGoods.getGoodsId()),
                    topGoodsInfo,
                    bottomGoodsInfo
            ));
        });
        return adminContainerModuleBoList;
    }

    /**
     * 添加标题界面数据
     *
     * @param titleName
     * @param goodsIds
     * @return
     */
    @Override
    public SingleAllBo<Boolean> addToggleView(String titleName, List<String> goodsIds) {
        List<Goods> goodsList = new ArrayList<>();
        long toggleViewId = IdUtil.getSnowflakeNextId();
        ToggleView toggleView = new ToggleView(
                toggleViewId,
                titleName
        );
        for (String goodsId : goodsIds) {
            Goods goods = goodsDao.findById(Long.parseLong(goodsId)).orElseThrow(() -> {
                throw new AppException("未查询到商品！");
            });
            if (ObjectUtil.isNotEmpty(goods.getToggleViewGoods())) {
                throw new BizException("你选择的商品中有的商品已被使用，不能重复选择！");
            }
            goods.setToggleViewGoods(toggleViewId);
            goodsList.add(goods);
        }
        MyAloneHandlerReadWrite.write(() -> {
            toggleViewDao.save(toggleView);
            goodsDao.saveAll(goodsList);
            return null;
        });
        return new SingleAllBo<>(true);
    }

    /**
     * 添加主要界面数据
     *
     * @param titleName
     * @param goodsIds
     * @return
     */
    @Override
    public SingleAllBo<Boolean> addContainerModule(String titleName, List<String> goodsIds) {
        List<Goods> goodsList = new ArrayList<>();
        long containerModuleId = IdUtil.getSnowflakeNextId();
        ContainerModule containerModule = new ContainerModule(
                containerModuleId,
                titleName
        );
        int index = 0;
        for (String goodsId : goodsIds) {
            Goods goods = goodsDao.findById(Long.parseLong(goodsId)).orElseThrow(() -> {
                throw new AppException("未查询到商品！");
            });
            if (ObjectUtil.isNotEmpty(goods.getContainerSpecialGoods()) || ObjectUtil.isNotEmpty(goods.getContainerTopGoods()) || ObjectUtil.isNotEmpty(goods.getContainerBottomGoods())) {
                throw new BizException("你选择的商品中有的商品已被使用，不能重复选择！");
            }
            if (index == 0) {
                goods.setContainerSpecialGoods(containerModuleId);
            } else if (index <= 4) {
                goods.setContainerTopGoods(containerModuleId);
            } else {
                goods.setContainerBottomGoods(containerModuleId);
            }
            index++;
            goodsList.add(goods);
        }
        MyAloneHandlerReadWrite.write(() -> {
            containerModuleDao.save(containerModule);
            goodsDao.saveAll(goodsList);
            return null;
        });
        return new SingleAllBo<>(true);
    }

    /**
     * 删除标题界面
     *
     * @param id
     * @return
     */
    @Override
    public SingleAllBo<Boolean> delToggleView(String id) {
        long toggleViewId = Long.parseLong(id);
        ToggleView toggleView = toggleViewDao.findById(toggleViewId).orElseThrow(() -> {
            throw new BizException("未查到标题界面！");
        });
        List<Goods> allByToggleViewGoods = goodsDao.findAllByToggleViewGoods(toggleViewId);
        allByToggleViewGoods.forEach(goods -> goods.setToggleViewGoods(null));
        MyAloneHandlerReadWrite.write(() -> {
            toggleViewDao.delete(toggleView);
            goodsDao.saveAll(allByToggleViewGoods);
            return null;
        });
        return new SingleAllBo<>(true);
    }

    /**
     * 删除主要界面
     *
     * @param id
     * @return
     */
    @Override
    public SingleAllBo<Boolean> delContainerModule(String id) {
        long containerModuleId = Long.parseLong(id);
        ContainerModule containerModule = containerModuleDao.findById(containerModuleId).orElseThrow(() -> {
            throw new BizException("未查到主要界面！");
        });
        Goods byContainerSpecialGoods = goodsDao.findByContainerSpecialGoods(containerModuleId);
        byContainerSpecialGoods.setContainerSpecialGoods(null);
        List<Goods> allByContainerTopGoods = goodsDao.findAllByContainerTopGoods(containerModuleId);
        allByContainerTopGoods.forEach(goods -> goods.setContainerTopGoods(null));
        List<Goods> allByContainerBottomGoods = goodsDao.findAllByContainerBottomGoods(containerModuleId);
        allByContainerBottomGoods.forEach(goods -> goods.setContainerBottomGoods(null));
        MyAloneHandlerReadWrite.write(() -> {
            containerModuleDao.delete(containerModule);
            goodsDao.save(byContainerSpecialGoods);
            goodsDao.saveAll(allByContainerTopGoods);
            goodsDao.saveAll(allByContainerBottomGoods);
            return null;
        });
        return new SingleAllBo<>(true);
    }

    /**
     * 修改标题界面
     *
     * @param toggleViewId
     * @param titleName
     * @param goodsIds
     * @return
     */
    @Override
    public SingleAllBo<Boolean> fixToggleView(String toggleViewId, String titleName, List<String> goodsIds) {
        long id = Long.parseLong(toggleViewId);
        ToggleView toggleView = toggleViewDao.findById(id).orElseThrow(() -> {
            throw new BizException("未找到标题界面！");
        });
        toggleView.setTitleName(titleName);
        List<Goods> allByToggleViewGoods = goodsDao.findAllByToggleViewGoods(id);
        allByToggleViewGoods.forEach(goods -> goods.setToggleViewGoods(null));
        List<Goods> goodsList = new ArrayList<>();
        for (String goodsId : goodsIds) {
            Goods goods = goodsDao.findById(Long.parseLong(goodsId)).orElseThrow(() -> {
                throw new BizException("未查询到商品！");
            });
            if (ObjectUtil.isNotEmpty(goods.getToggleViewGoods()) && !goods.getToggleViewGoods().equals(id)) {
                throw new BizException("你选择的商品中有的商品已被使用，不能重复选择！");
            }
            goods.setToggleViewGoods(id);
            goodsList.add(goods);
        }
        MyAloneHandlerReadWrite.write(() -> {
            toggleViewDao.save(toggleView);
            goodsDao.saveAll(allByToggleViewGoods);
            goodsDao.saveAll(goodsList);
            return null;
        });
        return new SingleAllBo<>(true);
    }

    /**
     * 修改轮播图
     *
     * @param goodsIds
     * @return
     */
    @Override
    public SingleAllBo<Boolean> fixRotationChart(List<String> goodsIds) {
        List<Goods> check = goodsDao.findAllByRotationChartGoods("check");
        check.forEach(goods -> goods.setRotationChartGoods(null));
        List<Goods> goodsList = new ArrayList<>();
        for (String goodsId : goodsIds) {
            Goods goods = goodsDao.findById(Long.parseLong(goodsId)).orElseThrow(() -> {
                throw new BizException("未查询到商品！");
            });
            goods.setRotationChartGoods("check");
            goodsList.add(goods);
        }
        MyAloneHandlerReadWrite.write(() -> {
            goodsDao.saveAll(check);
            goodsDao.saveAll(goodsList);
            return null;
        });
        return new SingleAllBo<>(true);
    }

    /**
     * 修改广告界面
     *
     * @param goodsIds
     * @return
     */
    @Override
    public SingleAllBo<Boolean> fixGoodsAd(List<String> goodsIds) {
        List<Goods> check = goodsDao.findAllByAdGoods("check");
        check.forEach(goods -> goods.setAdGoods(null));
        List<Goods> goodsList = new ArrayList<>();
        for (String goodsId : goodsIds) {
            Goods goods = goodsDao.findById(Long.parseLong(goodsId)).orElseThrow(() -> {
                throw new BizException("未查询到商品！");
            });
            goods.setAdGoods("check");
            goodsList.add(goods);
        }
        MyAloneHandlerReadWrite.write(() -> {
            goodsDao.saveAll(check);
            goodsDao.saveAll(goodsList);
            return null;
        });
        return new SingleAllBo<>(true);
    }

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
    @Override
    public SingleAllBo<Boolean> fixContainerModule(String containerModuleId, String titleName, List<String> containerSpecialGoodsId, List<String> containerTopGoodsIds, List<String> containerBottomGoodsIds) {
        long id = Long.parseLong(containerModuleId);
        ContainerModule containerModule = containerModuleDao.findById(id).orElseThrow(() -> {
            throw new BizException("未找到主要界面！");
        });
        containerModule.setTitleName(titleName);
        Goods byContainerSpecialGoods = goodsDao.findByContainerSpecialGoods(id);
        byContainerSpecialGoods.setContainerSpecialGoods(null);
        List<Goods> allByContainerTopGoods = goodsDao.findAllByContainerTopGoods(id);
        allByContainerTopGoods.forEach(goods -> goods.setContainerTopGoods(null));
        List<Goods> allByContainerBottomGoods = goodsDao.findAllByContainerBottomGoods(id);
        allByContainerBottomGoods.forEach(goods -> goods.setContainerBottomGoods(null));
        Goods containerSpecialGoods = goodsDao.findById(Long.parseLong(containerSpecialGoodsId.get(0))).orElseThrow(() -> {
            throw new BizException("未查询到商品！");
        });
        if (ObjectUtil.isNotEmpty(containerSpecialGoods.getContainerSpecialGoods()) && !containerSpecialGoods.getContainerSpecialGoods().equals(id)) {
            throw new BizException("你选择的左侧高的商品中有的商品已被使用，不能重复选择！");
        }
        containerSpecialGoods.setContainerSpecialGoods(id);
        List<Goods> containerTopGoodsList = new ArrayList<>();
        for (String goodsIdStr : containerTopGoodsIds) {
            Goods goods = goodsDao.findById(Long.parseLong(goodsIdStr)).orElseThrow(() -> {
                throw new BizException("未查询到商品！");
            });
            if (ObjectUtil.isNotEmpty(goods.getContainerTopGoods()) && !goods.getContainerTopGoods().equals(id)) {
                throw new BizException("你选择的上排商品中有的商品已被使用，不能重复选择！");
            }
            goods.setContainerTopGoods(id);
            containerTopGoodsList.add(goods);
        }
        List<Goods> containerBottomGoodsList = new ArrayList<>();
        for (String goodsIdStr : containerBottomGoodsIds) {
            Goods goods = goodsDao.findById(Long.parseLong(goodsIdStr)).orElseThrow(() -> {
                throw new BizException("未查询到商品！");
            });
            if (ObjectUtil.isNotEmpty(goods.getContainerBottomGoods()) && !goods.getContainerBottomGoods().equals(id)) {
                throw new BizException("你选择的下排商品中有的商品已被使用，不能重复选择！");
            }
            goods.setContainerBottomGoods(id);
            containerBottomGoodsList.add(goods);
        }
        MyAloneHandlerReadWrite.write(() -> {
            containerModuleDao.save(containerModule);
            goodsDao.save(byContainerSpecialGoods);
            goodsDao.saveAll(allByContainerTopGoods);
            goodsDao.saveAll(allByContainerBottomGoods);
            goodsDao.save(containerSpecialGoods);
            goodsDao.saveAll(containerTopGoodsList);
            goodsDao.saveAll(containerBottomGoodsList);
            return null;
        });
        return new SingleAllBo<>(true);
    }
}
