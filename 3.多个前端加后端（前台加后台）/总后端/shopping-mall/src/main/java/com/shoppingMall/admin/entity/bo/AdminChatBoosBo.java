package com.shoppingMall.admin.entity.bo;

/**
 * 后台返回聊天数据
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/5/10
 */
public class AdminChatBoosBo {
    /**
     * 发送人id
     */
    private String userId;

    /**
     * 发送人的身份
     */
    private int fromUser;

    /**
     * 消息内容
     */
    private String message;

    /**
     * 是否已读
     */
    private int isUnRead;

    public AdminChatBoosBo() {
    }

    public AdminChatBoosBo(String userId, int fromUser, String message, int isUnRead) {
        this.userId = userId;
        this.fromUser = fromUser;
        this.message = message;
        this.isUnRead = isUnRead;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getFromUser() {
        return fromUser;
    }

    public void setFromUser(int fromUser) {
        this.fromUser = fromUser;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getIsUnRead() {
        return isUnRead;
    }

    public void setIsUnRead(int isUnRead) {
        this.isUnRead = isUnRead;
    }

    @Override
    public String toString() {
        return "AdminChatBoosBo{" +
                "userId='" + userId + '\'' +
                ", fromUser=" + fromUser +
                ", message='" + message + '\'' +
                ", isUnRead=" + isUnRead +
                '}';
    }
}
