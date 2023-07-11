package com.shoppingMall.entity.bo;

/**
 * 用于登录验证token实体类
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/13
 */
public class TokenBo {

    /**
     * 状态,是否全部过期
     */
    private Boolean status;

    /**
     * 令牌
     */
    private String token;

    /**
     * 刷新令牌
     */
    private String refreshToken;

    public TokenBo() {
    }

    public TokenBo(Boolean status, String token, String refreshToken) {
        this.status = status;
        this.token = token;
        this.refreshToken = refreshToken;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
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
        return "TokenBo{" +
                "status=" + status +
                ", token='" + token + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                '}';
    }
}
