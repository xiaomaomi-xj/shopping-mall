package com.shoppingMall.service;

import com.shoppingMall.entity.bo.ChatBoosBo;
import com.shoppingMall.entity.bo.SingleAllBo;

import java.util.List;

/**
 * 操作聊天信息
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/2/28
 */
public interface ChatBoosService {
    /**
     * 获取当前用户的消息数据
     *
     * @param token
     * @return
     */
    List<ChatBoosBo> getChatBoosData(String token);

    /**
     * 机器人聊天
     *
     * @param token
     * @param message
     * @return
     */
    SingleAllBo<Boolean> robotChatBoosData(String token,String message);

    /**
     * 把消息变为已读
     *
     * @param token
     */
    void readChatBoosData(String token);
}
