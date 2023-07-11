package com.shoppingMall.admin.service;

import com.shoppingMall.admin.entity.bo.VipCardBo;
import com.shoppingMall.entity.bo.SingleAllBo;

import java.util.List;

/**
 * 购物卡服务类
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/5/4
 */
public interface VipCardService {
    /**
     * 获取所有购物卡数据
     *
     * @return
     */
    List<VipCardBo> getAllVipCard();

    /**
     * 添加购物卡
     *
     * @param vipCardPassword
     * @param vipCardBalance
     */
    SingleAllBo<Boolean> addVipCard(String vipCardPassword, Float vipCardBalance);

    /**
     * 修改购物卡余额
     *
     * @param vipCardId
     * @param vipCardBalance
     * @return
     */
    SingleAllBo<Boolean> modifyVipCardBalance(String vipCardId, Float vipCardBalance);

    /**
     * 删除购物卡
     *
     * @param vipCardId
     * @return
     */
    SingleAllBo<Boolean> delVipCard(String vipCardId);

    /**
     * 修改购物卡支付密码
     *
     * @param vipCardId
     * @param vipCardPassword
     * @return
     */
    SingleAllBo<Boolean> modifyVipCardPassword(String vipCardId, String vipCardPassword);
}
