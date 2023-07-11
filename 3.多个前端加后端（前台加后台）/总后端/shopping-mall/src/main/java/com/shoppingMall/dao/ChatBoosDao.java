package com.shoppingMall.dao;

import com.shoppingMall.entity.po.ChatBoos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * 消息操作接口
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/2/28
 */
public interface ChatBoosDao extends JpaRepository<ChatBoos,Long>, JpaSpecificationExecutor<ChatBoos> {
    /**
     * 根据用户id获取多条信息
     *
     * @param userId
     * @return
     */
    List<ChatBoos> findAllByUserId(Long userId);
}
