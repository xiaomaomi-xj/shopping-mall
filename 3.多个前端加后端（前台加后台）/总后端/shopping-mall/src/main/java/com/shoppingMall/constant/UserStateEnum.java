package com.shoppingMall.constant;

/**
 * 用户类型
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/5/6
 */
public enum UserStateEnum {
    /**
     * 二维码登录和邮箱登录
     */
    WECHAT("wechat-user"),
    USER("email-user");
    private String type;

    UserStateEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
