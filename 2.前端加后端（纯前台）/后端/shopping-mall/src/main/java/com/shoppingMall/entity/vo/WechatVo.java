package com.shoppingMall.entity.vo;

/**
 * 微信接收参数实体类
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/5
 */
public class WechatVo {

    /**
     * code码
     */
    private String code;

    /**
     * 回调状态
     */
    private String state;

    public WechatVo(){

    }

    public WechatVo(String code, String state) {
        this.code = code;
        this.state = state;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "WechatVo{" +
                "code='" + code + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
