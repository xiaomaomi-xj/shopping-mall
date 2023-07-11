package com.shoppingMall.controller;

import com.shoppingMall.admin.annotation.CheckAdmin;
import com.shoppingMall.admin.entity.bo.AdminContainerModuleBo;
import com.shoppingMall.admin.entity.bo.AdminPageCheckBo;
import com.shoppingMall.admin.entity.bo.AdminToggleViewBo;
import com.shoppingMall.admin.entity.vo.AdminAddViewVo;
import com.shoppingMall.admin.entity.vo.AdminFixContainerModuleVo;
import com.shoppingMall.admin.entity.vo.AdminFixViewVo;
import com.shoppingMall.entity.bo.SingleAllBo;
import com.shoppingMall.entity.bo.pageconfig.ContainerModuleBo;
import com.shoppingMall.entity.bo.pageconfig.GoodsAdBo;
import com.shoppingMall.entity.bo.pageconfig.RotationChartBo;
import com.shoppingMall.entity.bo.pageconfig.ToggleViewBo;
import com.shoppingMall.entity.vo.IdVo;
import com.shoppingMall.exception.BizException;
import com.shoppingMall.service.PageConfigService;
import com.shoppingMall.utils.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 页面配置接口
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/1
 */
@RestController
@RequestMapping("/page_config")
public class PageConfigController {
    private final PageConfigService pageConfigService;

    public PageConfigController(PageConfigService pageConfigService) {
        this.pageConfigService = pageConfigService;
    }

    /**
     * 后台获取标题数据
     *
     * @return
     */
    @PostMapping("/get_admin_toggle_view")
    @CheckAdmin
    public List<AdminToggleViewBo> getAdminToggleView() {
        return pageConfigService.getAdminToggleView();
    }

    /**
     * 添加标题界面数据
     *
     * @param adminAddViewVo
     * @return
     */
    @PostMapping("/add_toggle_view")
    @CheckAdmin
    public SingleAllBo<Boolean> addToggleView(@RequestBody AdminAddViewVo adminAddViewVo) {
        String titleName = adminAddViewVo.getTitleName();
        Assert.isNotBlank(titleName, "标题界面标题");
        List<String> goodsIds = adminAddViewVo.getGoodsIds();
        Assert.isNotNull(goodsIds, "选择的商品");
        if (goodsIds.size() < 1 || goodsIds.size() > 7) {
            throw new BizException("选择的商品数量不得小于1个，不得大于7个！");
        }
        return pageConfigService.addToggleView(titleName, goodsIds);
    }

    /**
     * 删除标题界面数据
     *
     * @param idVo
     * @return
     */
    @PostMapping("/del_toggle_view")
    @CheckAdmin
    public SingleAllBo<Boolean> delToggleView(@RequestBody IdVo idVo) {
        String id = idVo.getId();
        Assert.isNotBlank(id, "标题界面id");
        return pageConfigService.delToggleView(id);
    }

    /**
     * 修改标题界面
     *
     * @param adminFixViewVo
     * @return
     */
    @PostMapping("/fix_toggle_view")
    @CheckAdmin
    public SingleAllBo<Boolean> fixToggleView(@RequestBody AdminFixViewVo adminFixViewVo) {
        String titleName = adminFixViewVo.getTitleName();
        String toggleViewId = adminFixViewVo.getToggleViewId();
        List<String> goodsIds = adminFixViewVo.getGoodsIds();
        Assert.isNotBlank(titleName, "标题界面标题名字");
        Assert.isNotBlank(toggleViewId, "标题界面id");
        Assert.isNotNull(goodsIds, "选择的商品");
        if (goodsIds.size() < 1 || goodsIds.size() > 7) {
            throw new BizException("选择的商品数量不得小于1个，不得大于7个！");
        }
        return pageConfigService.fixToggleView(toggleViewId, titleName, goodsIds);
    }


    /**
     * 后台获取轮播图数据
     *
     * @return
     */
    @PostMapping("/get_admin_rotation_chart")
    @CheckAdmin
    public AdminPageCheckBo getAdminRotationChart() {
        return pageConfigService.getAdminRotationChart();
    }

    /**
     * 修改轮播图
     *
     * @param adminFixViewVo
     * @return
     */
    @PostMapping("/fix_rotation_chart")
    @CheckAdmin
    public SingleAllBo<Boolean> fixRotationChart(@RequestBody AdminFixViewVo adminFixViewVo) {
        List<String> goodsIds = adminFixViewVo.getGoodsIds();
        Assert.isNotNull(goodsIds, "选择的商品");
        if (goodsIds.size() < 4 || goodsIds.size() > 6) {
            throw new BizException("轮播图的商品数量最好是4至6个！");
        }
        return pageConfigService.fixRotationChart(goodsIds);
    }

