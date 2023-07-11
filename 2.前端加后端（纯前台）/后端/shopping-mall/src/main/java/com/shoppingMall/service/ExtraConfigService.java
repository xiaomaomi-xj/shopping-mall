package com.shoppingMall.service;

import com.shoppingMall.entity.bo.ExtraConfigBo;

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
}
