package com.shoppingMall.admin.annotation;

import java.lang.annotation.*;

/**
 * 用于标识那些接口需要验证,写类上代表了全部方法需要验证
 * 使用方式：如果一个类很多方法，只有一个方法不需要鉴权，就可以在方法上写上@CheckAdmin(isCancel=true) ,在类上写上@CheckAdmin，其他方法不写
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/4/7
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CheckAdmin {
    /**
     * true 取消验证，false，需要验证，默认需要验证
     */
    boolean isCancel() default false;
}
