package com.shoppingMall;

import com.shoppingMall.utils.ThreadPoolUtil;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

/**
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/3
 */
@Component
public class ApplicationShutdown {
    @PreDestroy
    public void shutdown(){
        //关闭
        ThreadPoolUtil.shutdown();
    }
}
