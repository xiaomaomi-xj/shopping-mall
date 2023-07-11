package com.shoppingMall.entity.bo;

/**
 * 用于返回参数
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/5
 */
public class WechatBo {
    /**
     * 头像url
     */
    private String headImgUrl;

    /**
     * 微信账户
     */
    private String openId;

    /**
     * 微信昵称
     */
    private String nickName;

    /**
     * 微信性别
     */
    private int sex;

    public WechatBo() {

    }

    public WechatBo(String headImgUrl, String openId, String nickName, int sex) {
        this.headImgUrl = headImgUrl;
        this.openId = openId;
        this.nickName = nickName;
        this.sex = sex;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
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

    @Override
    public String toString() {
        return "WechatBo{" +
                "headImgUrl='" + headImgUrl + '\'' +
                ", openId='" + openId + '\'' +
                ", nickName='" + nickName + '\'' +
                ", sex=" + sex +
                '}';
    }
}
