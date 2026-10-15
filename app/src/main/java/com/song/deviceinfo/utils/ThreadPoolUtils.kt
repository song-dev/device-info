package com.song.deviceinfo.utils

import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.ThreadFactory
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger
import kotlin.concurrent.Volatile

/**
 * Created by chensongsong on 2020/5/20.
 */
object ThreadPoolUtils {
    private val threadFactory = ThreadFactory { r ->
        Thread(r, "Device_Info_Task #${threadCount.getAndIncrement()}")
    }

    private val threadCount = AtomicInteger(1)
    private val fixedThreadPool = Executors.newFixedThreadPool(3, threadFactory)
    private val scheduledThreadPool = Executors.newScheduledThreadPool(3, threadFactory)

    /**
     * 开启runnable
     *
     * @param runnable
     */
    fun execute(runnable: Runnable) {
        try {
            fixedThreadPool.execute(runnable)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    
    /**
     * 同步获取通知
     *
     * @param task
     * @param <T>
     * @return
    </T> */
    fun <T> submit(task: Callable<T>): Future<T>? {
        return try {
            fixedThreadPool.submit(task)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
    
    /**
     * 执行计时任务
     */
    fun executeScheduled(runnable: Runnable, initialDelay: Long, period: Long, unit: TimeUnit) {
        try {
            scheduledThreadPool.scheduleAtFixedRate(runnable, initialDelay, period, unit)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
