package com.shoppingMall.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.shoppingMall.admin.entity.bo.AdminExtraConfigBo;
import com.shoppingMall.constant.EnableConstantEnum;
import com.shoppingMall.dao.ExtraConfigDao;
import com.shoppingMall.dao.ImgDao;
import com.shoppingMall.entity.bo.ExtraConfigBo;
import com.shoppingMall.entity.bo.SingleAllBo;
import com.shoppingMall.entity.po.ExtraConfig;
import com.shoppingMall.entity.po.Img;
import com.shoppingMall.exception.AppException;
import com.shoppingMall.exception.BizException;
import com.shoppingMall.service.ExtraConfigService;
import com.shoppingMall.utils.UploadFileUtil;
import com.zhuyahui.annotation.ZyhDataSourceRead;
import com.zhuyahui.annotation.ZyhService;
import com.zhuyahui.util.MyAloneHandlerReadWrite;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

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
        Long registerBgImgUrl = extraConfig.getRegisterBgImgUrl();
        if (ObjectUtil.isNull(registerBgImgUrl)) {
            throw new AppException("缺少注册界面背景图片!");
        }
        Long loginBgImgUrl = extraConfig.getLoginBgImgUrl();
        if (ObjectUtil.isNull(loginBgImgUrl)) {
            throw new AppException("缺少登录界面背景图片！");
        }
        Img registerImg = imgDao.findById(registerBgImgUrl).orElseThrow(() -> {
            throw new AppException("缺少注册界面背景图片！");
        });
        Img loginImg = imgDao.findById(loginBgImgUrl).orElseThrow(() -> {
            throw new AppException("缺少登录界面背景图片！");
        });
        return new ExtraConfigBo(
                extraConfig.getStoreNameHaveSpecial() == EnableConstantEnum.ON.getCode(),
                extraConfig.getSpecialText(),
                extraConfig.getStoreName(),
                extraConfig.getPasswordTemplateText(),
                extraConfig.getBelowPageText(),
                registerImg.getImgName(),
                loginImg.getImgName()
        );
    }

    /**
     * 管理端获取特殊配置信息
     *
     * @return
     */
    @Override
    public AdminExtraConfigBo getExtraConfig() {
        List<ExtraConfig> all = extraConfigDao.findAll();
        if (all.isEmpty()) {
            throw new BizException("未查询到特殊配置数据！");
        }
        ExtraConfig extraConfig = all.get(0);
        Long registerBgImgUrl = extraConfig.getRegisterBgImgUrl();
        if (ObjectUtil.isNull(registerBgImgUrl)) {
            throw new AppException("缺少注册界面背景图片!");
        }
        Long loginBgImgUrl = extraConfig.getLoginBgImgUrl();
        if (ObjectUtil.isNull(loginBgImgUrl)) {
            throw new AppException("缺少登录界面背景图片！");
        }
        Img registerImg = imgDao.findById(registerBgImgUrl).orElseThrow(() -> {
            throw new AppException("缺少注册界面背景图片！");
        });
        Img loginImg = imgDao.findById(loginBgImgUrl).orElseThrow(() -> {
            throw new AppException("缺少登录界面背景图片！");
        });
        return new AdminExtraConfigBo(
                String.valueOf(extraConfig.getExtraId()),
                extraConfig.getStoreNameHaveSpecial() == EnableConstantEnum.ON.getCode(),
                extraConfig.getSpecialText(),
                extraConfig.getStoreName(),
                extraConfig.getPasswordTemplateText(),
                extraConfig.getBelowPageText(),
                registerImg.getImgName(),
                loginImg.getImgName()
        );
    }

    /**
     * 修改特殊配置
     *
     * @param extraId
     * @param storeNameHaveSpecial
     * @param specialText
     * @param storeName
     * @param passwordTemplateText
     * @param belowPageText
     * @param files
     * @return
     */
    @Override
    public SingleAllBo<Boolean> fixExtraConfig(String extraId, int storeNameHaveSpecial, String specialText, String storeName, String passwordTemplateText, String belowPageText, List<MultipartFile> files) {
        ExtraConfig extraConfig = extraConfigDao.findById(Long.parseLong(extraId)).orElseThrow(() -> {
            throw new BizException("特殊配置信息不存在！");
        });
        Long registerBgImgUrl = extraConfig.getRegisterBgImgUrl();
        Long loginBgImgUrl = extraConfig.getLoginBgImgUrl();
        Img registerImg = imgDao.findById(registerBgImgUrl).orElseThrow(() -> {
            throw new AppException("注册界面图片不存在！");
        });
        Img logImg = imgDao.findById(loginBgImgUrl).orElseThrow(() -> {
            throw new AppException("登录界面图片不存在！");
        });
        UploadFileUtil.uploadFile(files);
        registerImg.setImgName(files.get(0).getOriginalFilename());
        logImg.setImgName(files.get(1).getOriginalFilename());
        extraConfig.setStoreNameHaveSpecial(storeNameHaveSpecial);
        extraConfig.setSpecialText(specialText);
        extraConfig.setStoreName(storeName);
        extraConfig.setPasswordTemplateText(passwordTemplateText);
        extraConfig.setBelowPageText(belowPageText);
        extraConfig.setStoreNameHaveSpecial(storeNameHaveSpecial);
        MyAloneHandlerReadWrite.write(() -> {
            ArrayList<Img> imgList = new ArrayList<>();
            imgList.add(registerImg);
            imgList.add(logImg);
            imgDao.saveAll(imgList);
            extraConfigDao.save(extraConfig);
            return null;
        });
        return new SingleAllBo<>(true);
    }
}
