package com.song.deviceinfo.utils

import java.security.MessageDigest

object HashUtils {
    /**
     * md5摘要
     *
     * @param content
     * @return
     */
    @JvmStatic
    fun md5Encode(content: ByteArray): String? {
        try {
            return HexUtils.bytesToHex(digest(content, "MD5"))
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
    
    fun sha1Encode(content: ByteArray): String? {
        try {
            return HexUtils.bytesToHex(digest(content, "SHA-1"))
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
    
    /**
     * SHA256摘要处理
     *
     * @param content 内容
     * @return 哈希摘要字符串
     */
    fun sha256Encode(content: ByteArray): String? {
        try {
            return HexUtils.bytesToHex(digest(content, "SHA-256"))
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
    
    /**
     * hash 计算
     *
     * @param content   内容
     * @param algorithm 算法
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    private fun digest(content: ByteArray, algorithm: String): ByteArray {
        // 将此 algorithm 换成MD5、SHA-1、SHA-224、SHA-256、SHA-384、SHA-512等参数
        val messageDigest = MessageDigest.getInstance(algorithm)
        messageDigest.update(content)
        return messageDigest.digest()
    }
}
