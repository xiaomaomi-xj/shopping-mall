package com.shoppingMall.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import com.shoppingMall.entity.bo.SingleAllBo;
import com.shoppingMall.service.CaptchaService;
import com.shoppingMall.utils.CaptchaUtil;
import com.shoppingMall.utils.ResourceErrorUtil;
import com.zhuyahui.annotation.ZyhService;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * 操作验证码
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/3
 */
@ZyhService
public class CaptchaServiceImpl implements CaptchaService {
    private final RedisTemplate<String, String> redisTemplate;

    public CaptchaServiceImpl(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 手动生成验证码内容，验证码存活时间为10分钟,返回保存的key
     *
     * @return
     */
    @Override
    public SingleAllBo<String> getCaptchaKey() {
        String code = RandomUtil.randomString("0123456789", 4);
        String id = IdUtil.objectId();
        BoundValueOperations<String, String> stringStringBoundValueOperations = redisTemplate.boundValueOps(id);
        stringStringBoundValueOperations.set(code, 10, TimeUnit.MINUTES);
        return new SingleAllBo<>(id);
    }

    /**
     * 根据key查询出随机生成的code，生成图片返回前端
     *
     * @param id
     * @return
     */
    @Override
    public byte[] getCaptchaContent(String id) {
        Boolean aBoolean = redisTemplate.hasKey(id);
        if (aBoolean != null && aBoolean) {
            String code = redisTemplate.boundValueOps(id).get();
            if (ObjectUtil.isNull(code)) {
                return ResourceErrorUtil.getCheckImg();
            }
            return CaptchaUtil.getCheckCode(code);
        }
        return ResourceErrorUtil.getCheckImg();
    }

    /**
     * 如果验证成功，要删掉验证码
     *
     * @param id
     * @param code
     * @return
     */
    @Override
    public SingleAllBo<Boolean> verifyCaptcha(String id, String code) {
        boolean result = false;
        Boolean aBoolean = redisTemplate.hasKey(id);
        if (aBoolean != null && aBoolean) {
            String sCode = redisTemplate.boundValueOps(id).get();
            if (ObjectUtil.isNull(sCode)) {
                return new SingleAllBo<>(false);
            }
            if (code.equalsIgnoreCase(sCode)) {
                result = true;
                redisTemplate.delete(id);
            }else{
                //不管成功与否，用完就删
                redisTemplate.delete(id);
            }
        }
        return new SingleAllBo<>(result);
    }
}
