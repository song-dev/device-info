package com.song.deviceinfo.info;

import com.song.deviceinfo.model.beans.PartitionsBean;
import com.song.deviceinfo.utils.Constants;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by chensongsong on 2020/5/29.
 */
public class PartitionsInfo {

    public static List<PartitionsBean> getPartitionsInfo() {
        String[] lines = mountReader();
        List<PartitionsBean> list = new ArrayList<>();
        for (String line : lines) {
            String[] args = line.split(" ");
            if (args.length < 4) {
                continue;
            }
            String mount = args[0];
            String path = args[2];
            String type = args[4];
            String rws = args[5];
            rws = rws.replace("(", "").replace(")", "");
            String[] split = rws.split(",");
            String rw = "";
            if (split.length > 0) {
                if ("rw".equalsIgnoreCase(split[0])) {
                    rw = "read-write";
                } else if ("ro".equalsIgnoreCase(split[0])) {
                    rw = "read-only";
                } else {
                    rw = Constants.UNKNOWN;
                }
            }
            PartitionsBean bean = new PartitionsBean();
            bean.setPath(path);
            bean.setMount(mount);
            bean.setFs(type);
            bean.setMod(rw);
            list.add(bean);
        }
        return list;
    }

    /**
     * 读取分区信息
     *
     * @return
     */
    private static String[] mountReader() {
        InputStream inputstream = null;
        String allPaths = "";
        try {
            inputstream = Runtime.getRuntime().exec("mount").getInputStream();
            allPaths = new Scanner(inputstream).useDelimiter("\\A").next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allPaths.split("\n");
    }

}
