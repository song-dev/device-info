package com.song.deviceinfo.utils

import android.annotation.SuppressLint
import android.app.usage.StorageStatsManager
import android.content.Context
import android.os.Build
import android.os.Environment
import android.os.StatFs
import android.os.storage.StorageManager
import android.os.storage.StorageVolume
import android.text.format.Formatter
import com.song.deviceinfo.model.beans.StorageBean
import java.io.File
import java.io.IOException
import java.lang.reflect.Method
import java.util.Locale
import java.util.UUID

/**
 * Created by chensongsong on 2020/6/1.
 */
object SdUtils {
    @JvmStatic val isMounted: Boolean
        get() = Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()
    
    val dirPath: String
        get() = Environment.getExternalStorageDirectory().absolutePath
    
    /**
     * 获取 sd 卡存储信息
     *
     * @param context
     * @param bean
     */
    @JvmStatic
    fun getStoreInfo(context: Context, bean: StorageBean) {
        val card = Environment.getExternalStorageDirectory()
        bean.storePath = card.absolutePath
        val totalSpace = card.totalSpace
        val freeSpace = card.freeSpace
        val usableSpace = totalSpace - freeSpace
        val total = Formatter.formatFileSize(context, totalSpace)
        val usable = Formatter.formatFileSize(context, usableSpace)
        val free = Formatter.formatFileSize(context, freeSpace)
        bean.totalStore = total
        bean.freeStore = free
        bean.usedStore = usable
        val ratio = ((usableSpace / totalSpace.toDouble()) * 100).toInt()
        bean.ratioStore = ratio
        bean.romSize = getRealStorage(context)
    }
    
    /**
     * 获取 ROM 空间大小
     *
     * @param context
     * @return
     */
    private fun getRomTotal(context: Context): String {
        val path = Environment.getDataDirectory()
        val stat = StatFs(path.path)
        val blockSize = stat.blockSize.toLong()
        val totalBlocks = stat.blockCount.toLong()
        return Formatter.formatFileSize(context, totalBlocks * blockSize)
    }
    
    @SuppressLint("DiscouragedPrivateApi")
    fun getRealStorage(context: Context): String? {
        var total = 0L
        try {
            val storageManager = context.getSystemService(Context.STORAGE_SERVICE) as StorageManager
            val version = Build.VERSION.SDK_INT
            val unit = (if (version >= Build.VERSION_CODES.O) 1000 else 1024).toFloat()
            if (version < Build.VERSION_CODES.M) {
                val getVolumeList = StorageManager::class.java.getDeclaredMethod("getVolumeList")
                val volumeList = getVolumeList.invoke(storageManager) as Array<StorageVolume>
                if (volumeList != null) {
                    var getPathFile: Method? = null
                    for (volume in volumeList) {
                        if (getPathFile == null) {
                            getPathFile = volume.javaClass.getDeclaredMethod("getPathFile")
                        }
                        val file = getPathFile!!.invoke(volume) as File
                        total += file.totalSpace
                    }
                }
            } else {
                @SuppressLint("PrivateApi") val getVolumes = StorageManager::class.java.getDeclaredMethod("getVolumes")
                val getVolumeInfo = getVolumes.invoke(storageManager) as List<Any>
                for (obj in getVolumeInfo) {
                    val getType = obj.javaClass.getField("type")
                    val type = getType.getInt(obj)
                    if (type == 1) {
                        var totalSize = 0L
                        if (version >= Build.VERSION_CODES.O) {
                            val getFsUuid = obj.javaClass.getDeclaredMethod("getFsUuid")
                            val fsUuid = getFsUuid.invoke(obj) as String
                            totalSize = getTotalSize(context, fsUuid)
                        } else if (version >= Build.VERSION_CODES.N_MR1) {
                            val getPrimaryStorageSize = StorageManager::class.java.getMethod("getPrimaryStorageSize")
                            totalSize = getPrimaryStorageSize.invoke(storageManager) as Long
                        }
                        val isMountedReadable = obj.javaClass.getDeclaredMethod("isMountedReadable")
                        val readable = isMountedReadable.invoke(obj) as Boolean
                        if (readable) {
                            val file = obj.javaClass.getDeclaredMethod("getPath")
                            val f = file.invoke(obj) as File
                            if (totalSize == 0L) {
                                totalSize = f.totalSpace
                            }
                            total += totalSize
                        }
                    } else if (type == 0) {
                        val isMountedReadable = obj.javaClass.getDeclaredMethod("isMountedReadable")
                        val readable = isMountedReadable.invoke(obj) as Boolean
                        if (readable) {
                            val file = obj.javaClass.getDeclaredMethod("getPath")
                            val f = file.invoke(obj) as File
                            total += f.totalSpace
                        }
                    }
                }
            }
            return getUnit(total.toFloat(), unit)
        } catch (ignore: Exception) {
        }
        return null
    }
    
    private val units = arrayOf("B", "KB", "MB", "GB", "TB")
    
    /**
     * 进制转换
     */
    private fun getUnit(size: Float, base: Float): String {
        var size = size
        var index = 0
        while (size > base && index < 4) {
            size = size / base
            index++
        }
        return String.format(Locale.getDefault(), "%.2f %s ", size, units[index])
    }
    
    /**
     * API 26 android O
     * 获取总共容量大小，包括系统大小
     */
    @SuppressLint("NewApi")
    private fun getTotalSize(context: Context, fsUuid: String?): Long {
        try {
            val id = if (fsUuid == null) {
                StorageManager.UUID_DEFAULT
            } else {
                UUID.fromString(fsUuid)
            }
            val stats = context.getSystemService(StorageStatsManager::class.java)
            return stats.getTotalBytes(id)
        } catch (e: NoSuchFieldError) {
            e.printStackTrace()
            return -1
        } catch (e: NoClassDefFoundError) {
            e.printStackTrace()
            return -1
        } catch (e: NullPointerException) {
            e.printStackTrace()
            return -1
        } catch (e: IOException) {
            e.printStackTrace()
            return -1
        }
    }
}
