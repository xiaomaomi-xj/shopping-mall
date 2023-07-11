package com.shoppingMall.pay.vippay.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.shoppingMall.constant.OrderStateEnum;
import com.shoppingMall.dao.GoodsDao;
import com.shoppingMall.dao.OrderDao;
import com.shoppingMall.dao.ShopCartDao;
import com.shoppingMall.entity.po.Goods;
import com.shoppingMall.entity.po.Merchant;
import com.shoppingMall.entity.po.Order;
import com.shoppingMall.entity.po.self.Pay;
import com.shoppingMall.pay.core.constant.GoodsStateEnum;
import com.shoppingMall.pay.core.constant.MerchantTypeEnum;
import com.shoppingMall.pay.core.utils.PayUtil;
import com.shoppingMall.pay.vippay.dao.VipCardDao;
import com.shoppingMall.pay.vippay.entity.po.VipCard;
import com.shoppingMall.pay.vippay.service.VipPayHtmlService;
import com.shoppingMall.pay.vippay.service.VipPayService;
import com.shoppingMall.pay.vippay.utils.VipPayUtil;
import com.shoppingMall.properties.SelfConfigPropertiesBean;
import com.shoppingMall.utils.PasswordUtil;
import com.zhuyahui.annotation.ZyhDataSourceRead;
import com.zhuyahui.annotation.ZyhService;
import com.zhuyahui.util.MyAloneHandlerReadWrite;

import java.util.List;
import java.util.Map;

/**
 * vip支付方式
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/24
 */
@ZyhService
@ZyhDataSourceRead
public class VipPayServiceImpl implements VipPayService {
    private final SelfConfigPropertiesBean selfConfigPropertiesBean;
    private final VipPayHtmlService vipPayHtmlService;
    private final OrderDao orderDao;
    private final VipCardDao vipCardDao;
    private final ShopCartDao shopCartDao;
    private final GoodsDao goodsDao;

    public VipPayServiceImpl(SelfConfigPropertiesBean selfConfigPropertiesBean, VipPayHtmlService vipPayHtmlService, OrderDao orderDao, VipCardDao vipCardDao, ShopCartDao shopCartDao, GoodsDao goodsDao) {
        this.selfConfigPropertiesBean = selfConfigPropertiesBean;
        this.vipPayHtmlService = vipPayHtmlService;
        this.orderDao = orderDao;
        this.vipCardDao = vipCardDao;
        this.shopCartDao = shopCartDao;
        this.goodsDao = goodsDao;
    }

    @Override
    public String getMerchantType() {
        return MerchantTypeEnum.VIP_PAY.getCode();
    }

    /**
     * 获取支付链接
     *
     * @param merchant   商户信息
     * @param orderId    订单号
     * @param goodsName  商品名字
     * @param totalPrice 总价格
     * @return
     */
    @Override
    public String getPayUrl(Merchant merchant, Long orderId, String goodsName, Float totalPrice) {
        Pay pay = selfConfigPropertiesBean.getConfig().getPay();
        String requestFromUrl = pay.getRequestFromUrl();
        return VipPayUtil.getPayUrl(merchant, orderId, totalPrice, goodsName, requestFromUrl);
    }

