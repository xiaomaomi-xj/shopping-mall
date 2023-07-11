package com.shoppingMall.utils;

import com.shoppingMall.constant.LocalResourceConstant;
import com.shoppingMall.exception.BizException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * 二维码和验证码过去处理
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/18
 */
public class ResourceErrorUtil {
    /**
     * 获取二维码失效图片
     *
     * @return
     */
    public static byte[] getQrErrorImg(){
        try {
            return Files.readAllBytes(Path.of(LocalResourceConstant.LOCAL_IMG_URL,LocalResourceConstant.LOCAL_QR_ERROR_IMG));
        } catch (IOException e) {
            throw new BizException("获取图片失败！请刷新页面！");
        }
    }

    /**
     * 获取验证码失效图片
     *
     * @return
     */
    public static byte[] getCheckImg(){
        try {
            return Files.readAllBytes(Path.of(LocalResourceConstant.LOCAL_IMG_URL,LocalResourceConstant.LOCAL_CHECK_ERROR_IMG));
        } catch (IOException e) {
            throw new BizException("获取图片失败！请刷新页面！");
        }
    }
}
