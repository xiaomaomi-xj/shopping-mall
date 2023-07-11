package com.shoppingMall.pay.core.utils;

import com.shoppingMall.constant.GlobalConstant;
import com.shoppingMall.pay.core.constant.PayHtmlConstant;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * 全局支付工具类
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/25
 */
public class PayUtil {
    /**
     * 返回支付失败页面
     *
     * @param message
     * @return
     */
    public static String getPayErrorHtml(String message) {
        try {
            String payErrorHtml = Files.readString(Path.of(PayHtmlConstant.PAY_ERROR_HTML), StandardCharsets.UTF_8);
            return payErrorHtml.replace("#{requestFromUrl}", GlobalConstant.FORE_END_LOCAL_HOME_URL).replace("#{message}", message);
        } catch (IOException e) {
            return "error";
        }
    }

    /**
     * 返回支付成功页面
     *
     * @return
     */
    public static String getPaySuccessHtml() {
        try {
            String paySuccessHtml = Files.readString(Path.of(PayHtmlConstant.PAY_SUCCESS_HTML), StandardCharsets.UTF_8);
            return paySuccessHtml.replace("#{requestFromUrl}", GlobalConstant.FORE_END_LOCAL_HOME_URL);
        } catch (IOException e) {
            return "error";
        }
    }
}
