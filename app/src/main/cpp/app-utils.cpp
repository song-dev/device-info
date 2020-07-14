//
// Created by 陈颂颂 on 2019/12/30.
//

#include "include/app-utils.h"
#include "include/utils.h"
#include <stdio.h>
#include <stdlib.h>
#include <zconf.h>

/**
 * 获取当前 app 包名
 * @param packageName
 * @return
 */
int getPackageName(char *packageName) {

    char path[64];
    sprintf(path, "/proc/%d/cmdline", getpid());

    FILE *f = NULL;
    f = fopen(path, "r");
    if (f == NULL) {
        return 1;
    }
    fgets(packageName, BUF_SIZE_64, f);
    fclose(f);
    return 0;

}

