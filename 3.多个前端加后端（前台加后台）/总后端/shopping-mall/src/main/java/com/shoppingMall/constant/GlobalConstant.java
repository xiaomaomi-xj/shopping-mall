package com.shoppingMall.constant;

/**
 * 用于一些特别表示的常量
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/2/28
 */
public class GlobalConstant {
    /**
     * 消息发送人身份，用户
     */
    public static final int USER = 1;
    /**
     * 消息发送人身份，老板
     */
    public static final int BOOS = 0;

    /**
     * 消息状态，1，未读
     */
    public static final int UN_READ = 1;

    /**
     * 消息状态，0，已读
     */
    public static final int READ = 0;

    /**
     * 性别，1，男
     */
    public static final int MAN = 1;

    /**
     * 性别，0，女
     */
    public static final int GIRL = 0;

    /**
     * 运送状态，1，未发货
     */
    public static final int UN_DELIVERY = 1;

    /**
     * 运送状态，2，运送中
     */
    public static final int IN_DELIVERY = 2;

    /**
     * 运送状态，3，已送达
     */
    public static final int END_DELIVERY = 3;

    /**
     * 模糊查询的时候使用
     */
    public static final String HOLDER = "%";

    /**
     * 图片路径
     */
    public static final String IMG_BASE_URL = "http://localhost:8088/shopping-mall/img/";

    /**
     * 防盗链检查的信息头字段
     */
    public static final String CHECK_FIELD = "Referer";

    /**
     * 验证图片正则
     */
    public static final String CHECK_IMG_PATTERN = "/img/.+\\.\\w+";

    /**
     * 前端地址
     */
    public static final String FORE_END_URL = "http://127.0.0.1:80";

    /**
     * 前端本地地址
     */
    public static final String FORE_END_LOCAL_URL = "http://localhost";

    /**
     * 后端地址
     */
    public static final String AFTER_END_URL="http://127.0.0.1:5500";

    /**
     * 后端本地地址
     */
    public static final String AFTER_END_LOCAL_URL="http://localhost:5500";

    /**
     * 前端主页地址
     */
    public static final String FORE_END_LOCAL_HOME_URL = "http://localhost/shopping-mall";

    /**
     * 用于随机生成的邮箱验证码模板
     */
    public static final String CODE_TEMPLATE = "0123456789ABCDEFGHICKLMNOPQRSTUVWXYZ";

    /**
     * 回调地址
     */
    public static final String REDIRECT_URL = "/shopping-mall/wechat/get_wechat_code";

    /**
     * 未登录
     */
    public static final String NO_LOGIN = "NO_LOGIN";

    /**
     * BASE64转码后的倍数
     */
    public static final float BASE64_CONVERT_SIZE_TIMES = 1.3F;

    /**
     * 组合式商品名字
     */
    public static final String COMPOSE_GOODS_NAME = "【组合式商品】（在购物车购买的商品不显示具体名字）";
}
