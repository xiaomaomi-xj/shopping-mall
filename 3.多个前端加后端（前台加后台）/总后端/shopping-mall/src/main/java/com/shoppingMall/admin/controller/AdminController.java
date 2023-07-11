package com.shoppingMall.admin.controller;

import com.shoppingMall.admin.annotation.CheckAdmin;
import com.shoppingMall.admin.constan.AdminConstant;
import com.shoppingMall.admin.entity.bo.AdminTokenBo;
import com.shoppingMall.admin.entity.vo.AdminVo;
import com.shoppingMall.admin.service.AdminService;
import com.shoppingMall.entity.bo.SingleAllBo;
import com.shoppingMall.entity.vo.ModifyPasswordVo;
import com.shoppingMall.utils.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 管理员账号接口
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/30
 */
@RestController
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    /**
     * 管理员登录
     *
     * @param adminVo
     * @return
     */
    @PostMapping("/login")
    public AdminTokenBo login(@RequestBody AdminVo adminVo) {
        String adminUser = adminVo.getAdminUser();
        String adminPassword = adminVo.getAdminPassword();
        Assert.isNotBlank(adminUser, "管理员账号");
        Assert.isNotBlank(adminPassword, "管理员密码");
        return adminService.login(adminUser, adminPassword);
    }

    /**
     * 刷新token
     *
     * @param request
     * @return
     */
    @PostMapping("/refresh-token")
    public AdminTokenBo refreshToken(HttpServletRequest request) {
        String token = request.getHeader(AdminConstant.ADMIN_TOKEN_KEY);
        String refreshToken = request.getHeader(AdminConstant.ADMIN_REFRESH_TOKEN_KEY);
        Assert.isNotBlank(token, "token");
        Assert.isNotBlank(refreshToken, "refreshToken");
        return adminService.refreshToken(token, refreshToken);
    }

    /**
     * 验证登录
     *
     * @return
     */
    @PostMapping("/check-login")
    @CheckAdmin
    public SingleAllBo<Boolean> checkLogin() {
        //只要能有返回值就代表登陆了
        return new SingleAllBo<>(true);
    }

    /**
     * 修改密码
     *
     * @param request
     * @return
     */
    @PostMapping("modify-password")
    @CheckAdmin
    public SingleAllBo<Boolean> modifyPassword(HttpServletRequest request, @RequestBody ModifyPasswordVo modifyPasswordVo) {
        String token = request.getHeader(AdminConstant.ADMIN_TOKEN_KEY);
        String oldPassword = modifyPasswordVo.getOldPassword();
        String newPassword = modifyPasswordVo.getNewPassword();
        Assert.isNotBlank(token, "用户令牌");
        Assert.isNotBlank(oldPassword, "旧密码");
        Assert.isNotBlank(newPassword, "新密码");
        return adminService.modifyPassword(token, oldPassword, newPassword);
    }
}
