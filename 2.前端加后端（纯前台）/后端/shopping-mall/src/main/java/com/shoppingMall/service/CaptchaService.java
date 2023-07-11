package com.shoppingMall.service;

import com.shoppingMall.entity.bo.SingleAllBo;

/**
 * 操作图片验证码
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/3
 */
public interface CaptchaService {

    /**
     * 获取redis存取的验证码的key
     *
     * @return
     */
    SingleAllBo<String> getCaptchaKey();

    /**
     * 根据id查询code，生成图片
     *
     * @param id
     * @return
     */
    byte[] getCaptchaContent(String id);

    /**
     * 根据redis的key，来判断验证码正确性
     *
     * @param id
     * @param code
     * @return
     */
    SingleAllBo<Boolean> verifyCaptcha(String id, String code);

}
