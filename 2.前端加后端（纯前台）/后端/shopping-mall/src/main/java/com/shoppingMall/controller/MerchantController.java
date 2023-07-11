package com.shoppingMall.controller;

import com.shoppingMall.entity.bo.MerchantBo;
import com.shoppingMall.service.MerchantService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 商户信息接口类
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/23
 */
@RestController
@RequestMapping("/merchant")
public class MerchantController {
    private final MerchantService merchantService;

    public MerchantController(MerchantService merchantService) {
        this.merchantService = merchantService;
    }

    /**
     * 获取所有商户信息
     *
     * @return
     */
    @PostMapping("/get_all_merchant")
    public List<MerchantBo> getAllMerchant() {
        return merchantService.getMerchants();
    }
}
