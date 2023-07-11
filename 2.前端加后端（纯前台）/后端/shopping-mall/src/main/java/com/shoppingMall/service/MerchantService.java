package com.shoppingMall.service;

import com.shoppingMall.entity.bo.MerchantBo;

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
}
