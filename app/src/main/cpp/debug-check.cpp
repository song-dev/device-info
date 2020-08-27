//
// Created by 陈颂颂 on 2020/1/4.
//

#include "include/debug-check.h"
#include "include/utils.h"
#include "include/log.h"

#include <stdio.h>
#include <string.h>
#include <sys/ptrace.h>

/**
 * 获取 TracerPid
 * @return
 */
int getTracerPid() {
    FILE *f = NULL;
    char path[BUF_SIZE_64] = {0};
    sprintf(path, "/proc/%d/status", getpid());
    char line[BUF_SIZE_256] = {0};
    f = fopen(path, "r");
    while (fgets(line, BUF_SIZE_256, f)) {
        if (strstr(line, "TracerPid")) {
            LOGI("TracerPid line: %s", line);
            int statue = atoi(&line[10]);
            LOGW("TracerPid: %d", statue);
            return statue;
        }
    }
    return -1;
}

/**
 * /proc/net/tcp 文件检测
 * @return
 * @deprecated 失效
 */
int tcpCheck() {

    FILE *f = NULL;
    char path[BUF_SIZE_64] = "/proc/net/tcp";
    char line[BUF_SIZE_512];

    f = fopen(path, "r");
    while (fgets(line, BUF_SIZE_512, f)) {
        LOGI("TCP line: %s", line);
    }

    return 0;

}

/**
 * 括号后第一个字符为 t 则为调试状态
 * @return
 * @deprecated 失效
 */
int statCheck() {
    FILE *f = NULL;
    char path[BUF_SIZE_64];
    sprintf(path, "/proc/%d/stat", getpid());

    char line[BUF_SIZE_512];
    f = fopen(path, "r");
    while (fgets(line, BUF_SIZE_512, f)) {
        LOGI("stat line: %s", line);
    }

    return 0;
}

/**
 * wchan 内容为 ptrace_stop 则为调试状态
 * @return
 * @deprecated 失效
 */
int wchanCheck() {
    FILE *f = NULL;
    char path[BUF_SIZE_64];
    sprintf(path, "/proc/%d/wchan", getpid());

    char line[BUF_SIZE_512];
    f = fopen(path, "r");
    while (fgets(line, BUF_SIZE_512, f)) {
        LOGI("wchan line: %s", line);
    }

    return 0;
}

/**
 * 每个进程只能被一个进程 trace
 * @return
 * @deprecated 失效，只能在 fork 单独的进程中使用
 */
int ptraceCheck() {
    int trace = ptrace(PTRACE_TRACEME, 0, 0, 0);
    LOGW("ptrace: %d", trace);
    return trace;
}
