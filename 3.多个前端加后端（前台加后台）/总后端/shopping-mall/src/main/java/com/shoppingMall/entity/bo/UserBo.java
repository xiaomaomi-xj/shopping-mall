package com.shoppingMall.entity.bo;

/**
 * 用于返回参数
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/19
 */
public class UserBo {
    /**
     * 用户头像url
     */
    private String headImgUrl;
    /**
     * 姓名
     */
    private String userName;

    /**
     * 账号
     */
    private String userEmail;

    /**
     * 性别
     */
    private int userSex;

    public UserBo() {
    }

    public UserBo(String headImgUrl, String userName, String userEmail, int userSex) {
        this.headImgUrl = headImgUrl;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userSex = userSex;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public int getUserSex() {
        return userSex;
    }

    public void setUserSex(int userSex) {
        this.userSex = userSex;
    }

    @Override
    public String toString() {
        return "UserBo{" +
                "headImgUrl='" + headImgUrl + '\'' +
                ", userName='" + userName + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userSex=" + userSex +
                '}';
    }
}
