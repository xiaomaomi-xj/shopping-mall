package com.shoppingMall.dao;

import com.shoppingMall.entity.po.WechatPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 用于操作微信用户
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/5
 */
public interface WechatDao extends JpaRepository<WechatPo,Long>, JpaSpecificationExecutor<WechatPo> {
    /**
     * 根据openid判断用户是否存在
     *
     * @param openId
     * @return
     */
    boolean existsByOpenId(String openId);

    /**
     * 根据openid查询微信用户
     *
     * @param openId
     * @return
     */
    WechatPo findByOpenId(String openId);

    /**
     * 根据userId查询wechat
     *
     * @param userId
     * @return
     */
    boolean existsByUserId(Long userId);
}
