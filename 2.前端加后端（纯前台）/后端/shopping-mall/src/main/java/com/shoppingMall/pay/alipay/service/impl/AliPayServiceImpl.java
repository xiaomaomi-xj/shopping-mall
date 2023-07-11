package com.shoppingMall.pay.alipay.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.shoppingMall.constant.OrderStateEnum;
import com.shoppingMall.dao.GoodsDao;
import com.shoppingMall.dao.MerchantDao;
import com.shoppingMall.dao.OrderDao;
import com.shoppingMall.dao.ShopCartDao;
import com.shoppingMall.entity.po.Goods;
import com.shoppingMall.entity.po.Merchant;
import com.shoppingMall.entity.po.Order;
import com.shoppingMall.entity.po.self.Pay;
import com.shoppingMall.pay.alipay.service.AliPayService;
import com.shoppingMall.pay.alipay.utils.AliPayUtil;
import com.shoppingMall.pay.core.constant.GoodsStateEnum;
import com.shoppingMall.pay.core.constant.MerchantTypeEnum;
import com.shoppingMall.pay.core.utils.PayUtil;
import com.shoppingMall.properties.SelfConfigPropertiesBean;
import com.zhuyahui.annotation.ZyhDataSourceRead;
import com.zhuyahui.annotation.ZyhService;
import com.zhuyahui.util.MyAloneHandlerReadWrite;

import java.util.List;
import java.util.Map;

/**
 * 阿里支付
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/23
 */
@ZyhService
@ZyhDataSourceRead
public class AliPayServiceImpl implements AliPayService {
    private final SelfConfigPropertiesBean selfConfigPropertiesBean;
    private final OrderDao orderDao;
    private final MerchantDao merchantDao;
    private final ShopCartDao shopCartDao;
    private final GoodsDao goodsDao;

    public AliPayServiceImpl(SelfConfigPropertiesBean selfConfigPropertiesBean, OrderDao orderDao, MerchantDao merchantDao, ShopCartDao shopCartDao, GoodsDao goodsDao) {
        this.selfConfigPropertiesBean = selfConfigPropertiesBean;
        this.orderDao = orderDao;
        this.merchantDao = merchantDao;
        this.shopCartDao = shopCartDao;
        this.goodsDao = goodsDao;
    }

    /**
     * 商户类型为alipay
     *
     * @return
     */
    @Override
    public String getMerchantType() {
        return MerchantTypeEnum.ALIPAY.getCode();
    }

