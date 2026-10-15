package com.song.deviceinfo.utils

import android.content.Context
import android.os.Environment
import android.os.Handler
import android.os.HandlerThread
import android.os.Message
import android.util.Log
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Created by chensongsong on 2020/6/1.
 */
object LogUtils {
    private const val VERBOSE = 1
    private const val DEBUG = 2
    private const val INFO = 3
    private const val WARN = 4
    private const val ERROR = 5
    private var LEVEL = VERBOSE
    private var logger: Logger? = null
    private var TAG = Constants.TAG
    private const val PRINT_SIZE = 3800
    
    fun init(level: Int, tag: String) {
        LEVEL = level
        TAG = tag
    }
    
    fun v(msg: String) {
        if (LEVEL <= VERBOSE) {
            Log.v(TAG, msg)
            log2sd(TAG, msg)
        }
    }
    
    @JvmStatic
    fun d(msg: String) {
        if (LEVEL <= DEBUG) {
            Log.d(TAG, msg)
            log2sd(TAG, msg)
        }
    }
    
    fun i(msg: String) {
        if (LEVEL <= INFO) {
            Log.i(TAG, msg)
            log2sd(TAG, msg)
        }
    }
    
    fun w(msg: String) {
        if (LEVEL <= WARN) {
            Log.w(TAG, msg)
            log2sd(TAG, msg)
        }
    }
    
    @JvmStatic
    fun e(msg: String) {
        if (LEVEL <= ERROR) {
            Log.e(TAG, msg)
            log2sd(TAG, msg)
        }
    }
    
    fun v(tag: String, msg: String) {
        if (LEVEL <= VERBOSE) {
            Log.v(tag, msg)
            log2sd(tag, msg)
        }
    }
    
    fun d(tag: String, msg: String) {
        if (LEVEL <= DEBUG) {
            Log.d(tag, msg)
            log2sd(tag, msg)
        }
    }
    
    fun i(tag: String, msg: String) {
        if (LEVEL <= INFO) {
            Log.i(tag, msg)
            log2sd(tag, msg)
        }
    }
    
    fun w(tag: String, msg: String) {
        if (LEVEL <= WARN) {
            Log.w(tag, msg)
            log2sd(tag, msg)
        }
    }
    
    fun e(tag: String, msg: String) {
        if (LEVEL <= ERROR) {
            Log.e(tag, msg)
            log2sd(tag, msg)
        }
    }
    
    @JvmStatic
    fun release(msg: String) {
        Log.i(TAG, msg)
        log2sd(TAG, msg)
    }
    
    /**
     * 销毁
     */
    fun destroy() {
        logger?.destroy()
    }
    
    private fun log2sd(tag: String, msg: String) {
        logger?.log(tag, msg)
    }
    
    fun initLogger(context: Context) {
        if (logger == null) {
            logger = Logger()
            logger?.init(context)
            logger?.checkLogFile()
        }
    }
    
    /**
     * 打印超长字符串
     *
     * @param data
     */
    @JvmStatic
    fun printLongString(data: String) {
        val len = data.length
        if (len > PRINT_SIZE) {
            var n = 0
            while ((len - n) > PRINT_SIZE) {
                val s = data.substring(n, n + PRINT_SIZE)
                Log.i(TAG, s)
                n += PRINT_SIZE
            }
            Log.i(TAG, data.substring(n))
        } else {
            Log.i(TAG, data)
        }
    }
    
    private class Logger {
        private var thread: HandlerThread? = null
        private var handler: Handler? = null
        private val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA)
        private var context: Context? = null
        
        @Synchronized
        fun init(context: Context) {
            this.context = context.applicationContext
            thread = HandlerThread("Device Info Thread")
            thread?.start()
            handler = object : Handler(thread?.looper!!) {
                override fun handleMessage(msg: Message) {
                    super.handleMessage(msg)
                    if (Thread.interrupted()) {
                        return
                    }
                    if (msg.what == WHAT_MSG) {
                        val item = msg.obj as Item
                        write(build(sdf, item.millis, item.tag, item.message))
                    } else if (msg.what == WHAT_INIT) {
                        deleteCauseExceedMaxSize(context)
                    }
                }
            }
        }
        
        private class Item {
            var millis: Long = 0
            var tag: String? = null
            var message: String? = null
        }
        
        @Synchronized
        fun log(tag: String?, msg: String?) {
            val message = handler!!.obtainMessage()
            message.what = WHAT_MSG
            
            val item = Item()
            item.millis = System.currentTimeMillis()
            item.tag = tag
            item.message = msg
            message.obj = item
            
            handler!!.sendMessage(message)
        }
        
        @Synchronized
        fun checkLogFile() {
            val message = handler!!.obtainMessage()
            message.what = WHAT_INIT
            handler!!.sendMessage(message)
        }
        
        @Synchronized
        fun destroy() {
            if (thread != null && handler != null) {
                handler!!.removeMessages(WHAT_MSG)
                handler!!.removeMessages(WHAT_INIT)
                
                thread!!.quit()
                thread = null
            }
            context = null
        }
        
        fun write(content: String) {
            context?.let { ctx ->
                val dir = File(ctx.getExternalFilesDir(null), "logs")
                if (!dir.exists()) {
                    dir.mkdirs()
                }
                
                var out: BufferedOutputStream? = null
                try {
                    out = BufferedOutputStream(FileOutputStream(File(dir, FILE_NAME), true))
                    out.write(content.toByteArray(charset("utf-8")))
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    out?.close()
                }
            }
        }
        
        companion object {
            private const val FILE_NAME = "device_info_log.txt"
            private const val WHAT_MSG = 0
            private const val WHAT_INIT = 1
            private const val MAX_FILE_SIZE = 10L * 1024 * 1024 // 10MB
            
            private fun build(sdf: SimpleDateFormat, millis: Long, tag: String?, message: String?): String {
                return "${sdf.format(Date(millis))} $tag: $message\n"
            }
            
            private fun deleteCauseExceedMaxSize(context: Context) {
                context.let { ctx ->
                    val dir = File(ctx.getExternalFilesDir(null), "logs")
                    if (!dir.exists()) {
                        return
                    }
                    val file = File(dir, FILE_NAME)
                    if (file.exists() && file.length() > MAX_FILE_SIZE) {
                        file.delete()
                    }
                }
            }
        }
    }
}
