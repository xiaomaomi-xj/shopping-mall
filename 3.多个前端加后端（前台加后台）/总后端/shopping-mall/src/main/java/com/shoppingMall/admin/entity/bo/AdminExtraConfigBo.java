package com.shoppingMall.admin.entity.bo;

import com.shoppingMall.exception.BizException;
import com.shoppingMall.utils.Assert;
import com.shoppingMall.utils.ChatCheckUtil;

/**
 * 管理端获取特殊配置bo类
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/5/6
 */
public class AdminExtraConfigBo {

    /**
     * 特殊配置id
     */
    private String extraId;

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

    public AdminExtraConfigBo() {
    }

    public AdminExtraConfigBo(String extraId, boolean storeNameHaveSpecial, String specialText, String storeName, String passwordTemplateText, String belowPageText, String registerBgImgUrl, String loginBgImgUrl) {
        this.extraId = extraId;
        this.storeNameHaveSpecial = storeNameHaveSpecial;
        this.specialText = specialText;
        this.storeName = storeName;
        this.passwordTemplateText = passwordTemplateText;
        this.belowPageText = belowPageText;
        this.registerBgImgUrl = registerBgImgUrl;
        this.loginBgImgUrl = loginBgImgUrl;
    }

    public String getExtraId() {
        return extraId;
    }

    public void setExtraId(String extraId) {
        this.extraId = extraId;
    }

    public boolean isStoreNameHaveSpecial() {
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

    public String getLoginBgImgUrl() {
        return loginBgImgUrl;
    }

    public void setLoginBgImgUrl(String loginBgImgUrl) {
        this.loginBgImgUrl = loginBgImgUrl;
    }

    public void check() {
        Assert.isNotBlank(belowPageText, "页脚内容");
        Assert.isNotBlank(extraId, "特殊配置id");
        Assert.isNotBlank(passwordTemplateText, "密码模板");
        Assert.isNotBlank(specialText, "特殊字");
        Assert.isNotBlank(storeName, "商城名字");
        Assert.isNotNull(storeNameHaveSpecial, "商城名是否含有特殊字");
        if(ChatCheckUtil.check(passwordTemplateText) && passwordTemplateText.length() > 2){
            throw new BizException("密码模板只能为1个字符！");
        }else if(!ChatCheckUtil.check(passwordTemplateText) && passwordTemplateText.length() > 1){
            throw new BizException("密码模板只能为1个字符！");
        }
        if (specialText.length() > 1){
            throw new BizException("特殊字最多为一个字符！");
        }
    }

    @Override
    public String toString() {
        return "AdminExtraConfigBo{" +
                "extraId='" + extraId + '\'' +
                ", storeNameHaveSpecial=" + storeNameHaveSpecial +
                ", specialText='" + specialText + '\'' +
                ", storeName='" + storeName + '\'' +
                ", passwordTemplateText='" + passwordTemplateText + '\'' +
                ", belowPageText='" + belowPageText + '\'' +
                ", registerBgImgUrl='" + registerBgImgUrl + '\'' +
                ", loginBgImgUrl='" + loginBgImgUrl + '\'' +
                '}';
    }
}
