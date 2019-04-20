package com.example.websocket;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileUtil {

    public static String read(String path) {
        File file = new File(path);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            StringBuilder sb = new StringBuilder();
            String line = "";
            while ((line = reader.readLine()) != null) {//逐行读取文件内容，不读取换行符和末尾的空格
                sb.append(line).append("\n");//将读取的字符串添加换行符后累加存放在缓存中
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static boolean deleteFile(String path) {
        if (path != null && path.length() != 0) {
            File file = new File(path);
            return deleteFile(file);
        }
        return false;
    }

    public static boolean deleteFile(File file) {
        if (file != null && file.exists()) {
            return file.delete();
        }
        return false;
    }
}
