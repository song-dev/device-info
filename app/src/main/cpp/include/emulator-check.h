//
// Created by 陈颂颂 on 2019/12/27.
//

#ifndef SECURITY_CHECK_ANDROID_EMULATOR_CHECK_H
#define SECURITY_CHECK_ANDROID_EMULATOR_CHECK_H

#include <jni.h>

/**
 * 检测模拟器不存在的相关文件
 * @param env
 * @return
 */
jint specialFilesEmulatorcheck();

/**
 * 检测特殊目录，/sys/class/thermal/thermal_zoneX/temp(温度挂载文件)
 * @return 大于 0 为真机，等于 0 为模拟器
 */
jint thermalCheck();

///**
// * build 文件检测示例
// * @return
// */
//jboolean buildCheck();

/**
 * 蓝牙文件检测
 * @return
 */
jint bluetoothCheck();

///**
// * 获取当前
// * @return
// */
//int getApiVersion();

/**
 * 获取当前架构类型
 * @return
 */
int getArch();

/**
 * 从 maps 读取当前运行的架构
 * @param dst
 * @return
 */
int getMapsArch(char *dst);

#endif //SECURITY_CHECK_ANDROID_EMULATOR_CHECK_H
