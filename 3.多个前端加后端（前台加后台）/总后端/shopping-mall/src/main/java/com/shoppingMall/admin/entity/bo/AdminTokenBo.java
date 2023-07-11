package com.shoppingMall.admin.entity.bo;

/**
 * 存储后台token
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/5/4
 */
public class AdminTokenBo {
    /**
     * 后台令牌
     */
    private String token;

    /**
     * 后台刷新令牌
     */
    private String refreshToken;

    public AdminTokenBo(){

    }

    public AdminTokenBo(String token, String refreshToken) {
        this.token = token;
        this.refreshToken = refreshToken;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
