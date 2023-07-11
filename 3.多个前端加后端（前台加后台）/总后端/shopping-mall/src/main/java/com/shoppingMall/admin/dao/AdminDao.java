package com.shoppingMall.admin.dao;

import com.shoppingMall.admin.entity.po.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/30
 */
public interface AdminDao extends JpaRepository<Admin,Long>, JpaSpecificationExecutor<Admin> {
    /**
     * 根据账号合密码查询用户
     *
     * @param adminUser
     * @return
     */
    Admin findByAdminUser(String adminUser);
}
