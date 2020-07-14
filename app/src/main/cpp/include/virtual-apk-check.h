//
// Created by 陈颂颂 on 2019/12/30.
//

#ifndef SECURITY_CHECK_ANDROID_VIRTUAL_APK_CHECK_H
#define SECURITY_CHECK_ANDROID_VIRTUAL_APK_CHECK_H

#include <jni.h>

/**
 * 0. 多开检测 false
 * 1. 多开检测 true
 * 2. 检测失败（$unknown）
 * 检测多开
 * @return
 */
int moreOpenCheck(JNIEnv *env, jobject context);

#endif //SECURITY_CHECK_ANDROID_VIRTUAL_APK_CHECK_H
