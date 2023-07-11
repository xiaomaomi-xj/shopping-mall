package com.shoppingMall.dao;

import com.shoppingMall.entity.po.ToggleView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 标题界面数据操作接口
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/2/28
 */
public interface ToggleViewDao extends JpaRepository<ToggleView,Long>, JpaSpecificationExecutor<ToggleView>{
}
