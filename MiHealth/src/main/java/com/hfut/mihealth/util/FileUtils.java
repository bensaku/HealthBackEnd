package com.hfut.mihealth.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtils {

    /**
     * 创建文件夹
     */
    public static boolean createDirectory(String dirPath) {
        File dir = new File(dirPath);
        return dir.mkdirs();
    }

    /**
     * 检查文件是否存在
     */
    public static boolean checkFileExists(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }

    /**
     * 读取文件内容为字符串
     */
    public static String readFileContent(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }

    // 根据需求添加更多文件操作方法
}
