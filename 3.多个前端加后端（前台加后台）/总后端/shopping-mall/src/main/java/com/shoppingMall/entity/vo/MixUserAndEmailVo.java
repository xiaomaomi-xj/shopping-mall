package com.shoppingMall.entity.vo;

/**
 * 对于接受多个参数
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/4
 */
public class MixUserAndEmailVo {
    /**
     * 用户名字
     */
    private String userName;

    /**
     * 用户邮箱（账号）
     */
    private String userEmail;

    /**
     * 用户性别
     */
    private int userSex;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 邮箱的id
     */
    private String emailKey;

    /**
     * 邮箱验证码
     */
    private String emailCode;

    /**
     * 邮箱账号
     */
    private String emailAccount;

    public MixUserAndEmailVo(){

    }

    public MixUserAndEmailVo(String userName, String userEmail, int userSex, String password, String emailKey, String emailCode, String emailAccount) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userSex = userSex;
        this.password = password;
        this.emailKey = emailKey;
        this.emailCode = emailCode;
        this.emailAccount = emailAccount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public int getUserSex() {
        return userSex;
    }

    public void setUserSex(int userSex) {
        this.userSex = userSex;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmailKey() {
        return emailKey;
    }

    public void setEmailKey(String emailKey) {
        this.emailKey = emailKey;
    }

    public String getEmailCode() {
        return emailCode;
    }

    public void setEmailCode(String emailCode) {
        this.emailCode = emailCode;
    }

    public String getEmailAccount() {
        return emailAccount;
    }

    public void setEmailAccount(String emailAccount) {
        this.emailAccount = emailAccount;
    }

    @Override
    public String toString() {
        return "MixUserAndEmailVo{" +
                "userName='" + userName + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userSex=" + userSex +
                ", password='" + password + '\'' +
                ", emailKey='" + emailKey + '\'' +
                ", emailCode='" + emailCode + '\'' +
                ", emailAccount='" + emailAccount + '\'' +
                '}';
    }
}
