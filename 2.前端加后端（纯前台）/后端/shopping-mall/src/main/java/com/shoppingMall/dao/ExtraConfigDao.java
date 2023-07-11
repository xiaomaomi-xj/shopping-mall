package com.shoppingMall.dao;

import com.shoppingMall.entity.po.ExtraConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 额外配置操作接口
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/2/28
 */
public interface ExtraConfigDao extends JpaRepository<ExtraConfig,Long>, JpaSpecificationExecutor<ExtraConfig> {
}
