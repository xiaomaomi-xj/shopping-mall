package com.shoppingMall.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.shoppingMall.constant.GlobalConstant;
import com.shoppingMall.constant.LoginStateEnum;
import com.shoppingMall.dao.*;
import com.shoppingMall.entity.bo.SingleAllBo;
import com.shoppingMall.entity.bo.TokenBo;
import com.shoppingMall.entity.bo.WechatBo;
import com.shoppingMall.entity.po.*;
import com.shoppingMall.entity.po.self.Wechat;
import com.shoppingMall.exception.AppException;
import com.shoppingMall.exception.BizException;
import com.shoppingMall.properties.SelfConfigPropertiesBean;
import com.shoppingMall.service.TokenService;
import com.shoppingMall.service.WechatService;
import com.shoppingMall.utils.Assert;
import com.shoppingMall.utils.PasswordUtil;
import com.shoppingMall.utils.ResourceErrorUtil;
import com.shoppingMall.utils.WechatUtil;
import com.zhuyahui.annotation.ZyhDataSourceRead;
import com.zhuyahui.annotation.ZyhService;
import com.zhuyahui.util.MyAloneHandlerReadWrite;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 扫码登录
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/5
 */
@ZyhService
@ZyhDataSourceRead
public class WechatServiceImpl implements WechatService {
    private final SelfConfigPropertiesBean selfConfigPropertiesBean;
    private final RedisTemplate<String, String> redisTemplate;
    private final WechatDao wechatDao;
    private final TokenService tokenService;
    private final UserDao userDao;
    private final ShopCartDao shopCartDao;
    private final ChatBoosDao chatBoosDao;
    private final OrderDao orderDao;

    public WechatServiceImpl(SelfConfigPropertiesBean selfConfigPropertiesBean, RedisTemplate<String, String> redisTemplate, WechatDao wechatDao, TokenService tokenService, UserDao userDao, ShopCartDao shopCartDao, ChatBoosDao chatBoosDao, OrderDao orderDao) {
        this.selfConfigPropertiesBean = selfConfigPropertiesBean;
        this.redisTemplate = redisTemplate;
        this.wechatDao = wechatDao;
        this.tokenService = tokenService;
        this.userDao = userDao;
        this.shopCartDao = shopCartDao;
        this.chatBoosDao = chatBoosDao;
        this.orderDao = orderDao;
    }

    /**
     * 获取微信登录id
     *
     * @return
     */
    @Override
    public SingleAllBo<String> getWechatLoginId() {
        String id = IdUtil.objectId();
        BoundValueOperations<String, String> stringStringBoundValueOperations = redisTemplate.boundValueOps(id);
        stringStringBoundValueOperations.set(GlobalConstant.NO_LOGIN, 10, TimeUnit.MINUTES);
        return new SingleAllBo<>(id);
    }

    /**
     * 获取登录二维码,有效期十分钟
     *
     * @return
     */
    @Override
    public byte[] getWechatLoginImg(String loginImgId) {
        Boolean aBoolean = redisTemplate.hasKey(loginImgId);
        if (ObjectUtil.isNull(aBoolean)) {
            return ResourceErrorUtil.getQrErrorImg();
        }
        if (!aBoolean) {
            return ResourceErrorUtil.getQrErrorImg();
        }
        Wechat wechat = selfConfigPropertiesBean.getConfig().getWechat();
        String appId = wechat.getAppId();
        String redirectUrl = wechat.getRedirectUrl();
        return WechatUtil.getWechatLoginImg(appId, redirectUrl, loginImgId);
    }

    /**
     * 获取用户信息
     *
     * @param code
     * @param state
     * @return
     */
    @Override
    public String getUserInfo(String code, String state) {
        Wechat wechat = selfConfigPropertiesBean.getConfig().getWechat();
        String appId = wechat.getAppId();
        String appSecret = wechat.getAppSecret();
        Boolean aBoolean = redisTemplate.hasKey(state);
        if (ObjectUtil.isNull(aBoolean)) {
            return "loginError";
        }
        if (!aBoolean) {
            return "loginError";
        }
        try {
            WechatBo userInfo = WechatUtil.getUserInfo(appId, appSecret, code);
            String headImgUrl = userInfo.getHeadImgUrl();
            String openId = userInfo.getOpenId();
            String nickName = userInfo.getNickName();
            int sex = userInfo.getSex();
            Assert.isNotBlank(headImgUrl, "头像url");
            Assert.isNotBlank(openId, "微信账号");
            Assert.isNotBlank(nickName, "微信昵称");
            Assert.isNotNull(sex, "微信性别");
            saveWechatUser(headImgUrl, openId, nickName, sex);
            BoundValueOperations<String, String> stringStringBoundValueOperations = redisTemplate.boundValueOps(state);
            stringStringBoundValueOperations.set(openId);
            return "loginSuccess";
        } catch (Exception e) {
            return "loginError";
        }
    }

