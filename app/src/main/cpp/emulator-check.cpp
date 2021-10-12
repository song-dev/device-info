//
// Created by 陈颂颂 on 2019/12/27.
//

#include <dirent.h>
#include <string.h>
#include <stdio.h>
#include "include/emulator-check.h"
#include "include/utils.h"
#include "include/log.h"

/**
 * 检测模拟器不存在的相关文件
 * @param env
 * @return
 */
jint specialFilesEmulatorcheck() {

    if (exists("/system/lib/libdroid4x.so") // 文卓爷
        || exists("/system/bin/windroyed") // 文卓爷
        || exists("/system/bin/microvirtd") // 逍遥
        || exists("/system/bin/nox-prop") // 夜神
        || exists("/system/bin/ttVM-prop") // 天天
        || exists("/system/lib/libc_malloc_debug_qemu.so")) {

        return 1;
    }
    return 0;

}

/**
 * 检测特殊目录，/sys/class/thermal/thermal_zoneX/temp(温度挂载文件)
 * @return 大于 0 为真机，等于 0 为模拟器
 */
jint thermalCheck() {

    //当前手机的温度检测，手机下均有thermal_zone文件
    DIR *dirptr = NULL;
    int i = 0;
    struct dirent *entry;

    if ((dirptr = opendir("/sys/class/thermal/")) != NULL) {
        while ((entry = readdir(dirptr))) {
            if (!strcmp(entry->d_name, ".") || !strcmp(entry->d_name, "..")) {
                continue;
            }
            char *tmp = entry->d_name;
            LOGI("thermal name: %s", tmp);
            if (strstr(tmp, "thermal_zone") != NULL) {
                i++;
                // 读取当前文件内容
                char buf[BUF_SIZE_128] = {0};
                char temperature[BUF_SIZE_64] = {0};
                sprintf(temperature, "cat /sys/class/thermal/%s/temp", tmp);
                shellExecute(temperature, buf, BUF_SIZE_128);
                char type[BUF_SIZE_64] = {0};
                sprintf(type, "cat /sys/class/thermal/%s/type", tmp);
                shellExecute(type, buf, BUF_SIZE_128);
            }
        }
        closedir(dirptr);
    } else {
        i = -1;
        LOGE("open thermal fail");
    }
    return i;

}

/**
 * build 文件检测示例
 * @return
 */
jboolean buildCheck() {
    char name[64] = "";
    getProperty("ro.product.name", name);
    if (strcmp(name, "ChangWan") == 0
        || strcmp(name, "Droid4X") == 0
        || strcmp(name, "lgshouyou") == 0
        || strcmp(name, "nox") == 0
        || strcmp(name, "ttVM_Hdragon") == 0) {
        return JNI_TRUE;
    }
    return JNI_FALSE;
}

/**
 * 蓝牙文件检测
 * @return
 */
jint bluetoothCheck() {
    if (!exists("/system/lib/libbluetooth_jni.so")) {
        return 1;
    }
    return 0;
}

/**
 * 获取当前
 * @return
 */
int getApiVersion() {
    return __ANDROID_API__;
}

/**
 * 获取当前架构类型，可能存在当前架构
 * @return
 */
int getArch() {

    int arch = 0;
#if defined(__x86_64__) || defined(__i386__)
    arch = 1;
#else
    arch = 0;
#endif
    return arch;

}

/**
 * 从 maps 读取当前运行的架构
 * @param dst
 * @return
 */
int getMapsArch(char *dst) {
    char path[BUF_SIZE_32];
    sprintf(path, "/proc/%d/maps", getpid());

    // 读取数据
    FILE *f = NULL;
    char buf[BUF_SIZE_512];
    f = fopen(path, "r");

    if (f != NULL) {
        while (fgets(buf, BUF_SIZE_512, f)) {
            // fgets 当读取 (n-1) 个字符时，或者读取到换行符时，或者到达文件末尾时，它会停止，具体视情况而定。
            LOGI("maps: %s", buf);
            if (strstr(buf, "libnative-lib.so")) {
                // 解析对应架构
                char *arch = strstr(buf, "/lib/");
                if (arch != NULL) {
                    strncpy(dst, arch, strlen(arch) - 1);
                }
                fclose(f);
                return 1;
            }
        }
    }

    fclose(f);
    return 0;
}