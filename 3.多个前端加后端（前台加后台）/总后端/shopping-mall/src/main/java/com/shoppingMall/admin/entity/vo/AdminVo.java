package com.shoppingMall.admin.entity.vo;

/**
 * 用于接受参数
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/30
 */
public class AdminVo {
    /**
     * 管理员账号
     */
    private String adminUser;

    /**
     * 管理员密码
     */
    private String adminPassword;

    public AdminVo() {
    }

    public AdminVo(String adminUser, String adminPassword) {
        this.adminUser = adminUser;
        this.adminPassword = adminPassword;
    }

    public String getAdminUser() {
        return adminUser;
    }

    public void setAdminUser(String adminUser) {
        this.adminUser = adminUser;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    @Override
    public String toString() {
        return "AdminVo{" +
                "adminUser='" + adminUser + '\'' +
                ", adminPassword='" + adminPassword + '\'' +
                '}';
    }
}
