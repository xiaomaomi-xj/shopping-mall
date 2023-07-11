package com.shoppingMall.service.impl;

import cn.hutool.core.util.IdUtil;
import com.shoppingMall.admin.entity.bo.AdminChatBoosBo;
import com.shoppingMall.constant.GlobalConstant;
import com.shoppingMall.constant.LoginStateEnum;
import com.shoppingMall.dao.ChatBoosDao;
import com.shoppingMall.dao.UserDao;
import com.shoppingMall.dao.WechatDao;
import com.shoppingMall.entity.bo.ChatBoosBo;
import com.shoppingMall.entity.bo.SingleAllBo;
import com.shoppingMall.entity.po.ChatBoos;
import com.shoppingMall.entity.po.User;
import com.shoppingMall.entity.po.WechatPo;
import com.shoppingMall.exception.BizException;
import com.shoppingMall.manager.ManagerTokenService;
import com.shoppingMall.service.ChatBoosService;
import com.shoppingMall.utils.RobotUtil;
import com.shoppingMall.utils.ThreadPoolUtil;
import com.zhuyahui.annotation.ZyhDataSourceRead;
import com.zhuyahui.annotation.ZyhService;
import com.zhuyahui.util.MyAloneHandlerReadWrite;

import java.util.ArrayList;
import java.util.List;

/**
 * 操作聊天信息
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/2/28
 */
@ZyhService
@ZyhDataSourceRead
public class ChatBoosServiceImpl implements ChatBoosService {
    private final ChatBoosDao chatBoosDao;
    private final ManagerTokenService managerTokenService;
    private final UserDao userDao;
    private final WechatDao wechatDao;

    public ChatBoosServiceImpl(ChatBoosDao chatBoosDao, ManagerTokenService managerTokenService, UserDao userDao, WechatDao wechatDao) {
        this.chatBoosDao = chatBoosDao;
        this.managerTokenService = managerTokenService;
        this.userDao = userDao;
        this.wechatDao = wechatDao;
    }

    /**
     * 获取所有聊天数据
     *
     * @return
     */
    @Override
    public List<AdminChatBoosBo> getAllChatBoosData() {
        List<AdminChatBoosBo> chatBoosBoList = new ArrayList<>();
        List<ChatBoos> all = chatBoosDao.findAll();
        for (ChatBoos chatBoos : all) {
            chatBoosBoList.add(new AdminChatBoosBo(
                    String.valueOf(chatBoos.getUserId()),
                    chatBoos.getFromUser(),
                    chatBoos.getMessage(),
                    chatBoos.getIsUnRead()
            ));
        }
        return chatBoosBoList;
    }

    /**
     * 获取当前用户的消息数据
     *
     * @param token
     * @return
     */
    @Override
    public List<ChatBoosBo> getChatBoosData(String token) {
        List<ChatBoosBo> chatBoosBoList = new ArrayList<>();
        String loginType = managerTokenService.getLoginTypeOnToken(token);
        if (LoginStateEnum.EMAIL_CODE.getType().equals(loginType)) {
            //邮箱用户
            User user = managerTokenService.getUserOnToken(token);
            List<ChatBoos> allByUserId = chatBoosDao.findAllByUserId(user.getUserId());
            allByUserId.forEach(v -> {
                chatBoosBoList.add(new ChatBoosBo(
                        v.getFromUser(),
                        v.getMessage(),
                        v.getIsUnRead()
                ));
            });
        } else {
            //微信用户
            WechatPo wechatOnToken = managerTokenService.getWechatOnToken(token);
            List<ChatBoos> allByUserId = chatBoosDao.findAllByUserId(wechatOnToken.getWechatId());
            allByUserId.forEach(v -> {
                chatBoosBoList.add(new ChatBoosBo(
                        v.getFromUser(),
                        v.getMessage(),
                        v.getIsUnRead()
                ));
            });
        }
        return chatBoosBoList;
    }

