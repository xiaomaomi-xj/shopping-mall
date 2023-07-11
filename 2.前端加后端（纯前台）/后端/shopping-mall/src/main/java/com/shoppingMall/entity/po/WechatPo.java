package com.shoppingMall.entity.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

/**
 * 微信用户实体类
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/5
 */
@Entity(name = "wechat")
public class WechatPo implements Serializable {
    private static final long serialVersionUID = 7533311646842795074L;

    /**
     * 微信用户id
     */
    @Id
    @Column(name = "wechat_id")
    private Long wechatId;

    /**
     * 头像地址
     */
    @Column(name = "wechat_head_url")
    private String wechatHeadUrl;

    /**
     * 微信账户
     */
    @Column(name = "open_id")
    private String openId;

    /**
     * 微信昵称
     */
    @Column(name = "nick_name")
    private String nickName;

    /**
     * 微信性别
     */
    @Column(name = "sex")
    private int sex;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private Long userId;

    public WechatPo() {
    }

    public WechatPo(Long wechatId, String wechatHeadUrl, String openId, String nickName, int sex, Long userId) {
        this.wechatId = wechatId;
        this.wechatHeadUrl = wechatHeadUrl;
        this.openId = openId;
        this.nickName = nickName;
        this.sex = sex;
        this.userId = userId;
    }

    public Long getWechatId() {
        return wechatId;
    }

    public void setWechatId(Long wechatId) {
        this.wechatId = wechatId;
    }

    public String getWechatHeadUrl() {
        return wechatHeadUrl;
    }

    public void setWechatHeadUrl(String wechatHeadUrl) {
        this.wechatHeadUrl = wechatHeadUrl;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WechatPo wechatPo = (WechatPo) o;
        return sex == wechatPo.sex &&
                Objects.equals(wechatId, wechatPo.wechatId) &&
                Objects.equals(wechatHeadUrl, wechatPo.wechatHeadUrl) &&
                Objects.equals(openId, wechatPo.openId) &&
                Objects.equals(nickName, wechatPo.nickName) &&
                Objects.equals(userId, wechatPo.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(wechatId, wechatHeadUrl, openId, nickName, sex, userId);
    }

    @Override
    public String toString() {
        return "WechatPo{" +
                "wechatId=" + wechatId +
                ", wechatHeadUrl='" + wechatHeadUrl + '\'' +
                ", openId='" + openId + '\'' +
                ", nickName='" + nickName + '\'' +
                ", sex=" + sex +
                ", userId=" + userId +
                '}';
    }
}
