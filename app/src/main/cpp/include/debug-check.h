//
// Created by 陈颂颂 on 2020/1/4.
//

#ifndef SECURITY_CHECK_ANDROID_DEBUG_CHECK_H
#define SECURITY_CHECK_ANDROID_DEBUG_CHECK_H

/**
 * 检测 TracerPid 若不为 0 则为debug 状态
 * @return
 */
int getTracerPid();

int tcpCheck();

int statCheck();

int wchanCheck();

/**
 * 每个进程只能被一个进程 trace
 * @return
 */
int ptraceCheck();

#endif //SECURITY_CHECK_ANDROID_DEBUG_CHECK_H
