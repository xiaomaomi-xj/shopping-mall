package com.shoppingMall.config;

import com.shoppingMall.constant.GlobalConstant;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置跨域
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/2
 */
@Configuration
public class MyCorsConfiguration implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(GlobalConstant.FORE_END_URL, GlobalConstant.FORE_END_LOCAL_URL)
                .allowedMethods(HttpMethod.GET.name(), HttpMethod.POST.name())
                .allowedHeaders("*")
                .maxAge(1800)
                .exposedHeaders("*");
    }
}
