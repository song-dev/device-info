package com.song.deviceinfo.utils;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by chensongsong on 2020/6/1.
 */
public class LogUtils {

    private static final int VERBOSE = 1;
    private static final int DEBUG = 2;
    private static final int INFO = 3;
    private static final int WARN = 4;
    private static final int ERROR = 5;
    private static int LEVEL = VERBOSE;
    private static LogUtils.Logger logger = null;
    private static String TAG = Constants.TAG;
    private final static int PRINT_SIZE = 3800;

    public static void init(int level, String tag) {
        LEVEL = level;
        TAG = tag;
    }

    public static void v(String msg) {
        if (LEVEL <= VERBOSE) {
            Log.v(TAG, msg);
            log2sd(TAG, msg);
        }
    }

    public static void d(String msg) {
        if (LEVEL <= DEBUG) {
            Log.d(TAG, msg);
            log2sd(TAG, msg);
        }
    }

    public static void i(String msg) {
        if (LEVEL <= INFO) {
            Log.i(TAG, msg);
            log2sd(TAG, msg);
        }
    }

    public static void w(String msg) {
        if (LEVEL <= WARN) {
            Log.w(TAG, msg);
            log2sd(TAG, msg);
        }
    }

    public static void e(String msg) {
        if (LEVEL <= ERROR) {
            Log.e(TAG, msg);
            log2sd(TAG, msg);
        }
    }

    public static void v(String tag, String msg) {
        if (LEVEL <= VERBOSE) {
            Log.v(tag, msg);
            log2sd(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (LEVEL <= DEBUG) {
            Log.d(tag, msg);
            log2sd(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (LEVEL <= INFO) {
            Log.i(tag, msg);
            log2sd(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (LEVEL <= WARN) {
            Log.w(tag, msg);
            log2sd(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (LEVEL <= ERROR) {
            Log.e(tag, msg);
            log2sd(tag, msg);
        }
    }

    public static void release(String msg) {
        Log.i(TAG, msg);
        log2sd(TAG, msg);
    }

    /**
     * 销毁
     */
    public static void destroy() {
        if (logger != null) {
            logger.destroy();
        }
    }

    private static void log2sd(String tag, String msg) {
        if (logger == null) {
            logger = new LogUtils.Logger();
            logger.init();
            logger.checkLogFile();
        }
        logger.log(tag, msg);
    }

    private static class Logger {
        private HandlerThread thread;
        private Handler handler;
        private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);

        private static final String FILE_NAME = "device_info_log.txt";
        private static final String EXTERNAL_DIR = "song";
        private static final int WHAT_MSG = 0;
        private static final int WHAT_INIT = 1;
        private static final long MAX_FILE_SIZE = 10 * 1024 * 1024; // 10m  10 * 1024 * 1024

        public Logger() {
        }

        public synchronized void init() {
            thread = new HandlerThread("Device Info Thread");
            thread.start();
            handler = new Handler(thread.getLooper()) {

                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    if (Thread.interrupted()) {
                        return;
                    }
                    if (!SdUtils.isMounted()) {
                        return;
                    }
                    if (msg.what == WHAT_MSG) {
                        LogUtils.Logger.Item item = (LogUtils.Logger.Item) msg.obj;
                        write(build(sdf, item.millis, item.tag, item.message));
                    } else if (msg.what == WHAT_INIT) {
                        deleteCauseExceedMaxSize();
                    }
                }
            };
        }

        private static class Item {
            public long millis;
            public String tag;
            public String message;
        }

        public synchronized void log(String tag, String msg) {

            Message message = handler.obtainMessage();
            message.what = WHAT_MSG;

            LogUtils.Logger.Item item = new LogUtils.Logger.Item();
            item.millis = System.currentTimeMillis();
            item.tag = tag;
            item.message = msg;
            message.obj = item;

            handler.sendMessage(message);
        }

        public synchronized void checkLogFile() {
            Message message = handler.obtainMessage();
            message.what = WHAT_INIT;
            handler.sendMessage(message);
        }

        public synchronized void destroy() {
            if (thread != null && handler != null) {
                handler.removeMessages(WHAT_MSG);
                handler.removeMessages(WHAT_INIT);

                thread.quit();
                thread = null;
            }
        }

        private static String externalDirPath() {
            return SdUtils.getDirPath() + File.separator + EXTERNAL_DIR;
        }

        private static boolean deleteCauseExceedMaxSize() {
            File dir = new File(externalDirPath());
            if (!dir.exists()) {
                return false;
            }
            File f = new File(dir, FILE_NAME);
            if (!f.exists()) {
                return false;
            }
            if (f.length() < MAX_FILE_SIZE) {
                return false;
            }
            return f.delete();
        }

        private void write(String content) {

            File dir = new File(externalDirPath());
            if (!dir.exists()) {
                dir.mkdirs();
            }

            BufferedOutputStream out = null;
            try {
                out = new BufferedOutputStream(new FileOutputStream(new File(dir, FILE_NAME), true));
                out.write(content.getBytes("utf-8"));
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                    }
                }
            }
        }

        private static String build(SimpleDateFormat sdf, long millis, String tag, String msg) {
            StringBuilder sb = new StringBuilder();
            sb.append(sdf.format(new Date(millis)));
            sb.append('\t');
            sb.append(tag);
            sb.append('\n');
            sb.append(msg);
            sb.append('\n');
            return sb.toString();
        }
    }

    /**
     * 打印超长字符串
     *
     * @param data
     */
    public static void printLongString(String tag, String data) {

        int len = data.length();
        if (len > PRINT_SIZE) {
            int n = 0;
            while ((len - n) > PRINT_SIZE) {
                String s = data.substring(n, PRINT_SIZE);
                Log.e(TAG, tag + s);
                n += PRINT_SIZE;
            }
            Log.e(TAG, tag + data.substring(n));
        } else {
            Log.e(TAG, tag + data);
        }
    }
}
