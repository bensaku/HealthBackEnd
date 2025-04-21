package com.hfut.mihealth.util;

public class NumberUtils {

    /**
     * 将字符串安全转换为整数，如果转换失败则返回默认值
     */
    public static int toInt(String str, int defaultValue) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * 判断一个数字是否在指定范围内
     */
    public static boolean isWithinRange(int num, int min, int max) {
        return num >= min && num <= max;
    }

    // 根据需求添加更多数字处理方法
}