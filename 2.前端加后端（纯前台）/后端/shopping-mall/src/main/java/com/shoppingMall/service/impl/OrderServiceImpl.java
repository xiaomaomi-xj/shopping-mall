package com.shoppingMall.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.shoppingMall.constant.GlobalConstant;
import com.shoppingMall.constant.OrderStateEnum;
import com.shoppingMall.dao.GoodsDao;
import com.shoppingMall.dao.ImgDao;
import com.shoppingMall.dao.OrderDao;
import com.shoppingMall.entity.bo.OrderBo;
import com.shoppingMall.entity.bo.SingleAllBo;
import com.shoppingMall.entity.po.Goods;
import com.shoppingMall.entity.po.Img;
import com.shoppingMall.entity.po.Order;
import com.shoppingMall.exception.AppException;
import com.shoppingMall.manager.ManagerTokenService;
import com.shoppingMall.pay.core.service.MorePayService;
import com.shoppingMall.service.OrderService;
import com.shoppingMall.utils.Assert;
import com.zhuyahui.annotation.ZyhDataSourceRead;
import com.zhuyahui.annotation.ZyhService;
import com.zhuyahui.util.MyAloneHandlerReadWrite;

import java.util.ArrayList;
import java.util.List;

/**
 * 操作订单
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/2/28
 */
@ZyhService
@ZyhDataSourceRead
public class OrderServiceImpl implements OrderService {
    private final OrderDao orderDao;
    private final GoodsDao goodsDao;
    private final ImgDao imgDao;
    private final ManagerTokenService managerTokenService;
    private final MorePayService morePayService;

    public OrderServiceImpl(OrderDao orderDao, GoodsDao goodsDao, ImgDao imgDao, ManagerTokenService managerTokenService, MorePayService morePayService) {
        this.orderDao = orderDao;
        this.goodsDao = goodsDao;
        this.imgDao = imgDao;
        this.managerTokenService = managerTokenService;
        this.morePayService = morePayService;
    }

    /**
     * 获取订单数据
     *
     * @param token
     * @return
     */
    @Override
    public List<OrderBo> getOrderData(String token) {
        List<OrderBo> orderBoList = new ArrayList<>();
        Long userId = managerTokenService.getUserId(token);
        List<Order> orderList = orderDao.findAllByUserIdAndOrderState(userId, OrderStateEnum.COMPLET.getState());
        for (Order order : orderList) {
            Goods goods = goodsDao.findById(order.getGoodsId()).orElseGet(() -> null);
            if (ObjectUtil.isNull(goods)) {
                throw new AppException("商品数据丢失，请联系管理员！");
            }
            Img img = imgDao.findByCoverGoods(order.getGoodsId()).orElseGet(() -> null);
            if (ObjectUtil.isNull(img)) {
                throw new AppException("图片数据丢失，请联系管理员！");
            }
            orderBoList.add(new OrderBo(
                    String.valueOf(order.getOrderId()),
                    String.valueOf(goods.getGoodsId()),
                    img.getImgName(),
                    goods.getGoodsName(),
                    goods.getGoodsPrice(),
                    order.getGoodsNum(),
                    order.getShipStatus(),
                    order.getTotalPrice()
            ));
        }
        return orderBoList;
    }

    /**
     * 删除订单数据
     *
     * @param token
     * @param orderId
     */
    @Override
    public void deleteOrderData(String token, Long orderId) {
        Long userId = managerTokenService.getUserId(token);
        Order order = orderDao.findById(orderId).orElseGet(() -> null);
        if (ObjectUtil.isNull(order)) {
            throw new AppException("订单数据丢失，请联系管理员！");
        }
        if (!userId.equals(order.getUserId())) {
            throw new AppException("订单数据与用户数据不匹配，请联系管理员！");
        }
        MyAloneHandlerReadWrite.write(() -> {
            orderDao.delete(order);
            return null;
        });
    }

