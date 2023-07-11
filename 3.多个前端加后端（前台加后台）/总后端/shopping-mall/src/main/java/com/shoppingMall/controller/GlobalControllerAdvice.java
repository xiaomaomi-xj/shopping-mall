package com.shoppingMall.controller;

import com.shoppingMall.entity.bo.ExceptionBo;
import com.shoppingMall.exception.AppException;
import com.shoppingMall.exception.BizException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/4
 */
@RestControllerAdvice
public class GlobalControllerAdvice {

    /**
     * 处理biz异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(BizException.class)
    public ExceptionBo handleBizException(BizException e){
        return new ExceptionBo(
                e.getMessage(),
                HttpStatus.BAD_REQUEST.value()
        );
    }

    /**
     * 处理app异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(AppException.class)
    public ExceptionBo handleAppException(AppException e){
        return new ExceptionBo(
                e.getMessage(),
                HttpStatus.FORBIDDEN.value()
        );
    }
}
