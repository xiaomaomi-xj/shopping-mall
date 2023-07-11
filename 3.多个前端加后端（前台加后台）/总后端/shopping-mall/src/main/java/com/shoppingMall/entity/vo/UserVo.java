package com.shoppingMall.entity.vo;

/**
 * 用于注册用户接受参数
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/2
 */
public class UserVo {
    /**
     * 用于获取当前用户
     */
    private String token;

    /**
     * 用户名字
     */
    private String userName;

    /**
     * 用户邮箱（账号）
     */
    private String userEmail;

    /**
     * 用户性别
     */
    private int userSex;

    /**
     * 用户密码
     */
    private String password;

    public UserVo(){

    }

    public UserVo(String token, String userName, String userEmail, int userSex, String password) {
        this.token = token;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userSex = userSex;
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserVo{" +
                "token='" + token + '\'' +
                ", userName='" + userName + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userSex=" + userSex +
                ", password='" + password + '\'' +
                '}';
    }
}
