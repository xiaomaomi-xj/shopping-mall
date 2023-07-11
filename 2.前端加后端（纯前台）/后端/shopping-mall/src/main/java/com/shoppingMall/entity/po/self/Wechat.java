package com.shoppingMall.entity.po.self;

/**
 * 微信登录参数实体类
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/5
 */
public class Wechat {
    /**
     * 账号id
     */
    private String appId;

    /**
     * 账号密钥
     */
    private String appSecret;

    /**
     * 回调地址
     */
    private String redirectUrl;

    public Wechat(){

    }

    public Wechat(String appId, String appSecret, String redirectUrl) {
        this.appId = appId;
        this.appSecret = appSecret;
        this.redirectUrl = redirectUrl;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    @Override
    public String toString() {
        return "Wechat{" +
                "appId='" + appId + '\'' +
                ", appSecret='" + appSecret + '\'' +
                ", redirectUrl='" + redirectUrl + '\'' +
                '}';
    }
}