    /**
     * 后台获取广告数据
     *
     * @return
     */
    @PostMapping("/get_admin_goods_ad")
    @CheckAdmin
    public AdminPageCheckBo getAdminGoodsAd() {
        return pageConfigService.getAdminGoodsAd();
    }

    /**
     * 修改广告
     *
     * @param adminFixViewVo
     * @return
     */
    @PostMapping("/fix_goods_ad")
    @CheckAdmin
    public SingleAllBo<Boolean> fixGoodsAd(@RequestBody AdminFixViewVo adminFixViewVo) {
        List<String> goodsIds = adminFixViewVo.getGoodsIds();
        Assert.isNotNull(goodsIds, "选择的商品");
        if (goodsIds.size() != 2) {
            throw new BizException("广告界面的商品数量必须为2个！");
        }
        return pageConfigService.fixGoodsAd(goodsIds);
    }

    /**
     * 后台获取主要界面数据
     *
     * @return
     */
    @PostMapping("/get_admin_container_module")
    @CheckAdmin
    public List<AdminContainerModuleBo> getAdminContainerModule() {
        return pageConfigService.getAdminContainerModule();
    }

    /**
     * 添加主要界面数据
     *
     * @param adminAddViewVo
     * @return
     */
    @PostMapping("/add_container_module")
    @CheckAdmin
    public SingleAllBo<Boolean> addContainerModule(@RequestBody AdminAddViewVo adminAddViewVo) {
        String titleName = adminAddViewVo.getTitleName();
        Assert.isNotBlank(titleName, "主要界面标题");
        List<String> goodsIds = adminAddViewVo.getGoodsIds();
        Assert.isNotNull(goodsIds, "选择的商品");
        if (goodsIds.size() != 9) {
            throw new BizException("主要界面选择的商品数量必须为9个！");
        }
        return pageConfigService.addContainerModule(titleName, goodsIds);
    }

    /**
     * 修改主要界面
     *
     * @param adminFixContainerModuleVo
     * @return
     */
    @PostMapping("/fix_container_module")
    @CheckAdmin
    public SingleAllBo<Boolean> fixContainerModule(@RequestBody AdminFixContainerModuleVo adminFixContainerModuleVo) {
        String containerModuleId = adminFixContainerModuleVo.getContainerModuleId();
        String titleName = adminFixContainerModuleVo.getTitleName();
        List<String> containerSpecialGoodsId = adminFixContainerModuleVo.getContainerSpecialGoodsId();
        List<String> containerTopGoodsIds = adminFixContainerModuleVo.getContainerTopGoodsIds();
        List<String> containerBottomGoodsIds = adminFixContainerModuleVo.getContainerBottomGoodsIds();
        Assert.isNotBlank(containerModuleId, "主要界面id");
        Assert.isNotBlank(titleName, "主要界面标题名字");
        Assert.isNotNull(containerSpecialGoodsId, "选择的左侧高的商品");
        Assert.isNotNull(containerTopGoodsIds, "选择的上排的商品");
        Assert.isNotNull(containerBottomGoodsIds, "选择的下排的商品");
        if (containerSpecialGoodsId.size() != 1) {
            throw new BizException("左侧高的商品选择的数量必须为1个！");
        }
        if (containerTopGoodsIds.size() != 4) {
            throw new BizException("上排商品选择的数量必须4个！");
        }
        if (containerBottomGoodsIds.size() != 4) {
            throw new BizException("下排商品选择的数量必须4个！");
        }
        return pageConfigService.fixContainerModule(containerModuleId, titleName, containerSpecialGoodsId, containerTopGoodsIds, containerBottomGoodsIds);
    }

    /**
     * 删除主要界面数据
     *
     * @param idVo
     * @return
     */
    @PostMapping("/del_container_module")
    @CheckAdmin
    public SingleAllBo<Boolean> delContainerModule(@RequestBody IdVo idVo) {
        String id = idVo.getId();
        Assert.isNotBlank(id, "主要界面id");
        return pageConfigService.delContainerModule(id);
    }

    /**
     * 获取标题数据
     *
     * @return
     */
    @PostMapping("/get_toggle_view")
    public List<ToggleViewBo> getToggleView() {
        return pageConfigService.getToggleView();
    }

    /**
     * 获取轮播图数据
     *
     * @return
     */
    @PostMapping("/get_rotation_chart")
    public RotationChartBo getRotationChart() {
        return pageConfigService.getRotationChart();
    }

    /**
     * 获取广告数据
     *
     * @return
     */
    @PostMapping("/get_goods_ad")
    public GoodsAdBo getGoodsAd() {
        return pageConfigService.getGoodsAd();
    }

    /**
     * 获取主要界面数据
     *
     * @return
     */
    @PostMapping("/get_container_module")
    public List<ContainerModuleBo> getContainerModule() {
        return pageConfigService.getContainerModule();
    }
}
