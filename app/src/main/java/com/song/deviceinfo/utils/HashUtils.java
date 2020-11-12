package com.song.deviceinfo.utils;

import java.security.MessageDigest;

public class HashUtils {

    /**
     * md5摘要
     *
     * @param content
     * @return
     */
    public static String md5Encode(byte[] content) {
        try {
            return HexUtils.bytesToHex(digest(content, "MD5"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String sha1Encode(byte[] content) {
        try {
            return HexUtils.bytesToHex(digest(content, "SHA-1"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * SHA256摘要处理
     *
     * @param content 内容
     * @return 哈希摘要字符串
     */
    public static String sha256Encode(byte[] content) {
        try {
            return HexUtils.bytesToHex(digest(content, "SHA-256"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * hash 计算
     *
     * @param content   内容
     * @param algorithm 算法
     * @return
     * @throws Exception
     */
    private static byte[] digest(byte[] content, String algorithm) throws Exception {
        // 将此 algorithm 换成MD5、SHA-1、SHA-224、SHA-256、SHA-384、SHA-512等参数
        MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
        messageDigest.update(content);
        return messageDigest.digest();
    }

}
