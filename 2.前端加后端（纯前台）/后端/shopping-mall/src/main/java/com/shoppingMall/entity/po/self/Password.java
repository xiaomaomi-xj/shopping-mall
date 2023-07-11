package com.shoppingMall.entity.po.self;

/**
 * 加密需要的盐
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/3
 */
public class Password {

    /**
     * 加密时用到的盐
     */
    private String salt;

    public Password(){

    }

    public Password(String salt) {
        this.salt = salt;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    @Override
    public String toString() {
        return "Password{" +
                "salt='" + salt + '\'' +
                '}';
    }
}
