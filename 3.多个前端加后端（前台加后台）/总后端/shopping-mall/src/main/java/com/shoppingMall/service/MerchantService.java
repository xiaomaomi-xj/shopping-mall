package com.shoppingMall.service;

import com.shoppingMall.admin.entity.bo.AdminMerchantBo;
import com.shoppingMall.entity.bo.MerchantBo;
import com.shoppingMall.entity.bo.SingleAllBo;

import java.util.List;

/**
 * 商户服务类
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/23
 */
public interface MerchantService {
    /**
     * 查询所有商户信息
     *
     * @return
     */
    List<MerchantBo> getMerchants();

    /**
     * 后台获取所有商户信息
     *
     * @return
     */
    List<AdminMerchantBo> getAdminMerchants();

    /**
     * 根据商户id删除商户
     *
     * @param merchantId
     * @return
     */
    SingleAllBo<Boolean> delMerchantById(String merchantId);

    /**
     * 修改商户信息
     *
     * @param merchantId
     * @param merchantCode
     * @param merchantName
     * @param merchantType
     * @param payPlatformAppId
     * @param publicKey
     * @param privateKey
     * @param encryptPassword
     * @param serverUrl
     * @param callbackUrl
     * @return
     */
    SingleAllBo<Boolean> modifyMerchant(String merchantId, String merchantCode, String merchantName, String merchantType, String payPlatformAppId, String publicKey, String privateKey, String encryptPassword, String serverUrl, String callbackUrl);

    /**
     * 添加商户信息
     *
     * @param merchantCode
     * @param merchantName
     * @param merchantType
     * @param payPlatformAppId
     * @param publicKey
     * @param privateKey
     * @param encryptPassword
     * @param serverUrl
     * @param callbackUrl
     * @return
     */
    SingleAllBo<Boolean> addMerchant(String merchantCode, String merchantName, String merchantType, String payPlatformAppId, String publicKey, String privateKey, String encryptPassword, String serverUrl, String callbackUrl);
}
