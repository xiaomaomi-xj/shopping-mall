package com.shoppingMall.entity.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

/**
 * 聊天信息实体类
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/2/28
 */
@Entity(name = "chat_boos")
public class ChatBoos implements Serializable {
    private static final long serialVersionUID = -3702672051436075813L;

    /**
     * 消息id
     */
    @Id
    @Column(name = "message_id")
    private Long messageId;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 发送人身份，0老板，1客户
     */
    @Column(name = "from_user")
    private int fromUser;

    /**
     * 消息内容
     */
    @Column(name = "message")
    private String message;

    /**
     * 是否已读
     */
    @Column(name = "is_un_read")
    private int isUnRead;

    public ChatBoos() {

    }

    public ChatBoos(Long messageId, Long userId, int fromUser, String message, int isUnRead) {
        this.messageId = messageId;
        this.userId = userId;
        this.fromUser = fromUser;
        this.message = message;
        this.isUnRead = isUnRead;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatBoos chatBoos = (ChatBoos) o;
        return fromUser == chatBoos.fromUser &&
                isUnRead == chatBoos.isUnRead &&
                Objects.equals(messageId, chatBoos.messageId) &&
                Objects.equals(userId, chatBoos.userId) &&
                Objects.equals(message, chatBoos.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(messageId, userId, fromUser, message, isUnRead);
    }

    @Override
    public String toString() {
        return "ChatBoos{" +
                "messageId=" + messageId +
                ", userId=" + userId +
                ", fromUser=" + fromUser +
                ", message='" + message + '\'' +
                ", isUnRead=" + isUnRead +
                '}';
    }
}
