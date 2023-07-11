package com.shoppingMall.service;

import com.shoppingMall.admin.entity.bo.AdminExtraConfigBo;
import com.shoppingMall.entity.bo.ExtraConfigBo;
import com.shoppingMall.entity.bo.SingleAllBo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 操作额外配置
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/2/28
 */
public interface ExtraConfigService {
    /**
     * 获取配置内容
     *
     * @return
     */
    ExtraConfigBo getConfig();

    /**
     * 管理端获取配置信息
     *
     * @return
     */
    AdminExtraConfigBo getExtraConfig();

    /**
     * 修改特殊配置
     *
     * @param extraId
     * @param storeNameHaveSpecial
     * @param specialText
     * @param storeName
     * @param passwordTemplateText
     * @param belowPageText
     * @param files
     * @return
     */
    SingleAllBo<Boolean> fixExtraConfig(String extraId, int storeNameHaveSpecial, String specialText, String storeName, String passwordTemplateText, String belowPageText, List<MultipartFile> files);
}