    /**
     * 机器人聊天
     *
     * @param token
     * @return
     */
    @Override
    public SingleAllBo<Boolean> robotChatBoosData(String token, String message) {
        Long id = managerTokenService.getUserId(token);
        MyAloneHandlerReadWrite.write(() -> chatBoosDao.save(new ChatBoos(
                IdUtil.getSnowflakeNextId(),
                id,
                GlobalConstant.USER,
                message,
                GlobalConstant.UN_READ
        )));
        //调用机器人回复，因为很耗时间，开启线程
        ThreadPoolUtil.execute(() -> {
            String robotMessage = RobotUtil.getMessage(message);
            MyAloneHandlerReadWrite.write(() -> chatBoosDao.save(new ChatBoos(
                    IdUtil.getSnowflakeNextId(),
                    id,
                    GlobalConstant.BOOS,
                    robotMessage,
                    GlobalConstant.UN_READ
            )));
        });
        return new SingleAllBo<>(true);
    }

    /**
     * 真人聊天
     *
     * @param token
     * @param message
     * @return
     */
    @Override
    public SingleAllBo<Boolean> sendUserChatBoosData(String token, String message) {
        Long id = managerTokenService.getUserId(token);
        MyAloneHandlerReadWrite.write(() -> chatBoosDao.save(new ChatBoos(
                IdUtil.getSnowflakeNextId(),
                id,
                GlobalConstant.USER,
                message,
                GlobalConstant.UN_READ
        )));
        return new SingleAllBo<>(true);
    }

    /**
     * 消息变为已读
     *
     * @param token
     */
    @Override
    public void readChatBoosData(String token) {
        Long id = managerTokenService.getUserId(token);
        List<ChatBoos> allByUserId = chatBoosDao.findAllByUserId(id);
        MyAloneHandlerReadWrite.write(() -> {
            allByUserId.forEach(v -> {
                if (GlobalConstant.BOOS == v.getFromUser()) {
                    if (GlobalConstant.READ != v.getIsUnRead()) {
                        v.setIsUnRead(GlobalConstant.READ);
                        chatBoosDao.save(v);
                    }
                }
            });
            return null;
        });
    }

    /**
     * 已读
     *
     * @return
     */
    @Override
    public SingleAllBo<Boolean> readAdminChatBoos(String id) {
        List<ChatBoos> allByUserId = chatBoosDao.findAllByUserId(Long.parseLong(id));
        final boolean[] isChange = {false};
        allByUserId.forEach(chatBoos -> {
            if (GlobalConstant.USER == chatBoos.getFromUser() && GlobalConstant.READ != chatBoos.getIsUnRead()) {
                chatBoos.setIsUnRead(GlobalConstant.READ);
                isChange[0] = true;
            }
        });
        if (isChange[0]) {
            MyAloneHandlerReadWrite.write(() -> chatBoosDao.saveAll(allByUserId));
        }
        return new SingleAllBo<>(true);
    }

    /**
     * 后台发送消息
     *
     * @param userId
     * @param message
     * @return
     */
    @Override
    public SingleAllBo<Boolean> sendAdminChatBoos(String userId, String message) {
        boolean isExits = false;
        long id = Long.parseLong(userId);
        User user = userDao.findById(id).orElse(null);
        if (user != null) {
            isExits = true;
        }
        WechatPo wechatPo = wechatDao.findById(id).orElse(null);
        if (wechatPo != null) {
            isExits = true;
        }
        if (!isExits) {
            throw new BizException("用户不存在！");
        }
        ChatBoos chatBoos = new ChatBoos(
                IdUtil.getSnowflakeNextId(),
                id,
                GlobalConstant.BOOS,
                message,
                GlobalConstant.UN_READ
        );
        MyAloneHandlerReadWrite.write(() -> chatBoosDao.save(chatBoos));
        return new SingleAllBo<>(true);
    }
}
