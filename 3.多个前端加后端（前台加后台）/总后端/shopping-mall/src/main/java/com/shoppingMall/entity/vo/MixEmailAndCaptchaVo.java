package com.shoppingMall.entity.vo;

/**
 * 对于多个接受参数
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/4
 */
public class MixEmailAndCaptchaVo {
    /**
     * 验证码的id
     */
    private String captchaId;

    /**
     * 验证码的code
     */
    private String captchaCode;

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

    public MixEmailAndCaptchaVo(){

    }

    public MixEmailAndCaptchaVo(String captchaId, String captchaCode, String emailKey, String emailCode, String emailAccount) {
        this.captchaId = captchaId;
        this.captchaCode = captchaCode;
        this.emailKey = emailKey;
        this.emailCode = emailCode;
        this.emailAccount = emailAccount;
    }

    public String getCaptchaId() {
        return captchaId;
    }

    public void setCaptchaId(String captchaId) {
        this.captchaId = captchaId;
    }

    public String getCaptchaCode() {
        return captchaCode;
    }

    public void setCaptchaCode(String captchaCode) {
        this.captchaCode = captchaCode;
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
        return "MixEmailAndCaptchaVo{" +
                "captchaId='" + captchaId + '\'' +
                ", captchaCode='" + captchaCode + '\'' +
                ", emailKey='" + emailKey + '\'' +
                ", emailCode='" + emailCode + '\'' +
                ", emailAccount='" + emailAccount + '\'' +
                '}';
    }
}
