package com.shoppingMall.manager;

import com.shoppingMall.entity.po.User;
import com.shoppingMall.entity.po.WechatPo;

/**
 * 用于多次使用的token管理类
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/21
 */
public interface ManagerTokenService {
    /**
     * 根据token获取用户id
     *
     * @return
     * @param token
     */
    Long getUserId(String token);

    /**
     * 根据token获取用户信息
     *
     * @param token
     * @return
     */
    String getLoginTypeOnToken(String token);

    /**
     * 根据token获取用户
     *
     * @param token
     * @return
     */
    User getUserOnToken(String token);

    /**
     * 根据token获取微信用户
     *
     * @param token
     * @return
     */
    WechatPo getWechatOnToken(String token);
}
