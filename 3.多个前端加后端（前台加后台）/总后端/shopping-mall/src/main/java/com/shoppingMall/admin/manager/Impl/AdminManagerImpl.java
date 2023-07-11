package com.shoppingMall.admin.manager.Impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import com.shoppingMall.admin.constan.AdminConstant;
import com.shoppingMall.admin.dao.AdminDao;
import com.shoppingMall.admin.entity.bo.AdminTokenBo;
import com.shoppingMall.admin.entity.po.Admin;
import com.shoppingMall.admin.manager.AdminManager;
import com.shoppingMall.constant.GlobalConstant;
import com.shoppingMall.exception.AppException;
import com.shoppingMall.utils.PasswordUtil;
import com.zhuyahui.annotation.ZyhDataSourceRead;
import com.zhuyahui.annotation.ZyhService;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * token管理类
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/30
 */
@ZyhService
@ZyhDataSourceRead
public class AdminManagerImpl implements AdminManager {
    private final RedisTemplate<String, String> redisTemplate;
    private final AdminDao adminDao;

    public AdminManagerImpl(RedisTemplate<String, String> redisTemplate, AdminDao adminDao) {
        this.redisTemplate = redisTemplate;
        this.adminDao = adminDao;
    }

    /**
     * 检查token
     *
     * @param adminToken
     * @return
     */
    @Override
    public boolean checkAdminToken(String adminToken) {
        Boolean aBoolean = redisTemplate.hasKey(adminToken);
        if (ObjectUtil.isNotNull(aBoolean) && aBoolean) {
            BoundValueOperations<String, String> stringStringBoundValueOperations = redisTemplate.boundValueOps(adminToken);
            if (ObjectUtil.isNull(stringStringBoundValueOperations)) {
                return false;
            }
            String adminId = stringStringBoundValueOperations.get();
            if (adminId == null || adminId.trim().length() <= 0) {
                return false;
            }
            try {
                Admin admin = adminDao.findById(Long.parseLong(adminId)).orElse(null);
                if (admin == null) {
                    return false;
                }
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    /**
     * 创建token
     *
     * @param adminId
     * @return
     */
    @Override
    public AdminTokenBo createAdminToken(String adminId) {
        String token = PasswordUtil.createToken(IdUtil.objectId(), RandomUtil.randomString(GlobalConstant.CODE_TEMPLATE, 6));
        BoundValueOperations<String, String> stringStringBoundValueOperations = redisTemplate.boundValueOps(token);
        stringStringBoundValueOperations.set(adminId, 30, TimeUnit.MINUTES);
        String refreshToken = PasswordUtil.createToken(IdUtil.objectId(), RandomUtil.randomString(GlobalConstant.CODE_TEMPLATE, 6));
        BoundValueOperations<String, String> stringStringBoundValueOperations1 = redisTemplate.boundValueOps(refreshToken);
        stringStringBoundValueOperations1.set(token + AdminConstant.TOKEN_SEPARATOR + adminId, 60, TimeUnit.MINUTES);
        return new AdminTokenBo(
                token,
                refreshToken
        );
    }

    /**
     * 刷新token
     *
     * @param token
     * @param refreshToken
     * @return
     */
    @Override
    public AdminTokenBo refreshToken(String token, String refreshToken) {
        Boolean aBoolean = redisTemplate.hasKey(token);
        if (aBoolean == null) {
            aBoolean = false;
        }
        if (aBoolean) {
            throw new AppException("token未过期！");
        } else {
            Boolean aBoolean1 = redisTemplate.hasKey(refreshToken);
            if (aBoolean1 != null && aBoolean1) {
                BoundValueOperations<String, String> stringStringBoundValueOperations = redisTemplate.boundValueOps(refreshToken);
                String text = stringStringBoundValueOperations.get();
                if (ObjectUtil.isNull(text)) {
                    throw new AppException("token刷新失败！");
                }
                String[] tokenInfo = text.split(AdminConstant.TOKEN_SEPARATOR);
                String redisToken = tokenInfo[0];
                String adminId = tokenInfo[1];
                if (ObjectUtil.isEmpty(redisToken) || ObjectUtil.isEmpty(adminId)) {
                    throw new AppException("token刷新失败！");
                }
                if (!redisToken.equals(token)) {
                    throw new AppException("token错误！");
                }
                return createAdminToken(adminId);
            } else {
                throw new AppException("长时间未操作，登录已过期！");
            }
        }
    }


    /**
     * 获取管理员账号
     *
     * @param token
     * @return
     */
    @Override
    public Admin getAdminOnToken(String token) {
        Boolean aBoolean = redisTemplate.hasKey(token);
        if (ObjectUtil.isNotNull(aBoolean) && aBoolean) {
            BoundValueOperations<String, String> stringStringBoundValueOperations = redisTemplate.boundValueOps(token);
            if (ObjectUtil.isNull(stringStringBoundValueOperations)) {
                return null;
            }
            String adminId = stringStringBoundValueOperations.get();
            if (adminId == null || adminId.trim().length() <= 0) {
                return null;
            }
            try {
                return adminDao.findById(Long.parseLong(adminId)).orElse(null);
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }
}
