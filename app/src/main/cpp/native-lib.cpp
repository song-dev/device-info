#include <jni.h>

#include "include/utils.h"
#include "include/log.h"
#include "include/emulator-check.h"
#include "include/virtual-apk-check.h"
#include "include/debug-check.h"

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

extern "C"
JNIEXPORT jint JNICALL
Java_com_song_deviceinfo_utils_EmulatorUtils_thermalCheck(JNIEnv *env, jclass clazz) {
    return thermalCheck();
}

extern "C"
JNIEXPORT jint JNICALL
Java_com_song_deviceinfo_info_VirtualAppInfo_moreOpenCheck(JNIEnv *env, jclass clazz,
                                                           jobject context) {
    return moreOpenCheck(env, context);
}

extern "C"
JNIEXPORT jint JNICALL
Java_com_song_deviceinfo_utils_DebugUtils_getTracerPid(JNIEnv *env, jclass clazz) {
    return getTracerPid();
}