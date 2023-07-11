package com.shoppingMall.pay.core.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.shoppingMall.constant.GlobalConstant;
import com.shoppingMall.dao.GoodsDao;
import com.shoppingMall.dao.MerchantDao;
import com.shoppingMall.dao.OrderDao;
import com.shoppingMall.entity.bo.SingleAllBo;
import com.shoppingMall.entity.po.Goods;
import com.shoppingMall.entity.po.Merchant;
import com.shoppingMall.entity.po.Order;
import com.shoppingMall.exception.AppException;
import com.shoppingMall.pay.core.service.MorePayService;
import com.shoppingMall.pay.core.service.PayService;
import com.zhuyahui.annotation.ZyhDataSourceRead;
import com.zhuyahui.annotation.ZyhService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 多个支付控制
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/23
 */
@ZyhService
@ZyhDataSourceRead
public class MorePayServiceImpl implements MorePayService {
    private final MerchantDao merchantDao;
    private final OrderDao orderDao;
    private final GoodsDao goodsDao;
    private Map<String, PayService> payServiceMap;

    public MorePayServiceImpl(MerchantDao merchantDao, OrderDao orderDao, GoodsDao goodsDao) {
        this.merchantDao = merchantDao;
        this.orderDao = orderDao;
        this.goodsDao = goodsDao;
    }

    /**
     * 自动注入多个payService
     *
     * @param payServiceList
     */
    @Autowired
    public void setPayServiceMap(List<PayService> payServiceList) {
        Map<String, PayService> payServiceMap = new HashMap<>(1);
        payServiceList.forEach(v -> {
            payServiceMap.put(v.getMerchantType(), v);
        });
        this.payServiceMap = payServiceMap;
    }

    /**
     * 获取支付链接
     *
     * @param merchantId 商户id
     * @param orderId    商品id
     * @return
     */
    @Override
    public SingleAllBo<String> getPayUrl(Long merchantId, Long orderId) {
        Merchant merchant = merchantDao.findById(merchantId).orElse(null);
        if (ObjectUtil.isNull(merchant)) {
            throw new AppException("未找到商户信息，请联系管理员！");
        }
        PayService payService = this.payServiceMap.get(merchant.getMerchantType());
        if (ObjectUtil.isNull(payService)) {
            throw new AppException("错误的商户信息，请联系管理员！");
        }
        Order order = orderDao.findById(orderId).orElse(null);
        if (ObjectUtil.isNull(order)) {
            throw new AppException("未找到此订单，请联系管理员！");
        }
        float totalPrice = order.getTotalPrice();
        Long goodsId = order.getGoodsId();
        Goods goods = goodsDao.findById(goodsId).orElse(null);
        if (ObjectUtil.isNull(goods)) {
            throw new AppException("未找到商品，请联系管理员");
        }
        String goodsName = goods.getGoodsName() + "--[数量:" + order.getGoodsNum() + "]";
        return new SingleAllBo<>(payService.getPayUrl(merchant, orderId, goodsName, totalPrice));
    }

    /**
     * 获取支付链接
     *
     * @param merchantId     商户id
     * @param composeOrderId 组合id
     * @param amount         金额
     * @return
     */
    @Override
    public SingleAllBo<String> getPayUrl(Long merchantId, Long composeOrderId, Float amount) {
        Merchant merchant = merchantDao.findById(merchantId).orElse(null);
        if (ObjectUtil.isNull(merchant)) {
            throw new AppException("未找到商户信息！");
        }
        PayService payService = this.payServiceMap.get(merchant.getMerchantType());
        if (ObjectUtil.isNull(payService)) {
            throw new AppException("错误的商户信息，请联系管理员！");
        }
        return new SingleAllBo<>(payService.getPayUrl(merchant, composeOrderId, GlobalConstant.COMPOSE_GOODS_NAME, amount));
    }
}
