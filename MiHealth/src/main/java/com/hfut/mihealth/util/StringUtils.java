package com.hfut.mihealth.util;

public class StringUtils {

    /**
     * 判断字符串是否为空或null
     */
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    /**
     * 安全获取字符串长度
     */
    public static int getSafeLength(String str) {
        return isNullOrEmpty(str) ? 0 : str.length();
    }

    /**
     * 首字母转大写
     */
    public static String capitalizeFirstLetter(String str) {
        if (isNullOrEmpty(str)) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    // 可以根据需要添加更多字符串处理方法
}
