package com.shoppingMall.utils;

import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;

/**
 * 邮箱工具类
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/2
 */
public class EmailUtil {

    /**
     * 发送邮箱验证码
     *
     * @param fromEmail     发送者
     * @param host          主机
     * @param port          端口
     * @param emailPassword 授权码
     * @param toEmail       接收者
     * @param message       内容
     * @param storeName     商店名
     */
    public static void postEmail(String fromEmail, String host, String port, String emailPassword, String toEmail, String message, String storeName) {
        String content = String.format("【%s】验证码为：<span style='color: red;'>%s</span>，10分钟内有效。", storeName, message);
        String emailTitle = String.format("%s---验证码", storeName);
        MailAccount mailAccount = new MailAccount();
        mailAccount.setFrom(fromEmail);
        mailAccount.setHost(host);
        mailAccount.setPort(Integer.valueOf(port));
        mailAccount.setUser(fromEmail);
        mailAccount.setPass(emailPassword);
        mailAccount.setSslEnable(true);
        MailUtil.send(mailAccount, toEmail, emailTitle, content, true);
    }
}
