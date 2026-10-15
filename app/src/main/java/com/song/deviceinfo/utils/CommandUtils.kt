package com.song.deviceinfo.utils

import android.annotation.SuppressLint
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.DataOutputStream
import java.io.IOException
import java.io.InputStream
import java.util.Scanner

/**
 * Created by chensongsong on 2020/6/1.
 */
object CommandUtils {
    @JvmStatic
    @SuppressLint("PrivateApi")
    fun getProperty(propName: String?): String? {
        val roSecureObj: Any?
        try {
            roSecureObj = Class.forName("android.os.SystemProperties")
                    .getMethod("get", String::class.java)
                    .invoke(null, propName)
            if (roSecureObj != null) {
                return roSecureObj as String
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
    
    @JvmStatic
    fun exec(command: String?): Array<String> {
        var inputstream: InputStream? = null
        var allPaths = ""
        try {
            inputstream = Runtime.getRuntime().exec(command).inputStream
            allPaths = Scanner(inputstream).useDelimiter("\\A").next()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return allPaths.split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
    }
    
    @JvmStatic
    fun execute(command: String): String? {
        var bufferedOutputStream: BufferedOutputStream? = null
        var bufferedInputStream: BufferedInputStream? = null
        var process: Process? = null
        try {
            process = Runtime.getRuntime().exec("sh")
            bufferedOutputStream = BufferedOutputStream(process.outputStream)
            bufferedInputStream = BufferedInputStream(process.inputStream)
            bufferedOutputStream.write(command.toByteArray())
            bufferedOutputStream.write('\n'.code)
            bufferedOutputStream.flush()
            bufferedOutputStream.close()
            process.waitFor()
            val outputStr = getStrFromBufferInputSteam(bufferedInputStream)
            if (outputStr[outputStr.length - 1] == '\n') {
                return outputStr.substring(0, outputStr.length - 1)
            }
            return outputStr
        } catch (e: Exception) {
            return null
        } finally {
            if (bufferedOutputStream != null) {
                try {
                    bufferedOutputStream.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            if (bufferedInputStream != null) {
                try {
                    bufferedInputStream.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            process?.destroy()
        }
    }
    
    private fun getStrFromBufferInputSteam(bufferedInputStream: BufferedInputStream?): String {
        if (null == bufferedInputStream) {
            return ""
        }
        val bufferSize = 512
        val buffer = ByteArray(bufferSize)
        val result = StringBuilder()
        try {
            while (true) {
                val read = bufferedInputStream.read(buffer)
                if (read > 0) {
                    result.append(String(buffer, 0, read))
                }
                if (read < bufferSize) {
                    break
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return result.toString()
    }
    
    fun rootCommand(command: String): Boolean {
        var process: Process? = null
        var os: DataOutputStream? = null
        try {
            process = Runtime.getRuntime().exec("su")
            os = DataOutputStream(process.outputStream)
            os.writeBytes(command + "\n")
            os.writeBytes("exit\n")
            os.flush()
            process.waitFor()
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        } finally {
            try {
                os?.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return true
    }
    
    @JvmStatic val uidStrFormat: String?
        get() {
            try {
                var filter = execute("cat /proc/self/cgroup")
                if (filter == null || filter.length == 0) {
                    return null
                }
                
                val uidStartIndex = filter.lastIndexOf("uid")
                var uidEndIndex = filter.lastIndexOf("/pid")
                if (uidStartIndex < 0) {
                    return null
                }
                if (uidEndIndex <= 0) {
                    uidEndIndex = filter.length
                }
                
                filter = filter.substring(uidStartIndex + 4, uidEndIndex)
                val strUid = filter.replace("\n".toRegex(), "")
                if (isNumber(strUid)) {
                    val uid = strUid.toInt()
                    filter = String.format("u0_a%d", uid - 10000)
                    return filter
                }
                return null
            } catch (e: Exception) {
                e.printStackTrace()
                return null
            }
        }
    
    private fun isNumber(str: String?): Boolean {
        if (str == null || str.length == 0) {
            return false
        }
        for (i in 0 until str.length) {
            if (!Character.isDigit(str[i])) {
                return false
            }
        }
        return true
    }
}
