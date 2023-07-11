package com.shoppingMall.service;

import com.shoppingMall.entity.bo.SingleAllBo;

/**
 * 操作邮箱
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/3
 */
public interface EmailService {

    /**
     * 获取redis存放邮箱验证码的key,以及存放邮箱
     *
     * @param emailAccount
     * @return
     */
    SingleAllBo<String> getEmailKey(String emailAccount);

    /**
     * 发送邮箱验证码
     *
     * @param captchaId
     * @param captchaCode
     * @param emailKey
     * @param emailAccount
     */
    void sendEmail(String captchaId, String captchaCode, String emailKey, String emailAccount);

    /**
     * 验证邮箱验证码
     *
     * @param emailKey
     * @param emailCode
     * @param emailAccount
     * @return
     */
    SingleAllBo<Boolean> checkEmailCode(String emailKey, String emailCode, String emailAccount);
}
