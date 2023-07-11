package com.shoppingMall.service.impl;

import com.shoppingMall.constant.EnableConstantEnum;
import com.shoppingMall.dao.ExtraConfigDao;
import com.shoppingMall.dao.ImgDao;
import com.shoppingMall.entity.bo.ExtraConfigBo;
import com.shoppingMall.entity.po.ExtraConfig;
import com.shoppingMall.entity.po.Img;
import com.shoppingMall.exception.BizException;
import com.shoppingMall.service.ExtraConfigService;
import com.zhuyahui.annotation.ZyhDataSourceRead;
import com.zhuyahui.annotation.ZyhService;

import java.util.List;
import java.util.Optional;

/**
 * 操作额外配置
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/2/28
 */
@ZyhService
@ZyhDataSourceRead
public class ExtraConfigServiceImpl implements ExtraConfigService {

    private final ExtraConfigDao extraConfigDao;
    private final ImgDao imgDao;

    public ExtraConfigServiceImpl(ExtraConfigDao extraConfigDao, ImgDao imgDao) {
        this.extraConfigDao = extraConfigDao;
        this.imgDao = imgDao;
    }

    /**
     * 获取配置数据
     * 对于缓存是永久存在，如果后续更新，也会更新缓存 @CachePut用它去进行更新缓存
     * 缓存只用于有后台的时候，否者做调试的时候，修改数据，可能界面没有变化，让人疑惑
     *
     * @return
     * @Cacheable(value = "extra", key = "'config'", sync = true)
     */
    @Override
    public ExtraConfigBo getConfig() {
        List<ExtraConfig> all = extraConfigDao.findAll();
        if (all.isEmpty()) {
            throw new BizException("未查询到特殊配置数据！");
        }
        ExtraConfig extraConfig = all.get(0);
        Optional<Img> registerImg = imgDao.findById(extraConfig.getRegisterBgImgUrl());
        Optional<Img> loginImg = imgDao.findById(extraConfig.getLoginBgIImgUrl());
        return new ExtraConfigBo(
                extraConfig.getStoreNameHaveSpecial() == EnableConstantEnum.ON.getCode(),
                extraConfig.getSpecialText(),
                extraConfig.getStoreName(),
                extraConfig.getPasswordTemplateText(),
                extraConfig.getBelowPageText(),
                registerImg.get().getImgName(),
                loginImg.get().getImgName()
        );
    }
}
