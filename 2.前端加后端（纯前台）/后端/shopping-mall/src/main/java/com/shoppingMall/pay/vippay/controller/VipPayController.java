package com.shoppingMall.pay.vippay.controller;

import com.shoppingMall.exception.BizException;
import com.shoppingMall.pay.alipay.utils.AliPayUtil;
import com.shoppingMall.pay.vippay.entity.vo.VipPayVo;
import com.shoppingMall.pay.vippay.service.VipPayHtmlService;
import com.shoppingMall.pay.vippay.service.VipPayService;
import com.shoppingMall.pay.vippay.utils.VipPayHtmlUtil;
import com.shoppingMall.utils.Assert;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * vip支付接口类
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/24
 */
@Controller
@RequestMapping("/vip_pay")
public class VipPayController {
    private final VipPayService vipPayService;
    private final VipPayHtmlService vipPayHtmlService;

    public VipPayController(VipPayService vipPayService, VipPayHtmlService vipPayHtmlService) {
        this.vipPayService = vipPayService;
        this.vipPayHtmlService = vipPayHtmlService;
    }

    /**
     * 返回支付页面
     *
     * @param response
     * @param vipPayVo
     * @throws IOException
     */
    @GetMapping(value = "/to_pay_html", produces = "application/Html;charset=UTF-8")
    public void toPayHtml(HttpServletResponse response, VipPayVo vipPayVo) throws IOException {
        String orderId = vipPayVo.getOrderId();
        String amount = vipPayVo.getAmount();
        String goodsName = vipPayVo.getGoodsName();
        String requestFromUrl = vipPayVo.getRequestFromUrl();
        String callbackUrl = vipPayVo.getCallbackUrl();
        String returnHtml;
        try {
            Assert.isNotBlank(goodsName, "");
            Assert.isNotBlank(requestFromUrl, "");
            Assert.isNotBlank(callbackUrl, "");
            Assert.isNotBlank(amount, "");
            Assert.isNotBlank(orderId, "");
            String decodeGoodsName = URLDecoder.decode(goodsName, StandardCharsets.UTF_8);
            String decodeRequestFromUrl = URLDecoder.decode(requestFromUrl, StandardCharsets.UTF_8);
            String decodeCallBackUrl = URLDecoder.decode(callbackUrl, StandardCharsets.UTF_8);
            returnHtml = vipPayHtmlService.toPayHtml(orderId, amount, decodeGoodsName, decodeRequestFromUrl, decodeCallBackUrl);
        } catch (BizException e) {
            returnHtml = VipPayHtmlUtil.getVipErrorPayHtml("创建订单失败！");
        }
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            outputStream.write(returnHtml.getBytes(StandardCharsets.UTF_8));
        }
    }

    /**
     * 支付页面
     *
     * @param response
     * @param vipPayVo
     * @throws IOException
     */
    @GetMapping(value = "/pay", produces = "application/Html;charset=UTF-8")
    public void pay(HttpServletResponse response, VipPayVo vipPayVo) throws IOException {
        String returnHtml;
        String token = vipPayVo.getToken();
        try {
            Assert.isNotBlank(token, "");
            String decodeToken = URLDecoder.decode(token, StandardCharsets.UTF_8);
            returnHtml = vipPayHtmlService.getPayHtml(decodeToken);
        } catch (BizException e) {
            returnHtml = VipPayHtmlUtil.getVipErrorPayHtml("订单不存在，或者订单已过期！");
        }
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            outputStream.write(returnHtml.getBytes(StandardCharsets.UTF_8));
        }
    }

    /**
     * 支付结果回调
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @PostMapping(value = "/callback",produces = "application/Html;charset=UTF-8")
    public void callback(HttpServletRequest request,HttpServletResponse response) throws IOException {
        Map<String, String> data = AliPayUtil.getDataOnRequest(request);
        String callBackHtml = vipPayService.callBack(data);
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            outputStream.write(callBackHtml.getBytes(StandardCharsets.UTF_8));
        }
    }
}
