package com.shoppingMall.manager.impl;

import cn.hutool.core.util.ObjectUtil;
import com.shoppingMall.constant.LoginStateEnum;
import com.shoppingMall.dao.UserDao;
import com.shoppingMall.dao.WechatDao;
import com.shoppingMall.entity.po.User;
import com.shoppingMall.entity.po.WechatPo;
import com.shoppingMall.exception.AppException;
import com.shoppingMall.manager.ManagerTokenService;
import com.zhuyahui.annotation.ZyhDataSourceRead;
import com.zhuyahui.annotation.ZyhService;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * token管理
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/21
 */
@ZyhService
@ZyhDataSourceRead
public class ManagerTokenServiceImpl implements ManagerTokenService {
    private final RedisTemplate<String, String> redisTemplate;
    private final WechatDao wechatDao;
    private final UserDao userDao;

    public ManagerTokenServiceImpl(RedisTemplate<String, String> redisTemplate, WechatDao wechatDao, UserDao userDao) {
        this.redisTemplate = redisTemplate;
        this.wechatDao = wechatDao;
        this.userDao = userDao;
    }

    /**
     * 根据token获取用户id
     *
     * @param token
     * @return
     */
    @Override
    public Long getUserId(String token) {
        String loginType = getLoginTypeOnToken(token);
        if (LoginStateEnum.EMAIL_CODE.getType().equals(loginType)) {
            //邮箱用户
            User user = getUserOnToken(token);
            return user.getUserId();
        } else {
            //微信用户
            WechatPo wechatPo = getWechatOnToken(token);
            return wechatPo.getWechatId();
        }
    }

    /**
     * 获取账号类型
     *
     * @param token
     * @return
     */
    @Override
    public String getLoginTypeOnToken(String token) {
        Boolean aBoolean = redisTemplate.hasKey(token);
        if (aBoolean != null && aBoolean) {
            BoundValueOperations<String, String> stringStringBoundValueOperations = redisTemplate.boundValueOps(token);
            String tokenValue = stringStringBoundValueOperations.get();
            if (ObjectUtil.isNull(tokenValue)) {
                throw new AppException("token验证失败！");
            }
            String[] split = tokenValue.split("&&&");
            String loginType = split[0];
            if (LoginStateEnum.EMAIL_CODE.getType().equals(loginType)) {
                return LoginStateEnum.EMAIL_CODE.getType();
            } else if (LoginStateEnum.QR_CODE.getType().equals(loginType)) {
                return LoginStateEnum.QR_CODE.getType();
            }
            throw new AppException("token错误！");
        }
        throw new AppException("token错误！");
    }

    /**
     * 获取用户
     *
     * @param token
     * @return
     */
    @Override
    public User getUserOnToken(String token) {
        Boolean aBoolean = redisTemplate.hasKey(token);
        if (aBoolean != null && aBoolean) {
            BoundValueOperations<String, String> stringStringBoundValueOperations = redisTemplate.boundValueOps(token);
            String tokenValue = stringStringBoundValueOperations.get();
            if (ObjectUtil.isNull(tokenValue)) {
                throw new AppException("token验证失败！");
            }
            String[] split = tokenValue.split("&&&");
            String userId = split[1];
            User user = userDao.findById(Long.parseLong(userId)).orElseGet(() -> null);
            if (ObjectUtil.isNotNull(user)) {
                return user;
            }
            throw new AppException("token错误！");
        }
        throw new AppException("token错误！");
    }

    /**
     * 获取微信用户
     *
     * @param token
     * @return
     */
    @Override
    public WechatPo getWechatOnToken(String token) {
        Boolean aBoolean = redisTemplate.hasKey(token);
        if (aBoolean != null && aBoolean) {
            BoundValueOperations<String, String> stringStringBoundValueOperations = redisTemplate.boundValueOps(token);
            String tokenValue = stringStringBoundValueOperations.get();
            if (ObjectUtil.isNull(tokenValue)) {
                throw new AppException("token验证失败！");
            }
            String[] split = tokenValue.split("&&&");
            String wechatId = split[1];
            WechatPo wechatPo = wechatDao.findById(Long.parseLong(wechatId)).orElseGet(() -> null);
            if (ObjectUtil.isNotNull(wechatPo)) {
                return wechatPo;
            }
            throw new AppException("token错误！");
        }
        throw new AppException("token错误！");
    }
}
