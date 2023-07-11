package com.shoppingMall.entity.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

/**
 * 特殊的配置实体类
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/2/28
 */
@Entity(name = "extra_config")
public class ExtraConfig implements Serializable {
    private static final long serialVersionUID = -6270007545657624487L;

    /**
     * 额外配置id
     */
    @Id
    @Column(name = "extra_id")
    private Long extraId;

    /**
     * 是否含有特殊字
     */
    @Column(name = "store_name_have_special")
    private int storeNameHaveSpecial;

    /**
     * 特殊字
     */
    @Column(name = "special_text")
    private String specialText;

    /**
     * 商店名称
     */
    @Column(name = "store_name")
    private String storeName;

    /**
     * 密码模板
     */
    @Column(name = "password_template_text")
    private String passwordTemplateText;

    /**
     * 页脚内容
     */
    @Column(name = "below_page_text")
    private String belowPageText;

    /**
     * 注册界面背景
     */
    @Column(name = "register_bg_img_url")
    private Long registerBgImgUrl;

    /**
     * 登陆界面背景
     */
    @Column(name = "login_bg_img_url")
    private Long loginBgImgUrl;

    public ExtraConfig() {

    }

    public ExtraConfig(Long extraId, int storeNameHaveSpecial, String specialText, String storeName, String passwordTemplateText, String belowPageText, Long registerBgImgUrl, Long loginBgImgUrl) {
        this.extraId = extraId;
        this.storeNameHaveSpecial = storeNameHaveSpecial;
        this.specialText = specialText;
        this.storeName = storeName;
        this.passwordTemplateText = passwordTemplateText;
        this.belowPageText = belowPageText;
        this.registerBgImgUrl = registerBgImgUrl;
        this.loginBgImgUrl = loginBgImgUrl;
    }

    public Long getExtraId() {
        return extraId;
    }

    public void setExtraId(Long extraId) {
        this.extraId = extraId;
    }

    public int getStoreNameHaveSpecial() {
        return storeNameHaveSpecial;
    }

    public void setStoreNameHaveSpecial(int storeNameHaveSpecial) {
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

    public Long getRegisterBgImgUrl() {
        return registerBgImgUrl;
    }

    public void setRegisterBgImgUrl(Long registerBgImgUrl) {
        this.registerBgImgUrl = registerBgImgUrl;
    }

    public Long getLoginBgImgUrl() {
        return loginBgImgUrl;
    }

    public void setLoginBgIImgUrl(Long loginBgImgUrl) {
        this.loginBgImgUrl = loginBgImgUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExtraConfig that = (ExtraConfig) o;
        return storeNameHaveSpecial == that.storeNameHaveSpecial &&
                Objects.equals(extraId, that.extraId) &&
                Objects.equals(specialText, that.specialText) &&
                Objects.equals(storeName, that.storeName) &&
                Objects.equals(passwordTemplateText, that.passwordTemplateText) &&
                Objects.equals(belowPageText, that.belowPageText) &&
                Objects.equals(registerBgImgUrl, that.registerBgImgUrl) &&
                Objects.equals(loginBgImgUrl, that.loginBgImgUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(extraId, storeNameHaveSpecial, specialText, storeName, passwordTemplateText, belowPageText, registerBgImgUrl, loginBgImgUrl);
    }

    @Override
    public String toString() {
        return "ExtraConfig{" +
                "extraId=" + extraId +
                ", storeNameHaveSpecial=" + storeNameHaveSpecial +
                ", specialText='" + specialText + '\'' +
                ", storeName='" + storeName + '\'' +
                ", passwordTemplateText='" + passwordTemplateText + '\'' +
                ", belowPageText='" + belowPageText + '\'' +
                ", registerBgImgUrl=" + registerBgImgUrl +
                ", loginBgIImgUrl=" + loginBgImgUrl +
                '}';
    }
}
