package com.shoppingMall.utils;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池工具类
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/3
 */
public class ThreadPoolUtil {
    /**
     * io密集型,非主要线程1分钟不用死亡
     */
    private static final ThreadPoolExecutor THREAD_POOL = new ThreadPoolExecutor(
            Runtime.getRuntime().availableProcessors() * 5,
            Runtime.getRuntime().availableProcessors() * 5 + 10,
            60L,
            TimeUnit.SECONDS,
            new LinkedBlockingDeque<>(3),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.DiscardOldestPolicy()
    );

    /**
     * 执行任务
     *
     * @param runnable
     */
    public static void execute(Runnable runnable){
        THREAD_POOL.submit(runnable);
    }

    /**
     * 关闭线程池
     */
    public static void shutdown(){
        THREAD_POOL.shutdown();
    }


}
