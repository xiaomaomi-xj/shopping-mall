package com.shoppingMall.pay.vippay.dao;

import com.shoppingMall.pay.vippay.entity.po.VipCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * 购物卡控制类
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/25
 */
public interface VipCardDao extends JpaRepository<VipCard,Long>, JpaSpecificationExecutor<VipCard> {
    /**
     * 根据卡号查询信息
     *
     * @param vipCardAccount
     * @return
     */
    Optional<VipCard> findByVipCardAccount(String vipCardAccount);

    /**
     * 减少金额(妈的还必须加事务)
     *
     * @param vipCardBalance
     * @param vipCardId
     * @return
     */
    @Modifying
    @Transactional(rollbackFor = RuntimeException.class)
    @Query(value = "UPDATE vip_card set vip_card_balance = vip_card_balance - :vipCardBalance where vip_card_id=:vipCardId",nativeQuery = true)
    void declineVipCardBalance(@Param("vipCardBalance") Float vipCardBalance,@Param("vipCardId") Long vipCardId);

    /**
     * 增加金额
     *
     * @param vipCardBalance
     * @param vipCardId
     */
    @Modifying
    @Transactional(rollbackFor = RuntimeException.class)
    @Query(value = "UPDATE vip_card set vip_card_balance = vip_card_balance + :vipCardBalance where vip_card_id=:vipCardId",nativeQuery = true)
    void increaseVipCardBalance(@Param("vipCardBalance") Float vipCardBalance,@Param("vipCardId") Long vipCardId);
}
