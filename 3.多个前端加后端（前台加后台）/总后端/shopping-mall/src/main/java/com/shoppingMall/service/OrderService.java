package com.shoppingMall.service;

import com.shoppingMall.entity.bo.OrderBo;
import com.shoppingMall.entity.bo.OrderOnAdminBo;
import com.shoppingMall.entity.bo.SingleAllBo;

import java.util.List;

/**
 * 操作订单
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/2/28
 */
public interface OrderService {
    /**
     * 获取订单数据
     *
     * @param token
     * @return
     */
    List<OrderBo> getOrderData(String token);

    /**
     * 删除订单数据
     *
     * @param token
     * @param orderId
     */
    void deleteOrderData(String token, Long orderId);

    /**
     * 创建单个订单
     *
     * @param token
     * @param merchantId
     * @param goodsId
     * @param goodsNum
     * @param totalPrice
     * @return
     */
    SingleAllBo<String> createSingleOrderData(String token, Long merchantId, String goodsId, Integer goodsNum, Float totalPrice);

    /**
     * 创建组合订单
     *
     * @param token
     * @param merchantId
     * @param goodsInfo
     * @param goodsInfos
     * @return
     */
    SingleAllBo<String> createMultipleOrderData(String token, Long merchantId, OrderBo goodsInfo, List<OrderBo> goodsInfos);

    /**
     * 获取所有订单数据
     *
     * @return
     */
    List<OrderOnAdminBo> getAllOrderData();

    /**
     * 修改订单状态
     *
     * @param orderId
     * @param shipStatus
     * @return
     */
    SingleAllBo<Boolean> modifyOrderShipStatus(Long orderId, int shipStatus);
}
