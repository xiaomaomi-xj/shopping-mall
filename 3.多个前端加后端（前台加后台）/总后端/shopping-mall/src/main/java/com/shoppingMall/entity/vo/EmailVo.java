package com.shoppingMall.entity.vo;

/**
 * 用于接受邮箱参数
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/3
 */
public class EmailVo {

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

    public EmailVo(){

    }

    public EmailVo(String emailKey, String emailCode, String emailAccount) {
        this.emailKey = emailKey;
        this.emailCode = emailCode;
        this.emailAccount = emailAccount;
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
        return "EmailVo{" +
                "emailKey='" + emailKey + '\'' +
                ", emailCode='" + emailCode + '\'' +
                ", emailAccount='" + emailAccount + '\'' +
                '}';
    }
}
