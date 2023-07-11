package com.shoppingMall.utils;

/**
 * 检验是不是表情字符，只能大致判断
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/5/7
 */
public class ChatCheckUtil {
    /**
     * 表情字符
     */
    private static final int maxsize=55000;

    /**
     * 检验是不是表情字符
     *
     * @param str
     * @return
     */
    public static boolean check(String str){
        if(str.codePointAt(0) >= maxsize){
            return true;
        }
        return false;
    }
}