    /**
     * 获取支付宝的支付链接
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
        return AliPayUtil.getPayUrl(merchant, orderId, totalPrice, goodsName, requestFromUrl);
    }

    /**
     * 因为支付完，是该有异步通知的，但是这个功能需要公网，所以我没用这个功能，只使用了结果回调，缺点就是，支付完需要等待他自动跳转才行
     * 有时候扫码支付，订单不会跳转，所以建议用账号支付密码支付
     * 支付结果回调
     *
     * @param data
     * @return
     */
    @Override
    public String callBack(Map<String, String> data) {
        String orderId = data.get("out_trade_no");
        String merchantCode = data.get("seller_id");
        String tradeNo = data.get("trade_no");
        String amount = data.get("total_amount");
        if (ObjectUtil.isEmpty(data) || ObjectUtil.isAllEmpty(orderId, merchantCode, tradeNo)) {
            return PayUtil.getPayErrorHtml("支付宝支付失败：回调数据，部分或全部数据为空！");
        }
        Order order = orderDao.findById(Long.parseLong(orderId)).orElse(null);
        boolean signState;
        if (order == null) {
            //还有可能是组合订单
            List<Order> allByComposeOrderId = orderDao.findAllByComposeOrderId(Long.parseLong(orderId));
            if (allByComposeOrderId.isEmpty()) {
                return PayUtil.getPayErrorHtml("支付宝支付失败：未查询到订单！");
            } else {
                signState = AliPayUtil.checkSign(getPublicKey(allByComposeOrderId.get(0), merchantCode), data);
                if (signState) {
                    String goodsState = getGoodsState(allByComposeOrderId);
                    if (GoodsStateEnum.LOSE.getCode().equals(goodsState)) {
                        //此时是自己商品问题，用户已经支付，要进行退款,能走到这，商户一定不为null
                        Merchant merchant = getMerchant(allByComposeOrderId.get(0));
                        if (AliPayUtil.refund(merchant, orderId, amount, tradeNo)) {
                            return PayUtil.getPayErrorHtml("支付宝支付失败：购买的商品不存在,已进行退款处理！");
                        }
                        return PayUtil.getPayErrorHtml("支付宝支付失败：购买的商品不存在，退款处理失败，请联系客服进行退款！");
                    } else if (GoodsStateEnum.LOOT.getCode().equals(goodsState)) {
                        //此时是自己商品问题，用户已经支付，要进行退款,能走到这，商户一定不为null
                        Merchant merchant = getMerchant(allByComposeOrderId.get(0));
                        if (AliPayUtil.refund(merchant, orderId, amount, tradeNo)) {
                            return PayUtil.getPayErrorHtml("支付宝支付失败：购买的商品被别人先一步抢光了,已进行退款处理！");
                        }
                        return PayUtil.getPayErrorHtml("支付宝支付失败：购买的商品被别人先一步抢光了，退款处理失败，请联系客服进行退款！");
                    } else {
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
                        return PayUtil.getPaySuccessHtml();
                    }
                }
            }
        } else {
            signState = AliPayUtil.checkSign(getPublicKey(order, merchantCode), data);
            if (signState) {
                String goodsState = getGoodsState(order);
                if (GoodsStateEnum.LOSE.getCode().equals(goodsState)) {
                    //此时是自己商品问题，用户已经支付，要进行退款,能走到这，商户一定不为null
                    Merchant merchant = getMerchant(order);
                    if (AliPayUtil.refund(merchant, orderId, amount, tradeNo)) {
                        return PayUtil.getPayErrorHtml("支付宝支付失败：购买的商品不存在,已进行退款处理！");
                    }
                    return PayUtil.getPayErrorHtml("支付宝支付失败：购买的商品不存在，退款处理失败，请联系客服进行退款！");
                } else if (GoodsStateEnum.LOOT.getCode().equals(goodsState)) {
                    //此时是自己商品问题，用户已经支付，要进行退款,能走到这，商户一定不为null
                    Merchant merchant = getMerchant(order);
                    if (AliPayUtil.refund(merchant, orderId, amount, tradeNo)) {
                        return PayUtil.getPayErrorHtml("支付宝支付失败：购买的商品被别人先一步抢光了,已进行退款处理！");
                    }
                    return PayUtil.getPayErrorHtml("支付宝支付失败：购买的商品被别人先一步抢光了，退款处理失败，请联系客服进行退款！");
                } else {
                    MyAloneHandlerReadWrite.write(() -> {
                        goodsDao.declineMaxBuyNum(order.getGoodsNum(), order.getGoodsId());
                        order.setOrderState(OrderStateEnum.COMPLET.getState());
                        orderDao.save(order);
                        return null;
                    });
                    return PayUtil.getPaySuccessHtml();
                }
            }
        }
        return PayUtil.getPayErrorHtml("支付宝支付失败：数据验签失败！");
    }

    /**
     * 根据订单获取公钥
     *
     * @param order
     * @return
     */
    private String getPublicKey(Order order, String merchantCode) {
        Long merchantId = order.getMerchantId();
        Merchant merchant = merchantDao.findById(merchantId).orElse(null);
        if (merchant == null) {
            //返回错误信息，验签肯定不成功
            return "error";
        }
        if (!merchant.getMerchantCode().equals(merchantCode)) {
            return "error";
        }
        return merchant.getPublicKey();
    }

    /**
     * 查询商户信息
     *
     * @param order
     * @return
     */
    private Merchant getMerchant(Order order) {
        Long merchantId = order.getMerchantId();
        return merchantDao.findById(merchantId).orElse(null);
    }

    /**
     * 获取单个商品的状态
     *
     * @param order
     * @return
     */
    private String getGoodsState(Order order) {
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
    private String getGoodsState(List<Order> orderList) {
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
