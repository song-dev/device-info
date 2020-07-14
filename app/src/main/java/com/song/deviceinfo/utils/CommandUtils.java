package com.song.deviceinfo.utils;

import android.annotation.SuppressLint;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;


/**
 * Created by chensongsong on 2020/6/1.
 */
public class CommandUtils {

    @SuppressLint("PrivateApi")
    public static String getProperty(String propName) {
        Object roSecureObj;
        try {
            roSecureObj = Class.forName("android.os.SystemProperties")
                    .getMethod("get", String.class)
                    .invoke(null, propName);
            if (roSecureObj != null) {
                return (String) roSecureObj;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String[] exec(String command) {
        InputStream inputstream = null;
        String allPaths = "";
        try {
            inputstream = Runtime.getRuntime().exec(command).getInputStream();
            allPaths = new Scanner(inputstream).useDelimiter("\\A").next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allPaths.split("\n");
    }

    public static String execute(String command) {
        BufferedOutputStream bufferedOutputStream = null;
        BufferedInputStream bufferedInputStream = null;
        Process process = null;
        try {
            process = Runtime.getRuntime().exec("sh");
            bufferedOutputStream = new BufferedOutputStream(process.getOutputStream());
            bufferedInputStream = new BufferedInputStream(process.getInputStream());
            bufferedOutputStream.write(command.getBytes());
            bufferedOutputStream.write('\n');
            bufferedOutputStream.flush();
            bufferedOutputStream.close();
            process.waitFor();
            String outputStr = getStrFromBufferInputSteam(bufferedInputStream);
            if (outputStr.charAt(outputStr.length() - 1) == '\n') {
                return outputStr.substring(0, outputStr.length() - 1);
            }
            return outputStr;
        } catch (Exception e) {
            return null;
        } finally {
            if (bufferedOutputStream != null) {
                try {
                    bufferedOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bufferedInputStream != null) {
                try {
                    bufferedInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (process != null) {
                process.destroy();
            }
        }
    }

    private static String getStrFromBufferInputSteam(BufferedInputStream bufferedInputStream) {
        if (null == bufferedInputStream) {
            return "";
        }
        int bufferSize = 512;
        byte[] buffer = new byte[bufferSize];
        StringBuilder result = new StringBuilder();
        try {
            while (true) {
                int read = bufferedInputStream.read(buffer);
                if (read > 0) {
                    result.append(new String(buffer, 0, read));
                }
                if (read < bufferSize) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    public static boolean rootCommand(String command) {
        Process process = null;
        DataOutputStream os = null;
        try {
            process = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(process.getOutputStream());
            os.writeBytes(command + "\n");
            os.writeBytes("exit\n");
            os.flush();
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public static String getUidStrFormat() {
        try {
            String filter = execute("cat /proc/self/cgroup");
            if (filter == null || filter.length() == 0) {
                return null;
            }

            int uidStartIndex = filter.lastIndexOf("uid");
            int uidEndIndex = filter.lastIndexOf("/pid");
            if (uidStartIndex < 0) {
                return null;
            }
            if (uidEndIndex <= 0) {
                uidEndIndex = filter.length();
            }

            filter = filter.substring(uidStartIndex + 4, uidEndIndex);
            String strUid = filter.replaceAll("\n", "");
            if (isNumber(strUid)) {
                int uid = Integer.valueOf(strUid);
                filter = String.format("u0_a%d", uid - 10000);
                return filter;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static boolean isNumber(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
