package com.shoppingMall.entity.po.self;

/**
 * 自己的配置
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/3
 */
public class SelfConfig {
    /**
     * 商店名字
     */
    private String storeName;

    /**
     * 邮箱配置
     */
    private Email email;

    /**
     * 加密配置
     */
    private Password password;

    /**
     * 微信配置
     */
    private Wechat wechat;

    /**
     * 头像文件配置
     */
    private HeadImgFile headImgFile;

    /**
     * 支付配置
     */
    private Pay pay;


    public SelfConfig(){

    }

    public SelfConfig(String storeName, Email email, Password password, Wechat wechat, HeadImgFile headImgFile, Pay pay) {
        this.storeName = storeName;
        this.email = email;
        this.password = password;
        this.wechat = wechat;
        this.headImgFile = headImgFile;
        this.pay = pay;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public Password getPassword() {
        return password;
    }

    public void setPassword(Password password) {
        this.password = password;
    }

    public Wechat getWechat() {
        return wechat;
    }

    public void setWechat(Wechat wechat) {
        this.wechat = wechat;
    }

    public HeadImgFile getHeadImgFile() {
        return headImgFile;
    }

    public void setHeadImgFile(HeadImgFile headImgFile) {
        this.headImgFile = headImgFile;
    }

    public Pay getPay() {
        return pay;
    }

    public void setPay(Pay pay) {
        this.pay = pay;
    }

    @Override
    public String toString() {
        return "SelfConfig{" +
                "storeName='" + storeName + '\'' +
                ", email=" + email +
                ", password=" + password +
                ", wechat=" + wechat +
                ", headImgFile=" + headImgFile +
                ", pay=" + pay +
                '}';
    }
}
