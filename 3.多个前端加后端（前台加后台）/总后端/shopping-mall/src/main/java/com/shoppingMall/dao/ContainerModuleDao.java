package com.shoppingMall.dao;

import com.shoppingMall.entity.po.ContainerModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 主要界面操作接口
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/2/28
 */
public interface ContainerModuleDao extends JpaRepository<ContainerModule,Long>, JpaSpecificationExecutor<ContainerModule> {
}
