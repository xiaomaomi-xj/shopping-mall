package com.shoppingMall.constant;

/**
 * 用于表示开关的枚举类
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/2/28
 */
public enum EnableConstantEnum {
    /**
     * 表示开启
     */
    ON(1),
    /**
     * 表示关闭
     */
    OFF(0);
    /**
     * 数据库真正存的值
     */
    private final int code;

    EnableConstantEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
