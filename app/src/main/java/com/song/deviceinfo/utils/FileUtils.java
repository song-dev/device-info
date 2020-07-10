package com.song.deviceinfo.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by chensongsong on 2020/6/1.
 */
public class FileUtils {

    /**
     * 是否存在
     *
     * @param name
     * @return
     */
    public static boolean exists(String name) {
        return new File(name).exists();
    }

    public static String readFile(String name) {
        return readFile(new File(name));
    }

    public static String readFile(File file) {
        FileReader fileReader = null;
        BufferedReader reader = null;
        try {
            String line;
            StringBuffer sb = new StringBuffer();
            fileReader = new FileReader(file);
            reader = new BufferedReader(fileReader);
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append('\n');
            }
            if (sb.length() > 0) {
                sb.deleteCharAt(sb.length() - 1);
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
