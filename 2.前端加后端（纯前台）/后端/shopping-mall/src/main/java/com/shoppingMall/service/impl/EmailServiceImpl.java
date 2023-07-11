package com.shoppingMall.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import com.shoppingMall.constant.GlobalConstant;
import com.shoppingMall.entity.bo.SingleAllBo;
import com.shoppingMall.entity.po.self.Email;
import com.shoppingMall.entity.po.self.SelfConfig;
import com.shoppingMall.exception.BizException;
import com.shoppingMall.properties.SelfConfigPropertiesBean;
import com.shoppingMall.service.CaptchaService;
import com.shoppingMall.service.EmailService;
import com.shoppingMall.utils.EmailUtil;
import com.shoppingMall.utils.ThreadPoolUtil;
import com.zhuyahui.annotation.ZyhService;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/3
 */
@ZyhService
public class EmailServiceImpl implements EmailService {
    private final SelfConfigPropertiesBean selfConfigPropertiesBean;
    private final RedisTemplate<String, String> redisTemplate;
    private final CaptchaService captchaService;

    public EmailServiceImpl(SelfConfigPropertiesBean selfConfigPropertiesBean, RedisTemplate<String, String> redisTemplate, CaptchaService captchaService) {
        this.selfConfigPropertiesBean = selfConfigPropertiesBean;
        this.redisTemplate = redisTemplate;
        this.captchaService = captchaService;
    }

    /**
     * 获取redis存放邮箱验证码的key,邮箱验证码有效期10分钟
     *
     * @return
     */
    @Override
    public SingleAllBo<String> getEmailKey(String emailAccount) {
        String code = RandomUtil.randomString(GlobalConstant.CODE_TEMPLATE, 6);
        String id = IdUtil.objectId();
        BoundListOperations<String, String> stringStringBoundListOperations = redisTemplate.boundListOps(id);
        //要记住先进后出
        stringStringBoundListOperations.leftPushAll(code,emailAccount);
        stringStringBoundListOperations.expire(10, TimeUnit.MINUTES);
        return new SingleAllBo<>(id);
    }

    /**
     * 发送邮箱
     *
     * @param emailKey
     * @param emailAccount
     */
    @Override
    public void sendEmail(String captchaId, String captchaCode, String emailKey, String emailAccount) {
        //先验证图片验证码
        SingleAllBo<Boolean> booleanSingleAllBo = captchaService.verifyCaptcha(captchaId, captchaCode);
        if (!booleanSingleAllBo.getValue()) {
            throw new BizException("图片验证码不通过！");
        }
        SelfConfig config = selfConfigPropertiesBean.getConfig();
        Email email = config.getEmail();
        Boolean aBoolean = redisTemplate.hasKey(emailKey);
        if (aBoolean != null && aBoolean) {
            BoundListOperations<String, String> stringStringBoundListOperations = redisTemplate.boundListOps(emailKey);
            String emailCode = stringStringBoundListOperations.index(1);
            if (ObjectUtil.isNotEmpty(emailCode)) {
                //对于发送邮箱，应该采用多线程的方式，防止堵塞
                ThreadPoolUtil.execute(() -> EmailUtil.postEmail(
                        email.getFromEmail(),
                        email.getHost(),
                        email.getPort(),
                        email.getEmailPassword(),
                        emailAccount,
                        emailCode,
                        config.getStoreName()
                ));
            } else {
                throw new BizException("非法调用！");
            }
        } else {
            throw new BizException("非法调用！");
        }
    }

    /**
     * 验证邮箱验证码
     *
     * @return
     */
    @Override
    public SingleAllBo<Boolean> checkEmailCode(String emailKey, String emailCode, String emailAccount) {
        boolean result = false;
        Boolean aBoolean = redisTemplate.hasKey(emailKey);
        if (aBoolean != null && aBoolean) {
            BoundListOperations<String, String> stringStringBoundListOperations = redisTemplate.boundListOps(emailKey);
            String code = stringStringBoundListOperations.index(1);
            String account = stringStringBoundListOperations.index(0);
            if (ObjectUtil.isNotNull(code) && ObjectUtil.isNotNull(account)) {
                if (emailAccount.equals(account) && emailCode.equalsIgnoreCase(code)) {
                    result = true;
                    redisTemplate.delete(emailKey);
                }
            }
        }
        return new SingleAllBo<>(result);
    }
}
