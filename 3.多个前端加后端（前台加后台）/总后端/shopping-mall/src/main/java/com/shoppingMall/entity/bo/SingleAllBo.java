package com.shoppingMall.entity.bo;

/**
 * 用于返回值只有一个类型是的包装类
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/3
 */
public class SingleAllBo<T> {
    private T value;

    public SingleAllBo(){

    }

    public SingleAllBo(T value){
        this.value=value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "SingleAllBo{" +
                "value=" + value +
                '}';
    }
}
