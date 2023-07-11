package com.shoppingMall.admin.constan;

import com.shoppingMall.constant.GlobalConstant;

import java.util.List;

/**
 * 后台管理常量
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/4/7
 */
public class AdminConstant {
    /**
     * 后台管理检验token名字，信息头获取
     */
    public final static String ADMIN_TOKEN_KEY = "admin_token";

    /**
     * 刷新token的key
     */
    public final static String ADMIN_REFRESH_TOKEN_KEY = "admin_refresh_token";

    /**
     * 错误请求响应信息格式头
     */
    public final static String ERROR_RESPONSE_TYPE_KEY = "Content-type";

    /**
     * 错误请求响应信息格式值
     */
    public final static String ERROR_RESPONSE_TYPE_VALUE = "application/json";

    /**
     * 请求地址key
     */
    public final static String ORIGIN_KEY = "Origin";
    /**
     * 跨域地址
     */
    public final static String CROSS_DOMAIN_ORIGIN_KEY = "Access-Control-Allow-Origin";

    /**
     * 允许跨域的值
     */
    public final static List<String> CROSS_DOMAIN_ORIGIN_VALUES = List.of(GlobalConstant.AFTER_END_LOCAL_URL, GlobalConstant.AFTER_END_URL);

    /**
     * 跨域请求头
     */
    public final static String CROSS_DOMAIN_HEADERS_KEY = "Access-Control-Allow-Headers";

    /**
     * 常见允许通过的请求头
     */
    public final static String ALLOW_HEADERS = "Origin, X-Requested-With, Content-Type, Accept";

    /**
     * 跨域方法
     */
    public final static String CROSS_DOMAIN_METHOD_KEY = "Access-Control-Allow-Methods";

    /**
     * 跨域保存时长
     */
    public final static String CROSS_DOMAIN_AGE_KEY = "Access-Control-Max-Age";

    /**
     * 跨域保存时长值
     */
    public final static String CROSS_DOMAIN_AGE_VALUE = "1800";

    public final static String TOKEN_SEPARATOR = "&&&";
}
