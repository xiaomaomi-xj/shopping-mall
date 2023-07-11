package com.shoppingMall.admin.entity.bo;

import com.shoppingMall.utils.Assert;

/**
 * 后台商户bo类(用于返回值，新增，修改)
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/4/28
 */
public class AdminMerchantBo {

    /**
     * 商户id
     */
    private String merchantId;

    /**
     * 商户号
     */
    private String merchantCode;

    /**
     * 商户名字
     */
    private String merchantName;

    /**
     * 商户类型
     */
    private String merchantType;

    /**
     * 支付平台应用id
     */
    private String payPlatformAppId;

    /**
     * 商户公钥
     */
    private String publicKey;

    /**
     * 商户私钥
     */
    private String privateKey;

    /**
     * 内容加密密码
     */
    private String encryptPassword;

    /**
     * 平台地址
     */
    private String serverUrl;

    /**
     * 回调地址
     */
    private String callbackUrl;

    public AdminMerchantBo() {
    }

    public AdminMerchantBo(String merchantId, String merchantCode, String merchantName, String merchantType, String payPlatformAppId, String publicKey, String privateKey, String encryptPassword, String serverUrl, String callbackUrl) {
        this.merchantId = merchantId;
        this.merchantCode = merchantCode;
        this.merchantName = merchantName;
        this.merchantType = merchantType;
        this.payPlatformAppId = payPlatformAppId;
        this.publicKey = publicKey;
        this.privateKey = privateKey;
        this.encryptPassword = encryptPassword;
        this.serverUrl = serverUrl;
        this.callbackUrl = callbackUrl;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getMerchantType() {
        return merchantType;
    }

    public void setMerchantType(String merchantType) {
        this.merchantType = merchantType;
    }

    public String getPayPlatformAppId() {
        return payPlatformAppId;
    }

    public void setPayPlatformAppId(String payPlatformAppId) {
        this.payPlatformAppId = payPlatformAppId;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getEncryptPassword() {
        return encryptPassword;
    }

    public void setEncryptPassword(String encryptPassword) {
        this.encryptPassword = encryptPassword;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    /**
     * 用于修改和新增的时候自检,只要这几个不为空就好
     */
    public void check() {
        Assert.isNotBlank(merchantName, "商户名字");
        Assert.isNotBlank(merchantType, "商户类型");
        Assert.isNotBlank(serverUrl, "平台地址");
        Assert.isNotBlank(callbackUrl, "回调地址");
    }

    @Override
    public String toString() {
        return "AdminMerchantBo{" +
                "merchantId='" + merchantId + '\'' +
                ", merchantCode='" + merchantCode + '\'' +
                ", merchantName='" + merchantName + '\'' +
                ", merchantType='" + merchantType + '\'' +
                ", payPlatformAppId='" + payPlatformAppId + '\'' +
                ", publicKey='" + publicKey + '\'' +
                ", privateKey='" + privateKey + '\'' +
                ", encryptPassword='" + encryptPassword + '\'' +
                ", serverUrl='" + serverUrl + '\'' +
                ", callbackUrl='" + callbackUrl + '\'' +
                '}';
    }
}
