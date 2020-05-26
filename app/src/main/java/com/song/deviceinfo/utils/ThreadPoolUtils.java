package com.song.deviceinfo.utils;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by chensongsong on 2020/5/20.
 */
public class ThreadPoolUtils {
    private static volatile ThreadPoolUtils singleton;
    private static volatile ExecutorService scheduledThreadPool;

    private static final ThreadFactory threadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        public Thread newThread(Runnable r) {
            return new Thread(r, "Device_Info_Task #" + mCount.getAndIncrement());
        }
    };

    /**
     * 初始化线程池
     *
     * @return
     */
    public static ThreadPoolUtils getInstance() {
        if (singleton == null) {
            synchronized (ThreadPoolUtils.class) {
                if (singleton == null) {
                    singleton = new ThreadPoolUtils();
                    scheduledThreadPool = Executors.newFixedThreadPool(3, threadFactory);
                }
            }
        }
        return singleton;
    }

    /**
     * 开启runnable
     *
     * @param runnable
     */
    public void execute(Runnable runnable) {
        try {
            if (runnable != null) {
                scheduledThreadPool.execute(runnable);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 同步获取通知
     *
     * @param task
     * @param <T>
     * @return
     */
    public <T> Future<T> submit(Callable<T> task) {
        try {
            if (task != null) {
                return scheduledThreadPool.submit(task);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
