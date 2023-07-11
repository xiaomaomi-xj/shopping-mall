package com.shoppingMall.entity.vo;

/**
 * 用于接受一个参数redis id的时候使用
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/5
 */
public class IdVo {
    /**
     * redis存储的id
     */
    private String id;

    public IdVo() {

    }

    public IdVo(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "IdVo{" +
                "id='" + id + '\'' +
                '}';
    }
}
