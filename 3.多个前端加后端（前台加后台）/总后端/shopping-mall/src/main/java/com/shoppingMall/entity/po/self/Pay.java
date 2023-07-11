package com.shoppingMall.entity.po.self;

/**
 * 支付配置（就不要异步通知了，省的再做内网穿透）
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/24
 */
public class Pay {
    /**
     * 取消支付跳转的地址
     */
    private String requestFromUrl;

    public Pay() {
    }

    public Pay(String requestFromUrl) {
        this.requestFromUrl = requestFromUrl;
    }

    public String getRequestFromUrl() {
        return requestFromUrl;
    }

    public void setRequestFromUrl(String requestFromUrl) {
        this.requestFromUrl = requestFromUrl;
    }

    @Override
    public String toString() {
        return "Pay{" +
                "requestFromUrl='" + requestFromUrl + '\'' +
                '}';
    }
}
