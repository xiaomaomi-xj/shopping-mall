package com.shoppingMall.entity.vo;

/**
 * 接受token所需要的参数
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/13
 */
public class TokenVo {

    /**
     * 令牌
     */
    private String token;

    /**
     * 刷新令牌
     */
    private String refreshToken;

    public TokenVo() {
    }

    public TokenVo(String token, String refreshToken) {
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

    @Override
    public String toString() {
        return "TokenVo{" +
                "token='" + token + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                '}';
    }
}
