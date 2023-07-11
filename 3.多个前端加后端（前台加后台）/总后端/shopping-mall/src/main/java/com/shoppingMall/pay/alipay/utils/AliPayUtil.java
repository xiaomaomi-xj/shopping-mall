package com.shoppingMall.pay.alipay.utils;

import cn.hutool.core.date.DateUtil;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.shoppingMall.entity.po.Merchant;
import com.shoppingMall.exception.AppException;
import com.shoppingMall.exception.BizException;
import com.shoppingMall.pay.alipay.constant.AliPayConstant;
import org.springframework.http.HttpMethod;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * 阿里支付工具类
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/23
 */
public class AliPayUtil {

    /**
     * 创建订单，获取支付链接
     *
     * @param merchant       商户信息
     * @param orderId        订单号
     * @param amount         金额
     * @param goodsName      商品名字
     * @param requestFromUrl 来源地址
     * @return
     */
    public static String getPayUrl(Merchant merchant, Long orderId, Float amount, String goodsName, String requestFromUrl) {
        AlipayConfig alipayConfig = new AlipayConfig();
        alipayConfig.setServerUrl(merchant.getServerUrl());
        alipayConfig.setAppId(merchant.getPayPlatformAppId());
        alipayConfig.setPrivateKey(merchant.getPrivateKey());
        alipayConfig.setAlipayPublicKey(merchant.getPublicKey());
        alipayConfig.setEncryptKey(merchant.getEncryptPassword());
        alipayConfig.setSignType(AliPayConstant.SIGN_TYPE);
        try {
            AlipayClient alipayClient = new DefaultAlipayClient(alipayConfig);
            AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
            AlipayTradePagePayModel model = new AlipayTradePagePayModel();
            model.setOutTradeNo(String.valueOf(orderId));
            model.setTotalAmount(String.valueOf(amount));
            model.setSubject(goodsName);
            model.setProductCode(AliPayConstant.PRODUCT_CODE);
            model.setRequestFromUrl(requestFromUrl);
            //30分钟超时时间
            model.setTimeExpire(DateUtil.format(DateUtil.offsetMinute(DateUtil.date(), 30), "yyyy-MM-dd HH:mm:ss"));
            request.setBizModel(model);
            request.setReturnUrl(merchant.getCallbackUrl());
            AlipayTradePagePayResponse response = alipayClient.pageExecute(request, HttpMethod.GET.name());
            if (response.isSuccess()) {
                return response.getBody();
            } else {
                throw new BizException("调用失败！");
            }
        } catch (Exception e) {
            throw new AppException("支付宝获取链接失败：创建订单失败！");
        }
    }

    /**
     * 退款
     *
     * @param merchant   商户信息
     * @param outTradeNo 商户订单
     * @param amount     退款金额
     * @param tradeNo    支付宝流水
     * @return
     */
    public static Boolean refund(Merchant merchant, String outTradeNo, String amount, String tradeNo) {
        try {
            AlipayConfig alipayConfig = new AlipayConfig();
            alipayConfig.setServerUrl(merchant.getServerUrl());
            alipayConfig.setAppId(merchant.getPayPlatformAppId());
            alipayConfig.setPrivateKey(merchant.getPrivateKey());
            alipayConfig.setAlipayPublicKey(merchant.getPublicKey());
            AlipayClient alipayClient = new DefaultAlipayClient(alipayConfig);
            AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
            AlipayTradeRefundModel model = new AlipayTradeRefundModel();
            model.setTradeNo(tradeNo);
            model.setOutTradeNo(outTradeNo);
            model.setRefundAmount(amount);
            request.setBizModel(model);
            AlipayTradeRefundResponse response = alipayClient.execute(request);
            if (response.isSuccess()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 验签
     *
     * @param publicKey 商户信息
     * @param data      待验签的数据
     * @return
     */
    public static Boolean checkSign(String publicKey, Map<String, String> data) {
        try {
            return AlipaySignature.verifyV1(data, publicKey, StandardCharsets.UTF_8.name(), AliPayConstant.SIGN_TYPE);
        } catch (AlipayApiException e) {
            return false;
        }
    }

    /**
     * 从请求头拿取数据
     *
     * @param request
     * @return
     */
    public static Map<String, String> getDataOnRequest(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        return new HashMap<>() {
            {
                parameterMap.forEach((k, v) -> {
                    put(k, v[0]);
                });
            }
        };
    }
}
