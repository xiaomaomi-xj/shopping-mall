package com.shoppingMall.pay.vippay.entity.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/25
 */
@Entity(name = "vip_card")
public class VipCard implements Serializable {
    private static final long serialVersionUID = 7921083674636820976L;
    /**
     * 购物卡id
     */
    @Id
    @Column(name = "vip_card_id")
    private Long vipCardId;

    /**
     * 购物卡账号
     */
    @Column(name = "vip_card_account")
    private String vipCardAccount;

    /**
     * 购物卡密码
     */
    @Column(name = "vip_card_password")
    private String vipCardPassword;

    /**
     * 购物卡余额
     */
    @Column(name = "vip_card_balance")
    private Float vipCardBalance;

    public VipCard() {
    }

    public VipCard(Long vipCardId, String vipCardAccount, String vipCardPassword, Float vipCardBalance) {
        this.vipCardId = vipCardId;
        this.vipCardAccount = vipCardAccount;
        this.vipCardPassword = vipCardPassword;
        this.vipCardBalance = vipCardBalance;
    }

    public Long getVipCardId() {
        return vipCardId;
    }

    public void setVipCardId(Long vipCardId) {
        this.vipCardId = vipCardId;
    }

    public String getVipCardAccount() {
        return vipCardAccount;
    }

    public void setVipCardAccount(String vipCardAccount) {
        this.vipCardAccount = vipCardAccount;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VipCard vipCard = (VipCard) o;
        return Objects.equals(vipCardId, vipCard.vipCardId) &&
                Objects.equals(vipCardAccount, vipCard.vipCardAccount) &&
                Objects.equals(vipCardPassword, vipCard.vipCardPassword) &&
                Objects.equals(vipCardBalance, vipCard.vipCardBalance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vipCardId, vipCardAccount, vipCardPassword, vipCardBalance);
    }

    @Override
    public String toString() {
        return "VipCard{" +
                "vipCardId=" + vipCardId +
                ", vipCardAccount='" + vipCardAccount + '\'' +
                ", vipCardPassword='" + vipCardPassword + '\'' +
                ", vipCardBalance=" + vipCardBalance +
                '}';
    }
}
