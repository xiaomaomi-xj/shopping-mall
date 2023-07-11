package com.shoppingMall.controller;

import com.shoppingMall.entity.bo.pageconfig.ContainerModuleBo;
import com.shoppingMall.entity.bo.pageconfig.GoodsAdBo;
import com.shoppingMall.entity.bo.pageconfig.RotationChartBo;
import com.shoppingMall.entity.bo.pageconfig.ToggleViewBo;
import com.shoppingMall.service.PageConfigService;
import org.springframework.web.bind.annotation.PostMapping;
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
     * 获取标题数据
     *
     * @return
     */
    @PostMapping("/get_toggle_view")
    public List<ToggleViewBo> getToggleView(){
        return pageConfigService.getToggleView();
    }

    /**
     * 获取轮播图数据
     *
     * @return
     */
    @PostMapping("/get_rotation_chart")
    public RotationChartBo getRotationChart(){
        return pageConfigService.getRotationChart();
    }

    /**
     * 获取广告数据
     *
     * @return
     */
    @PostMapping("/get_goods_ad")
    public GoodsAdBo getGoodsAd(){
        return pageConfigService.getGoodsAd();
    }

    /**
     * 获取主要界面数据
     *
     * @return
     */
    @PostMapping("/get_container_module")
    public List<ContainerModuleBo> getContainerModule(){
        return pageConfigService.getContainerModule();
    }
}
