package com.shoppingMall.service.impl;

import com.shoppingMall.dao.MerchantDao;
import com.shoppingMall.entity.bo.MerchantBo;
import com.shoppingMall.entity.po.Merchant;
import com.shoppingMall.service.MerchantService;
import com.zhuyahui.annotation.ZyhDataSourceRead;
import com.zhuyahui.annotation.ZyhService;

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
}
