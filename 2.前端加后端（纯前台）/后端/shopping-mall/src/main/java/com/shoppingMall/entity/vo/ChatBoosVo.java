package com.shoppingMall.entity.vo;

/**
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/21
 */
public class ChatBoosVo {
    /**
     * 用户令牌
     */
    private String token;

    /**
     * 发送的消息
     */
    private String message;

    public ChatBoosVo() {
    }

    public ChatBoosVo(String token, String message) {
        this.token = token;
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ChatBoosVo{" +
                "token='" + token + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
