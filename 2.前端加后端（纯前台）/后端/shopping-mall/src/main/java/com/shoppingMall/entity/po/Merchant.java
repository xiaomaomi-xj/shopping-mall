package com.shoppingMall.entity.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * 商户信息
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/23
 */
@Entity(name = "merchant")
public class Merchant implements Serializable {
    private static final long serialVersionUID = -8091626498090321155L;
    /**
     * 商户id
     */
    @Id
    @Column(name = "merchant_id")
    private Long merchantId;

    /**
     * 商户号
     */
    @Column(name = "merchant_code")
    private String merchantCode;

    /**
     * 商户名字
     */
    @Column(name = "merchant_name")
    private String merchantName;

    /**
     * 商户类型
     */
    @Column(name = "merchant_type")
    private String merchantType;

    /**
     * 支付平台应用id
     */
    @Column(name = "pay_platform_app_id")
    private String payPlatformAppId;

    /**
     * 商户公钥
     */
    @Column(name = "public_key")
    private String publicKey;

    /**
     * 商户私钥
     */
    @Column(name = "private_key")
    private String privateKey;

    /**
     * 内容加密密码
     */
    @Column(name = "encrypt_password")
    private String encryptPassword;

    /**
     * 平台地址
     */
    @Column(name = "server_url")
    private String serverUrl;

    /**
     * 回调地址
     */
    @Column(name = "callback_url")
    private String callbackUrl;

    public Merchant() {
    }

    public Merchant(Long merchantId, String merchantCode, String merchantName, String merchantType, String payPlatformAppId, String publicKey, String privateKey, String encryptPassword, String serverUrl, String callbackUrl) {
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

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
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

    @Override
    public String toString() {
        return "Merchant{" +
                "merchantId=" + merchantId +
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
