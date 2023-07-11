package com.shoppingMall.entity.bo;

/**
 * 留给后端获取全部用户使用
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/4/12
 */
public class UserAndWechatBo {
    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户头像
     */
    private String userHeadUrl;

    /**
     * 用户名字
     */
    private String userName;

    /**
     * 用户账号
     */
    private String userAccount;

    /**
     * 性别
     */
    private int userSex;

    /**
     * 绑定的id
     */
    private String bindId;

    /**
     * 用户类型
     */
    private String userType;

    public UserAndWechatBo() {
    }

    public UserAndWechatBo(String userId, String userHeadUrl, String userName, String userAccount, int userSex, String bindId, String userType) {
        this.userId = userId;
        this.userHeadUrl = userHeadUrl;
        this.userName = userName;
        this.userAccount = userAccount;
        this.userSex = userSex;
        this.bindId = bindId;
        this.userType = userType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
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

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public int getUserSex() {
        return userSex;
    }

    public void setUserSex(int userSex) {
        this.userSex = userSex;
    }

    public String getBindId() {
        return bindId;
    }

    public void setBindId(String bindId) {
        this.bindId = bindId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "UserAndWechatBo{" +
                "userId='" + userId + '\'' +
                ", userHeadUrl='" + userHeadUrl + '\'' +
                ", userName='" + userName + '\'' +
                ", userAccount='" + userAccount + '\'' +
                ", userSex=" + userSex +
                ", bindId='" + bindId + '\'' +
                ", userType='" + userType + '\'' +
                '}';
    }
}
