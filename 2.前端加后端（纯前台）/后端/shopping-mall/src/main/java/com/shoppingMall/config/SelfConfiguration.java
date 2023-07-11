package com.shoppingMall.config;

import com.shoppingMall.properties.SelfConfigPropertiesBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 对应配置文件中自己配置内容的配置类
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/3
 */
@Import({SelfConfigPropertiesBean.class})
@EnableConfigurationProperties
@Configuration
public class SelfConfiguration {
    @Bean
    @ConfigurationProperties(
            prefix = "self"
    )
    public SelfConfigPropertiesBean selfConfigPropertiesBean() {
        return new SelfConfigPropertiesBean();
    }
}
