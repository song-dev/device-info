package com.song.deviceinfo.info;

import android.text.TextUtils;

import com.song.deviceinfo.model.beans.PartitionsBean;
import com.song.deviceinfo.utils.CommandUtils;
import com.song.deviceinfo.utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chensongsong on 2020/5/29.
 */
public class PartitionsInfo {

    public static List<PartitionsBean> getPartitionsInfo() {
        String[] lines = CommandUtils.exec("mount");
        Map<String, PartitionsBean> df = parseDf();
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
            String rw = "";
            if (!TextUtils.isEmpty(rws)) {
                if (rws.startsWith("(rw")) {
                    rw = "read-write";
                } else if (rws.startsWith("(ro")) {
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
            if (df.containsKey(mount)) {
                PartitionsBean partitionsBean = df.get(mount);
                bean.setRatio(partitionsBean.getRatio());
                bean.setUsed(partitionsBean.getUsed());
                bean.setSize(partitionsBean.getSize());
            }
            list.add(bean);
        }
        return list;
    }

    /**
     * 解析 df 数据
     *
     * @return
     */
    private static Map<String, PartitionsBean> parseDf() {
        String[] dfs = CommandUtils.exec("df -h");
        Map<String, PartitionsBean> map = new HashMap<>();
        for (String line : dfs) {
            if (line.startsWith("Filesystem")) {
                continue;
            }
            String[] args = line.split("\\s+");
            if (args.length < 5) {
                continue;
            }
            String path = args[0];
            String size = args[1];
            String used = args[2];
            String ratio = args[4];
            String mount = args[5];
            PartitionsBean bean = new PartitionsBean();
            bean.setPath(path);
            bean.setMount(mount);
            bean.setSize(size);
            bean.setUsed(used);
            int i = 0;
            try {
                i = Integer.parseInt(ratio.replace("%", ""));
            } catch (Exception e) {
                e.printStackTrace();
            }
            bean.setRatio(i);
            map.put(path, bean);
        }
        return map;
    }

}
