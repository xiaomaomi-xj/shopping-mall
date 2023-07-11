package com.shoppingMall.service;

import com.shoppingMall.entity.bo.SingleAllBo;
import com.shoppingMall.entity.bo.TokenBo;

/**
 * 扫码登录
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/5
 */
public interface WechatService {

    /**
     * 获取微信登录id
     *
     * @return
     */
    SingleAllBo<String> getWechatLoginId();

    /**
     * 获取登录二维码
     *
     * @param loginImgId
     * @return
     */
    byte[] getWechatLoginImg(String loginImgId);

    /**
     * 获取用户信息
     *
     * @param code
     * @param state
     * @return
     */
    String getUserInfo(String code,String state);

    /**
     * 轮询验证登录是否成功
     *
     * @param loginImgId
     * @return
     */
    TokenBo checkLogin(String loginImgId);

    /**
     * 微信用户绑定邮箱账号
     *
     * @param token
     * @param userEmail
     * @param password
     * @return
     */
    TokenBo bindAccount(String token,String userEmail,String password);
}
