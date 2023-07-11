package com.shoppingMall.pay.alipay.controller;

import com.shoppingMall.pay.alipay.service.AliPayService;
import com.shoppingMall.pay.alipay.utils.AliPayUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * 支付宝回调接口
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/24
 */
@Controller
@RequestMapping("/alipay")
public class AliPayController {
    private final AliPayService aliPayService;

    public AliPayController(AliPayService aliPayService) {
        this.aliPayService = aliPayService;
    }

    /**
     * 结果回调
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/callback")
    public void callback(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, String> data = AliPayUtil.getDataOnRequest(request);
        String callBackHtml = aliPayService.callBack(data);
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            outputStream.write(callBackHtml.getBytes(StandardCharsets.UTF_8));
        }
    }
}
