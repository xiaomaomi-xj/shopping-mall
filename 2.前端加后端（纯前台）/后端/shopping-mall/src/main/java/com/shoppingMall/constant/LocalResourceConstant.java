package com.shoppingMall.constant;

/**
 * 本地图片资源常量类
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/18
 */
public class LocalResourceConstant {
    /**
     * 本地资源路径（需要注意的是，这个路径并不是web访问的路径，会出现上传图片不更新的情况）
     */
    public static final String LOCAL_IMG_URL = "src/main/resources/static";

    /**
     * 本地防盗链图片
     */
    public static final String LOCAL_ANTI_THEFT_CHAIN_IMG = "error.png";

    /**
     * 二维码错误图片
     */
    public static final String LOCAL_QR_ERROR_IMG = "qr_error.png";

    /**
     * 验证码错误图片
     */
    public static final String LOCAL_CHECK_ERROR_IMG = "check_error.png";
}
