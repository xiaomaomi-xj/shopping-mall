package com.shoppingMall.properties;

import com.shoppingMall.entity.po.self.SelfConfig;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * 配置类
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/3
 */
public class SelfConfigPropertiesBean {
    @NestedConfigurationProperty
    private SelfConfig config;

    public SelfConfigPropertiesBean(){

    }

    public SelfConfig getConfig() {
        return config;
    }

    public void setConfig(SelfConfig config) {
        this.config = config;
    }
}
