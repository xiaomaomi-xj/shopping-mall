package com.shoppingMall.controller;

import com.shoppingMall.admin.annotation.CheckAdmin;
import com.shoppingMall.admin.entity.vo.ModifyOrderVo;
import com.shoppingMall.entity.bo.OrderBo;
import com.shoppingMall.entity.bo.OrderOnAdminBo;
import com.shoppingMall.entity.bo.SingleAllBo;
import com.shoppingMall.entity.vo.OrderVo;
import com.shoppingMall.service.OrderService;
import com.shoppingMall.utils.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 订单接口类
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/22
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * 获取所有订单数据，需要管理员权限
     *
     * @return
     */
    @PostMapping("/get_all_order_data")
    @CheckAdmin
    public List<OrderOnAdminBo> getAllOrderData() {
        return orderService.getAllOrderData();
    }

    /**
     * 修改订单运送状态
     *
     * @param modifyOrderVo
     */
    @PostMapping("/modify_order_ship_statue")
    @CheckAdmin
    public SingleAllBo<Boolean> modifyOrderShipStatus(@RequestBody ModifyOrderVo modifyOrderVo) {
        Long orderId = modifyOrderVo.getOrderId();
        int shipStatus = modifyOrderVo.getShipStatus();
        Assert.isNotNull(orderId, "订单id");
        Assert.isNotNull(shipStatus, "运送状态");
        return orderService.modifyOrderShipStatus(orderId, shipStatus);
    }

    /**
     * 获取订单数据
     *
     * @param orderVo
     * @return
     */
    @PostMapping("/get_order_data")
    public List<OrderBo> getOrderData(@RequestBody OrderVo orderVo) {
        String token = orderVo.getToken();
        Assert.isNotBlank(token, "用户令牌");
        return orderService.getOrderData(token);
    }

    /**
     * 删除订单
     *
     * @param orderVo
     */
    @PostMapping("/del_order_data")
    public void delOrderData(@RequestBody OrderVo orderVo) {
        String token = orderVo.getToken();
        Long orderId = orderVo.getOrderId();
        Assert.isNotBlank(token, "用户令牌");
        Assert.isNotNull(orderId, "商品id");
        orderService.deleteOrderData(token, orderId);
    }

    /**
     * 创建单个商品的订单
     *
     * @param orderVo
     * @return
     */
    @PostMapping("/create_single_order_data")
    public SingleAllBo<String> createSingleOrderData(@RequestBody OrderVo orderVo) {
        String token = orderVo.getToken();
        Long merchantId = orderVo.getMerchantId();
        OrderBo goodsInfo = orderVo.getGoodsInfo();
        Assert.isNotBlank(token, "用户令牌");
        Assert.isNotNull(merchantId, "商户id");
        Assert.isNotNull(goodsInfo, "购买的商品");
        Float totalPrice = goodsInfo.getTotalPrice();
        String goodsId = goodsInfo.getGoodsId();
        Integer goodsNum = goodsInfo.getGoodsNum();
        Assert.isNotNull(totalPrice, "总价格");
        Assert.isNotBlank(goodsId, "商品id");
        Assert.isNotNull(goodsNum, "商品数量");
        return orderService.createSingleOrderData(token, merchantId, goodsId, goodsNum, totalPrice);
    }

    /**
     * 创建多个商品的订单
     *
     * @param orderVo
     * @return
     */
    @PostMapping("/create_multiple_order_data")
    public SingleAllBo<String> createMultipleOrderData(@RequestBody OrderVo orderVo) {
        String token = orderVo.getToken();
        Long merchantId = orderVo.getMerchantId();
        OrderBo goodsInfo = orderVo.getGoodsInfo();
        List<OrderBo> goodsInfos = orderVo.getGoodsInfos();
        Assert.isNotBlank(token, "用户令牌");
        Assert.isNotNull(merchantId, "商户id");
        Assert.isNotNull(goodsInfo, "组合商品信息");
        Assert.isNotNull(goodsInfos, "购买的商品");
        Assert.isNotEmpty(goodsInfos, "购买的商品");
        return orderService.createMultipleOrderData(token, merchantId, goodsInfo, goodsInfos);
    }
}
