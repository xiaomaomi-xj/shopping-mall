package com.shoppingMall.utils;

import cn.hutool.captcha.LineCaptcha;
import cn.hutool.captcha.generator.RandomGenerator;

/**
 * 验证码工具类
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/2
 */
public class CaptchaUtil {
    /**
     * 线段干扰验证码实例
     */
    private static final LineCaptcha LINE_CAPTCHA = cn.hutool.captcha.CaptchaUtil.createLineCaptcha(200, 100);

    /**
     * 获取自定义验证码
     *
     * @return
     */
    public static byte[] getCheckCode(String code) {
        RandomGenerator randomGenerator=new RandomGenerator(code,4){
            private static final long serialVersionUID = -3842555854733033031L;
            @Override
            public String generate() {
                //直接原样返回即可
                return this.baseStr.substring(0,this.length);
            }
        };
        LINE_CAPTCHA.setGenerator(randomGenerator);
        LINE_CAPTCHA.createCode();
        return LINE_CAPTCHA.getImageBytes();
    }
}
