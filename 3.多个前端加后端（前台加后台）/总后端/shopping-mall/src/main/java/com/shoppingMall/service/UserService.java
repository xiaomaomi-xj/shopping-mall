package com.shoppingMall.service;

import com.shoppingMall.entity.bo.SingleAllBo;
import com.shoppingMall.entity.bo.TokenBo;
import com.shoppingMall.entity.bo.UserAndWechatBo;

import java.util.List;

/**
 * 操作用户
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/2/28
 */
public interface UserService {
    /**
     * 用户注册
     *
     * @param emailKey
     * @param emailCode
     * @param userName
     * @param userEmail
     * @param sex
     * @param password
     * @return
     */
    TokenBo register(String emailKey, String emailCode, String userName, String userEmail, int sex, String password);

    /**
     * 账号密码登录
     *
     * @param userEmail
     * @param password
     * @return
     */
    TokenBo loginOnEmailAndPassword(String userEmail, String password);

    /**
     * 账号验证码登录
     *
     * @param userEmail
     * @param emailKey
     * @param emailCode
     * @return
     */
    TokenBo loginOnEmailCode(String userEmail, String emailKey, String emailCode);

    /**
     * 修改当前用户密码
     *
     * @param token
     * @param oldPassword
     * @param newPassword
     * @return
     */
    void modifyPassword(String token, String oldPassword, String newPassword);

    /**
     * 修改当前用户信息
     *
     * @param token
     * @param imgData
     * @param userEmail
     * @param userName
     * @param userSex
     * @return
     */
    TokenBo modifyUserInfo(String token, String imgData, String userEmail, String userName, int userSex);

    /**
     * 获取全部用户
     *
     * @return
     */
    List<UserAndWechatBo> getAllUser();

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    SingleAllBo<Boolean> delUser(String id);
}
