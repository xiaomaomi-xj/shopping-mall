package com.shoppingMall.entity.vo;

/**
 * 修改用户信息vo类
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/22
 */
public class ModifyUserInfoVo {
    /**
     * 用户令牌
     */
    private String token;

    /**
     * 头像数据
     */
    private String imgData;

    /**
     * 用户账号
     */
    private String userEmail;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户性别
     */
    private int userSex;

    public ModifyUserInfoVo() {
    }

    public ModifyUserInfoVo(String token, String imgData, String userEmail, String userName, int userSex) {
        this.token = token;
        this.imgData = imgData;
        this.userEmail = userEmail;
        this.userName = userName;
        this.userSex = userSex;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getImgData() {
        return imgData;
    }

    public void setImgData(String imgData) {
        this.imgData = imgData;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserSex() {
        return userSex;
    }

    public void setUserSex(int userSex) {
        this.userSex = userSex;
    }

    @Override
    public String toString() {
        return "ModifyUserInfoVo{" +
                "token='" + token + '\'' +
                ", imgData='" + imgData + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userName='" + userName + '\'' +
                ", userSex=" + userSex +
                '}';
    }
}
