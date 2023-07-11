package com.shoppingMall.entity.vo;

/**
 * 修改密码接受参数
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/21
 */
public class ModifyPasswordVo {
    /**
     * 用户令牌
     */
    private String token;

    /**
     * 旧密码
     */
    private String oldPassword;

    /**
     * 新密码
     */
    private String newPassword;

    public ModifyPasswordVo() {
    }

    public ModifyPasswordVo(String token, String oldPassword, String newPassword) {
        this.token = token;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @Override
    public String toString() {
        return "ModifyPasswordVo{" +
                "token='" + token + '\'' +
                ", oldPassword='" + oldPassword + '\'' +
                ", newPassword='" + newPassword + '\'' +
                '}';
    }
}
