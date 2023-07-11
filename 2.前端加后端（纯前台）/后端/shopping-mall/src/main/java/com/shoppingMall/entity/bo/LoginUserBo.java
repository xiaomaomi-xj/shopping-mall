package com.shoppingMall.entity.bo;

/**
 * 根据token查询用户
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/12
 */
public class LoginUserBo {
    /**
     * 登录状态,是否登录成功
     */
    private Boolean status;

    /**
     * 登录成功后的用户
     */
    private Object data;

    /**
     * 登录类型,两种情况,一种邮箱,一种微信id
     */
    private String type;

    public LoginUserBo() {
    }

    public LoginUserBo(Boolean status, Object data, String type) {
        this.status = status;
        this.data = data;
        this.type = type;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "LoginStateBo{" +
                "status=" + status +
                ", data=" + data +
                ", type='" + type + '\'' +
                '}';
    }
}
