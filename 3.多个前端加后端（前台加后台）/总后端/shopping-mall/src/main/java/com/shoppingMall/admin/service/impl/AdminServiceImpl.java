package com.shoppingMall.admin.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.shoppingMall.admin.dao.AdminDao;
import com.shoppingMall.admin.entity.bo.AdminTokenBo;
import com.shoppingMall.admin.entity.po.Admin;
import com.shoppingMall.admin.manager.AdminManager;
import com.shoppingMall.admin.service.AdminService;
import com.shoppingMall.entity.bo.SingleAllBo;
import com.shoppingMall.exception.BizException;
import com.shoppingMall.properties.SelfConfigPropertiesBean;
import com.shoppingMall.utils.PasswordUtil;
import com.zhuyahui.annotation.ZyhDataSourceRead;
import com.zhuyahui.annotation.ZyhService;
import com.zhuyahui.util.MyAloneHandlerReadWrite;

/**
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/30
 */
@ZyhService
@ZyhDataSourceRead
public class AdminServiceImpl implements AdminService {
    private final AdminDao adminDao;
    private final SelfConfigPropertiesBean selfConfigPropertiesBean;
    private final AdminManager adminManager;

    public AdminServiceImpl(AdminDao adminDao, SelfConfigPropertiesBean selfConfigPropertiesBean, AdminManager adminManager) {
        this.adminDao = adminDao;
        this.selfConfigPropertiesBean = selfConfigPropertiesBean;
        this.adminManager = adminManager;
    }

    /**
     * 登录(管理员用户存储时间为2小时，但是并不会自动刷新)
     *
     * @return
     */
    @Override
    public AdminTokenBo login(String adminUser, String adminPassword) {
        Admin admin = adminDao.findByAdminUser(adminUser);
        if (ObjectUtil.isNull(admin)) {
            throw new BizException("账号或密码错误！");
        }
        String adminPassword1 = admin.getAdminPassword();
        String password = PasswordUtil.getPassword(adminPassword, selfConfigPropertiesBean.getConfig().getPassword().getSalt());
        if (!password.equals(adminPassword1)) {
            throw new BizException("账号或密码错误！");
        }
        return adminManager.createAdminToken(String.valueOf(admin.getAdminId()));
    }

    /**
     * 修改密码
     *
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return
     */
    @Override
    public SingleAllBo<Boolean> modifyPassword(String token, String oldPassword, String newPassword) {
        Admin adminOnToken = adminManager.getAdminOnToken(token);
        String adminPassword = adminOnToken.getAdminPassword();
        String salt = selfConfigPropertiesBean.getConfig().getPassword().getSalt();
        String password = PasswordUtil.getPassword(oldPassword, salt);
        if (!adminPassword.equals(password)) {
            throw new BizException("旧密码错误！");
        }
        String password1 = PasswordUtil.getPassword(newPassword, salt);
        adminOnToken.setAdminPassword(password1);
        MyAloneHandlerReadWrite.write(() -> adminDao.save(adminOnToken));
        return new SingleAllBo<>(true);
    }

    /**
     * 刷新token
     *
     * @param token
     * @param refreshToken
     * @return
     */
    @Override
    public AdminTokenBo refreshToken(String token, String refreshToken) {
        return adminManager.refreshToken(token, refreshToken);
    }
}
