package com.shoppingMall.config;

import com.ulisesbocchio.jasyptspringboot.EncryptablePropertyDetector;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置文件配置类
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/2/24
 */
@Configuration
public class JasyptConfiguration {
    /**
     * 配置文件加解密的前后缀
     */
    @Bean(name = "encryptablePropertyDetector")
    @ConditionalOnMissingBean
    public EncryptablePropertyDetector encryptablePropertyDetector() {
        return new EncryptablePropertyDetector(){
            @Override
            public boolean isEncrypted(String s) {
                return s.startsWith("###{configClassName}(") && s.endsWith(")");
            }

            @Override
            public String unwrapEncryptedValue(String s) {
                return s;
            }
        };
    }
}
