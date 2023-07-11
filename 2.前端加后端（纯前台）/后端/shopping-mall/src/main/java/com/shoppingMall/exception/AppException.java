package com.shoppingMall.exception;

/**
 * 系统级异常
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/13
 */
public class AppException extends RuntimeException{
    private static final long serialVersionUID = -7792717929508120451L;

    public AppException(){
        super();
    }

    public AppException(String message){
        super(message);
    }
}
