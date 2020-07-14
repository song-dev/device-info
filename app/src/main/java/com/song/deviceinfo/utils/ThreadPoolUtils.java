package com.song.deviceinfo.utils;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by chensongsong on 2020/5/20.
 */
public class ThreadPoolUtils {
    private static volatile ThreadPoolUtils singleton;
    private static volatile ExecutorService fixedThreadPool;
    private static volatile ScheduledExecutorService executorService;

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
                    fixedThreadPool = Executors.newFixedThreadPool(3, threadFactory);
                    executorService = Executors.newScheduledThreadPool(3, threadFactory);
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
                fixedThreadPool.execute(runnable);
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
                return fixedThreadPool.submit(task);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 执行计时任务
     */
    public void executeScheduled(Runnable runnable, long initialDelay, long period, TimeUnit unit) {
        try {
            if (runnable != null) {
                executorService.scheduleAtFixedRate(runnable, initialDelay, period, unit);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