    /**
     * 支付结果回调
     *
     * @param data
     * @return
     */
    @Override
    public String callBack(Map<String, String> data) {
        String cardNumber = data.get("cardNumber");
        String cardPassword = data.get("cardPassword");
        String orderId = data.get("orderId");
        String token = data.get("token");
        if (ObjectUtil.isEmpty(cardNumber) || ObjectUtil.isEmpty(cardPassword) || ObjectUtil.isEmpty(orderId) || cardNumber.length() != 29 || ObjectUtil.isEmpty(token)) {
            return PayUtil.getPayErrorHtml("VIP支付失败：回调数据，部分或全部数据为空！");
        }
        //验证订单是否还存在
        Boolean aBoolean = vipPayHtmlService.hasToken(token);
        if (!aBoolean) {
            return PayUtil.getPayErrorHtml("VIP支付失败：订单已被支付，或者订单不存在！");
        }
        //验证购物卡
        String salt = selfConfigPropertiesBean.getConfig().getPassword().getSalt();
        VipCard vipCard = vipCardDao.findByVipCardAccount(cardNumber).orElse(null);
        if (vipCard == null) {
            return PayUtil.getPayErrorHtml("VIP支付失败：购物卡账号或密码错误！");
        }
        String vipCardPassword = vipCard.getVipCardPassword();
        if (!vipCardPassword.equals(PasswordUtil.getPassword(cardPassword, salt))) {
            return PayUtil.getPayErrorHtml("VIP支付失败：购物卡账号或密码错误！");
        }
        Order order = orderDao.findById(Long.parseLong(orderId)).orElse(null);
        float amount = 0f;
        if (order == null) {
            //还有可能是组合订单
            List<Order> allByComposeOrderId = orderDao.findAllByComposeOrderId(Long.parseLong(orderId));
            if (allByComposeOrderId.isEmpty()) {
                return PayUtil.getPayErrorHtml("VIP支付失败：未查询到订单！");
            } else {
                //计算金额
                for (Order order1 : allByComposeOrderId) {
                    amount += order1.getTotalPrice();
                }
                //验证金额是否足够
                if (vipCard.getVipCardBalance() < amount) {
                    return PayUtil.getPayErrorHtml("VIP支付失败：购物卡余额不足！");
                }
                String goodsState = getGoodsState(allByComposeOrderId, token);
                if (GoodsStateEnum.LOSE.getCode().equals(goodsState)) {
                    return PayUtil.getPayErrorHtml("VIP支付失败：购买的商品不存在,不会扣除你的购物卡金额！");
                } else if (GoodsStateEnum.LOOT.getCode().equals(goodsState)) {
                    return PayUtil.getPayErrorHtml("VIP支付失败：购买的商品被别人先一步抢光了，不会扣除你的购物卡金额！");
                } else {
                    //因为这个支付系统是我们自己控制的，所以在发生错误之前，是不会扣费，所以也没有退费这一说
                    MyAloneHandlerReadWrite.write(() -> {
                        for (Order order1 : allByComposeOrderId) {
                            //减少库存
                            goodsDao.declineMaxBuyNum(order1.getGoodsNum(), order1.getGoodsId());
                            order1.setOrderState(OrderStateEnum.COMPLET.getState());
                            orderDao.save(order1);
                            //多个购买成功，还要删掉购物车数据
                            shopCartDao.deleteByGoodsIdAndUserId(order1.getGoodsId(), order1.getUserId());
                        }
                        return null;
                    });
                    //减掉购物卡金额,本身自带事务,可以放在外面
                    vipCardDao.declineVipCardBalance(amount, vipCard.getVipCardId());
                    return PayUtil.getPaySuccessHtml();
                }
            }
        } else {
            //获取金额
            amount = order.getTotalPrice();
            //验证金额是否足够
            if (vipCard.getVipCardBalance() < amount) {
                return PayUtil.getPayErrorHtml("VIP支付失败：购物卡余额不足！");
            }
            String goodsState = getGoodsState(order, token);
            if (GoodsStateEnum.LOSE.getCode().equals(goodsState)) {
                return PayUtil.getPayErrorHtml("VIP支付失败：购买的商品不存在，不会扣除你的购物卡金额！");
            } else if (GoodsStateEnum.LOOT.getCode().equals(goodsState)) {
                return PayUtil.getPayErrorHtml("VIP支付失败：购买的商品被别人先一步抢光了，不会扣除你的购物卡金额！");
            } else {
                //因为这个支付系统是我们自己控制的，所以在发生错误之前，是不会扣费，所以也没有退费这一说
                MyAloneHandlerReadWrite.write(() -> {
                    goodsDao.declineMaxBuyNum(order.getGoodsNum(), order.getGoodsId());
                    order.setOrderState(OrderStateEnum.COMPLET.getState());
                    orderDao.save(order);
                    return null;
                });
                //减掉购物卡金额,本身自带事务,可以放在外面
                vipCardDao.declineVipCardBalance(amount, vipCard.getVipCardId());
                return PayUtil.getPaySuccessHtml();
            }
        }
    }

    /**
     * 获取单个商品状态
     *
     * @return
     */
    private String getGoodsState(Order order, String token) {
        //在获取的时候，删除订单
        vipPayHtmlService.deleteToken(token);
        Long goodsId = order.getGoodsId();
        Goods goods = goodsDao.findById(goodsId).orElse(null);
        if (goods == null) {
            return GoodsStateEnum.LOSE.getCode();
        } else {
            int goodsNum = order.getGoodsNum();
            Integer maxBuyNum = goods.getMaxBuyNum();
            if (goodsNum > maxBuyNum) {
                return GoodsStateEnum.LOOT.getCode();
            }
        }
        return GoodsStateEnum.SUCCESS.getCode();
    }

    /**
     * 获取多个商品状态
     *
     * @param orderList
     * @return
     */
    private String getGoodsState(List<Order> orderList, String token) {
        //在获取的时候，删除订单
        vipPayHtmlService.deleteToken(token);
        for (Order order : orderList) {
            Long goodsId = order.getGoodsId();
            Goods goods = goodsDao.findById(goodsId).orElse(null);
            if (goods == null) {
                return GoodsStateEnum.LOSE.getCode();
            } else {
                int goodsNum = order.getGoodsNum();
                Integer maxBuyNum = goods.getMaxBuyNum();
                if (goodsNum > maxBuyNum) {
                    return GoodsStateEnum.LOOT.getCode();
                }
            }
        }
        return GoodsStateEnum.SUCCESS.getCode();
    }
}
