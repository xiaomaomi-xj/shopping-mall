package com.shoppingMall.entity.bo;

/**
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/4
 */
public class ExceptionBo {
    /**
     * 错误信息
     */
    private String errMessage;

    /**
     * 错误状态码
     */
    private int errStatus;

    public ExceptionBo() {

    }

    public ExceptionBo(String errMessage, int errStatus) {
        this.errMessage = errMessage;
        this.errStatus = errStatus;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }

    public int getErrStatus() {
        return errStatus;
    }

    public void setErrStatus(int errStatus) {
        this.errStatus = errStatus;
    }

    public String toJson() {
        return String.format("{\"errMessage\":\"%s\",\"errStatus\":\"%s\"}", errMessage, errStatus);
    }

    @Override
    public String toString() {
        return "ExceptionBo{" +
                "errMessage='" + errMessage + '\'' +
                ", errStatus='" + errStatus + '\'' +
                '}';
    }
}
