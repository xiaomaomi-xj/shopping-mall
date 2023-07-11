package com.shoppingMall.admin.manager;

import com.shoppingMall.admin.entity.bo.AdminTokenBo;
import com.shoppingMall.admin.entity.po.Admin;

/**
 * 管理员token管理类
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/30
 */
public interface AdminManager {
    /**
     * 检查token是否可用
     *
     * @param adminToken
     * @return
     */
    boolean checkAdminToken(String adminToken);

    /**
     * 创建token
     *
     * @param adminId
     * @return
     */
    AdminTokenBo createAdminToken(String adminId);

    /**
     * 刷新token
     *
     * @param token
     * @param refreshToken
     * @return
     */
    AdminTokenBo refreshToken(String token,String refreshToken);

    /**
     * 根据token获取管理员用户
     *
     * @param token
     * @return
     */
    Admin getAdminOnToken(String token);
}
