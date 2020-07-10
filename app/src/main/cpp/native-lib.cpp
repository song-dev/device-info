#include <jni.h>

#include "include/emulator-check.h"
#include "include/utils.h"
#include "include/log.h"

extern "C"
JNIEXPORT jint JNICALL
Java_com_song_deviceinfo_utils_EmulatorUtils_bluetoothCheck(JNIEnv *env, jclass thiz) {
    return bluetoothCheck();
}

extern "C"
JNIEXPORT jint JNICALL
Java_com_song_deviceinfo_utils_EmulatorUtils_getArch(JNIEnv *env, jclass thiz) {
    return getArch();
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_song_deviceinfo_utils_EmulatorUtils_getMapsArch(JNIEnv *env, jclass thiz) {
    char dst[BUF_SIZE_64] = UNKNOWN;
    getMapsArch(dst);
    return env->NewStringUTF(dst);
}

extern "C"
JNIEXPORT jint JNICALL
Java_com_song_deviceinfo_utils_EmulatorUtils_specialFilesEmulatorCheck(JNIEnv *env, jclass clazz) {
    return specialFilesEmulatorcheck();
}