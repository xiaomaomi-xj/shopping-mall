package com.shoppingMall.admin.entity.bo;

/**
 * 购物卡bo类
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/5/4
 */
public class VipCardBo {
    /**
     * 购物卡id
     */
    private String vipCardId;

    /**
     * 购物卡账号
     */
    private String vipCardAccount;

    /**
     * 购物卡金额
     */
    private Float vipCardBalance;

    public VipCardBo() {
    }

    public VipCardBo(String vipCardId, String vipCardAccount, Float vipCardBalance) {
        this.vipCardId = vipCardId;
        this.vipCardAccount = vipCardAccount;
        this.vipCardBalance = vipCardBalance;
    }

    public String getVipCardId() {
        return vipCardId;
    }

    public void setVipCardId(String vipCardId) {
        this.vipCardId = vipCardId;
    }

    public String getVipCardAccount() {
        return vipCardAccount;
    }

    public void setVipCardAccount(String vipCardAccount) {
        this.vipCardAccount = vipCardAccount;
    }

    public Float getVipCardBalance() {
        return vipCardBalance;
    }

    public void setVipCardBalance(Float vipCardBalance) {
        this.vipCardBalance = vipCardBalance;
    }

    @Override
    public String toString() {
        return "VipCardBo{" +
                "vipCardId='" + vipCardId + '\'' +
                ", vipCardAccount='" + vipCardAccount + '\'' +
                ", vipCardBalance=" + vipCardBalance +
                '}';
    }
}
