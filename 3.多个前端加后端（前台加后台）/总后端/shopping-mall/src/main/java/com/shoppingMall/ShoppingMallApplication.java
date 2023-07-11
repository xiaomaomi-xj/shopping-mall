package com.shoppingMall;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import com.zhuyahui.annotation.EnableZyhDynamicDataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 启动类
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/2/24
 */
@SpringBootApplication
@EnableEncryptableProperties
@EnableZyhDynamicDataSource
@EnableCaching
public class ShoppingMallApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(ShoppingMallApplication.class, args);
        //这里调用关闭的时候，就会调用@PreDestroy的方法
//        run.close();
    }
}
