package com.deng.bitpic.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * @description: ID生成
 * @author: Deng
 * @create: 2019-02-12
 */
public class KeyUtil {

    /**
     * 生成修饰过的UUID
     * @return UUID
     */
    public static String randomUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 根据种子产生key（种子相同key相同）
     * 主要用于参数的跨controller安全传输
     * @param seed 种子
     * @return key
     */
    public static String getKey(String seed) {
        // seed 简单处理
        String str = seed + seed;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            return byte2Hex(md.digest());
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    /**
     * 字节数组转hex
     * @param b 字节数组
     * @return 摘要
     */
    private static String byte2Hex(byte[] b)
    {
        StringBuilder hex = new StringBuilder();
        String t;
        for (int n = 0; n < b.length; n ++) {
            t = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (t.length() == 1) {
                hex.append("0");
            }
            hex.append(t);
        }
        return hex.toString();
    }

}
