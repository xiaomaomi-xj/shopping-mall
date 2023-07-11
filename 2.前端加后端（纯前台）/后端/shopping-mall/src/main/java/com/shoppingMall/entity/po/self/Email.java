package com.shoppingMall.entity.po.self;

/**
 * 邮箱参数实体类
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/3
 */
public class Email {
    /**
     * 邮箱账号
     */
    private String fromEmail;

    /**
     * 邮箱授权码
     */
    private String emailPassword;

    /**
     * 邮箱地址
     */
    private String host;

    /**
     * 邮箱端口
     */
    private String port;

    public Email(){

    }

    public Email(String fromEmail, String emailPassword, String host, String port) {
        this.fromEmail = fromEmail;
        this.emailPassword = emailPassword;
        this.host = host;
        this.port = port;
    }

    public String getFromEmail() {
        return fromEmail;
    }

    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }

    public String getEmailPassword() {
        return emailPassword;
    }

    public void setEmailPassword(String emailPassword) {
        this.emailPassword = emailPassword;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "Email{" +
                "fromEmail='" + fromEmail + '\'' +
                ", emailPassword='" + emailPassword + '\'' +
                ", host='" + host + '\'' +
                ", port='" + port + '\'' +
                '}';
    }
}
