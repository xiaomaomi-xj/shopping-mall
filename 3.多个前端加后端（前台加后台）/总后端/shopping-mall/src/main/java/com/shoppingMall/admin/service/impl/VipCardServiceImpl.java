package com.shoppingMall.admin.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.shoppingMall.admin.entity.bo.VipCardBo;
import com.shoppingMall.admin.service.VipCardService;
import com.shoppingMall.entity.bo.SingleAllBo;
import com.shoppingMall.exception.BizException;
import com.shoppingMall.pay.vippay.dao.VipCardDao;
import com.shoppingMall.pay.vippay.entity.po.VipCard;
import com.shoppingMall.properties.SelfConfigPropertiesBean;
import com.shoppingMall.utils.PasswordUtil;
import com.zhuyahui.annotation.ZyhDataSourceRead;
import com.zhuyahui.annotation.ZyhService;
import com.zhuyahui.util.MyAloneHandlerReadWrite;

import java.util.ArrayList;
import java.util.List;

/**
 * 购物卡服务类
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/5/4
 */
@ZyhService
@ZyhDataSourceRead
public class VipCardServiceImpl implements VipCardService {
    private final VipCardDao vipCardDao;
    private final SelfConfigPropertiesBean selfConfigPropertiesBean;

    public VipCardServiceImpl(VipCardDao vipCardDao, SelfConfigPropertiesBean selfConfigPropertiesBean) {
        this.vipCardDao = vipCardDao;
        this.selfConfigPropertiesBean = selfConfigPropertiesBean;
    }

    /**
     * 获取所有购物卡数据
     *
     * @return
     */
    @Override
    public List<VipCardBo> getAllVipCard() {
        List<VipCardBo> vipCardBoList = new ArrayList<>();
        List<VipCard> all = vipCardDao.findAll();
        all.forEach(v -> vipCardBoList.add(new VipCardBo(
                String.valueOf(v.getVipCardId()),
                v.getVipCardAccount(),
                v.getVipCardBalance()
        )));
        return vipCardBoList;
    }

    /**
     * 添加购物卡
     *
     * @param vipCardPassword
     * @param vipCardBalance
     */
    @Override
    public SingleAllBo<Boolean> addVipCard(String vipCardPassword, Float vipCardBalance) {
        String salt = selfConfigPropertiesBean.getConfig().getPassword().getSalt();
        MyAloneHandlerReadWrite.write(() -> vipCardDao.save(new VipCard(
                IdUtil.getSnowflakeNextId(),
                IdUtil.getSnowflakeNextId() + RandomUtil.randomString("0123456789", 10),
                PasswordUtil.getPassword(vipCardPassword, salt),
                vipCardBalance
        )));
        return new SingleAllBo<>(true);
    }

    /**
     * 修改购物卡
     *
     * @param vipCardId
     * @param vipCardBalance
     * @return
     */
    @Override
    public SingleAllBo<Boolean> modifyVipCardBalance(String vipCardId, Float vipCardBalance) {
        VipCard vipCard = vipCardDao.findById(Long.parseLong(vipCardId)).orElseThrow(() -> {
            throw new BizException("此购物卡信息不存在！");
        });
        vipCard.setVipCardBalance(vipCardBalance);
        MyAloneHandlerReadWrite.write(() -> vipCardDao.save(vipCard));
        return new SingleAllBo<>(true);
    }

    /**
     * 删除购物卡
     *
     * @param vipCardId
     * @return
     */
    @Override
    public SingleAllBo<Boolean> delVipCard(String vipCardId) {
        VipCard vipCard = vipCardDao.findById(Long.parseLong(vipCardId)).orElseThrow(() -> {
            throw new BizException("此购物卡信息不存在！");
        });
        MyAloneHandlerReadWrite.write(() -> {
            vipCardDao.delete(vipCard);
            return null;
        });
        return new SingleAllBo<>(true);
    }

    /**
     * 修改购物卡支付密码
     *
     * @param vipCardId
     * @param vipCardPassword
     * @return
     */
    @Override
    public SingleAllBo<Boolean> modifyVipCardPassword(String vipCardId, String vipCardPassword) {
        VipCard vipCard = vipCardDao.findById(Long.parseLong(vipCardId)).orElseThrow(() -> {
            throw new BizException("此购物卡信息不存在！");
        });
        String salt = selfConfigPropertiesBean.getConfig().getPassword().getSalt();
        PasswordUtil.getPassword(vipCardPassword, salt);
        MyAloneHandlerReadWrite.write(() -> vipCardDao.save(vipCard));
        return new SingleAllBo<>(true);
    }
}
