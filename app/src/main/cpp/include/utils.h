//
// Created by 陈颂颂 on 2019/12/27.
//

#ifndef SECURITY_CHECK_ANDROID_UTILS_H
#define SECURITY_CHECK_ANDROID_UTILS_H

#include <zconf.h>
#include <jni.h>

#define BUF_SIZE_32 32
#define BUF_SIZE_64 64
#define BUF_SIZE_128 128
#define BUF_SIZE_256 256
#define BUF_SIZE_512 512
#define BUF_SIZE_1024 1024

#define UNKNOWN "$unknown"

/**
 * path: 路径
 * 返回值 1:文件存在; 0:文件不存在
 */
int exists(const char *path);

int getProperty(const char *name, char *dest);

int shellExecute(const char *cmdStr, char *dest, int len);

int errorCatch(JNIEnv *env);

jstring getPackageName(JNIEnv *env, jobject context);

#endif //SECURITY_CHECK_ANDROID_UTILS_H
