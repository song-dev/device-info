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
 * 2. 检测失败（$unknown）
 * 检测多开, 若可访问规定目录则为正常，否则为多开环境
 * @return
 */
int moreOpenCheck() {

    // 判断是否支持ls命令
    if (exists("/system/bin/ls")) {

        char packageName[BUF_SIZE_64] = UNKNOWN;
        if (getPackageName(packageName) != 0) {
            return 2;
        }

        char path[BUF_SIZE_128];
        sprintf(path, "ls /data/data/%s", packageName);

        FILE *f = NULL;
        f = popen(path, "r");
        if (f != NULL) {
            char buff[BUF_SIZE_32];
            if (fgets(buff, BUF_SIZE_32, f)) {
                if (strlen(buff) != 0) {
                    pclose(f);
                    return 0;
                }
            }
            pclose(f);
            return 1;
        } else {
            LOGD("file pointer is null.");
            return 2;
        }

    } else {
        return 2;
    }

}
