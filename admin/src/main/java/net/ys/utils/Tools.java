package net.ys.utils;

import net.ys.constant.X;

import java.security.MessageDigest;
import java.util.Random;
import java.util.UUID;

/**
 * User: LiWenC
 * Date: 16-9-8
 */
public class Tools {

    private static Random rand;
    public static String KEY_PREFIX = "88_";

    static {
        rand = new Random();
    }

    /**
     * 获取指定长度随机字符串
     *
     * @param len
     * @return
     */
    public static String randomString(int len) {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, len);
    }

    /**
     * MD5加密
     *
     * @param key
     * @return
     */
    public static String genMD5(String key) {
        try {
            if (key == null || "".equals(key.trim())) {
                return "";
            }
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bs = md5.digest((KEY_PREFIX + key).getBytes(X.ENCODING.U));
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bs.length; i++) {
                sb.append(Character.forDigit((bs[i] >>> 4) & 0x0F, 16)).append(Character.forDigit(bs[i] & 0x0F, 16));
            }
            return sb.toString();
        } catch (Exception e) {
        }
        return "";
    }

    /**
     * 生成问号字符串
     *
     * @param size
     * @return
     */
    public static String genMark(int size) {
        StringBuffer sb = new StringBuffer("?");
        for (int i = 1; i <= size; i++) {
            sb.append(",?");
        }
        return sb.toString();
    }
}
