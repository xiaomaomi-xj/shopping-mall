package com.shoppingMall.entity.bo;

import java.io.Serializable;

/**
 * 不与数据库对应，用于返回前端
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/1
 */
public class ExtraConfigBo implements Serializable {

    private static final long serialVersionUID = 5640533658670664862L;

    /**
     * 是否含有特殊字
     */
    private boolean storeNameHaveSpecial;

    /**
     * 特殊字
     */
    private String specialText;

    /**
     * 商店名称
     */
    private String storeName;

    /**
     * 密码模板
     */
    private String passwordTemplateText;

    /**
     * 页脚内容
     */
    private String belowPageText;

    /**
     * 注册界面背景，就是正确的路径，不再是id
     */
    private String registerBgImgUrl;

    /**
     * 登陆界面背景，就是正确的路径，不再是id
     */
    private String loginBgImgUrl;

    public ExtraConfigBo() {

    }

    public ExtraConfigBo(boolean storeNameHaveSpecial, String specialText, String storeName, String passwordTemplateText, String belowPageText, String registerBgImgUrl, String loginBgImgUrl) {
        this.storeNameHaveSpecial = storeNameHaveSpecial;
        this.specialText = specialText;
        this.storeName = storeName;
        this.passwordTemplateText = passwordTemplateText;
        this.belowPageText = belowPageText;
        this.registerBgImgUrl = registerBgImgUrl;
        this.loginBgImgUrl = loginBgImgUrl;
    }

    public boolean getStoreNameHaveSpecial() {
        return storeNameHaveSpecial;
    }

    public void setStoreNameHaveSpecial(boolean storeNameHaveSpecial) {
        this.storeNameHaveSpecial = storeNameHaveSpecial;
    }

    public String getSpecialText() {
        return specialText;
    }

    public void setSpecialText(String specialText) {
        this.specialText = specialText;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getPasswordTemplateText() {
        return passwordTemplateText;
    }

    public void setPasswordTemplateText(String passwordTemplateText) {
        this.passwordTemplateText = passwordTemplateText;
    }

    public String getBelowPageText() {
        return belowPageText;
    }

    public void setBelowPageText(String belowPageText) {
        this.belowPageText = belowPageText;
    }

    public String getRegisterBgImgUrl() {
        return registerBgImgUrl;
    }

    public void setRegisterBgImgUrl(String registerBgImgUrl) {
        this.registerBgImgUrl = registerBgImgUrl;
    }

    public String getloginBgImgUrl() {
        return loginBgImgUrl;
    }

    public void setloginBgImgUrl(String loginBgImgUrl) {
        this.loginBgImgUrl = loginBgImgUrl;
    }

    @Override
    public String toString() {
        return "ExtraConfigBo{" +
                "storeNameHaveSpecial=" + storeNameHaveSpecial +
                ", specialText='" + specialText + '\'' +
                ", storeName='" + storeName + '\'' +
                ", passwordTemplateText='" + passwordTemplateText + '\'' +
                ", belowPageText='" + belowPageText + '\'' +
                ", registerBgImgUrl='" + registerBgImgUrl + '\'' +
                ", loginBgImgUrl='" + loginBgImgUrl + '\'' +
                '}';
    }
}
