package com.shoppingMall.service.impl;

import cn.hutool.core.util.IdUtil;
import com.shoppingMall.admin.entity.bo.AdminMerchantBo;
import com.shoppingMall.dao.MerchantDao;
import com.shoppingMall.entity.bo.MerchantBo;
import com.shoppingMall.entity.bo.SingleAllBo;
import com.shoppingMall.entity.po.Merchant;
import com.shoppingMall.exception.BizException;
import com.shoppingMall.service.MerchantService;
import com.zhuyahui.annotation.ZyhDataSourceRead;
import com.zhuyahui.annotation.ZyhService;
import com.zhuyahui.util.MyAloneHandlerReadWrite;

import java.util.ArrayList;
import java.util.List;

/**
 * 商户服务类
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/23
 */
@ZyhService
@ZyhDataSourceRead
public class MerchantServiceImpl implements MerchantService {
    private final MerchantDao merchantDao;

    public MerchantServiceImpl(MerchantDao merchantDao) {
        this.merchantDao = merchantDao;
    }

    /**
     * 获取全部商户信息
     *
     * @return
     */
    @Override
    public List<MerchantBo> getMerchants() {
        List<MerchantBo> merchantBoList = new ArrayList<>();
        List<Merchant> all = merchantDao.findAll();
        all.forEach(v -> merchantBoList.add(new MerchantBo(String.valueOf(v.getMerchantId()), v.getMerchantName())));
        return merchantBoList;
    }

    /**
     * 后台获取所有商户信息
     *
     * @return
     */
    @Override
    public List<AdminMerchantBo> getAdminMerchants() {
        List<AdminMerchantBo> adminMerchantBos = new ArrayList<>();
        List<Merchant> all = merchantDao.findAll();
        all.forEach(v -> adminMerchantBos.add(new AdminMerchantBo(
                String.valueOf(v.getMerchantId()),
                v.getMerchantCode(),
                v.getMerchantName(),
                v.getMerchantType(),
                v.getPayPlatformAppId(),
                v.getPublicKey(),
                v.getPrivateKey(),
                v.getEncryptPassword(),
                v.getServerUrl(),
                v.getCallbackUrl()
        )));
        return adminMerchantBos;
    }

    /**
     * 更换id删除商户
     */
    @Override
    public SingleAllBo<Boolean> delMerchantById(String merchantId) {
        Merchant merchant = merchantDao.findById(Long.parseLong(merchantId)).orElseThrow(() -> {
            throw new BizException("商户id不存在！");
        });
        MyAloneHandlerReadWrite.write(() -> {
            merchantDao.delete(merchant);
            return null;
        });
        return new SingleAllBo<>(true);
    }

    /**
     * 修改商户信息
     */
    @Override
    public SingleAllBo<Boolean> modifyMerchant(String merchantId, String merchantCode, String merchantName, String merchantType, String payPlatformAppId, String publicKey, String privateKey, String encryptPassword, String serverUrl, String callbackUrl) {
        Merchant merchant = merchantDao.findById(Long.parseLong(merchantId)).orElseThrow(() -> {
            throw new BizException("商户不存在！");
        });
        merchant.setMerchantCode(merchantCode);
        merchant.setMerchantName(merchantName);
        merchant.setMerchantType(merchantType);
        merchant.setPayPlatformAppId(payPlatformAppId);
        merchant.setPublicKey(publicKey);
        merchant.setPrivateKey(privateKey);
        merchant.setEncryptPassword(encryptPassword);
        merchant.setServerUrl(serverUrl);
        merchant.setCallbackUrl(callbackUrl);
        MyAloneHandlerReadWrite.write(() -> merchantDao.save(merchant));
        return new SingleAllBo<>(true);
    }

    /**
     * 新增商户信息
     */
    @Override
    public SingleAllBo<Boolean> addMerchant(String merchantCode, String merchantName, String merchantType, String payPlatformAppId, String publicKey, String privateKey, String encryptPassword, String serverUrl, String callbackUrl) {
        MyAloneHandlerReadWrite.write(() -> merchantDao.save(new Merchant(
                IdUtil.getSnowflakeNextId(),
                merchantCode,
                merchantName,
                merchantType,
                payPlatformAppId,
                publicKey,
                privateKey,
                encryptPassword,
                serverUrl,
                callbackUrl
        )));
        return new SingleAllBo<>(true);
    }
}
