package com.shoppingMall.admin.entity.vo;

/**
 * 后台发送消息接受的参数vo类
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/5/11
 */
public class AdminChatBoosVo {
    /**
     * 用户id
     */
    private String userId;

    /**
     * 发送的消息
     */
    private String message;

    public AdminChatBoosVo() {
    }

    public AdminChatBoosVo(String userId, String message) {
        this.userId = userId;
        this.message = message;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "AdminChatBoosVo{" +
                "userId='" + userId + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
