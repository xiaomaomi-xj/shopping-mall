package com.shoppingMall.admin.service;

import com.shoppingMall.admin.entity.bo.AdminTokenBo;
import com.shoppingMall.entity.bo.SingleAllBo;

/**
 * 管理员账号服务类
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/30
 */
public interface AdminService {
    /**
     * 管理员登录
     *
     * @param adminUser     账号
     * @param adminPassword 密码
     * @return
     */
    AdminTokenBo login(String adminUser, String adminPassword);

    /**
     * 修改密码
     *
     * @param token
     * @param oldPassword
     * @param newPassword
     * @return
     */
    SingleAllBo<Boolean> modifyPassword(String token, String oldPassword, String newPassword);

    /**
     * 刷新token
     *
     * @param token
     * @param refreshToken
     * @return
     */
    AdminTokenBo refreshToken(String token, String refreshToken);
}
