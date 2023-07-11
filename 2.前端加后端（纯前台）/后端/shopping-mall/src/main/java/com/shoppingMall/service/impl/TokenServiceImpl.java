package com.shoppingMall.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import com.shoppingMall.constant.GlobalConstant;
import com.shoppingMall.constant.LoginStateEnum;
import com.shoppingMall.dao.UserDao;
import com.shoppingMall.dao.WechatDao;
import com.shoppingMall.entity.bo.*;
import com.shoppingMall.entity.po.User;
import com.shoppingMall.entity.po.WechatPo;
import com.shoppingMall.exception.AppException;
import com.shoppingMall.exception.BizException;
import com.shoppingMall.service.TokenService;
import com.shoppingMall.utils.PasswordUtil;
import com.zhuyahui.annotation.ZyhDataSourceRead;
import com.zhuyahui.annotation.ZyhService;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * token操作
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/12
 */
@ZyhService
@ZyhDataSourceRead
public class TokenServiceImpl implements TokenService {
    private final RedisTemplate<String, String> redisTemplate;
    private final WechatDao wechatDao;
    private final UserDao userDao;

    public TokenServiceImpl(RedisTemplate<String, String> redisTemplate, WechatDao wechatDao, UserDao userDao) {
        this.redisTemplate = redisTemplate;
        this.wechatDao = wechatDao;
        this.userDao = userDao;
    }

    @Override
    public SingleAllBo<Boolean> checkToken(String token) {
        Boolean aBoolean = redisTemplate.hasKey(token);
        if (aBoolean != null && aBoolean) {
            BoundValueOperations<String, String> stringStringBoundValueOperations = redisTemplate.boundValueOps(token);
            String tokenValue = stringStringBoundValueOperations.get();
            if (ObjectUtil.isNull(tokenValue)) {
                throw new AppException("token验证失败！");
            }
            String[] split = tokenValue.split("&&&");
            String loginType = split[0];
            String userId = split[1];
            if (LoginStateEnum.EMAIL_CODE.getType().equals(loginType)) {
                UserBo userBo = this.getUser(userId);
                if (userBo == null) {
                    return new SingleAllBo<>(false);
                }
                return new SingleAllBo<>(true);
            }
            if (LoginStateEnum.QR_CODE.getType().equals(loginType)) {
                WechatBo wechatBo = this.getWechat(userId);
                if (wechatBo == null) {
                    return new SingleAllBo<>(false);
                }
                return new SingleAllBo<>(true);
            }
            return new SingleAllBo<>(false);
        }
        return new SingleAllBo<>(false);
    }

    /**
     * token可用时间为30分钟,refreshToken为一小时
     */
    @Override
    public TokenBo createToken(String loginType, String userId) {
        String id = IdUtil.objectId();
        String token = PasswordUtil.createToken(id, RandomUtil.randomString(GlobalConstant.CODE_TEMPLATE, 6));
        BoundValueOperations<String, String> stringStringBoundValueOperations = redisTemplate.boundValueOps(token);
        stringStringBoundValueOperations.set(loginType + "&&&" + userId, 30, TimeUnit.MINUTES);
        String refreshId = IdUtil.objectId();
        String refreshToken = PasswordUtil.createToken(refreshId, RandomUtil.randomString(GlobalConstant.CODE_TEMPLATE, 6));
        BoundListOperations<String, String> stringStringBoundListOperations = redisTemplate.boundListOps(refreshToken);
        stringStringBoundListOperations.leftPushAll(token, loginType, userId);
        stringStringBoundListOperations.expire(1, TimeUnit.HOURS);
        return new TokenBo(
                true,
                token,
                refreshToken
        );
    }

    /**
     * token过期，refreshToken不过期就要刷新token
     */
    @Override
    public TokenBo refreshToken(String token, String refreshToken) {
        Boolean aBoolean1 = redisTemplate.hasKey(token);
        if (aBoolean1 != null && aBoolean1) {
            throw new BizException("token未过期！");
        }
        Boolean aBoolean = redisTemplate.hasKey(refreshToken);
        if (aBoolean != null && aBoolean) {
            BoundListOperations<String, String> stringStringBoundListOperations = redisTemplate.boundListOps(refreshToken);
            String userId = stringStringBoundListOperations.index(0);
            String loginType = stringStringBoundListOperations.index(1);
            String redisToken = stringStringBoundListOperations.index(2);
            if (ObjectUtil.isNull(userId) && ObjectUtil.isNull(loginType) && ObjectUtil.isNull(redisToken)) {
                throw new AppException("refreshToken获取失败！");
            }
            if (!redisToken.equals(token)) {
                throw new AppException("非法token！");
            }
            return this.createToken(loginType, userId);
        }
        return new TokenBo(
                false,
                null,
                null
        );
    }

    /**
     * 根据token获取用户
     *
     * @param token
     * @return
     */
    @Override
    public LoginUserBo getUserOnToken(String token) {
        LoginUserBo loginUserBo = new LoginUserBo();
        loginUserBo.setStatus(false);
        Boolean aBoolean = redisTemplate.hasKey(token);
        if (aBoolean != null && aBoolean) {
            var stringStringBoundValueOperations = redisTemplate.boundValueOps(token);
            String tokenValue = stringStringBoundValueOperations.get();
            if (ObjectUtil.isNull(tokenValue)) {
                throw new AppException("token验证失败！");
            }
            String[] split = tokenValue.split("&&&");
            String loginType = split[0];
            String userId = split[1];
            if (LoginStateEnum.EMAIL_CODE.getType().equals(loginType)) {
                UserBo userBo = this.getUser(userId);
                if (userBo == null) {
                    return loginUserBo;
                }
                return new LoginUserBo(
                        true,
                        userBo,
                        loginType
                );
            }
            if (LoginStateEnum.QR_CODE.getType().equals(loginType)) {
                WechatBo wechatBo = this.getWechat(userId);
                if (wechatBo == null) {
                    return loginUserBo;
                }
                return new LoginUserBo(
                        true,
                        wechatBo,
                        loginType
                );
            }
            return loginUserBo;
        }
        return loginUserBo;
    }

    private UserBo getUser(String userId) {
        User user = userDao.findById(Long.parseLong(userId)).orElse(null);
        if (user == null) {
            return null;
        }
        return new UserBo(
                user.getUserHeadUrl(),
                user.getUserName(),
                user.getUserEmail(),
                user.getUserSex()
        );
    }

    private WechatBo getWechat(String wechatId) {
        WechatPo wechatPo = wechatDao.findById(Long.parseLong(wechatId)).orElse(null);
        if (wechatPo == null) {
            return null;
        }
        return new WechatBo(
                wechatPo.getWechatHeadUrl(),
                wechatPo.getOpenId(),
                wechatPo.getNickName(),
                wechatPo.getSex()
        );
    }
}
