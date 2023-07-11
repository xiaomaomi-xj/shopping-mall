package com.shoppingMall.service;

import com.shoppingMall.entity.bo.LoginUserBo;
import com.shoppingMall.entity.bo.SingleAllBo;
import com.shoppingMall.entity.bo.TokenBo;

/**
 * 用于token的存储和验证和获取
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/12
 */
public interface TokenService {
    /**
     * 验证token是否正确
     *
     * @param token
     * @return
     */
    SingleAllBo<Boolean> checkToken(String token);

    /**
     * 创建token并存储
     *
     * @param loginType
     * @param userId
     * @return
     */
    TokenBo createToken(String loginType, String userId);

    /**
     * 刷新token
     *
     * @param token
     * @param refreshToken
     * @return
     */
    TokenBo refreshToken(String token,String refreshToken);

    /**
     * 根据token获取用户
     *
     * @param token
     * @return
     */
    LoginUserBo getUserOnToken(String token);
}
