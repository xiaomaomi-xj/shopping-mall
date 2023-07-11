package com.shoppingMall.entity.bo;

/**
 * 商户返回参数bo实体类
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/23
 */
public class MerchantBo {
    /**
     * 商户id,string保证精度
     */
    private String merchantId;

    /**
     * 商户名字
     */
    private String merchantName;

    public MerchantBo() {
    }

    public MerchantBo(String merchantId, String merchantName) {
        this.merchantId = merchantId;
        this.merchantName = merchantName;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    @Override
    public String toString() {
        return "MerchantBo{" +
                "merchantId='" + merchantId + '\'' +
                ", merchantName='" + merchantName + '\'' +
                '}';
    }
}
