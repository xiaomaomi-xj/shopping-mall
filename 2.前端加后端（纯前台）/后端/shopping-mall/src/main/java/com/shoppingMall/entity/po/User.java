package com.shoppingMall.entity.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

/**
 * 用户实体类
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/2/28
 */
@Entity(name = "user")
public class User implements Serializable {
    private static final long serialVersionUID = 4346292116679799673L;

    /**
     * 用户id
     */
    @Id
    @Column(name = "user_id")
    private Long userId;

    /**
     * 用户头像地址
     */
    @Column(name = "user_head_url")
    private String userHeadUrl;

    /**
     * 用户名字
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 用户邮箱（账号）
     */
    @Column(name = "user_email")
    private String userEmail;

    /**
     * 用户性别
     */
    @Column(name = "user_sex")
    private int userSex;

    /**
     * 用户密码
     */
    @Column(name = "password")
    private String password;

    public User() {

    }

    public User(Long userId, String userHeadUrl, String userName, String userEmail, int userSex, String password) {
        this.userId = userId;
        this.userHeadUrl = userHeadUrl;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userSex = userSex;
        this.password = password;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserHeadUrl() {
        return userHeadUrl;
    }

    public void setUserHeadUrl(String userHeadUrl) {
        this.userHeadUrl = userHeadUrl;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userSex == user.userSex &&
                Objects.equals(userId, user.userId) &&
                Objects.equals(userHeadUrl, user.userHeadUrl) &&
                Objects.equals(userName, user.userName) &&
                Objects.equals(userEmail, user.userEmail) &&
                Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, userHeadUrl, userName, userEmail, userSex, password);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userHeadUrl='" + userHeadUrl + '\'' +
                ", userName='" + userName + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userSex=" + userSex +
                ", password='" + password + '\'' +
                '}';
    }
}
