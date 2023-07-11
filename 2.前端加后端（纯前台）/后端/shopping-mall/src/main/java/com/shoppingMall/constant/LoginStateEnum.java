package com.shoppingMall.constant;

/**
 * 登录类型
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/12
 */
public enum LoginStateEnum {
    /**
     * 二维码登录和邮箱登录
     */
    QR_CODE("QR_CODE"),
    EMAIL_CODE("EMAIL_CODE");

    private String type;
    LoginStateEnum(String type){
        this.type=type;
    }

    public String getType() {
        return type;
    }
}
