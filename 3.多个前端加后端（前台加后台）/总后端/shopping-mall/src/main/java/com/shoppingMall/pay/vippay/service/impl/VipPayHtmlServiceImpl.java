package com.shoppingMall.pay.vippay.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.shoppingMall.pay.vippay.constant.VipPayConstant;
import com.shoppingMall.pay.vippay.service.VipPayHtmlService;
import com.shoppingMall.pay.vippay.utils.VipPayHtmlUtil;
import com.shoppingMall.utils.Assert;
import com.zhuyahui.annotation.ZyhDataSourceRead;
import com.zhuyahui.annotation.ZyhService;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * 处理支付页面
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/25
 */
@ZyhService
@ZyhDataSourceRead
public class VipPayHtmlServiceImpl implements VipPayHtmlService {
    private final RedisTemplate<String, String> redisTemplate;

    public VipPayHtmlServiceImpl(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 获取前往支付页面,订单超时时间为30分钟
     *
     * @param orderId
     * @param amount
     * @param goodsName
     * @param requestFromUrl
     * @param callbackUrl
     * @return
     */
    @Override
    public String toPayHtml(String orderId, String amount, String goodsName, String requestFromUrl, String callbackUrl) {
        String payHtmlToken = VipPayHtmlUtil.getPayHtmlToken();
        BoundListOperations<String, String> stringStringBoundListOperations = redisTemplate.boundListOps(payHtmlToken);
        stringStringBoundListOperations.leftPushAll(orderId, amount, goodsName, requestFromUrl, callbackUrl);
        stringStringBoundListOperations.expire(30, TimeUnit.MINUTES);
        return String.format("<html><script>window.location.href='%s?token=%s';</script></html>", VipPayConstant.PAY_URL, URLEncoder.encode(payHtmlToken, StandardCharsets.UTF_8));
    }

    /**
     * 前往支付页面
     *
     * @param token
     * @return
     */
    @Override
    public String getPayHtml(String token) {
        Boolean aBoolean = redisTemplate.hasKey(token);
        if (aBoolean != null && aBoolean) {
            BoundListOperations<String, String> stringStringBoundListOperations = redisTemplate.boundListOps(token);
            if (ObjectUtil.isNull(stringStringBoundListOperations)) {
                return VipPayHtmlUtil.getVipErrorPayHtml("订单不存在，或者订单已过期！");
            }
            String callbackUrl = stringStringBoundListOperations.index(0);
            String requestFromUrl = stringStringBoundListOperations.index(1);
            String goodsName = stringStringBoundListOperations.index(2);
            String amount = stringStringBoundListOperations.index(3);
            String orderId = stringStringBoundListOperations.index(4);
            try {
                Assert.isNotBlank(callbackUrl, "");
                Assert.isNotBlank(requestFromUrl, "");
                Assert.isNotBlank(goodsName, "");
                Assert.isNotBlank(amount, "");
                Assert.isNotBlank(orderId, "");
                return VipPayHtmlUtil.getVipPayHtml(callbackUrl, requestFromUrl, goodsName, amount, orderId, String.valueOf(stringStringBoundListOperations.getExpire()), token);
            } catch (Exception e) {
                return VipPayHtmlUtil.getVipErrorPayHtml("订单不存在，或者订单已过期！");
            }
        } else {
            return VipPayHtmlUtil.getVipErrorPayHtml("订单不存在，或者订单已过期！");
        }
    }

    /**
     * 删除token
     *
     * @param token
     */
    @Override
    public void deleteToken(String token) {
        Boolean aBoolean = redisTemplate.hasKey(token);
        if (aBoolean != null && aBoolean) {
            redisTemplate.delete(token);
        }
    }

    /**
     * 检查订单是否还存在
     *
     * @param token
     * @return
     */
    @Override
    public Boolean hasToken(String token) {
        Boolean aBoolean = redisTemplate.hasKey(token);
        return aBoolean != null && aBoolean;
    }

}
