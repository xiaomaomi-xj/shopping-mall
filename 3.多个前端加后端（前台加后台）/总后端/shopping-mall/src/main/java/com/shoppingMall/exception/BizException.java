package com.shoppingMall.exception;

/**
 * 业务级异常
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/1
 */
public class BizException extends RuntimeException {
    private static final long serialVersionUID = -9047656242091496204L;

    public BizException(){
        super();
    }

    public BizException(String errorMessage){
        super(errorMessage);
    }
}
