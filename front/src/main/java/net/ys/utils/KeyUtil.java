package net.ys.utils;

import java.io.InputStream;

/**
 * User: NMY
 * Date: 17-5-11
 */
public class KeyUtil {

    /**
     * 获取私钥
     *
     * @return
     */
    public static String getPrivateKey() {
        try {
            InputStream stream = KeyUtil.class.getClassLoader().getResourceAsStream("private.key");
            StringBuffer sb = new StringBuffer();
            int len;
            byte[] bytes = new byte[1024];
            while ((len = stream.read(bytes)) > 0) {
                sb.append(new String(bytes, 0, len) + "\n");
            }
            stream.close();
            return sb.toString();
        } catch (Exception e) {
        }
        return "";
    }
}
