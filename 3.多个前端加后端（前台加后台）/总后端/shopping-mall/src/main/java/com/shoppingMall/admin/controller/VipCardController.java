package com.shoppingMall.admin.controller;

import com.shoppingMall.admin.annotation.CheckAdmin;
import com.shoppingMall.admin.entity.bo.VipCardBo;
import com.shoppingMall.admin.entity.vo.VipCardVo;
import com.shoppingMall.admin.service.VipCardService;
import com.shoppingMall.entity.bo.SingleAllBo;
import com.shoppingMall.exception.BizException;
import com.shoppingMall.utils.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 购物卡接口类，不与支付混淆,此类所有接口都要验证,所以直接加在类上
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/5/4
 */
@RestController
@RequestMapping("/vip-card")
@CheckAdmin
public class VipCardController {
    private final VipCardService vipCardService;

    public VipCardController(VipCardService vipCardService) {
        this.vipCardService = vipCardService;
    }

    /**
     * 获取所有的购物卡数据
     *
     * @return
     */
    @PostMapping("/get-vip-card")
    public List<VipCardBo> getVipCard() {
        return vipCardService.getAllVipCard();
    }

    /**
     * 添加购物卡数据
     *
     * @param vipCardVo
     * @return
     */
    @PostMapping("/add-vip-card")
    public SingleAllBo<Boolean> addVipCard(@RequestBody VipCardVo vipCardVo) {
        String vipCardPassword = vipCardVo.getVipCardPassword();
        Float vipCardBalance = vipCardVo.getVipCardBalance();
        Assert.isNotBlank(vipCardPassword, "购物卡密码");
        Assert.isNotNull(vipCardBalance, "购物卡余额");
        if (vipCardBalance.isNaN()) {
            throw new BizException("金额格式不正确！");
        }
        if (vipCardBalance < 0) {
            throw new BizException("金额不得小于0！");
        }
        return vipCardService.addVipCard(vipCardPassword, vipCardBalance);
    }

    /**
     * 修改购物卡余额
     *
     * @param vipCardVo
     * @return
     */
    @PostMapping("/modify_vip-card-balance")
    public SingleAllBo<Boolean> modifyVipCardBalance(@RequestBody VipCardVo vipCardVo) {
        String vipCardId = vipCardVo.getVipCardId();
        Float vipCardBalance = vipCardVo.getVipCardBalance();
        Assert.isNotBlank(vipCardId, "购物卡id");
        Assert.isNotNull(vipCardBalance, "购物卡余额");
        if (vipCardBalance.isNaN()) {
            throw new BizException("金额格式不正确！");
        }
        if (vipCardBalance < 0) {
            throw new BizException("金额不得小于0！");
        }
        return vipCardService.modifyVipCardBalance(vipCardId, vipCardBalance);
    }

    /**
     * 重置购物卡密码
     *
     * @param vipCardVo
     * @return
     */
    @PostMapping("/modify-vip-card-password")
    public SingleAllBo<Boolean> modifyVipCardPassword(@RequestBody VipCardVo vipCardVo) {
        String vipCardId = vipCardVo.getVipCardId();
        String vipCardPassword = vipCardVo.getVipCardPassword();
        Assert.isNotBlank(vipCardId, "购物卡id");
        Assert.isNotBlank(vipCardPassword, "购物卡支付密码");
        return vipCardService.modifyVipCardPassword(vipCardId, vipCardPassword);
    }

    /**
     * 删除购物卡
     *
     * @param vipCardVo
     * @return
     */
    @PostMapping("/del-vip-card")
    public SingleAllBo<Boolean> delVipCard(@RequestBody VipCardVo vipCardVo) {
        String vipCardId = vipCardVo.getVipCardId();
        Assert.isNotBlank(vipCardId, "购物卡id");
        return vipCardService.delVipCard(vipCardId);
    }
}
