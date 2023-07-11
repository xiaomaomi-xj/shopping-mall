package com.shoppingMall.controller;

import com.shoppingMall.admin.annotation.CheckAdmin;
import com.shoppingMall.admin.entity.bo.AdminMerchantBo;
import com.shoppingMall.entity.bo.MerchantBo;
import com.shoppingMall.entity.bo.SingleAllBo;
import com.shoppingMall.service.MerchantService;
import com.shoppingMall.utils.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    /**
     * 后台获取所有商户信息
     *
     * @return
     */
    @PostMapping("/get_admin_all_merchant")
    @CheckAdmin
    public List<AdminMerchantBo> getAdminAllMerchant() {
        return merchantService.getAdminMerchants();
    }

    /**
     * 根据id删除商户
     *
     * @param merchantBo
     * @return
     */
    @PostMapping("/del_merchant_by_id")
    @CheckAdmin
    public SingleAllBo<Boolean> delMerchantById(@RequestBody MerchantBo merchantBo) {
        String merchantId = merchantBo.getMerchantId();
        Assert.isNotBlank(merchantId, "商户id");
        return merchantService.delMerchantById(merchantId);
    }

    /**
     * 修改商户
     *
     * @param adminMerchantBo
     * @return
     */
    @PostMapping("/modify_merchant")
    @CheckAdmin
    public SingleAllBo<Boolean> modifyMerchant(@RequestBody AdminMerchantBo adminMerchantBo) {
        adminMerchantBo.check();
        String merchantId = adminMerchantBo.getMerchantId();
        Assert.isNotBlank(merchantId, "商户id");
        return merchantService.modifyMerchant(
                adminMerchantBo.getMerchantId(),
                adminMerchantBo.getMerchantCode(),
                adminMerchantBo.getMerchantName(),
                adminMerchantBo.getMerchantType(),
                adminMerchantBo.getPayPlatformAppId(),
                adminMerchantBo.getPublicKey(),
                adminMerchantBo.getPrivateKey(),
                adminMerchantBo.getEncryptPassword(),
                adminMerchantBo.getServerUrl(),
                adminMerchantBo.getCallbackUrl()
        );
    }

    /**
     * 新政商户
     *
     * @param adminMerchantBo
     * @return
     */
    @PostMapping("/add_merchant")
    @CheckAdmin
    public SingleAllBo<Boolean> addMerchant(@RequestBody AdminMerchantBo adminMerchantBo) {
        adminMerchantBo.check();
        return merchantService.addMerchant(
                adminMerchantBo.getMerchantCode(),
                adminMerchantBo.getMerchantName(),
                adminMerchantBo.getMerchantType(),
                adminMerchantBo.getPayPlatformAppId(),
                adminMerchantBo.getPublicKey(),
                adminMerchantBo.getPrivateKey(),
                adminMerchantBo.getEncryptPassword(),
                adminMerchantBo.getServerUrl(),
                adminMerchantBo.getCallbackUrl()
        );
    }
}
