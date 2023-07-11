package com.shoppingMall.controller;

import com.shoppingMall.entity.bo.ExtraConfigBo;
import com.shoppingMall.service.ExtraConfigService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 特殊配置接口
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/1
 */
@RestController
@RequestMapping(value = "/extra_config")
public class ExtraConfigController {
    private final ExtraConfigService extraConfigService;

    public ExtraConfigController(ExtraConfigService extraConfigService) {
        this.extraConfigService = extraConfigService;
    }

    /**
     * 获取配置内容接口
     *
     * @return
     */
    @PostMapping("/get_config")
    public ExtraConfigBo getConfig(){
        return extraConfigService.getConfig();
    }
}
