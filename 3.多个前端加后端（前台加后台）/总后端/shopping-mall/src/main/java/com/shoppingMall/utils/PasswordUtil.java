package com.shoppingMall.utils;

import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.util.DigestUtils;

/**
 * 密码加密工具类
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/5
 */
public class PasswordUtil {

    /**
     * 对密码进行加密
     *
     * @param password
     * @param salt
     * @return
     */
    public static String getPassword(String password,String salt){
        password = salt + password + salt;
        return DigestUtils.md5DigestAsHex(password.getBytes());
    }

    /**
     * 生成用户token令牌,加密的message,只是一个随机生成的id,并没有什么敏感信息
     *
     * @param message
     * @param salt
     * @return
     */
    public static String createToken(String message,String salt){
        BasicTextEncryptor textEncryptor=new BasicTextEncryptor();
        textEncryptor.setPassword(salt);
        return textEncryptor.encrypt(message);
    }
}
