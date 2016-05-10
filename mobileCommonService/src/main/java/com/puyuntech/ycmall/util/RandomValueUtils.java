package com.puyuntech.ycmall.util;

import java.util.Random;
import java.util.UUID;

/**
 * 生成随机数 工具类
 * <p/>
 * Created by 施长成 on 2015/10/9 0009.
 */
public class RandomValueUtils {
    private static char[] numbersAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyz").toCharArray();
    private static char[] numbers = ("0123456789").toCharArray();

    /**
     * 生成 字母+数组的随机数
     *
     * @param length
     *
     * @return
     */
    public static String randomStringValue(int length) {
        Random randGen = new Random();
        if (length < 1) {
            return null;
        }
        char[] randBuffer = new char[length];
        for (int i = 0; i < randBuffer.length; i++) {
            randBuffer[i] = numbersAndLetters[randGen.nextInt(36)];
        }
        return new String(randBuffer);
    }

    /**
     * 生成 纯数字的随机数
     *
     * @param length
     *
     * @return
     */
    public static String randomNumberValue(int length) {
        Random randGen = new Random();
        if (length < 1) {
            return null;
        }
        char[] randBuffer = new char[length];
        for (int i = 0; i < randBuffer.length; i++) {
            randBuffer[i] = numbers[randGen.nextInt(10)];
        }
        return new String(randBuffer);
    }
    public static String token() {
        String _str = UUID.randomUUID().toString();
        String uid = _str.replace("-", "");
        return uid;
    }
}
