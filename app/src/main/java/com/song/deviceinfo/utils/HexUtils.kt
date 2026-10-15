package com.song.deviceinfo.utils

object HexUtils {
    private val HEX = charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F')
    
    /**
     * 将 byte 数组转换为十六进制字符串
     *
     * @param bytes
     * @return
     */
    fun bytesToHex(bytes: ByteArray): String {
        val sb = StringBuilder()
        for (i in bytes.indices) {
            sb.append(HEX[(bytes[i].toInt() shr 4) and 0x0f]).append(HEX[bytes[i].toInt() and 0x0f])
        }
        return sb.toString()
    }
    
    /**
     * 将十六进制字符串转换为 byte 数组
     *
     * @param content
     * @return
     */
    fun hexToBytes(content: String): ByteArray {
        val chars = content.toCharArray()
        val length = chars.size / 2
        val raw = ByteArray(length)
        for (i in 0 until length) {
            val high = chars[i * 2].digitToIntOrNull(16) ?: -1
            val low = chars[i * 2 + 1].digitToIntOrNull(16) ?: -1
            var value = (high shl 4) or low
            if (value > 127) {
                value -= 256
            }
            raw[i] = value.toByte()
        }
        return raw
    }
}
