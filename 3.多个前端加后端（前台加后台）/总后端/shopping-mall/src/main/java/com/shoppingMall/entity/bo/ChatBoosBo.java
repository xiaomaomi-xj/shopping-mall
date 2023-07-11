package com.shoppingMall.entity.bo;

/**
 * 聊天消息返回类
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/20
 */
public class ChatBoosBo {

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

    public ChatBoosBo() {
    }

    public ChatBoosBo(int fromUser, String message, int isUnRead) {
        this.fromUser = fromUser;
        this.message = message;
        this.isUnRead = isUnRead;
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
        return "ChatBoosBo{" +
                "fromUser=" + fromUser +
                ", message='" + message + '\'' +
                ", isUnRead=" + isUnRead +
                '}';
    }
}
