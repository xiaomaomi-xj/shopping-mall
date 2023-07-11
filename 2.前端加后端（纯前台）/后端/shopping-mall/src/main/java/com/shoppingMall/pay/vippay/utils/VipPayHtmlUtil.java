package com.shoppingMall.pay.vippay.utils;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.shoppingMall.constant.GlobalConstant;
import com.shoppingMall.pay.vippay.constant.VipPayConstant;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * 页面工具类
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/25
 */
public class VipPayHtmlUtil {
    /**
     * 生成支付错误页面
     *
     * @param errorMessage
     * @return
     */
    public static String getVipErrorPayHtml(String errorMessage) {
        try {
            String payErrorHtml = Files.readString(Path.of(VipPayConstant.PAY_ERROR_HTML_PATH), StandardCharsets.UTF_8);
            return payErrorHtml
                    .replace("#{errorMessage}", errorMessage)
                    .replace("#{requestFromUrl}", GlobalConstant.FORE_END_LOCAL_HOME_URL);
        } catch (Exception e) {
            return "error";
        }
    }

    /**
     * 生成支付页面
     *
     * @param callbackUrl
     * @param requestFromUrl
     * @param goodsName
     * @param amount
     * @param orderId
     * @return
     */
    public static String getVipPayHtml(String callbackUrl, String requestFromUrl, String goodsName, String amount, String orderId, String expire, String token) {
        try {
            String payHtml = Files.readString(Path.of(VipPayConstant.PAY_HTML_PATH), StandardCharsets.UTF_8);
            return payHtml
                    .replace(String.format("#{%s}", "callbackUrl"), callbackUrl)
                    .replace(String.format("#{%s}", "requestFromUrl"), requestFromUrl)
                    .replace(String.format("#{%s}", "goodsName"), goodsName)
                    .replace(String.format("#{%s}", "amount"), amount)
                    .replace(String.format("#{%s}", "expire"), expire)
                    .replace(String.format("#{%s}", "orderId"), orderId)
                    .replace(String.format("#{%s}", "token"), token);
        } catch (Exception e) {
            return "error";
        }
    }

    /**
     * 获取每个订单的token
     *
     * @return
     */
    public static String getPayHtmlToken() {
        return IdUtil.objectId() + RandomUtil.randomString(GlobalConstant.CODE_TEMPLATE, 10);
    }
}
