//
// Created by 陈颂颂 on 2019/12/27.
//

#ifdef __cplusplus
extern "C" {
#endif

#ifndef SECURITY_CHECK_ANDROID_LOG_H
#define SECURITY_CHECK_ANDROID_LOG_H

#include <android/log.h>

#if 1
#define TAG "Device_Info"
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, TAG, __VA_ARGS__)
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, TAG, __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, TAG, __VA_ARGS__)
#define LOGF(...) __android_log_print(ANDROID_LOG_FATAL, TAG ,__VA_ARGS__)
#define LOGW(...) __android_log_print(ANDROID_LOG_WARN, TAG ,__VA_ARGS__)
#else
#define LOGI(...)
#define LOGD(...)
#define LOGE(...)
#define LOGF(...)
#define LOGW(...)
#endif

#endif //SECURITY_CHECK_ANDROID_LOG_H

#ifdef __cplusplus
}
#endif
