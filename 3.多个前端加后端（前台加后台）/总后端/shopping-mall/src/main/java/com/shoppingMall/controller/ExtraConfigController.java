package com.shoppingMall.controller;

import com.shoppingMall.admin.annotation.CheckAdmin;
import com.shoppingMall.admin.entity.bo.AdminExtraConfigBo;
import com.shoppingMall.constant.EnableConstantEnum;
import com.shoppingMall.entity.bo.ExtraConfigBo;
import com.shoppingMall.entity.bo.SingleAllBo;
import com.shoppingMall.service.ExtraConfigService;
import com.shoppingMall.utils.UploadFileUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

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
     * 管理端获取特殊配置详细信息
     *
     * @return
     */
    @PostMapping("/get_extra_config")
    @CheckAdmin
    public AdminExtraConfigBo getExtraConfig() {
        return extraConfigService.getExtraConfig();
    }

    /**
     * 修改特殊配置
     *
     * @param request
     * @param adminExtraConfigBo
     * @return
     */
    @PostMapping("/fix_extra_config")
    @CheckAdmin
    public SingleAllBo<Boolean> fixExtraConfig(MultipartHttpServletRequest request, AdminExtraConfigBo adminExtraConfigBo) {
        adminExtraConfigBo.check();
        String belowPageText = adminExtraConfigBo.getBelowPageText();
        String extraId = adminExtraConfigBo.getExtraId();
        String passwordTemplateText = adminExtraConfigBo.getPasswordTemplateText();
        String specialText = adminExtraConfigBo.getSpecialText();
        String storeName = adminExtraConfigBo.getStoreName();
        int storeNameHaveSpecial = adminExtraConfigBo.isStoreNameHaveSpecial() ? EnableConstantEnum.ON.getCode() : EnableConstantEnum.OFF.getCode();
        List<MultipartFile> files = request.getFiles(UploadFileUtil.FILE_KEY);
        UploadFileUtil.check(files);
        return extraConfigService.fixExtraConfig(extraId, storeNameHaveSpecial, specialText, storeName, passwordTemplateText, belowPageText, files);
    }

    /**
     * 获取配置内容接口
     *
     * @return
     */
    @PostMapping("/get_config")
    public ExtraConfigBo getConfig() {
        return extraConfigService.getConfig();
    }
}
