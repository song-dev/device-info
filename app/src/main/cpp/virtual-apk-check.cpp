//
// 多开 app 检测
// Created by 陈颂颂 on 2019/12/30.
//

#include <stdio.h>
#include "include/virtual-apk-check.h"
#include "include/utils.h"
#include "include/log.h"
#include "include/app-utils.h"
#include <string.h>
#include <errno.h>

/**
 * 0. 多开检测 false
 * 1. 多开检测 true
 * -1. 检测失败（$unknown）
 * 检测多开, 若可访问规定目录则为正常，否则为多开环境
 * @return
 */
int moreOpenCheck(JNIEnv *env, jobject context) {

    // 判断是否支持ls命令
    if (exists("/system/bin/ls")) {
        char path[BUF_SIZE_256] = {0};
        jstring packageName = getPackageName(env, context);
        if (packageName == NULL) {
            return -1;
        }
        const char *name = env->GetStringUTFChars(packageName, 0);
        sprintf(path, "ls /data/data/%s", name);
        LOGD("moreOpenCheck: path %s", path);
        FILE *f = NULL;
        f = popen(path, "r");
        if (f != NULL) {
            char buff[BUF_SIZE_32];
            if (fgets(buff, BUF_SIZE_32, f)) {
                if (strlen(buff) != 0) {
                    LOGD("moreOpenCheck: buff %s", buff);
                    pclose(f);
                    env->ReleaseStringUTFChars(packageName, name);
                    return 0;
                }
            }
            pclose(f);
            env->ReleaseStringUTFChars(packageName, name);
            return 1;
        } else {
            LOGD("file pointer is null.");
            env->ReleaseStringUTFChars(packageName, name);
            return -1;
        }
    } else {
        return -1;
    }

}