    /**
     * 创建单个订单
     *
     * @param token
     * @param goodsId
     * @param goodsNum
     * @param totalPrice
     * @return
     */
    @Override
    public SingleAllBo<String> createSingleOrderData(String token, Long merchantId, String goodsId, Integer goodsNum, Float totalPrice) {
        if (goodsNum <= 0) {
            throw new AppException("商品购买失败，购买的商品数量不能少于等于0件！");
        }
        Long userId = managerTokenService.getUserId(token);
        Goods goods = goodsDao.findById(Long.parseLong(goodsId)).orElse(null);
        if (ObjectUtil.isNull(goods)) {
            throw new AppException("商品数据丢失，请联系管理员！");
        }
        if (goods.getMaxBuyNum() < goodsNum) {
            throw new AppException("商品购买失败，商品库存不足！");
        }
        Float goodsPrice = goods.getGoodsPrice();
        if (!totalPrice.equals((goodsPrice * goodsNum))) {
            throw new AppException("商品购买失败，购买价格和实际价格不一致！");
        }
        Long orderId = IdUtil.getSnowflakeNextId();
        MyAloneHandlerReadWrite.write(() -> orderDao.save(new Order(
                orderId,
                userId,
                Long.parseLong(goodsId),
                merchantId,
                goodsNum,
                GlobalConstant.UN_DELIVERY,
                totalPrice,
                OrderStateEnum.PENDING.getState(),
                null
        )));
        return morePayService.getPayUrl(merchantId, orderId);
    }

    /**
     * 创建组合订单
     *
     * @param token
     * @param goodsInfo
     * @param goodsInfos
     * @return
     */
    @Override
    public SingleAllBo<String> createMultipleOrderData(String token, Long merchantId, OrderBo goodsInfo, List<OrderBo> goodsInfos) {
        Long userId = managerTokenService.getUserId(token);
        //获取组合订单信息
        Float composeGoodsPrice = goodsInfo.getGoodsPrice();
        //验证价格
        float checkGoodsPrice = 0f;
        Integer composeGoodsNum = goodsInfo.getGoodsNum();
        Assert.isNotNull(composeGoodsNum, "组合商品数量");
        Assert.isNotNull(composeGoodsPrice, "组合商品价格");
        if (composeGoodsNum <= 0 || goodsInfos.size() <= 0) {
            throw new AppException("商品购买失败，商品的数量不得少于等于0！");
        }
        //验证数量
        int totalNum = 0;
        for (OrderBo orderBo : goodsInfos) {
            totalNum += orderBo.getGoodsNum();
        }
        if (composeGoodsNum != totalNum) {
            throw new AppException("商品购买失败，商品的数量不相等！");
        }
        //创建组合订单id
        Long composeOrderId = IdUtil.getSnowflakeNextId();
        for (OrderBo orderBo : goodsInfos) {
            String goodsId = orderBo.getGoodsId();
            Goods goods = goodsDao.findById(Long.parseLong(goodsId)).orElse(null);
            if (ObjectUtil.isNull(goods)) {
                throw new AppException("商品数据丢失，请联系管理员！");
            }
            Integer goodsNum = orderBo.getGoodsNum();
            Float totalPrice = orderBo.getTotalPrice();
            Assert.isNotNull(goodsNum, "商品数量");
            Assert.isNotNull(totalPrice, "商品价格");
            if (goodsNum <= 0) {
                throw new AppException("商品购买失败，购买的商品数量不能少于等于0件！");
            }
            if (goods.getMaxBuyNum() < goodsNum) {
                throw new AppException("商品购买失败，商品库存不足！");
            }
            Float goodsPrice = goods.getGoodsPrice();
            if (!totalPrice.equals(goodsNum * goodsPrice)) {
                throw new AppException("商品购买失败，购买价格和实际价格不一致！");
            }
            checkGoodsPrice += totalPrice;
            MyAloneHandlerReadWrite.write(() -> orderDao.save(new Order(
                    IdUtil.getSnowflakeNextId(),
                    userId,
                    Long.parseLong(goodsId),
                    merchantId,
                    goodsNum,
                    GlobalConstant.UN_DELIVERY,
                    totalPrice,
                    OrderStateEnum.PENDING.getState(),
                    composeOrderId
            )));
        }
        if (!composeGoodsPrice.equals(checkGoodsPrice)) {
            throw new AppException("商品购买失败，购买价格和实际价格不一致！");
        }
        return morePayService.getPayUrl(merchantId, composeOrderId, composeGoodsPrice);
    }
}
