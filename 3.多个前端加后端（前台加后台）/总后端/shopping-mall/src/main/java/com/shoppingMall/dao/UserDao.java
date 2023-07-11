package com.shoppingMall.dao;

import com.shoppingMall.entity.po.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 用户数据操作接口
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/2/28
 */
public interface UserDao extends JpaRepository<User,Long>, JpaSpecificationExecutor<User> {
    /**
     * 根据邮箱查询对应的账号
     *
     * @param userEmail
     * @return
     */
    boolean existsByUserEmail(String userEmail);

    /**
     * 根据邮箱查询用户
     *
     * @param userEmail
     * @return
     */
    User findByUserEmail(String userEmail);
}
