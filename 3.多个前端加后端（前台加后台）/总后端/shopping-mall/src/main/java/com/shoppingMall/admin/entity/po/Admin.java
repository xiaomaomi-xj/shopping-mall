package com.shoppingMall.admin.entity.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

/**
 * 后台管理员
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/30
 */
@Entity(name = "admin")
public class Admin implements Serializable {
    private static final long serialVersionUID = 6489709067003955750L;

    /**
     * 管理账号id
     */
    @Id
    @Column(name = "admin_id")
    private Long adminId;

    /**
     * 管理员账号
     */
    @Column(name = "admin_user")
    private String adminUser;

    /**
     * 管理员密码
     */
    @Column(name = "admin_password")
    private String adminPassword;

    public Admin() {
    }

    public Admin(Long adminId, String adminUser, String adminPassword) {
        this.adminId = adminId;
        this.adminUser = adminUser;
        this.adminPassword = adminPassword;
    }

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Admin admin = (Admin) o;
        return Objects.equals(adminId, admin.adminId) &&
                Objects.equals(adminUser, admin.adminUser) &&
                Objects.equals(adminPassword, admin.adminPassword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(adminId, adminUser, adminPassword);
    }

    @Override
    public String toString() {
        return "Admin{" +
                "adminId=" + adminId +
                ", adminUser='" + adminUser + '\'' +
                ", adminPassword='" + adminPassword + '\'' +
                '}';
    }
}
