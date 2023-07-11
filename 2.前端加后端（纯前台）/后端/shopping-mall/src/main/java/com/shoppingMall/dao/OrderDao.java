package com.shoppingMall.dao;

import com.shoppingMall.entity.po.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * 订单数据操作接口
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/2/28
 */
public interface OrderDao extends JpaRepository<Order,Long>, JpaSpecificationExecutor<Order> {

    /**
     * 根据用户查询所有订单
     *
     * @param userId
     * @return
     */
    List<Order> findAllByUserId(Long userId);

    /**
     * 根据userid查询出所有订单
     *
     * @param userId
     * @param orderState
     * @return
     */
    List<Order> findAllByUserIdAndOrderState(Long userId,String orderState);

    /**
     * 根据组合订单id查询所有数据
     *
     * @param composeOrderId
     * @return
     */
    List<Order> findAllByComposeOrderId(Long composeOrderId);
}
