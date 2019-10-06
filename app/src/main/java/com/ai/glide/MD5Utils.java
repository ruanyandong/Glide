package com.ai.glide;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author -> miracle
 * @date -> 2019/10/2
 * @email -> ruanyandongai@gmail.com 729368173@qq.com
 * @telephone -> 18983790146
 * @blog -> https://ruanyandong.github.io
 */
public class MD5Utils {
    private static MessageDigest digest;

    static {
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    /**
     * MD5加密
     *
     * @param key
     * @return
     */
    public static String toMD5(String key) {
        if (digest == null) {
            return String.valueOf(key.hashCode());
        }
        // 更新字节
        digest.update(key.getBytes());
        // 获取最终摘要
        return convert2HexString(digest.digest());
    }

    /**
     * 转为16进制字符串
     *
     * @param digest
     * @return
     */
    private static String convert2HexString(byte[] digest) {
        StringBuffer sb = new StringBuffer();
        for (byte b : digest) {
            String hex = Integer.toHexString(0xFF & b);
            if (hex.length() == 1){
                sb.append("0");
            }
            sb.append(hex);
        }
        return sb.toString();
    }

}
