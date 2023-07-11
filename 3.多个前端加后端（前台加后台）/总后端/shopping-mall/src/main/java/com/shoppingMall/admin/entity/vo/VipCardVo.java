package com.shoppingMall.admin.entity.vo;

/**
 * 购物卡接受参数类
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/5/4
 */
public class VipCardVo {
    /**
     * 购物卡id
     */
    private String vipCardId;

    /**
     * 购物卡支付密码
     */
    private String vipCardPassword;

    /**
     * 购物卡余额
     */
    private Float vipCardBalance;

    public VipCardVo() {
    }

    public VipCardVo(String vipCardId, String vipCardPassword, Float vipCardBalance) {
        this.vipCardId = vipCardId;
        this.vipCardPassword = vipCardPassword;
        this.vipCardBalance = vipCardBalance;
    }

    public String getVipCardId() {
        return vipCardId;
    }

    public void setVipCardId(String vipCardId) {
        this.vipCardId = vipCardId;
    }

    public String getVipCardPassword() {
        return vipCardPassword;
    }

    public void setVipCardPassword(String vipCardPassword) {
        this.vipCardPassword = vipCardPassword;
    }

    public Float getVipCardBalance() {
        return vipCardBalance;
    }

    public void setVipCardBalance(Float vipCardBalance) {
        this.vipCardBalance = vipCardBalance;
    }

    @Override
    public String toString() {
        return "VipCardVo{" +
                "vipCardId='" + vipCardId + '\'' +
                ", vipCardPassword='" + vipCardPassword + '\'' +
                ", vipCardBalance=" + vipCardBalance +
                '}';
    }
}
