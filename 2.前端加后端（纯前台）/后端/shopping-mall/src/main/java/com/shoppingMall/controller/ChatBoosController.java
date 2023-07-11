package com.shoppingMall.controller;

import com.shoppingMall.entity.bo.ChatBoosBo;
import com.shoppingMall.entity.bo.SingleAllBo;
import com.shoppingMall.entity.vo.ChatBoosVo;
import com.shoppingMall.service.ChatBoosService;
import com.shoppingMall.utils.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 聊天信息接口
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/1
 */
@RestController
@RequestMapping("/chat_boos")
public class ChatBoosController {
    private final ChatBoosService chatBoosService;

    public ChatBoosController(ChatBoosService chatBoosService) {
        this.chatBoosService = chatBoosService;
    }

    /**
     * 获取当前用户的聊天数据
     *
     * @param chatBoosVo
     * @return
     */
    @PostMapping("/get_chat_boos_data")
    public List<ChatBoosBo> getChatBoosData(@RequestBody ChatBoosVo chatBoosVo) {
        String token = chatBoosVo.getToken();
        Assert.isNotBlank(token, "用户令牌");
        return chatBoosService.getChatBoosData(token);
    }

    /**
     * 发送聊天内容
     *
     * @param chatBoosVo
     * @return
     */
    @PostMapping("/send_chat_boos_data")
    public SingleAllBo<Boolean> sendChatBoosData(@RequestBody ChatBoosVo chatBoosVo) {
        String token = chatBoosVo.getToken();
        String message = chatBoosVo.getMessage();
        Assert.isNotBlank(token, "用户令牌");
        Assert.isNotBlank(message, "发送的消息");
        return chatBoosService.robotChatBoosData(token, message);
    }

    /**
     * 把消息变为已读
     *
     * @param chatBoosVo
     */
    @PostMapping("/read_chat_boos_data")
    public void readChatBoosData(@RequestBody ChatBoosVo chatBoosVo) {
        String token = chatBoosVo.getToken();
        Assert.isNotBlank(token, "用户令牌");
        chatBoosService.readChatBoosData(token);
    }
}