    /**
     * 轮询验证是否登录成功
     *
     * @param loginImgId
     * @return
     */
    @Override
    public TokenBo checkLogin(String loginImgId) {
        BoundValueOperations<String, String> stringStringBoundValueOperations = redisTemplate.boundValueOps(loginImgId);
        String openId = stringStringBoundValueOperations.get();
        TokenBo tokenBo = new TokenBo();
        tokenBo.setStatus(false);
        if (ObjectUtil.isNull(openId)) {
            return tokenBo;
        }
        if (!wechatDao.existsByOpenId(openId)) {
            return tokenBo;
        }
        WechatPo wechatPo = wechatDao.findByOpenId(openId);
        Long userId = wechatPo.getUserId();
        if (ObjectUtil.isEmpty(userId)) {
            return tokenService.createToken(LoginStateEnum.QR_CODE.getType(), String.valueOf(wechatPo.getWechatId()));
        }
        User user = userDao.findById(userId).orElseGet(() -> null);
        if (ObjectUtil.isNull(user)) {
            return tokenService.createToken(LoginStateEnum.QR_CODE.getType(), String.valueOf(wechatPo.getWechatId()));
        }
        return tokenService.createToken(LoginStateEnum.EMAIL_CODE.getType(), String.valueOf(user.getUserId()));
    }

    /**
     * 绑定邮箱
     *
     * @param token
     * @param userEmail
     * @param password
     * @return
     */
    @Override
    public TokenBo bindAccount(String token, String userEmail, String password) {
        Boolean aBoolean = redisTemplate.hasKey(token);
        if (aBoolean != null && aBoolean) {
            BoundValueOperations<String, String> stringStringBoundValueOperations = redisTemplate.boundValueOps(token);
            String wechatContent = stringStringBoundValueOperations.get();
            if (ObjectUtil.isNull(wechatContent)) {
                throw new AppException("当前用户令牌错误，请重新登录！");
            }
            String loginType = wechatContent.split("&&&")[0];
            if (!LoginStateEnum.QR_CODE.getType().equals(loginType)) {
                throw new AppException("当前用户类型错误，请重新登录！");
            }
            String wechatIdStr = wechatContent.split("&&&")[1];
            long wechatId = Long.parseLong(wechatIdStr);
            WechatPo wechatPo = wechatDao.findById(wechatId).orElseGet(() -> null);
            if (ObjectUtil.isNull(wechatPo)) {
                throw new AppException("当前用户令牌错误，请重新登录！");
            }
            User user = userDao.findByUserEmail(userEmail);
            if (ObjectUtil.isNull(user)) {
                throw new BizException("你绑定的邮箱用户不存在！");
            }
            String userPassword = user.getPassword();
            String salt = selfConfigPropertiesBean.getConfig().getPassword().getSalt();
            String currentUserPassword = PasswordUtil.getPassword(password, salt);
            if (!userPassword.equals(currentUserPassword)) {
                throw new BizException("你绑定的邮箱用户密码不正确！");
            }
            Long userId = user.getUserId();
            if (wechatDao.existsByUserId(userId)) {
                //代表已经被绑定过了
                throw new BizException("此邮箱已被其他用户绑定！");
            }
            //绑定邮箱
            wechatPo.setUserId(userId);
            //判断绑定的邮箱有没有头像，没有微信的给他，有的话用邮箱的
            String userHeadUrl = user.getUserHeadUrl();
            if (ObjectUtil.isEmpty(userHeadUrl)) {
                user.setUserHeadUrl(wechatPo.getWechatHeadUrl());
            }
            //一旦绑定，购物车、商品、聊天都更改id,以便于之后的使用
            List<ShopCart> shopCartListByWechatId = shopCartDao.findAllByUserId(wechatId);
            List<ShopCart> shopCartListByUserId = shopCartDao.findAllByUserId(userId);
            List<Order> orderList = orderDao.findAllByUserId(wechatId);
            List<ChatBoos> chatBoosList = chatBoosDao.findAllByUserId(wechatId);
            MyAloneHandlerReadWrite.write(() -> {
                wechatDao.save(wechatPo);
                userDao.save(user);
                //购物车数据叠加，但是不能重复
                for (ShopCart shopCart : shopCartListByWechatId) {
                    boolean isRepeat = false;
                    for (ShopCart userShopCard : shopCartListByUserId) {
                        if (userShopCard.getGoodsId().equals(shopCart.getGoodsId())) {
                            isRepeat = true;
                            break;
                        }
                    }
                    if (!isRepeat) {
                        //没有重复的修改
                        shopCart.setUserId(userId);
                        shopCartDao.save(shopCart);
                    }
                }
                orderList.forEach(v -> {
                    v.setUserId(userId);
                    orderDao.save(v);
                });
                chatBoosList.forEach(v -> {
                    v.setUserId(userId);
                    chatBoosDao.save(v);
                });
                return null;
            });
            return tokenService.createToken(LoginStateEnum.EMAIL_CODE.getType(), String.valueOf(user.getUserId()));
        } else {
            throw new AppException("当前用户令牌错误，请重新登录！");
        }
    }

    /**
     * 插入微信用户
     *
     * @param wechatHeadUrl
     * @param openId
     * @param nickName
     * @param sex
     */
    private void saveWechatUser(String wechatHeadUrl, String openId, String nickName, int sex) {
        //有更新，没有新增
        WechatPo wechatPo = wechatDao.findByOpenId(openId);
        long wechatId = ObjectUtil.isNull(wechatPo) ? IdUtil.getSnowflakeNextId() : wechatPo.getWechatId();
        //使用减少事务粒度的方式,(默认的，读已提交，效率更高)
        MyAloneHandlerReadWrite.write(() ->
                wechatDao.save(new WechatPo(
                        wechatId,
                        wechatHeadUrl,
                        openId,
                        nickName,
                        sex,
                        ObjectUtil.isNull(wechatPo) ? null :wechatPo.getUserId()
                ))
        );

    }
}
