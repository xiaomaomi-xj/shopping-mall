package com.shoppingMall.entity.vo;

/**
 * 用于接受验证码参数
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/3
 */
public class CaptchaVo {

    /**
     * 验证码的id
     */
    private String captchaId;

    /**
     * 验证码的code
     */
    private String captchaCode;

    public CaptchaVo(){

    }

    public CaptchaVo(String captchaId, String captchaCode) {
        this.captchaId = captchaId;
        this.captchaCode = captchaCode;
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

    @Override
    public String toString() {
        return "CaptchaVo{" +
                "captchaId='" + captchaId + '\'' +
                ", captchaCode='" + captchaCode + '\'' +
                '}';
    }
}
